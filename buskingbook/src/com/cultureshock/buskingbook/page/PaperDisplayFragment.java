package com.cultureshock.buskingbook.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.cultureshock.buskingbook.R;

public class PaperDisplayFragment extends Fragment implements OnClickListener{
    private FragmentActivity mContext;
    private static PaperDisplayFragment mInstance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_paper_display, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mInstance = this;
    }

    public static PaperDisplayFragment getInstance() {
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
