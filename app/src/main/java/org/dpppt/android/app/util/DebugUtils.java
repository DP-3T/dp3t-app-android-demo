package org.dpppt.android.app.util;

import org.dpppt.android.app.BuildConfig;

public class DebugUtils {

	public static boolean isDev() {
		return BuildConfig.FLAVOR.equals("dev");
	}

}
