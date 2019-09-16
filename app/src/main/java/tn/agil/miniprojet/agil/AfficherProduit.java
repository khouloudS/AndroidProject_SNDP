package tn.agil.miniprojet.agil;
/**
 * Created by Linda on 12/11/2017.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class AfficherProduit extends Activity {
    String showUrl="http://192.168.1.2/WebService1/listepromoprod.php";
    String showUrlClient="http://192.168.1.2/WebService1/listeClients.php";
    private ListView listView=null;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> listClient = new ArrayList<String>();
    RequestQueue requestQueue;
    RequestQueue requestQueue2;
    ProgressBar progressBar;
    View dialogView;
    EditText cin ;
    boolean exist ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_produit);
        listView = (ListView) findViewById(R.id.listView1);
        requestQueue = Volley.newRequestQueue(this);
        requestQueue2 = Volley.newRequestQueue(this);
        recup();
        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.fab);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Intent i1= new Intent(getApplicationContext(),AjoutClient.class);
                startActivity(i1);*/
                dialogView = getLayoutInflater().inflate(R.layout.dialog_cin,null);
                cin=(EditText)dialogView.findViewById(R.id.cin);
                progressBar=(ProgressBar)dialogView.findViewById(R.id.progressBar2);
                final AlertDialog.Builder builder = new AlertDialog.Builder(AfficherProduit.this);
                builder.setTitle("Authentification");
                builder.setPositiveButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                });
                builder.setNegativeButton("Se connecter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                builder.setView(dialogView);

                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Boolean wantToCloseDialog = false;
                        //Do stuff, possibly set wantToCloseDialog to true then...
                        if(wantToCloseDialog)
                            dialog.dismiss();
                        //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                        String cinTest=cin.getText().toString();

                        if (TextUtils.isEmpty(cinTest)||cinTest.length()!=8 || !verifText(cinTest)) {
                            cin.setError("Non valide");
                        }else{

                            recupClient(cin);

                        }
                    }
                });
            }
        });
    }
    public boolean verifText(String ch){
        long num ;
        try{

            Long.parseLong(ch);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public void recup(){




        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,showUrl,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray produits = response.getJSONArray("produits");
                    int i=0;

                    for (i = 0; i < produits.length(); i++) {
                        JSONObject produit = produits.getJSONObject(i);
                        //add list

                        String nom = produit.getString("nom");
                        String quantite = produit.getString("quantite");
                        String prix= produit.getString("prixfinal");
                        String dateD= produit.getString("datedebut");
                        String dateF= produit.getString("datefin");
                        // String marque = produit
                        String ligne = nom+"\n "+quantite+"  \n"+prix +"DT"
                                +"\n "+dateD+" \n "+dateF;
                        list.add(ligne);

                    }

                    String[] items = new String[list.size()];
                    items = list.toArray(items);

                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(AfficherProduit.this,
                            R.layout.list_item, R.id.txtitem,items);

                    listView.setAdapter(adapter);

                    // Perform action when an item is clicked

                    listView.setOnItemClickListener(new
                                                            AdapterView.OnItemClickListener() {

                                                                @Override

                                                                public void onItemClick(AdapterView<?> parent, View view, int
                                                                        position, long id) {

                                                                    ViewGroup vg=(ViewGroup)view;
                                                                    String item = listView.getItemAtPosition(position).toString();



                                                                }

                                                            });
                    // Toast.makeText(MainActivity.this, list.toString(), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    Toast.makeText(AfficherProduit.this, "Ereur DB", Toast.LENGTH_SHORT).show();

                }

            }
        },

                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AfficherProduit.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();

                    }



                }){


        };
        requestQueue.add(jsonObjectRequest);

    }

    public void recupClient(EditText cint){

        final String test = cint.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        cin.setVisibility(View.GONE);
        exist = false ;



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,showUrlClient,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray clients = response.getJSONArray("clients");

                    for (int i = 0; i < clients.length(); i++) {
                        JSONObject client = clients.getJSONObject(i);
                        String cinb = client.getString("cin");
                        listClient.add(cinb);
                    }


                    if(listClient.contains(test)) {

                        throw new JSONException("erreur");

                    }else{

                        cin.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Intent myIntent= new Intent(AfficherProduit.this,AjoutClient.class);
                        myIntent.putExtra("cin",cin.getText().toString());
                        startActivity(myIntent);
                    }










                    //result.append("===\n");
                } catch (JSONException e) {
                    // result.setText("exception");
                    cin.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AfficherProduit.this, "Client existe ", Toast.LENGTH_LONG).show();
                }

            }
        },

                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //result.setText(ch);
                        cin.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(AfficherProduit.this, "Une erreur technique est survenue.Veuillez réessayer plus tard.", Toast.LENGTH_LONG).show();

                    }



                }){


        };
        requestQueue2.add(jsonObjectRequest);

    }

}
