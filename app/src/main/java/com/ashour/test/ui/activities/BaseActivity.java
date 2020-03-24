package com.ashour.test.ui.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ashour.test.ConnectivityChangeReceiver;
import com.ashour.test.MyApplication;
import com.ashour.test.R;
import com.ashour.test.utils.AppUtils;
import com.google.gson.Gson;
import com.pd.chocobar.ChocoBar;

import java.util.List;

import butterknife.ButterKnife;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public abstract class BaseActivity extends AppCompatActivity{

    private ProgressDialog mProgressDialog;
    private static BaseActivity instance;
    protected Gson gson;
    private AlertDialog noConnectionDialog;
    private ConnectivityChangeReceiver connectivityChangeReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.setLanguageWithoutReload("ar", this, BaseActivity.class);
        AppUtils.setLanguage("ar",this);
        setContentView(getLayout());
        ButterKnife.bind(this);
        instance = this;
        gson = new Gson();
        initProgressDialog();
        initView();
        setupNetworkListener();
    }

    protected abstract int getLayout();

    protected abstract void initView();

    public static BaseActivity getInstance() {
        return instance;
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        mProgressDialog.setMessage(MyApplication.getContext().getResources().getString(R.string.label_loading));
        mProgressDialog.setCancelable(false);
    }

    private void setupNetworkListener() {
        connectivityChangeReceiver = new ConnectivityChangeReceiver(isConnected -> {
            if (isConnected) {
                if (noConnectionDialog != null) {
                    noConnectionDialog.dismiss();
                }
            } else {
                noInternetConnectionAvailable();
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectivityChangeReceiver, filter);
    }

    private void noInternetConnectionAvailable() {
        noConnectionDialog = showAlertDialog(getString(R.string.label_offline), getString(R.string.msg_no_connection), getString(R.string.label_wifi_settings), R.drawable.ic_no_internet,
                (dialog, which) -> {
                    switch (which) {
                        case BUTTON_POSITIVE:
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            dialog.cancel();
                            break;
                        case BUTTON_NEGATIVE:
                            dialog.cancel();
                            break;
                    }
                });
    }

    public AlertDialog showAlertDialog(String title, String msg, String buttonName, int icon, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialog);
        dialogBuilder.setTitle(title);
        if (icon == 0) {
            dialogBuilder.setIcon(icon);
        }
        dialogBuilder.setIcon(icon);
        dialogBuilder.setMessage(msg);
        dialogBuilder.setPositiveButton(buttonName, listener);
        dialogBuilder.setCancelable(false);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    protected void setProgressBar(boolean show) {
        if (show) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    protected void fillSpinner(List list, Spinner spinner) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.spinner_dropdown, list);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(arrayAdapter);
    }

    protected void showToastMessage(String message) {
        AppUtils.longToast(this, message);
    }

    protected void successToast(String message) {
        ChocoBar.builder().setActivity(this)
                .setText(message).setDuration(ChocoBar.LENGTH_LONG).green().show();
    }

    protected void errorToast(String message) {
        ChocoBar.builder().setActivity(this)
                .setText(message).setDuration(ChocoBar.LENGTH_LONG).red().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        instance = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectivityChangeReceiver);
    }
}
