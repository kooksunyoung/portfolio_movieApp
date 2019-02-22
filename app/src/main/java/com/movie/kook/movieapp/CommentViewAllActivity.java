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
import android.widget.LinearLayout;
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

    LinearLayout LinearLayout_viewAll;

    ListView ListView_viewAll;

    TextView TextView_write2;

    float movieScore;

    String movieContents;

    RequestMovie requestMovie;

    public static final int REQUEST_CODE_WRITE = 102;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == REQUEST_CODE_WRITE){
            if(intent != null){
                movieScore = intent.getFloatExtra("movieScore", movieScore);
                movieContents = intent.getStringExtra("movieContents");
                MainActivity.adapter.addItem(new CommentItem("rnrtjsdud", movieScore , movieContents ));
                MainActivity.adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_view_all);

        ListView_viewAll = (ListView) findViewById(R.id.ListView_ViewAll);
        ListView_viewAll.setAdapter(MainActivity.adapter);

        TextView_write2 = (TextView) findViewById(R.id.TextView_write2);
        TextView_write2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_UP){
//                    Toast toast;
                    Toast.makeText(getApplicationContext(), "작성하기를 눌렀습니다.", Toast.LENGTH_LONG).show();
//                    toast.setGravity(Gravity.BOTTOM, 20, 1200);
//                    toast.show();
                    Intent intent = new Intent(getApplicationContext(), CommentWriteActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_WRITE);
                }
                return true;
            }
        });

        LinearLayout_viewAll = (LinearLayout) findViewById(R.id.LinearLayout_viewAll);

        requestMovie = new RequestMovie(LinearLayout_viewAll, "commentViewAll", getApplicationContext());


    }

}
