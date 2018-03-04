package com.lkp.numbercodeview.bottomsheet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.lkp.numbercodeview.BaseNumberCodeView;
import com.lkp.numbercodeview.R;


/**
 * Created by linkaipeng on 16/8/2.
 */
public class BottomSheetNumberCodeViewActivity extends AppCompatActivity
        implements BaseNumberCodeView.OnInputNumberCodeCallback,
        BottomSheetNumberCodeView.OnHideBottomLayoutListener {

    public static final int REQUEST_CODE_SHOW_BOTTOM_NUMBER_VIEW = 1001;
    public static final String KEY_DATA_NUMBER = "KeyDataNumber";
    private static final String KEY_DATA_IS_PASSWORD = "KeyDataIsPassword";
    private static final String KEY_DATA_CODE_VIEW_TITLE = "KEY_DATA_CODE_VIEW_TITLE";

    public static void show(Activity activity, boolean isPassword) {
        Intent intent = new Intent(activity, BottomSheetNumberCodeViewActivity.class);
        intent.putExtra(KEY_DATA_IS_PASSWORD, isPassword);
        activity.startActivityForResult(intent, REQUEST_CODE_SHOW_BOTTOM_NUMBER_VIEW);
    }

    public static void show(Activity activity, boolean isPassword, String title) {
        Intent intent = new Intent(activity, BottomSheetNumberCodeViewActivity.class);
        intent.putExtra(KEY_DATA_IS_PASSWORD, isPassword);
        intent.putExtra(KEY_DATA_CODE_VIEW_TITLE, title);
        activity.startActivityForResult(intent, REQUEST_CODE_SHOW_BOTTOM_NUMBER_VIEW);
    }

    private static BottomSheetNumberCodeView mNumberCodeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.push_bottom_to_top, 0);
        setContentView(R.layout.activity_bottom_sheet_number_code_view);
        initView();
    }

    private void initView() {
        boolean isPassword = getIntent().getBooleanExtra(KEY_DATA_IS_PASSWORD, true);
        String codeViewTitle = getIntent().getStringExtra(KEY_DATA_CODE_VIEW_TITLE);
        mNumberCodeView = (BottomSheetNumberCodeView) findViewById(R.id.bottom_sheet_number_code_view);
        mNumberCodeView.setNumberCodeCallback(this);
        mNumberCodeView.setOnHideBottomLayoutListener(this);
        mNumberCodeView.setIsPassword(isPassword);
        mNumberCodeView.showNumberCodeLayout();
        if (!TextUtils.isEmpty(codeViewTitle)) {
            mNumberCodeView.setCodeViewTitle(codeViewTitle);
        }
    }

    public static void setPassword(String password) {
        if (mNumberCodeView != null) {
            mNumberCodeView.setPassword(password);
        }
    }

    @Override
    public void onResult(String code) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_DATA_NUMBER, code);
        setResult(RESULT_OK, resultIntent);
    }

    @Override
    public void onHide() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (mNumberCodeView.isNumberCodeLayoutShowing()) {
            mNumberCodeView.hideNumberCodeLayout();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.push_top_to_bottom);
    }
}
