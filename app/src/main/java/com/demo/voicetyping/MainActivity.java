package com.demo.voicetyping;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class MainActivity extends AppCompatActivity {

    ConstraintLayout rate_us_btn;
    ConstraintLayout share_btn;
    TextView start_btn;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);


        AdAdmob adAdmob = new AdAdmob( this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.banner), this);
        adAdmob.FullscreenAd_Counter(this);



        this.start_btn = (TextView) findViewById(R.id.start_btn);
        this.rate_us_btn = (ConstraintLayout) findViewById(R.id.rate_us);
        this.share_btn = (ConstraintLayout) findViewById(R.id.share);
        this.start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), TypingActivity.class));
            }
        });
        this.rate_us_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = MainActivity.this;
                mainActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
            }
        });
        this.share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.SUBJECT", MainActivity.this.getString(R.string.app_name));
                intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName());
                MainActivity.this.startActivity(Intent.createChooser(intent, "Share With"));
            }
        });

    }
}
