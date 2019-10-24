package com.example.android.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.android.bluetoothlegatt.R;



public class DialogMessage extends BaseDialog {
    TextView mTitleView;
    TextView mMessageView;

    DialogButtonCallback mCallback;
    boolean showCancel = false;

    public DialogMessage(@NonNull Context context, boolean includCancel) {
        super(context);
        showCancel = includCancel;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_message);

        mTitleView = findViewById(R.id.title_view);
        mMessageView = findViewById(R.id.message_view);

        mTitleView.setText(sTitle);
        mMessageView.setText(sMessage);

        findViewById(R.id.confirm_view).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DialogMessage.this.dismiss();
                if (mCallback != null) {
                    mCallback.onButtonClicked(1, -1);
                }
            }
        });
        int status = -1;
        if (showCancel) {
            status = 0;
        } else {
            status = 8;
        }
        findViewById(R.id.btn_divider).setVisibility(status);
        findViewById(R.id.cancel_view).setVisibility(status);
        findViewById(R.id.cancel_view).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dismiss();
                if (mCallback != null) {
                    mCallback.onButtonClicked(-1, -1);
                }
            }
        });
    }

    public void setButtonClickListener(DialogButtonCallback callback) {
        mCallback = callback;
    }
}
