package com.example.android.dialog;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.bluetoothlegatt.R;


public class DialogList extends BaseDialog {
    String TAG = "DialogList_L";
    TextView mTitleView;
    ListView mListView;
    BaseAdapter mAdapter;
    DialogButtonCallback mCallback;

    public DialogList(Context context) {
        super(context);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_list);

        mTitleView = (TextView) findViewById(R.id.title_view);
        mTitleView.setText(sTitle);

        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mCallback != null) {
                    mCallback.onButtonClicked(2, i);
                }
            }
        });
        if (mAdapter != null) {
            mListView.setAdapter(mAdapter);
        }


        findViewById(R.id.cancel_view).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mCallback != null) {
                    mCallback.onButtonClicked(-1, -1);
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Point pt = new Point();
        getWindow().getWindowManager().getDefaultDisplay().getSize(pt);

        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(pt);

        int height = pt.y;
        ViewGroup.LayoutParams param = mListView.getLayoutParams();
        param.height = height / 2;
        mListView.setLayoutParams(param);

    }

    public void setButtonListener(DialogButtonCallback callback) {
        mCallback = callback;
    }


    public void setAdapter(BaseAdapter adapter) {
        mAdapter = adapter;
        if (mListView != null)
            mListView.setAdapter(mAdapter);
    }

    public void refresh() {
        if (mListView != null)
            mAdapter.notifyDataSetChanged();
    }
}
