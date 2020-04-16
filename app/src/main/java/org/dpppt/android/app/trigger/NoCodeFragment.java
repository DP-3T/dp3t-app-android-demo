/*
 * Created by Ubique Innovation AG
 * https://www.ubique.ch
 * Copyright (c) 2020. All rights reserved.
 */

package org.dpppt.android.app.trigger;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.dpppt.android.app.R;
import org.dpppt.android.app.util.PhoneUtil;

public class NoCodeFragment extends Fragment {

	public static NoCodeFragment newInstance() {
		return new NoCodeFragment();
	}

	public NoCodeFragment() {
		super(R.layout.fragment_no_code);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		view.findViewById(R.id.no_code_fragment_cancel).setOnClickListener(v -> getActivity().onBackPressed());
		view.findViewById(R.id.no_code_fragment_support_tel).setOnClickListener(v -> PhoneUtil.callHelpline(getContext()));
	}

}
