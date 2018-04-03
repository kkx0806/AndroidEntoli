package com.bluetea.entolidemo.ui.fragment.Intros;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bluetea.entolidemo.ui.interfaces.IntroPageButtonListener;
import com.bluetea.entolidemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Intro5Fragment extends Fragment implements View.OnClickListener {

    private TextView btnClose;
    public static IntroPageButtonListener buttonListener;

    public Intro5Fragment() {
        // Required empty public constructor
    }

    public static Intro5Fragment newInstance(IntroPageButtonListener callBack) {
        Intro5Fragment fragment = new Intro5Fragment();
        buttonListener = callBack;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_intro5, container, false);
        btnClose = (TextView) view.findViewById(R.id.tv_intro_5_close);

        btnClose.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        buttonListener.onCloseClicked();
    }
}
