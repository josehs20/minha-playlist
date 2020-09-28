package com.example.minhaplaylist.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.minhaplaylist.PlayActivity;
import com.example.minhaplaylist.R;
import com.example.minhaplaylist.adapter.AdapterVideo;
import com.example.minhaplaylist.api.YoutubeService;
import com.example.minhaplaylist.listener.Fragment_album;
import com.example.minhaplaylist.listener.Lista_Fragment;
import com.example.minhaplaylist.listener.Play_fragment;
import com.example.minhaplaylist.listener.Salva_fragment;
import com.example.minhaplaylist.helper.PlaylistConfig;
import com.example.minhaplaylist.helper.RetrofitConfig;
import com.example.minhaplaylist.listener.RecyclerItemClickListener;
import com.example.minhaplaylist.model.Item;
import com.example.minhaplaylist.model.Resultado;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    //Vídeos
    private RecyclerView listaVideos;
    private MaterialSearchView searchView;


    private List<Item> videos = new ArrayList<>();
    private Resultado resultado;
    private AdapterVideo adapterVideo;

    //Retrofit
    private Retrofit retrofit;

      // Botões e fragments
    private Button bottaolist, bottaoplayer, bottaoalbum,bottonSalva;
    private Salva_fragment salva_fragment;
    private Play_fragment play_fragment;
    private Lista_Fragment lista_fragment;
    private Fragment_album fragment_album;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Componentes
         listaVideos = findViewById(R.id.listaVideos);
         searchView = findViewById(R.id.searchView);

         //Confg inicial

        retrofit = RetrofitConfig.getRetrofit();


        //fragments

        bottonSalva = findViewById(R.id.bottonSalva);
        bottaoalbum = findViewById(R.id.bottaoalbum);
        bottaolist = findViewById(R.id.bottaolist);
        bottaoplayer = findViewById(R.id.bottaoplayer);





        /*Configurar toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Playlist");
        setSupportActionBar(toolbar);*/


        //Recupera
        recuperaVideos("");

        //Métodos para pesquisa
       searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recuperaVideos( query );
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

                recuperaVideos(" ");
            }
        });

    }
    private void recuperaVideos(String pesquisa) {

        String q = pesquisa.replaceAll(" ", "+");

        YoutubeService youtubeService = retrofit.create(YoutubeService.class);

        youtubeService.recuperaVideos(

                "snippet", "date", "50",
                PlaylistConfig.CHAVE_PLAYLIST_API, PlaylistConfig.CANAL_ID, q
        ).enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                Log.d("resultado", "resultado:" + response.toString());
                if (response.isSuccessful()) {
                    resultado = response.body();
                    videos = resultado.items;
                    configuraRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

            }
        });

    }
        public void configuraRecyclerView(){
            adapterVideo = new AdapterVideo(videos, this);

            listaVideos.setHasFixedSize( true);
            listaVideos.setLayoutManager( new LinearLayoutManager(this));
            listaVideos.setAdapter(adapterVideo);

            //Configura Clique
            listaVideos.addOnItemTouchListener(
                    new RecyclerItemClickListener(
                            this,
                            listaVideos,
                            new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    Item video = videos.get(position);
                                    String idVideo = video.id.videoId;
                                    Intent i = new Intent(MainActivity.this, PlayActivity.class);
                                    i.putExtra("idVideo", idVideo);
                                    startActivity(i);
                                }

                                @Override
                                public void onLongItemClick(View view, int position) {

                                }

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                }
                            }
                    )


            );


        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem(item);

        return true;
    }

}
