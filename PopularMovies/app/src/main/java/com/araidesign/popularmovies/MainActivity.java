package com.araidesign.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.araidesign.popularmovies.utils.JsonUtils;
import com.araidesign.popularmovies.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClickListener {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MovieData> allMovieData = new ArrayList<MovieData>();

    public String api_key;

    private static final int NUM_MOVIES = 20;
    private Toast mToast;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api_key = getString(R.string.my_API_key);

        mRecyclerView = (RecyclerView) findViewById(R.id.movie_recycler_view);
        mRecyclerView.setOnClickListener(launchDetailActivity());

        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        allMovieData = GetDataTest.loadSamplePosters();
        mMovieAdapter = new MovieAdapter(this, allMovieData);
        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator =  findViewById(R.id.pb_load_indicator);


    }


    private View.OnClickListener launchDetailActivity(int position){
        String movieJson;

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION,position);
//        intent.putExtra(DetailActivity.MOVIE_DATA, (Parcelable) allMovieData.get(position));
        intent.putExtra(DetailActivity.MOVIE_DATA, );


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int itemClicked = item.getItemId();
        switch (item.getItemId()){
            case R.id.action_popular:
                makePopularMovieSearch();
                break;
            case R.id.action_top_rated:
                makeTopRatedMovieSearch();
                break;
            default:
                if (mToast != null) {
                    mToast.cancel();
                }
                String toastMessage = "What?! Nothing Clicked?";
                mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void OnItemClick(int clickedItemIndex) {

        launchDetailActivity(clickedItemIndex);



        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        mToast.show();
    }

    void makePopularMovieSearch() {
        allMovieData.clear();
        URL url = NetworkUtils.buildTMDBQueryURL(getString(R.string.my_API_key),getString(R.string.popular_movie_search));

        new movieSearchTask().execute(url);
    }

    void makeTopRatedMovieSearch(){
        allMovieData.clear();
        URL url = NetworkUtils.buildTMDBQueryURL(getString(R.string.my_API_key),getString(R.string.top_rated_movie_search));
        new movieSearchTask().execute(url);

    }
    public class movieSearchTask extends AsyncTask<URL, Void, ArrayList<MovieData>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }


        @Override
        protected  ArrayList<MovieData> doInBackground(URL... url) {
            String movieListJSON = null;
            ArrayList<MovieData> movieDataFromJson = new ArrayList<>();
            try {
                //Go get the json string from the network
                movieListJSON = NetworkUtils.getMovieDBJSON(url[0]);

//       TODO: Change this to return movieListJSON.  Then JSON data is available to pass to intent
                try {
                    //Parse that json string
                    movieDataFromJson = JsonUtils.parseTMDBRequest(movieListJSON);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieDataFromJson;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> movieData){
            mLoadingIndicator.setVisibility(View.INVISIBLE);

// TODO: Is this the best method to copy ArrayList ??
// TODO: Don't need to do this here.  Moving all this out so JSON is available
//            allMovieData.addAll(movieData);
            for(int i=0; i<movieData.size(); i++){
                allMovieData.add(movieData.get(i));
            }
//    TODO: MainActivity is not consistently getting the screen refresshed when selecting Popular or Top Rated
            mMovieAdapter.notifyDataSetChanged();

        }
    }
}

