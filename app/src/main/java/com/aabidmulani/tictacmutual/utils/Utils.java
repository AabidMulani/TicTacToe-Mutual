package com.aabidmulani.tictacmutual.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import timber.log.Timber;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Utils {

    private static ProgressDialog progressDialog;


    public static void showProgressBar(Activity activity, String message, boolean isCancellable,
                                       DialogInterface.OnCancelListener onCancelListener) {
        if (!isProgressShowing()) {
            progressDialog = ProgressDialog.show(activity, null, message, true, isCancellable,
                    onCancelListener);
        } else {
            updateProgressMsg(message);
        }
    }


    private static boolean isProgressShowing() {
        if (progressDialog == null) {
            return false;
        }
        return progressDialog.isShowing();
    }

    private static void updateProgressMsg(String msg) {
        if (progressDialog != null) {
            progressDialog.setMessage(msg);
        }
    }

    public static void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    public static void hideKeyboardSoft(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Timber.i(Log.getStackTraceString(e));
        }
    }


    public static void hideKeyboardSoft(Activity activity, View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            Timber.i(Log.getStackTraceString(e));
        }
    }

    public static Typeface getThisFont(Context context, int textStyleIndex) {
        final int FONT_REGULAR = 1;
        final int FONT_LIGHT = 2;
        final int FONT_MEDIUM = 3;
        final int FONT_MEDIUM_ITALIC = 4;

        Typeface typeface;
        switch (textStyleIndex) {
            case FONT_REGULAR:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
                break;
            case FONT_LIGHT:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
                break;
            case FONT_MEDIUM:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
                break;
            case FONT_MEDIUM_ITALIC:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-MediumItalic.ttf");
                break;
            default:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
                break;
        }
        return typeface;
    }

    public static void showThisMsg(Context activity, String title, String message, DialogInterface.OnClickListener
            onOkClickListener, DialogInterface.OnClickListener onCancelClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (title != null) {
            builder.setTitle(title);
        } else {
            builder.setTitle(null);
        }
        builder.setMessage(message);
        builder.setPositiveButton(activity.getString(android.R.string.ok), onOkClickListener);
        if (onCancelClickListener != null) {
            builder.setNegativeButton(activity.getString(android.R.string.cancel), onCancelClickListener);
        }
        builder.setCancelable(false);
        AppCompatDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    public static boolean checkInternetConnection(Context mContext) {
        boolean retVal = false;
        try {
            ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            retVal = conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr
                    .getActiveNetworkInfo().isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }


    public static void showLongToast(Activity activity, String msg) {
        try {
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Timber.e(Log.getStackTraceString(ex));
        }
    }

    public static void showShortToast(Activity activity, String msg) {
        try {
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Timber.e(Log.getStackTraceString(ex));
        }
    }


    @SuppressLint("DefaultLocale")
    public static String formatAmountToString(Double price) {
        return price == null ? null : String.format("$ %.2f", price);
    }

    @SuppressLint("DefaultLocale")
    public static String formatAmountToString(Long price) {
        return price == null ? null : String.format("$ %.2f", price);
    }

}
