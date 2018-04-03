package com.bluetea.entolidemo.ui.fragment.Intros;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluetea.entolidemo.ui.interfaces.IntroPageButtonListener;
import com.bluetea.entolidemo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class Intro1Fragment extends Fragment implements View.OnClickListener {

    private int[] images = { };

    private Timer animationTimer = new Timer();
    private ImageView animeImageView;
    private TextView btnClose;
    private int imageIndex = 0;

    public static IntroPageButtonListener buttonListener;

    public Intro1Fragment() {
        // Required empty public constructor
    }

    public static Intro1Fragment newInstance(IntroPageButtonListener callBack) {
        Intro1Fragment fragment = new Intro1Fragment();
        buttonListener = callBack;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_intro1, container, false);
        animeImageView = (ImageView) view.findViewById(R.id.intro_1_imageview);
        btnClose = (TextView) view.findViewById(R.id.tv_intro_1_close);

        btnClose.setOnClickListener(this);
        onPageSelected();
        return view;
    }

    public void onPageSelected() {
        animationTimer = new Timer();
        imageIndex = 0;
        animationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                refreshImage();
            }
        }, 0, 100);
    }

    public void onPageUnSelected() {
        animationTimer.cancel();
    }

    private void refreshImage() {

        if (getActivity() == null) {
            return ;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (imageIndex < images.length) {
                    animeImageView.setImageResource(images[imageIndex]);
                    imageIndex++;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        buttonListener.onCloseClicked();
    }
}
