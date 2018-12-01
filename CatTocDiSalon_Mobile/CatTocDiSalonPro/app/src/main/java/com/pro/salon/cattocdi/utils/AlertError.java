package com.pro.salon.cattocdi.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.pro.salon.cattocdi.LoginActivity;

public class AlertError {
    public static void showDialogLoginFail(Context context, String text){
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Không thành công");
        dialog.setMessage(text);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public static void showDialofSuccess(Context context, String text){
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Thành công");
        dialog.setMessage(text);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
