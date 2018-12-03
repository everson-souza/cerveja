package br.com.everson.cerveja;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
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
import java.util.Iterator;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String token = br.com.everson.cerveja.Singleton.getInstance().getValue();
        String url;
        if (token ==null) {
            url = "https://www.formore.com.br/mobile/api/feed/read_feed.php?token=1)alskd";
        }else{
            url = "https://www.formore.com.br/mobile/api/feed/read_feed.php?token="+token;
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        int id;
                        String name, autor, local, lat, longi, avaliacao, imagem, data, desc, tipo ;
                        LinearLayout mContent =(LinearLayout) findViewById(R.id.mContent);

                        List<TextView> textos = new ArrayList<>();

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject row = null;
                            try {
                                row = response.getJSONObject(i);
                                name = row.getString("NomeCerveja");
                                local = row.getString("Local");
                                data = row.getString("Data");
                                id = row.getInt("IDPublicacao");
                                //Toast.makeText(MainActivity.this, name.toString(), Toast.LENGTH_LONG).show();

//                                final ImageView imgItem = new ImageView(MainActivity.this);
//                                imgItem.setId(id);
//                                imgItem.set(name);
//                                mContent.addView(txtItem);

                                final TextView txtItem = new TextView(MainActivity.this);
                                txtItem.setId(id);
                                txtItem.setText(name);
                                mContent.addView(txtItem);
                                if (row.getInt("Tipo") == 1) {
                                    txtItem.setTextAppearance(getApplicationContext(), R.style.nome_publicacao);
                                }else{
                                    txtItem.setTextAppearance(getApplicationContext(), R.style.nome_publicacao_stories);
                                }

                                final TextView localItem = new TextView(MainActivity.this);
                                localItem.setText(local);
                                mContent.addView(localItem);

                                final TextView dataItem = new TextView(MainActivity.this);
                                dataItem.setText(data);
                                mContent.addView(dataItem);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //String resposta = response.toString();
                            //Log.e("onResponse", "" + response);
                            //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to queue
        requestQueue.add(jsonArrayRequest);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, NewPubActivity.class);
                startActivity(it);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_estatisticas) {
            Intent intent = new Intent(this, EstatisticasActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_milestones) {
            Intent intent = new Intent(this, MilestonesActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_amizades) {
            Intent intent = new Intent(this, AmizadeActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_usuarios) {
            Intent intent = new Intent(this, UsuarioActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void carregarDados(){



// Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
