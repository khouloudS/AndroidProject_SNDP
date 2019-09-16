package tn.agil.miniprojet.agil;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Authentification extends AppCompatActivity implements View.OnClickListener{

    EditText log, pass;
    boolean exist=false;
    RequestQueue requestQueue;
    String showUrl="http://192.168.1.2/WebService1/loginGerant.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);

        Button btnOnlogin= (Button) findViewById(R.id.button1);
        log = (EditText)findViewById(R.id.editTextLog);
        pass = (EditText)findViewById(R.id.editTextPass);

        btnOnlogin.setOnClickListener(this);
        //partie web service
        requestQueue = Volley.newRequestQueue(this);


    }



    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.button1:

                connecte();

                break;

        }
    }
    public void connecte(){
        final String l=log.getText().toString();
        final String m=pass.getText().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,showUrl,new Response.Listener<JSONObject>(){




            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray users = response.getJSONArray("users");

                    for (int i = 0; i < users.length(); i++) {
                        JSONObject user = users.getJSONObject(i);
                        String testL = user.getString("login");
                        String testM = user.getString("passe");

                        if(testL.equals(l) && testM.contentEquals(m)) {
                            exist = true;
                            Toast.makeText(Authentification.this, "Bienvenue", Toast.LENGTH_LONG).show();
                            //acceder à la page suivante
                            Intent i1= new Intent(getApplicationContext(),AfficherProduit.class);
                            startActivity(i1);
                        }


                    }
                    if(exist==false){
                        throw new JSONException("erreur de connection");
                    }


                } catch (JSONException e) {

                    Toast.makeText(Authentification.this, "Verifier vos données", Toast.LENGTH_LONG).show();
                }

            }
        },

                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Authentification.this, "Erreur Connection", Toast.LENGTH_LONG).show();

                    }



                }){


        };
        requestQueue.add(jsonObjectRequest);

    }
}
