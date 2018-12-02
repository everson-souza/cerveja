package br.com.everson.cerveja;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String token = br.com.everson.cerveja.Singleton.getInstance().getValue();
        String url = "https://www.formore.com.br/mobile/api/usuario/read.php?token="+token;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        int id;
                        String nome, email, cidade, estado, pais;
                        LinearLayout usuarioContent =(LinearLayout) findViewById(R.id.inner_usuario_form);

                        try {
                            email = response.getString("email");
                            nome = response.getString("nome");
                            cidade = response.getString("cidade");
                            estado = response.getString("estado");
                            pais = response.getString("pais");

//                            EditText emailView =  findViewById(R.id.email_usuario);
//                            EditText nomeView =  findViewById(R.id.nome_usuario);
//                            EditText cidadeView =  findViewById(R.id.cidade_usuario);
//                            EditText estadoView =  findViewById(R.id.estado_usuario);
//                            EditText paisView =  findViewById(R.id.pais_usuario);

                            EditText emailItem = new EditText(UsuarioActivity.this);
                            emailItem.setText(email);
                            emailItem.setId(R.id.emailUsuario);

                            EditText nomeItem = new EditText(UsuarioActivity.this);
                            nomeItem.setText(nome);
                            nomeItem.setId(R.id.nomeUsuario);


                            EditText cidadeItem = new EditText(UsuarioActivity.this);
                            cidadeItem.setText(cidade);
                            cidadeItem.setId(R.id.cidadeUsuario);

                            EditText estadoItem = new EditText(UsuarioActivity.this);
                            estadoItem.setText(estado);
                            estadoItem.setId(R.id.estadoUsuario);

                            EditText paisItem = new EditText(UsuarioActivity.this);
                            paisItem.setText(pais);
                            paisItem.setId(R.id.paisUsuario);

                            usuarioContent.addView(emailItem);
                            usuarioContent.addView(nomeItem);
                            usuarioContent.addView(cidadeItem);
                            usuarioContent.addView(estadoItem);
                            usuarioContent.addView(paisItem);
                            //Toast.makeText(UsuarioActivity.this, response.toString(), Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(UsuarioActivity.this, error.toString(), Toast.LENGTH_LONG).show();

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

                EditText emailItem = (EditText) findViewById(R.id.emailUsuario);
                EditText nomeItem = (EditText) findViewById(R.id.nomeUsuario);
                EditText cidadeItem = (EditText) findViewById(R.id.cidadeUsuario);
                EditText estadoItem = (EditText) findViewById(R.id.estadoUsuario);
                EditText paisItem = (EditText) findViewById(R.id.paisUsuario);

                String url = null;
                try {
                    String token = br.com.everson.cerveja.Singleton.getInstance().getValue();

                    url = "https://www.formore.com.br/mobile/api/usuario/cadastro.php?token="+token+"&cidade="+cidadeItem.getText().toString()+"&estado="+estadoItem.getText().toString()+"&pais="+paisItem.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(UsuarioActivity.this, "Usuário alterado com sucesso!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(UsuarioActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                Toast.makeText(UsuarioActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                            }
                        });

                //Creating request queue
                RequestQueue requestQueue = Volley.newRequestQueue(UsuarioActivity.this);

                //Adding request to queue
                requestQueue.add(jsonObjectRequest);

            }
        });
    }

}
