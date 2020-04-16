/*
 * Created by Ubique Innovation AG
 * https://www.ubique.ch
 * Copyright (c) 2020. All rights reserved.
 */

package org.dpppt.android.app.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.dpppt.android.app.R;

public class PhoneUtil {

	public static void callHelpline(Context context) {
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:" + context.getString(R.string.tel_hotline)));
		if (intent.resolveActivity(context.getPackageManager()) != null) {
			context.startActivity(intent);
		}
	}

}
