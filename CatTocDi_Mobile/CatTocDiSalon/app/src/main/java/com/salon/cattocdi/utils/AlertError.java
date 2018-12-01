package com.salon.cattocdi.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.salon.cattocdi.SignUpActivity;

public class AlertError {

    public static void showDialogLoginFail(Context context, String msg){
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Không thành công");
        dialog.setMessage(msg);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showDialog(Context context, String msg){
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Thông báo");
        dialog.setMessage(msg);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
