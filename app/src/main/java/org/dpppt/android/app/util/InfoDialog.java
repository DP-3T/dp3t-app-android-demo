/*
 * Created by Ubique Innovation AG
 * https://www.ubique.ch
 * Copyright (c) 2020. All rights reserved.
 */

package org.dpppt.android.app.util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.DialogFragment;

import org.dpppt.android.app.R;

public class InfoDialog extends DialogFragment {

	private static final String ARG_TEXT_ID = "arg_text_id";
	private static final String ARG_TEXT_STRING = "arg_text_string";

	public static InfoDialog newInstance(@StringRes int text) {
		Bundle args = new Bundle();
		args.putInt(ARG_TEXT_ID, text);
		InfoDialog fragment = new InfoDialog();
		fragment.setArguments(args);
		return fragment;
	}

	public static InfoDialog newInstance(String text) {
		Bundle args = new Bundle();
		args.putString(ARG_TEXT_STRING, text);
		InfoDialog fragment = new InfoDialog();
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.dialog_info, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Bundle args = getArguments();

		TextView textView = view.findViewById(R.id.dialog_info_text);
		if (args.containsKey(ARG_TEXT_ID)) {
			textView.setText(args.getInt(ARG_TEXT_ID));
		} else {
			textView.setText(args.getString(ARG_TEXT_STRING));
		}
		view.findViewById(R.id.dialog_info_button).setOnClickListener(v -> getDialog().dismiss());
	}

	@Override
	public void onStart() {
		super.onStart();
		getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
	}

}
