package com.example.weatherapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class NavigationManager {
    public static void navigate(AppCompatActivity activity, int itemId) {
        if (itemId == R.id.menu_home && !isActivityOnTop(activity, MainActivity.class)) {
            // Handle Home navigation
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.overridePendingTransition(0, 0);
            activity.finish();
        } else if (itemId == R.id.menu_categories && !isActivityOnTop(activity, Categories.class)) {
            // Handle Categories navigation
            activity.startActivity(new Intent(activity, Categories.class));
            activity.overridePendingTransition(0, 0);
            activity.finish();
        }
        // Add more navigation logic for other menu items if needed
        else if (itemId == R.id.menu_helpline && !isActivityOnTop(activity, HelplineActivity.class)) {
            activity.startActivity(new Intent(activity, HelplineActivity.class));
            activity.overridePendingTransition(0, 0);
            activity.finish();
        }
    }

    private static boolean isActivityOnTop(AppCompatActivity activity, Class<?> clazz) {
        if (activity != null && !activity.isFinishing() && activity.getClass().equals(clazz)) {
            return true;
        }
        return false;
    }
}
