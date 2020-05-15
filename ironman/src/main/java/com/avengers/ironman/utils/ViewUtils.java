package com.avengers.ironman.utils;

import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.AnyRes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewUtils {
    public static String getLayoutLevel(View view) {
        View targetView = view;
        List<String> list = new ArrayList<>();
        do {
            String name = targetView.getClass().getSimpleName();
            View parent = (View) targetView.getParent();
            if (parent instanceof ViewGroup) {
                int index = ((ViewGroup) parent).indexOfChild(targetView);
                name = name.replace("AppCompat", "");
                list.add(name + "[" + index + "]");
            }
            Object nextView = parent.getParent();
            if (nextView instanceof View) {
                targetView = parent;
            } else {
                break;
            }
        } while (true);
        Collections.reverse(list);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            builder.append(list.get(i));
            if (i != list.size() - 1) {
                builder.append("/");
            }
        }
        return builder.toString();
    }

    /**
     * 获取view id 要特别注意 返回的字段包含空格  做判断时一定要trim()
     *
     * @param view
     * @return
     */
    public static String getIdText(View view) {
        final int id = view.getId();
        StringBuilder out = new StringBuilder();
        if (id != View.NO_ID) {
            final Resources r = view.getResources();
            if (id > 0 && resourceHasPackage(id) && r != null) {
                try {
                    String pkgname;
                    switch (id & 0xff000000) {
                        case 0x7f000000:
                            pkgname = "app";
                            break;
                        case 0x01000000:
                            pkgname = "android";
                            break;
                        default:
                            pkgname = r.getResourcePackageName(id);
                            break;
                    }
                    String typename = r.getResourceTypeName(id);
                    String entryname = r.getResourceEntryName(id);
                    out.append(" ");
                    out.append(pkgname);
                    out.append(":");
                    out.append(typename);
                    out.append("/");
                    out.append(entryname);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return TextUtils.isEmpty(out.toString()) ? "" : out.toString();
    }

    private static boolean resourceHasPackage(@AnyRes int resid) {
        return (resid >>> 24) != 0;
    }
}
