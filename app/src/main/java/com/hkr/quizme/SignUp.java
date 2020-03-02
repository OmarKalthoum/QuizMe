package com.hkr.quizme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hkr.quizme.database_utils.entities.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import static com.hkr.quizme.LogIn.playQuizMeSound;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText firstName, lastName, email, password, confirmPassword;
    private Button signUoBtn;
    private ImageView addProfilePic, logoBtn;
    private Uri imageLink = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = findViewById(R.id.firstNameInput);
        lastName = findViewById(R.id.lastNameInput);
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);
        confirmPassword = findViewById(R.id.confirmPasswordInput);

        signUoBtn = findViewById(R.id.singUpBtn);
        addProfilePic = findViewById(R.id.addProfileImage);
        logoBtn = findViewById(R.id.logo_image_sign_up);


        signUoBtn.setOnClickListener(this);
        logoBtn.setOnClickListener(this);
        addProfilePic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink_anim);
        if (v == signUoBtn) {
            v.startAnimation(animation);
            User user = new User(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString());
            user.hashAndSetPassword(password.getText().toString());
            if (user.checkUniqueEmail(this) && user.register()) {
                Toast.makeText(this, "Registration was successful!", Toast.LENGTH_LONG).show();
                Intent logInIntent = new Intent(this, LogIn.class);
                if (imageLink != null) {
                    saveImageToTextFile();
                }
                startActivity(logInIntent);
            }
        }
        if (v == addProfilePic) {
            chooseProfileImage();

        }
        if (v == logoBtn) {
            //Shake the logo
            Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.shakeanimation);
            v.startAnimation(animation2);
            playQuizMeSound(this);
        }
    }


    public void chooseProfileImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(photoPickerIntent, "Select picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri photoUri = data.getData();
            imageLink = photoUri;

            if (photoUri != null) {
                try {
                    addProfilePic.setImageBitmap(getCorrectlyOrientedImage(this, photoUri));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static int getOrientation(Context context, Uri photoUri) {
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public static Bitmap getCorrectlyOrientedImage(Context context, Uri photoUri) throws IOException {
        InputStream is = context.getContentResolver().openInputStream(photoUri);
        BitmapFactory.Options dbo = new BitmapFactory.Options();
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        assert is != null;
        is.close();

        int rotatedWidth, rotatedHeight;
        int orientation = getOrientation(context, photoUri);

        if (orientation == 90 || orientation == 270) {
            rotatedWidth = dbo.outHeight;
            rotatedHeight = dbo.outWidth;
        } else {
            rotatedWidth = dbo.outWidth;
            rotatedHeight = dbo.outHeight;
        }

        Bitmap srcBitmap;
        is = context.getContentResolver().openInputStream(photoUri);
        if (rotatedWidth > 512 || rotatedHeight > 512) {
            float widthRatio = ((float) rotatedWidth) / ((float) 512);
            float heightRatio = ((float) rotatedHeight) / ((float) 512);
            float maxRatio = Math.max(widthRatio, heightRatio);

            // Create the bitmap from file
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = (int) maxRatio;
            srcBitmap = BitmapFactory.decodeStream(is, null, options);
        } else {
            srcBitmap = BitmapFactory.decodeStream(is);
        }
        assert is != null;
        is.close();

        if (orientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);

            srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
                    srcBitmap.getHeight(), matrix, true);
        }
        return srcBitmap;
    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    public void saveImageToTextFile() {
        File myFile = new File(Environment.getExternalStorageDirectory(), "profImage.txt");
        try {
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(getPath(imageLink));
            myOutWriter.close();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
