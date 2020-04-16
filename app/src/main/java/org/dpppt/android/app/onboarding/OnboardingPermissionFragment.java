/*
 * Created by Ubique Innovation AG
 * https://www.ubique.ch
 * Copyright (c) 2020. All rights reserved.
 */

package org.dpppt.android.app.onboarding;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.dpppt.android.app.R;
import org.dpppt.android.app.util.DeviceFeatureHelper;
import org.dpppt.android.app.util.InfoDialog;

public class OnboardingPermissionFragment extends Fragment {

	private static final int REQUEST_CODE_ASK_PERMISSION_FINE_LOCATION = 123;

	private Button locationButton;
	private Button batteryButton;
	private Button bluetoothButton;

	private BroadcastReceiver bluetoothStateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
				updateButtonStatus();
			}
		}
	};

	public static OnboardingPermissionFragment newInstance() {
		return new OnboardingPermissionFragment();
	}

	public OnboardingPermissionFragment() {
		super(R.layout.fragment_onboarding_permission);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
		requireActivity().registerReceiver(bluetoothStateReceiver, filter);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		locationButton = view.findViewById(R.id.onboarding_location_permission_button);
		locationButton.setOnClickListener(v -> {
			String[] permissions = new String[] { Manifest.permission.ACCESS_FINE_LOCATION };
			requestPermissions(permissions, REQUEST_CODE_ASK_PERMISSION_FINE_LOCATION);
		});

		View locationInfoButton = view.findViewById(R.id.onboarding_location_permission_info);
		locationInfoButton.setOnClickListener(v -> {
			InfoDialog.newInstance(R.string.onboarding_android_location_permission_info)
					.show(getChildFragmentManager(), InfoDialog.class.getCanonicalName());
		});

		batteryButton = view.findViewById(R.id.onboarding_battery_button);
		batteryButton.setOnClickListener(v -> {
			startActivity(new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
					Uri.parse("package:" + requireContext().getPackageName())));
		});

		View batteryInfoButton = view.findViewById(R.id.onboarding_battery_info);
		batteryInfoButton.setOnClickListener(v -> {
			InfoDialog.newInstance(R.string.onboarding_android_battery_saving_info)
					.show(getChildFragmentManager(), InfoDialog.class.getCanonicalName());
		});

		bluetoothButton = view.findViewById(R.id.onboarding_bluetooth_button);
		bluetoothButton.setOnClickListener(v -> {
			Toast.makeText(v.getContext(), getString(R.string.activate_bluetooth_button) + " ...", Toast.LENGTH_SHORT).show();
			BluetoothAdapter.getDefaultAdapter().enable();
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		updateButtonStatus();
	}

	private void updateButtonStatus() {
		boolean locationPermissionGranted = DeviceFeatureHelper.isLocationPermissionGranted(requireContext());
		if (locationPermissionGranted) {
			setButtonOk(locationButton, R.string.button_permission_location_granted_android);
		} else {
			setButtonDefault(locationButton, R.string.button_permission_location_android);
		}

		boolean batteryOptDeactivated = DeviceFeatureHelper.isBatteryOptimizationDeactivated(requireContext());
		if (batteryOptDeactivated) {
			setButtonOk(batteryButton, R.string.button_battery_optimization_deactivated);
		} else {
			setButtonDefault(batteryButton, R.string.button_battery_optimization);
		}

		boolean bluetoothEnabled = DeviceFeatureHelper.isBluetoothEnabled();
		if (bluetoothEnabled) {
			setButtonOk(bluetoothButton, R.string.bluetooth_activated_label);
		} else {
			setButtonDefault(bluetoothButton, R.string.activate_bluetooth_button);
		}

		((OnboardingActivity) requireActivity()).updateStepButton();
	}

	private void setButtonDefault(Button button, @StringRes int buttonLabel) {
		button.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		button.setTextColor(Color.WHITE);
		button.setText(buttonLabel);
		button.setClickable(true);
		button.setElevation(getResources().getDimensionPixelSize(R.dimen.button_elevation));
		button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.blue_main));
	}

	private void setButtonOk(Button button, @StringRes int grantedLabel) {
		button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check_circle, 0, 0, 0);
		button.setTextColor(getResources().getColor(R.color.green_main, requireActivity().getTheme()));
		button.setText(grantedLabel);
		button.setClickable(false);
		button.setElevation(0);
		button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		requireActivity().unregisterReceiver(bluetoothStateReceiver);
	}

}