package com.movie.kook.movieapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.movie.kook.movieapp.data.Movie;
import com.movie.kook.movieapp.data.MovieInfo;
import com.movie.kook.movieapp.data.ResponseInfo;

public class CommentViewAllActivity extends AppCompatActivity {

    ListView ListView_writeAll;

    TextView TextView_write2;
    TextView TextView_title_viewAll;
    TextView TextView_reviewer_rating_viewAll;

    ImageView ImageView_grade_viewAll;

    RatingBar RatingBar_audience_rating_viewAll;

    float movieScore;

    String movieContents;

    MovieInfo movieInfo;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == 102){
            if(intent != null){
                movieScore = intent.getFloatExtra("movieScore", movieScore);
                movieContents = intent.getStringExtra("movieContents");
                MainActivity.adater.addItem(new CommentItem("rnrtjsdud", movieScore , movieContents ));
                MainActivity.adater.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_view_all);

        TextView_write2 = (TextView) findViewById(R.id.TextView_write);
        TextView_write2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_UP){
                    Toast toast = Toast.makeText(getApplicationContext(), "작성하기를 눌렀습니다.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 20, 1200);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(), CommentWriteActivity.class);
                    startActivityForResult(intent, 102);
                }
                return true;
            }
        });

        ListView_writeAll = (ListView) findViewById(R.id.ListView_writeAll);
        ListView_writeAll.setAdapter(MainActivity.adater);

        TextView_title_viewAll = (TextView) findViewById(R.id.TextView_title_viewAll);
        TextView_reviewer_rating_viewAll = (TextView) findViewById(R.id.TextView_reviewer_rating_viewAll);
        ImageView_grade_viewAll = (ImageView) findViewById(R.id.ImageView_grade_viewAll);
        RatingBar_audience_rating_viewAll = (RatingBar) findViewById(R.id.RatingBar_audience_rating_viewAll);

        requestMovie();


    }

    public void requestMovie() {

        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovie";
        url += "?" + "id=1";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }

        );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);

    }

    public void processResponse(String response) {
        Gson gson = new Gson();

        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
        if (info.code == 200) {
            Movie movie = gson.fromJson(response, Movie.class);

            for (int i = 0; i < movie.result.size(); i++) {
                movieInfo = movie.result.get(i);

                TextView_title_viewAll.setText(movieInfo.title);
                TextView_reviewer_rating_viewAll.setText(movieInfo.reviewer_rating + "");
                setImageView_grade_viewAll();
                RatingBar_audience_rating_viewAll.setRating(movieInfo.audience_rating / 2);
            }
        }
    }

    public void setImageView_grade_viewAll() {
        Resources res = getResources();
        BitmapDrawable drawable;

        switch (movieInfo.grade){
            case 12:
                drawable = (BitmapDrawable) res.getDrawable(R.drawable.ic_12);
                ImageView_grade_viewAll.setImageDrawable(drawable);
                break;
            case 15:
                drawable = (BitmapDrawable) res.getDrawable(R.drawable.ic_15);
                ImageView_grade_viewAll.setImageDrawable(drawable);
                break;
            case 19:
                drawable = (BitmapDrawable) res.getDrawable(R.drawable.ic_19);
                ImageView_grade_viewAll.setImageDrawable(drawable);
                break;
            default:
                drawable = (BitmapDrawable) res.getDrawable(R.drawable.ic_all);
                ImageView_grade_viewAll.setImageDrawable(drawable);
                break;
        }
    }
}
