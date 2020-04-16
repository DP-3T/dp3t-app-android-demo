/*
 * Created by Ubique Innovation AG
 * https://www.ubique.ch
 * Copyright (c) 2020. All rights reserved.
 */

package org.dpppt.android.app.onboarding;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import org.dpppt.android.app.R;

public class OnboardingContentFragment extends Fragment {

	private static final String ARG_RES_TITLE = "RES_TITLE";
	private static final String ARG_RES_DESCRIPTION = "RES_DESCRIPTION";
	private static final String ARG_RES_ILLUSTRATION = "RES_ILLUSTRATION";

	public static OnboardingContentFragment newInstance(@StringRes int title, @StringRes int description,
			@DrawableRes int illustration) {
		Bundle args = new Bundle();
		args.putInt(ARG_RES_TITLE, title);
		args.putInt(ARG_RES_DESCRIPTION, description);
		args.putInt(ARG_RES_ILLUSTRATION, illustration);
		OnboardingContentFragment fragment = new OnboardingContentFragment();
		fragment.setArguments(args);
		return fragment;
	}

	public OnboardingContentFragment() {
		super(R.layout.fragment_onboarding_content);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		Bundle args = requireArguments();
		if (args.containsKey(ARG_RES_TITLE)) {
			((TextView) view.findViewById(R.id.onboarding_title)).setText(args.getInt(ARG_RES_TITLE));
		}
		if (args.containsKey(ARG_RES_DESCRIPTION)) {
			((TextView) view.findViewById(R.id.onboarding_description)).setText(args.getInt(ARG_RES_DESCRIPTION));
		}
		if (args.containsKey(ARG_RES_ILLUSTRATION)) {
			((ImageView) view.findViewById(R.id.onboarding_illustration)).setImageResource(args.getInt(ARG_RES_ILLUSTRATION));
		}
	}

}
