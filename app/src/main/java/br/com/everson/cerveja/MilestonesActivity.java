package br.com.everson.cerveja;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MilestonesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milestones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String token = br.com.everson.cerveja.Singleton.getInstance().getValue();
        String url = "https://www.formore.com.br/mobile/api/estatisticas/milestones.php?token="+token;



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        int id;
                        String name, quantidade, data;


                        LinearLayout milestonesContent =(LinearLayout) findViewById(R.id.milestonesContent);
                        ImageView ouro = findViewById(R.id.ouro);
                        ImageView prata = findViewById(R.id.prata);
                        ImageView bronze = findViewById(R.id.bronze);
                        try {

                            int milestones = response.getInt("countCerveja");

                            if (milestones >= 20){
                                prata.setVisibility(View.INVISIBLE);
                                bronze.setVisibility(View.INVISIBLE);
                            }else if (milestones > 10 && milestones < 20){
                                ouro.setVisibility(View.INVISIBLE);
                                bronze.setVisibility(View.INVISIBLE);
                            }else {
                                ouro.setVisibility(View.INVISIBLE);
                                prata.setVisibility(View.INVISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(MilestonesActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to queue
        requestQueue.add(jsonObjectRequest);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
