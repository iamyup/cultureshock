package com.cultureshock.buskingbook.page;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.main.MainActivity;

public class PaperStickerFragment extends Fragment implements OnClickListener {
    private FragmentActivity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_paper_sticker, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();

        mContext.findViewById(R.id.paper_menu).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().showMenu();
            }
        });
        mContext.findViewById(R.id.paper_complete).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().replaceFragment(PaperDisplayFragment.class, null, false);
            }
        });
        mContext.findViewById(R.id.paper_btn_sticker).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.requestFocus();
            }
        });
        mContext.findViewById(R.id.paper_btn_handwriting).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.requestFocus();
            }
        });

        mContext.findViewById(R.id.paper_btn_sticker).requestFocus();

        Bundle bundle = getArguments();
        String string = bundle.getString("input");
        TextView textView = (TextView) mContext.findViewById(R.id.paper_string);
        textView.setText(string);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public synchronized void onClick(View v) {
        Log.d("CUTITOUT", "click");
    }
}
