package com.example.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

public class BaseDialog extends Dialog {

    String sTitle = "";
    String sMessage = "";

    public BaseDialog(Context context) {
        super(context);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    public void setTitle(String title) {
        sTitle = title;
    }

    public void setMessage(String msg) {
        sMessage = msg;
    }
}
