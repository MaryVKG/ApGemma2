package klinger.mary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;
    //Acá se capturan las variables
    Animation topAnim, botonAnim;
    ImageView chef;
    TextView miNombre;

    SharedPreferences onBoardingScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Acá se capturan las variables
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        botonAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        chef = findViewById(R.id.logoGemma2);
        miNombre = findViewById(R.id.ccMyName);

        chef.setAnimation(topAnim);
        miNombre.setAnimation(botonAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);

                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime",true);

                if(isFirstTime){

                    SharedPreferences.Editor editor = onBoardingScreen.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), OnBoarding.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                    finish();
                }


            }
        },SPLASH_SCREEN);


    }
}