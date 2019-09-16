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
import android.widget.TextView;
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

public class EnvoiSMS extends AppCompatActivity   {

    private EditText tel;
    private Button btnaenvoi;
    RequestQueue requestQueue;


    String showUrl="http://192.168.1.2/WebService1/envoisms.php";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoi_sms);
        tel = (EditText) findViewById(R.id.numClient);
        requestQueue=Volley.newRequestQueue(this);
        btnaenvoi = (Button) findViewById(R.id.btnenvoi);
        btnaenvoi.setOnClickListener(new OnClickListener());


        Bundle extra = getIntent().getExtras();
        String val = extra.getString("tel");
        tel.setText(val);

    }



    private class OnClickListener implements View.OnClickListener
    {
        public void onClick (View v)
        {
                        insert();



            }
        }

        public void insert ( ){
            StringRequest strReq = new StringRequest(Request.Method.POST,
                    showUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(EnvoiSMS.this,"valide",Toast.LENGTH_SHORT).show();
                    Intent i1= new Intent(getApplicationContext(),AjoutVente.class);
                    startActivity(i1);

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EnvoiSMS.this,"invalide",Toast.LENGTH_SHORT).show();


                }
            }) {


                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parametres = new HashMap<String, String>();
                    parametres.put("receiver",tel.getText().toString());


                    return parametres;
                }

            };


            requestQueue.add(strReq);
        }
    }







