package tn.agil.miniprojet.agil;

/**
 * Created by Linda on 25/10/2017.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AjoutClient extends AppCompatActivity  {

    private EditText cin;
    private EditText nom;
    private EditText prenom;
    private EditText tel;
    private Button btnajouter;
    RequestQueue requestQueue;


    String showUrl="http://192.168.1.2/WebService1/ajoutClient.php";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_client);
        cin = (EditText) findViewById(R.id.editCin);
        nom = (EditText) findViewById(R.id.editN);
        prenom = (EditText) findViewById(R.id.editP);
        tel = (EditText) findViewById(R.id.editT);
        requestQueue=Volley.newRequestQueue(this);
        btnajouter = (Button) findViewById(R.id.btnAjout);
        btnajouter.setOnClickListener(new OnClickListener());
        cin.setText(getIntent().getSerializableExtra("cin").toString());


    }



    private class OnClickListener implements View.OnClickListener
    {
        public void onClick (View v)
        {
            String cinStr= cin.getText().toString();
            String nomStr= nom.getText().toString();
            String prenomStr= prenom.getText().toString();
            String telStr= tel.getText().toString();


            switch (v.getId()){
                case R.id.btnAjout : {
                    if ((cinStr.isEmpty())||(nomStr.isEmpty())||(prenomStr.isEmpty())||(telStr.isEmpty()))
                    {
                        //Toast.makeText(AjoutClient.this,"Champs Vides !!",Toast.LENGTH_SHORT).show();
                        if (cinStr.isEmpty())
                            cin.setError("champs obligatoire");
                        if (nomStr.isEmpty())
                            nom.setError("champs obligatoire");
                        if (prenomStr.isEmpty())
                            prenom.setError("champs obligatoire");
                        if (telStr.isEmpty())
                            tel.setError("champs obligatoire");
                    }
                    else{

                        insert();


                    }}
            }
        }

        public void insert ( ){
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    showUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(AjoutClient.this,"valide",Toast.LENGTH_SHORT).show();
                    Intent i1= new Intent(getApplicationContext(),EnvoiSMS.class);
                    i1.putExtra("tel", tel.getText().toString());
                    startActivity(i1);

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AjoutClient.this,"invalide",Toast.LENGTH_SHORT).show();


                }
            }) {


                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parametres = new HashMap<String, String>();
                    parametres.put("cin",cin.getText().toString());
                    parametres.put("nom",nom.getText().toString());
                    parametres.put("prenom",prenom.getText().toString());
                    parametres.put("phone",tel.getText().toString());

                    return parametres;
                }

            };


            requestQueue.add(strReq);
        }
    }







}
