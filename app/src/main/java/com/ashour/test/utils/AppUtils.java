package com.ashour.test.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;
import java.util.Locale;

public class AppUtils {

    public static void setLanguage(String language, Activity from){
        Resources activityRes = from.getResources();
        Configuration activityConf = activityRes.getConfiguration();
        Locale newLocale = new Locale(language);
        activityConf.setLocale(newLocale);
        activityRes.updateConfiguration(activityConf, activityRes.getDisplayMetrics());

        Resources applicationRes = from.getApplicationContext().getResources();
        Configuration applicationConf = applicationRes.getConfiguration();
        applicationConf.setLocale(newLocale);
        applicationRes.updateConfiguration(applicationConf,
                applicationRes.getDisplayMetrics());
    }

    public static void setLanguageWithoutReload(String language, Activity from, Class to){
        Locale languageLocale = new Locale(language);
        Locale.setDefault(languageLocale);
        Configuration languageConfig = new Configuration();
        languageConfig.locale = languageLocale;
        from.getResources().updateConfiguration(languageConfig, from.getResources().getDisplayMetrics());
    }

    public static void longToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void openActivity(Context from, Class<?> to, Bundle bundle){
        Intent intent = new Intent(from, to);
        intent.putExtras(bundle);
        from.startActivity(intent);
    }

    public static void openActivity(Context from, Class<?> to){
        Intent intent = new Intent(from, to);
        from.startActivity(intent);
    }

    public static void openFragmentFromFragment(Fragment from, Fragment to, int layout, String stackName){
        from.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(layout, to).addToBackStack(stackName).commit();
    }

    public static void openFragmentFromActivity(AppCompatActivity from, Fragment to, int layout, String stackName){
        from.getSupportFragmentManager().beginTransaction().replace(layout, to).addToBackStack(stackName).commit();
    }

    public static void hideKeyboard(View view, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void saveInSharedPreference(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(AppTools.SHARED_PREFERENCE_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static void clearSharedPreference(Context context) {
        SharedPreferences settings = context.getSharedPreferences(AppTools.SHARED_PREFERENCE_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(AppTools.USER_DATA);
        editor.clear();
        editor.commit();
    }

    public static String getFromSharedPreference(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(AppTools.SHARED_PREFERENCE_TAG, Context.MODE_PRIVATE);
        return settings.getString(key, "");
    }

    public static void initVerticalRV(RecyclerView recyclerView, Context context, int spanCount) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, spanCount, LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
    }

    public static void initHorizontalRV(RecyclerView recyclerView, Context context, int spanCount) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, spanCount, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setNestedScrollingEnabled(false);
    }

    public static void shareUrl(String url, Context context){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = url;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static void getPermissions(Activity activity) {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.check(activity, permissions, null, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
            }
        });
    }
}
