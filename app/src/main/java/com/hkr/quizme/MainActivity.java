package com.hkr.quizme;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.hkr.quizme.global_data.CurrentUser;
import com.hkr.quizme.utils.Rankings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private ImageView profilPic;
    private TextView userName, rankTextView, levelInDigits;
    private ProgressBar levelBarMain;
    private AppBarConfiguration mAppBarConfiguration;
    private static int backCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        levelInDigits = findViewById(R.id.level_in_digits);
        profilPic = findViewById(R.id.profileImageMain);
        userName = findViewById(R.id.userNameMain);
        rankTextView = findViewById(R.id.RankTextView);
        levelBarMain = findViewById(R.id.levelBarMain);

        if (CurrentUser.getInstance().getUser() != null) {
            // String imageURL = getIntent().getStringExtra("profileImage");
            if (CurrentUser.getInstance().getUser().getImage() != null) {
                Glide.with(this).load(CurrentUser.getInstance().getUser().getImage()).into(profilPic);
            }
          /*  if (getIntent().getStringExtra("normalLogIN").equals("yes")) {
                String imagePath = getProfileImagePath();
                if (imagePath != null) {
                    profilPic.setImageURI(Uri.parse(imagePath));
                }
            }*/
            userName.setText(CurrentUser.getInstance().getUser().getFirstName() + " " + CurrentUser.getInstance().getUser().getLastName());
            Rankings rankings = new Rankings();
            rankTextView.setText(rankings.getRanking(CurrentUser.getInstance().getUser()).getName());
            levelBarMain.setProgress(rankings.getProgressPercent(CurrentUser.getInstance().getUser()));
            levelInDigits.setText(rankings.getProgressPercent(CurrentUser.getInstance().getUser()) + "%");
            System.out.println("RANKING:" + rankings.getProgressPercent(CurrentUser.getInstance().getUser()));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        backCounter++;
        if (backCounter >= 2) {
            backCounter = 0;
            this.finishAffinity();
        } else {

        }
    }

    public String getProfileImagePath() {
        String path = null;
        File myFile = new File(Environment.getExternalStorageDirectory(), "profImage.txt");
        if (myFile.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(myFile));
                String line;
                while ((line = br.readLine()) != null) {
                    path = line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return path;
    }

}
