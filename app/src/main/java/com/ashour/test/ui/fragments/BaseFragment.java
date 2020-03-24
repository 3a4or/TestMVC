package com.ashour.test.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ashour.test.MyApplication;
import com.ashour.test.R;
import com.ashour.test.ui.activities.BaseActivity;
import com.ashour.test.utils.AppUtils;
import com.google.gson.Gson;
import com.pd.chocobar.ChocoBar;

import java.util.List;

public abstract class BaseFragment extends Fragment {

    private ProgressDialog mProgressDialog;
    protected Gson gson;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initProgressDialog();
        gson = new Gson();
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
        mProgressDialog.setMessage(MyApplication.getContext().getResources().getString(R.string.label_loading));
        mProgressDialog.setCancelable(false);
    }

    protected void setProgressBar(boolean show) {
        if (show) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    protected void fillSpinner(List list, Spinner spinner) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_dropdown, list);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(arrayAdapter);
    }

    protected void showToastMessage(String message) {
        AppUtils.longToast(getActivity(), message);
    }

    protected void successToast(String message) {
        ChocoBar.builder().setActivity(BaseActivity.getInstance())
                .setText(message).setDuration(ChocoBar.LENGTH_LONG).green().show();
    }

    protected void errorToast(String message) {
        ChocoBar.builder().setActivity(BaseActivity.getInstance())
                .setText(message).setDuration(ChocoBar.LENGTH_LONG).red().show();
    }

}
