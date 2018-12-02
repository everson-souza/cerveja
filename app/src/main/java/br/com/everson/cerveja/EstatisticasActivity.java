package br.com.everson.cerveja;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class EstatisticasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);

        String token = br.com.everson.cerveja.Singleton.getInstance().getValue();
        String url = "https://www.formore.com.br/mobile/api/estatisticas/estatisticas.php?token="+token;



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        int id;
                        String name, quantidade, data;
                        LinearLayout estatisticasContent =(LinearLayout) findViewById(R.id.estatisticasContent);
                        try {
                            JSONArray jsonArray = response.getJSONArray("countTipo");

                            String totalCervejas = response.getString("countCerveja");
                            final TextView total = new TextView(EstatisticasActivity.this);
                            total.setText("Total de Cervejas: "+totalCervejas);
                            estatisticasContent.addView(total);

                            String totalMarcacoes = response.getString("countMarcacaoes");
                            final TextView marcacoes = new TextView(EstatisticasActivity.this);
                            marcacoes.setText("Total de Marcações: "+totalMarcacoes);
                            estatisticasContent.addView(marcacoes);

                            String totalPreferencias = response.getString("countPreferencias");
                            final TextView preferencias = new TextView(EstatisticasActivity.this);
                            preferencias.setText("Total de Preferências Cadastradas:"+totalPreferencias);
                            estatisticasContent.addView(preferencias);


                            final TextView labelView = new TextView(EstatisticasActivity.this);
                            labelView.setText("\nCervejas Experimentadas:");
                            estatisticasContent.addView(labelView);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject row = null;
                                    row = jsonArray.getJSONObject(i);
                                    name = row.getString("NomeCerveja");
                                    quantidade = row.getString("Quantidade");

                                    final TextView txtItem = new TextView(EstatisticasActivity.this);
                                    txtItem.setText(name+" - "+quantidade);
                                    estatisticasContent.addView(txtItem);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(EstatisticasActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to queue
        requestQueue.add(jsonObjectRequest);

    }
}
