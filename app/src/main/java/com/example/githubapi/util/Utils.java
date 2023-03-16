package com.example.githubapi.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.githubapi.BuildConfig;
import com.example.githubapi.R;

public class Utils {

    private static final String LANGUAGE_PYTHON = "Python";
    private static final String LANGUAGE_JAVA = "Java";
    private static final String LANGUAGE_JAVASCRIPT = "JavaScript";
    private static final String LANGUAGE_VUE = "Vue";
    private static final String LANGUAGE_SWIFT = "Swift";

    // Debug logs
    public static Drawable getLanguageColorTagDrawable(Context context, String language) {
        Drawable drawable;
        switch (language){
            case LANGUAGE_JAVA:
                drawable = ContextCompat.getDrawable(context,
                        R.drawable.repository_language_color_tag_brown);
                break;
            case LANGUAGE_JAVASCRIPT:
                drawable = ContextCompat.getDrawable(context,
                        R.drawable.repository_language_color_tag_yellow);
                break;
            case LANGUAGE_SWIFT:
                drawable = ContextCompat.getDrawable(context,
                        R.drawable.repository_language_color_tag_red);
                break;
            case LANGUAGE_VUE:
                drawable = ContextCompat.getDrawable(context,
                        R.drawable.repository_language_color_tag_blue);
                break;
            default:
                drawable = ContextCompat.getDrawable(context,
                        R.drawable.repository_language_color_tag_blue);
        }
        return drawable;
    }
}
