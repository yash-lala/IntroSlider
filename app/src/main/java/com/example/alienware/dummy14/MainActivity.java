package com.example.alienware.dummy14;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_playagain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we have to set firsttimelaunch to true
                Checker checker = new Checker(getApplicationContext());
                checker.setFirst(true);
                //start IntroActivity
                startActivity(new Intent(MainActivity.this , IntroActivity.class));
                finish();
            }
        });
    }


}
