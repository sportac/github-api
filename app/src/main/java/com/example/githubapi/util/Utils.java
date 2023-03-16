package com.example.githubapi.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.githubapi.BuildConfig;
import com.example.githubapi.R;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    private static final String LANGUAGE_PYTHON = "Python";
    private static final String LANGUAGE_JAVA = "Java";
    private static final String LANGUAGE_JAVASCRIPT = "JavaScript";
    private static final String LANGUAGE_VUE = "Vue";
    private static final String LANGUAGE_SWIFT = "Swift";

    /**
     * @brief Returns a drawable with its right color based on the input programming language.
     */
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

    /**
     * @brief Returns a string with the message of how long has passed since the repo was last
     * updated.
     */
    public static String getLastUpdatedString(String dateString){

        // Remove 'T' and 'Z' characters from the input date string
        dateString = dateString.replace("T", " ").replace("Z", "");

        // Parse the input date string into a LocalDateTime object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);

        // Calculate the time difference between the input date and the current date
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration duration = Duration.between(dateTime, currentDateTime);

        if (duration.toDays() > 365) {
            long years = duration.toDays() / 365;
            return "Updated " + years + (years > 1 ? " years ago" : " year ago");
        } else if (duration.toDays() > 30) {
            long months = duration.toDays() / 30;
            return "Updated " + months + (months > 1 ? " months ago" : " month ago");
        } else if (duration.toDays() > 7) {
            long weeks = duration.toDays() / 7;
            return "Updated " + weeks + (weeks > 1 ? " weeks ago" : " week ago");
        } else if (duration.toDays() > 0) {
            return "Updated " + duration.toDays() + (duration.toDays() > 1 ? " days ago" : " day ago");
        } else if (duration.toHours() > 0) {
            return "Updated " + duration.toHours() + (duration.toHours() > 1 ? " hours ago" : " hour ago");
        } else {
            return "Updated " + duration.toMinutes() + (duration.toMinutes() > 1 ? " mins ago" : " min ago");
        }
    }



}
