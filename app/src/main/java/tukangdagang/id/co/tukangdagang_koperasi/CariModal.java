package tukangdagang.id.co.tukangdagang_koperasi;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tukangdagang.id.co.tukangdagang_koperasi.carimakanan.ModelMakanan;
import tukangdagang.id.co.tukangdagang_koperasi.carimakanan.RvMakananAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.carimodal.ListViewAdapter;
import tukangdagang.id.co.tukangdagang_koperasi.carimodal.Model;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.JSON_URL;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.URL_KOPERASI;

public class CariModal extends AppCompatActivity {
    ListView listView;
    ListViewAdapter adapter;
    String[] title;
    String[] description;
    int[] icon;
    ArrayList<Model> arrayList = new ArrayList<Model>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_modal);
        ActionBar actionBar = getSupportActionBar();

        getdata();

    }

    private void getdata(){
        final ProgressDialog progressDialog = new ProgressDialog(CariModal.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_KOPERASI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray koperasiArray = obj.getJSONArray("result");
                            Log.d("resul",response);
                            for (int i = 0; i < koperasiArray.length(); i++) {

                                JSONObject koperasiobject = koperasiArray.getJSONObject(i);
                                Log.d("asd", response);


                                Model model = new Model(koperasiobject.getString("nama_koperasi"),
                                        koperasiobject.getString("simpanan_pokok"),
                                        koperasiobject.getString("logo_koperasi"),
                                        koperasiobject.getString("id"),
                                        koperasiobject.getString("rating_koperasi"),
                                        koperasiobject.getString("alamat_koperasi")
                                );

                                arrayList.add(model);
                            }
                            listView = findViewById(R.id.listKoprasi);
                            adapter = new ListViewAdapter(CariModal.this,arrayList);


                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Terjadi kesalahan pada saat melakukan permintaan data", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_koprasi, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search_koprasi);
        SearchView searchView = (SearchView)myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    adapter.filter("");
                    listView.clearTextFilter();
                }
                else {
                    adapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id==R.id.action_settings){
//            //do your functionality here
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}