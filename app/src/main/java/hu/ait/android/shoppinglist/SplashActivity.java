package hu.ait.android.shoppinglist;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,
                        MainActivity.class));

                finish();
            }
        },3000);

        //Translate the cart across the splash screen animation.
        final Animation moveCartAnimation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.anim_move_cart);

        final ImageView myCartLogo = (ImageView) findViewById(R.id.myCartLogo);
        Animation moveCartAnim = AnimationUtils.loadAnimation(
                SplashActivity.this, R.anim.anim_move_cart);

        moveCartAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        myCartLogo.startAnimation(moveCartAnimation);
    }
}