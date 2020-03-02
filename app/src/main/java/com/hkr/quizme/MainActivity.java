package com.hkr.quizme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.hkr.quizme.global_data.CurrentUser;
import com.hkr.quizme.utils.Rankings;

import java.io.File;
import java.util.Objects;

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
            if (CurrentUser.getInstance().getUser().getImage() != null) {
                Glide.with(this).load(CurrentUser.getInstance().getUser().getImage()).into(profilPic);
            } else if (Objects.equals(getIntent().getStringExtra("normalLogIN"), "yes")) {
                //  getProfileImagePath();   //DOESNT WORK
            }
            userName.setText(CurrentUser.getInstance().getUser().getFirstName() + " " + CurrentUser.getInstance().getUser().getLastName());
            Rankings rankings = new Rankings();
            String ran = rankings.getRanking(CurrentUser.getInstance().getUser()).getName();
            rankTextView.setText(ran);
            if (ran.equalsIgnoreCase("noob") /*|| ran.equalsIgnoreCase("rookie")*/) {
                NavigationView navigationView = findViewById(R.id.nav_view);
                Menu menuNav = navigationView.getMenu();
                MenuItem createQuizBtn = menuNav.findItem(R.id.nav_create_quiz);
                createQuizBtn.setEnabled(false);
            }
            levelBarMain.setProgress(rankings.getProgressPercent(CurrentUser.getInstance().getUser()));
            levelInDigits.setText(rankings.getProgressPercent(CurrentUser.getInstance().getUser()) + "%");
            System.out.println("RANKING:" + rankings.getProgressPercent(CurrentUser.getInstance().getUser()));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_prev_result, R.id.nav_take_quiz, R.id.nav_create_quiz,
                R.id.nav_my_quizzes, R.id.nav_about_us)
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

    public void getProfileImagePath() {
        String path = Environment.getExternalStorageDirectory().toString();
        File imgFile = new File(path, "image.jpg");
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            profilPic.setImageBitmap(myBitmap);
        }

    }
}
