package com.example.android.dialog;

public interface DialogButtonCallback {
    public static final int TYPE_CONFIRMCLICK = 1;

    public static final int TYPE_CANCELCLICK = -1;

    public static final int TYPE_LIST_ITEMCLICK = 2;

    void onButtonClicked(int paramInt1, int paramInt2);
}
