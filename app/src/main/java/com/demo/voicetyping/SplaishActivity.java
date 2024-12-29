package com.demo.voicetyping;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;


public class SplaishActivity extends AppCompatActivity {
    private static final int SPLAISH_TIMER = 1500;
    Animation animation;
    ImageView imageView;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splaish);
        this.imageView = (ImageView) findViewById(R.id.imageView);
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        this.animation = loadAnimation;
        this.imageView.setAnimation(loadAnimation);
        new Handler().postDelayed(new Runnable() { 
            @Override 
            public void run() {
                SplaishActivity.this.startActivity(new Intent(SplaishActivity.this.getApplicationContext(), MainActivity.class));
                SplaishActivity.this.finish();
            }
        }, 1500L);
    }
}
