package com.airwhip.circle.tips.getters;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by Whiplash on 05.03.14.
 */
public class ApplicationInformation {

    public static StringBuilder get(Context context) {
        StringBuilder result = new StringBuilder();

        final PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            result.append(packageInfo.packageName + "\n");
        }

        return result;
    }

}
