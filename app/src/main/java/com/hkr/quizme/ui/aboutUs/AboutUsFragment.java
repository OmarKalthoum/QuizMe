package com.hkr.quizme.ui.aboutUs;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hkr.quizme.R;

public class AboutUsFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_about_us, container, false);

        final TextView textView = root.findViewById(R.id.aboutUsText);
        final ImageView imageView = root.findViewById(R.id.logoAboutUs);

        textView.setText("Quiz me is an application that had been developed by four students at Kristianstad University\n\n" +
                "The main purpose of this application is to help other software development students to be prepared before the exam. " +
                "The application contains many quizzes but the student also has the ability to create her own quiz" +
                "\n\nFor more information please contact us:");

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.shakeanimation);
                imageView.startAnimation(animation2);
                playQuizMeSound(getContext());
            }
        });


        return root;
    }

    public static void playQuizMeSound(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.quiz_me);
        mediaPlayer.start();
    }
}