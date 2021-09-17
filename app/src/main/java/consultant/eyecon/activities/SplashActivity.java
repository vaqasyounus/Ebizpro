package consultant.eyecon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import consultant.eyecon.R;

import consultant.eyecon.models.AppConstants;
import consultant.eyecon.utils.Utilities;
import me.wangyuwei.particleview.ParticleView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ParticleView particleView = (ParticleView) findViewById(R.id.pv);
        particleView.startAnim();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        particleView.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                String isLogin = Utilities.readFromSharedPrefrence(getApplicationContext(), AppConstants.KEY_LOGIN);
                String isFirstRun = Utilities.readFromSharedPrefrence(getApplicationContext(), AppConstants.KEY_FIRST_RUN);
              /*  if (!isFirstRun.equals("1")) {
                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                } else {*/
                    /*if (isLogin.equalsIgnoreCase("true")) {
                        startActivity(new Intent(SplashActivity.this, ReportActivity.class));
                    } else {*/
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                    }
//                }
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
