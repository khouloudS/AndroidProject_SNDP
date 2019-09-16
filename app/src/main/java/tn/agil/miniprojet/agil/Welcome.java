package tn.agil.miniprojet.agil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity implements View.OnClickListener {
Button btngo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button btngo= (Button) findViewById(R.id.btngo);
        btngo.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent i1= new Intent(getApplicationContext(),Authentification.class);
        startActivity(i1);

    }
}
