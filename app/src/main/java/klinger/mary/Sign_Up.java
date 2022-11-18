package klinger.mary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Sign_Up extends AppCompatActivity {

    Button signUpregistro, signUpinicio;
    ImageView loginBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpregistro = findViewById(R.id.signUpBtn);
        signUpinicio = findViewById(R.id.loginBtn);

        signUpinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_Up.this, Login.class);
                startActivity(intent);
            }
        });

    }


}