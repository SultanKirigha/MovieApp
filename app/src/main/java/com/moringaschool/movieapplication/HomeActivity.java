package com.moringaschool.popularmoviedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.moringaschool.movieapplication.Adaptery;
import com.moringaschool.movieapplication.MovieModelClass;
import com.moringaschool.movieapplication.R;
import com.moringaschool.movieapplication.Slider_Adapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeActivity extends AppCompatActivity {

    //TMDB API for the popular movies: https://api.themoviedb.org/3/movie/popular?api_key=0584e67102d110c1eef2a29a602fd9bb
    private static String JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=0584e67102d110c1eef2a29a602fd9bb";



    List<MovieModelClass> movieList;
    RecyclerView recyclerView;



    SliderView myslider;
    int[] mylist = {
            R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_4,
            R.drawable.img_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        movieList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        myslider = findViewById(R.id.slider_image);

        Slider_Adapter sliderAdapter = new Slider_Adapter(mylist);
        myslider.setSliderAdapter(sliderAdapter);
        myslider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        myslider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        myslider.startAutoCycle();


        GetData getData = new GetData();
        getData.execute();


    }

    public class GetData extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            String current= "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();


                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data != -1){


                        current += (char) data;
                        data = isr.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i< jsonArray.length() ; i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    MovieModelClass model = new MovieModelClass();
                    model.setId(jsonObject1.getString("vote_average"));
                    model.setName(jsonObject1.getString("title"));
                    model.setImage(jsonObject1.getString("poster_path"));
                    movieList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(movieList);
        }
    }
    private void PutDataIntoRecyclerView(List<MovieModelClass> movieList){
        Adaptery adaptery = new Adaptery(this,movieList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        recyclerView.setAdapter(adaptery);
    }
}
