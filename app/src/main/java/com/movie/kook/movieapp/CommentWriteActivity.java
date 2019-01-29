package com.movie.kook.movieapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.movie.kook.movieapp.data.Movie;
import com.movie.kook.movieapp.data.MovieInfo;
import com.movie.kook.movieapp.data.ResponseInfo;

public class CommentWriteActivity extends AppCompatActivity {

    RatingBar RatingBar_movieScore;

    EditText EditText_movieContents;

    TextView TextView_title_write;

    ImageView ImageView_grade_write;

    Button Button_save;
    Button Button_cancel;

    MovieInfo movieInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_write);

        RatingBar_movieScore = (RatingBar) findViewById(R.id.RatingBar_movieScore);
        EditText_movieContents = (EditText) findViewById(R.id.EditText_movieContents);


        Button_save = (Button) findViewById(R.id.Button_save);
        Button_save.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_UP) {
                    returnToMain();
                    //onBackPressed();
                }
                return true;
            }
        });

        Button_cancel = (Button) findViewById(R.id.Button_cancel);
        Button_cancel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_UP) {
                    finish();
                }
                return true;
            }
        });

        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        TextView_title_write = (TextView) findViewById(R.id.TextView_title_write);
        ImageView_grade_write = (ImageView) findViewById(R.id.ImageView_grade_write);

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
                TextView_title_write.setText(movieInfo.title);
                setImageView_grade_write();
            }
        }
    }

    public void setImageView_grade_write() {
        switch (movieInfo.grade){
            case 12:
                ImageView_grade_write.setBackgroundResource(R.drawable.ic_12);
                break;
            case 15:
                ImageView_grade_write.setBackgroundResource(R.drawable.ic_15);
                break;
            case 19:
                ImageView_grade_write.setBackgroundResource(R.drawable.ic_19);
                break;
            default:
                ImageView_grade_write.setBackgroundResource(R.drawable.ic_all);
                break;
        }
    }

    public void returnToMain() {
        float movieScore = RatingBar_movieScore.getRating();
        String movieContents = EditText_movieContents.getText().toString().trim();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("movieScore", movieScore);
        intent.putExtra("movieContents", movieContents);
        setResult(101, intent);
        //  setResult(Activity.RESULT_OK, intent);

        finish();
    }
}
