package com.cultureshock.buskingbook.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.main.MainActivity;

public class PaperEditFragment extends Fragment implements OnClickListener{
    private FragmentActivity mContext;
    private static PaperEditFragment mInstance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_paper_edit, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mInstance = this;

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
        mContext.findViewById(R.id.paper_btn_sticker).requestFocus();
    }

    public static PaperEditFragment getInstance() {
        return mInstance;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public synchronized void onClick(View v) {
    }
}
