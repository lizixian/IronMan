package com.avengers.ironman.utils;

import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {
    public static String getLayoutLevel(View view) {
        View targetView = view;
        StringBuilder path = new StringBuilder("");
        do {
            String name = targetView.getClass().getSimpleName();
            View parent = (View) targetView.getParent();
            if (parent instanceof ViewGroup) {
                int index = ((ViewGroup) parent).indexOfChild(targetView);
                path.append(name).append("[").append(index).append("]").append("/");
            }
            Object nextView = parent.getParent();
            if (nextView instanceof View) {
                targetView = parent;
            } else {
                break;
            }
        } while (true);
        return path.toString();
    }
}
