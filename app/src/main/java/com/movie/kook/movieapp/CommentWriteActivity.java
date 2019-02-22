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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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

    LinearLayout LinearLayout_write;

    RatingBar RatingBar_movieScore;

    EditText EditText_movieContents;

    Button Button_save;
    Button Button_cancel;

    float movieScore;
    String movieContents;

    RequestMovie requestMovie;


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
                    movieScore = RatingBar_movieScore.getRating();
                    movieContents = EditText_movieContents.getText().toString().trim();
                    if(movieContents.length() > 0) {
                        returnToMain();
                    } else {
                        Toast.makeText(getApplicationContext(), "한줄평을 입력해주세요.", Toast.LENGTH_LONG).show();
                    }
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

        LinearLayout_write = (LinearLayout) findViewById(R.id.LinearLayout_write);

        requestMovie = new RequestMovie(LinearLayout_write, "commentWrite", getApplicationContext());

    }

    public void returnToMain() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("movieScore", movieScore);
        intent.putExtra("movieContents", movieContents);
        setResult(200, intent);
        //  setResult(Activity.RESULT_OK, intent);

        finish();
    }
}
