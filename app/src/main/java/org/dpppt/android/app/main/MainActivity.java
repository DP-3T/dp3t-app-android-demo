/*
 * Created by Ubique Innovation AG
 * https://www.ubique.ch
 * Copyright (c) 2020. All rights reserved.
 */

package org.dpppt.android.app.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import org.dpppt.android.app.R;
import org.dpppt.android.app.onboarding.OnboardingActivity;

public class MainActivity extends FragmentActivity {

	private static final String PREFS_COVID = "PREFS_COVID";
	private static final String PREF_KEY_ONBOARDING_COMPLETED = "PREF_KEY_ONBOARDING_COMPLETED";

	private static final int REQ_ONBOARDING = 123;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			SharedPreferences preferences = getSharedPreferences(PREFS_COVID, MODE_PRIVATE);
			boolean onboardingCompleted = preferences.getBoolean(PREF_KEY_ONBOARDING_COMPLETED, false);

			if (onboardingCompleted) {
				showMainFragment();
			} else {
				startActivityForResult(new Intent(this, OnboardingActivity.class), REQ_ONBOARDING);
			}
		}
	}

	public void showMainFragment() {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_fragment_container, MainFragment.newInstance())
				.commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_ONBOARDING) {
			if (resultCode == RESULT_OK) {
				SharedPreferences preferences = getSharedPreferences(PREFS_COVID, MODE_PRIVATE);
				preferences.edit().putBoolean(PREF_KEY_ONBOARDING_COMPLETED, true).apply();
				showMainFragment();
			} else {
				finish();
			}
		}
	}


	@Override
	public void onBackPressed() {
		FragmentManager fm = getSupportFragmentManager();
		for (Fragment frag : fm.getFragments()) {
			if (frag.isVisible()) {
				FragmentManager childFm = frag.getChildFragmentManager();
				if (childFm.getBackStackEntryCount() > 0) {
					childFm.popBackStack();
					return;
				}
			}
		}
		super.onBackPressed();
	}

}
