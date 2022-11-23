package klinger.mary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Sign_Up extends AppCompatActivity {

    Button signUpregistro, signUpinicio;
    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;

    FirebaseDatabase rootNode;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    FirebaseUser mUser;

    Vibrator vibrator;

    String userID;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Hooks to all xml elements in activity_sign_up.xml
        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);
        signUpregistro = findViewById(R.id.signUpBtn);
        signUpinicio = findViewById(R.id.loginBtn);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        //vibrador
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        //Save data in FireBase on button click
        signUpinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });

        signUpregistro.setOnClickListener(view -> {
            userRegister();

        });
    }
       //onCreate Method End

    private void openLoginActivity() {

        Intent intent = new Intent(getApplicationContext(), Login.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.loginBtn), "button_tran");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Sign_Up.this, pairs);
            startActivity(intent,options.toBundle());
        }else {
            startActivity(intent);
        }

    }

    public void userRegister(){
        String nombre = regName .getEditText().getText().toString();
        String usuario = regUsername.getEditText().getText().toString();
        String correo = regEmail.getEditText().getText().toString();
        String telefono = regPhoneNo.getEditText().getText().toString();
        String contrasena = regPassword.getEditText().getText().toString();


        if(TextUtils.isEmpty(nombre)){
            regName.setError("Ingrese su nombre completo.");
            regName.requestFocus();
        } else if(TextUtils.isEmpty(usuario)) {
            regUsername.setError("Ingrese su nombre de usuario.");
            regUsername.requestFocus();
        } else if(TextUtils.isEmpty(correo)) {
            regEmail.setError("Ingrese su correo electrónico.");
            regEmail.requestFocus();
        } else if(TextUtils.isEmpty(telefono)) {
            regPhoneNo.setError("Ingrese su número celular.");
            regPhoneNo.requestFocus();
        }else if(TextUtils.isEmpty(contrasena)) {
            regPassword.setError("Ingrese su contraseña.");
            regPassword.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = db.collection("users").document(userID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("Nombre Completo", regName);
                        user.put("Usuario", regUsername);
                        user.put("Correo Eléctronico", regEmail);
                        user.put("Teléfono", regPhoneNo);
                        user.put("Contraseña", regPassword);

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "EXITOSO, EL USUARIO SE HA REGISTRADO CON ÉXITO" +userID);
                            }
                        });
                        Toast.makeText(Sign_Up.this, "Usuario registrado con exito ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Sign_Up.this, Login.class));
                    }else{
                        Toast.makeText(Sign_Up.this, "EL usuario no se ha registrado con éxito"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }
            });
        }


    }

    //validaciones



}