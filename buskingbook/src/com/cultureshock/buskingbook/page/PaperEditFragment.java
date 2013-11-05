package com.cultureshock.buskingbook.page;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.main.MainActivity;

public class PaperEditFragment extends Fragment {
    private FragmentActivity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_paper_edit, container, false);
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
        mContext.findViewById(R.id.paper_next).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                EditText edit = (EditText) mContext.findViewById(R.id.paper_string);
                String input = edit.getText().toString();
                if (input.isEmpty() == true) {
                    Toast.makeText(mContext, getResources().getString(R.string.please_input), Toast.LENGTH_SHORT).show();
                } else {
                    Bundle o = new Bundle();
                    o.putString("input", input);
                    MainActivity.getInstance().replaceFragment(PaperStickerFragment.class, o, false);
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            View view = mContext.getCurrentFocus();
            if (view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
