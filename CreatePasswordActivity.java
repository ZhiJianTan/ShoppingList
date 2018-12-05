package com.example.zaphk.shoppinglist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreatePasswordActivity extends AppCompatActivity {

    EditText enterPass1, enterPass2;
    Button b_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        enterPass1 = (EditText) findViewById(R.id.pass_field1);
        enterPass2 = (EditText) findViewById(R.id.pass_field2);
        b_confirm = (Button) findViewById(R.id.b_confirm);


        b_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text1 = enterPass1.getText().toString();
                String text2 = enterPass2.getText().toString();

                if(text1.isEmpty()|| text2.isEmpty()){
                    //there is no password entered
                    Toast.makeText(CreatePasswordActivity.this,"No Password entered!",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(text1.equals(text2)){
                        //save the password to shared preference
                        SharedPreferences settings = getSharedPreferences("PREFS",0);
                        SharedPreferences.Editor editor= settings.edit();
                        editor.putString("password", text1);
                        editor.apply();

                        //Enter mainactivity
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else {
                        //Two password entered does not match
                        Toast.makeText(CreatePasswordActivity.this,"Password does not match!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
