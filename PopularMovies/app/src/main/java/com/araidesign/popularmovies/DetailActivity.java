package com.araidesign.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class DetailActivity extends AppCompatActivity {

    public static final String  EXTRA_POSITION = "extra_position";
    public static final String MOVIE_DATA = "extra_moviedata"

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageIv = findViewById(R.id.iv_detail_poster_item);
        Intent intent = getIntent();
        if (intent == null){
            closeOnError();
        }
        int position = 0;
        position = intent.getIntExtra(EXTRA_POSITION, 0);

    }

    private void closeOnError() {
    }
}
