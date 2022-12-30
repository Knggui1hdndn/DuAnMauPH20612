package khangndph20612.fpoly.pnlib;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class ManHinhCho extends AppCompatActivity {
    LottieAnimationView view;
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_cho);
        view = findViewById(R.id.a);
        t = findViewById(R.id.t);
        ;

        view.setAnimation(R.raw.cho);

        view.playAnimation();
        t.startAnimation(AnimationUtils.loadAnimation(this, R.anim.a));
        SharedPreferences preferences = getSharedPreferences("admin", MODE_PRIVATE);
        if (preferences.getString("tk", null) == null) {
            preferences.edit().putString("tk", "admin").apply();
            preferences.edit().putString("mk", "admin").apply();

        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManHinhCho.this, MainActivity.class));
                overridePendingTransition(R.anim.batdau1, R.anim.ketthucfrag);
            }
        });

    }
}