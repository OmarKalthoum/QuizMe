package com.hkr.quizme;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.hkr.quizme.global_data.CurrentUser;
import com.hkr.quizme.utils.Ranking;
import com.hkr.quizme.utils.Rankings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ImageView profilPic;
    private TextView userName, levelTxt;
    private ProgressBar levelBarMain;
    private AppBarConfiguration mAppBarConfiguration;
    private static int backCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profilPic = findViewById(R.id.profileImageMain);
        userName = findViewById(R.id.userNameMain);
        levelTxt = findViewById(R.id.levelTxt);
        levelBarMain = findViewById(R.id.levelBarMain);

        if (getIntent().getStringExtra("fb") != null) {
            //The user logged in with Fb
            //TODO: Save the info into data base
            String firstName = getIntent().getStringExtra("firstName");
            String lastName = getIntent().getStringExtra("lastName");
            String imageURL = getIntent().getStringExtra("profileImage");
            String email = getIntent().getStringExtra("email");
            String id = getIntent().getStringExtra("id");

            Glide.with(this).load(imageURL).into(profilPic);
            userName.setText(firstName + " " + lastName);
        } else if (CurrentUser.getInstance().getUser() != null) {
            userName.setText(CurrentUser.getInstance().getUser().getDisplayName());
            Rankings rankings = new Rankings();
            levelTxt.setText(rankings.getRanking(CurrentUser.getInstance().getUser()).getName());
            levelBarMain.setProgress(rankings.getProgressPercent(CurrentUser.getInstance().getUser()));
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

}
