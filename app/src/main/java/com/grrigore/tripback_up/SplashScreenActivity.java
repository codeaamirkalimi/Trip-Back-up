package com.grrigore.tripback_up;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity {

    //todo move into constants class
    public static final int ANIMATION_OFFSET = 60;

    @BindView(R.id.ivProgress)
    ImageView ivProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ButterKnife.bind(this);

        //hide toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        imageTranslation();
        new SplashScreenAsyncTask().execute();

    }

    private class SplashScreenAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void imageTranslation() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float width = displayMetrics.widthPixels;

        float xCurrentPos, yCurrentPos;
        xCurrentPos = ivProgress.getLeft();
        yCurrentPos = ivProgress.getTop();

        Animation animation = new TranslateAnimation(xCurrentPos - ANIMATION_OFFSET, xCurrentPos + width, yCurrentPos, yCurrentPos);
        animation.setDuration(5000);
        animation.setFillAfter(true);
        animation.setFillBefore(true);
        ivProgress.startAnimation(animation);
    }
}
