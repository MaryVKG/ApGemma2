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

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_Up extends AppCompatActivity {

    Button signUpregistro, signUpinicio;
    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

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
        //Save data in FireBase on button click


        signUpregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("clientes");
                //Get all the values
                String name = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNo = regPhoneNo.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();
                UserHelperClass helperClass = new UserHelperClass (name, username, email, phoneNo, password);
                reference.child(phoneNo).setValue(helperClass);

            }
        });//Register Button method end
    }//onCreate Method End


    //validaciones

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();
        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        }
        else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("White Spaces are not allowed");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()) {
            regEmail.setError("Debes rellenar el recuadro.");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("El Email es incorrecto.");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();
        if (val.isEmpty()) {
            regPhoneNo.setError("Debes rellenar el recuadro.");
            return false;
        } else {
            regPhoneNo.setError(null);
            regPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            regPassword.setError("Debes rellenar el recuadro.");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("La contraseña es demasiado débil.");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }



    public void registerUser(View view){
            if(!validateName() |!validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername())
            {
                return;
            }
        Intent intent = new Intent(getApplicationContext(), Login.class);
        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().toString();
        String email = regEmail.getEditText().toString();
        String phoneNo = regPhoneNo.getEditText().toString();
        String password = regPassword.getEditText().toString();
        UserHelperClass helperClass = new UserHelperClass (name, username, email, phoneNo, password);
        reference.child(phoneNo).setValue(helperClass);
        startActivity(intent);


    }



    public void callLoginScreen(View view) {
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
}