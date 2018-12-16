package com.nishat.vconfess.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nishat.vconfess.R;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_FIRST_START = "KEY_FIRST_START";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = sharedPreferences.edit();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isFirstStart = sharedPreferences.getBoolean(KEY_FIRST_START, true);
                if(isFirstStart){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(MainActivity.this, IntroActivity.class));
                            finish();
                        }
                    });
                    editor.putBoolean(KEY_FIRST_START, false);
                    editor.commit();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(MainActivity.this, LoginTypeSelectActivity.class));
                            finish();
                        }
                    });
                }
            }
        });

        thread.start();
    }
}
