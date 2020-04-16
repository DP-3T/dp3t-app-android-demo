/*
 * Created by Ubique Innovation AG
 * https://www.ubique.ch
 * Copyright (c) 2020. All rights reserved.
 */

package org.dpppt.android.app.onboarding;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.dpppt.android.app.R;

public class OnboardingSlidePageAdapter extends FragmentStateAdapter {

	public static final int SCREEN_INDEX_PERMISSIONS = 3;

	public OnboardingSlidePageAdapter(FragmentActivity fragmentActivity) {
		super(fragmentActivity);
	}

	@NonNull
	@Override
	public Fragment createFragment(int position) {
		switch (position) {
			case 0:
				return OnboardingContentFragment.newInstance(R.string.onboarding_title_1, R.string.onboarding_desc_1, R.drawable.ill_isolation);
			case 1:
				return OnboardingContentFragment.newInstance(R.string.onboarding_title_2, R.string.onboarding_desc_2, R.drawable.ill_privacy);
			case 2:
				return OnboardingContentFragment.newInstance(R.string.onboarding_title_3, R.string.onboarding_desc_3, R.drawable.ill_distancing);
			case SCREEN_INDEX_PERMISSIONS:
				return OnboardingPermissionFragment.newInstance();
			case 4:
				return OnboardingContentFragment.newInstance(R.string.onboarding_title_5, R.string.onboarding_desc_5, R.drawable.ill_distancing);
		}
		throw new IllegalArgumentException("There is no fragment for view pager position " + position);
	}

	@Override
	public int getItemCount() {
		return 5;
	}

}
