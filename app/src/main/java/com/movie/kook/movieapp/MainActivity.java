package com.movie.kook.movieapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.movie.kook.movieapp.data.ImageLoadTask;
import com.movie.kook.movieapp.data.Movie;
import com.movie.kook.movieapp.data.MovieInfo;
import com.movie.kook.movieapp.data.ResponseInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout LinearLayout_main;

    Button Button_likeicon;
    Button Button_dislikeicon;
    Button Button_viewAll;

    TextView TextView_write;

    ListView ListView_write;

    boolean likeState = false;
    boolean dislikeState = false;

    float movieScore;

    String movieContents;

    public static CommentAdater adapter;

    RequestMovie requestMovieActivity;

    public static final int REQUEST_CODE_WRITE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button_likeicon = (Button) findViewById(R.id.Button_likeicon);
        Button_likeicon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_UP){
                    likeState = !likeState;
                    if(likeState == false){
                        likeDecrCount();
                    }else if(likeState == true){
                        likeIncrCount();
                        if(dislikeState == true){
                            dislikeDecrCount();
                            dislikeState = false;
                        }
                    }
                }
                return true;
            }
        });


        Button_dislikeicon = (Button) findViewById(R.id.Button_dislikeicon);
        Button_dislikeicon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_UP) {
                    dislikeState = !dislikeState;
                    if (dislikeState == false) {
                        dislikeDecrCount();
                    } else if (dislikeState == true) {
                        dislikeIncrCount();
                        if (likeState == true) {
                            likeDecrCount();
                            likeState = false;
                        }
                    }
                }
                return true;
            }
        });


        TextView_write = (TextView) findViewById(R.id.TextView_write);
        TextView_write.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_UP){
                    Toast toast = Toast.makeText(getApplicationContext(), "작성하기를 눌렀습니다.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 20, 200);
                    toast.show();
                    showCommentWriteActivity();
                }

                return true;
            }
        });



        ListView_write = (ListView) findViewById(R.id.ListView_write);

        adapter = new CommentAdater();
        adapter.addItem(new CommentItem("kooktjsdud1", 3.5f, "핵노잼 ㅡ,.ㅡ"));
        adapter.addItem(new CommentItem("kym7171", 5, "적당히 재밌다.오랜만에 잠 안오는 영화 봤네요."));

        ListView_write.setAdapter(adapter);

        Button_viewAll = (Button) findViewById(R.id.Button_viewAll);
        Button_viewAll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_UP){
                    Toast toast = Toast.makeText(getApplicationContext(), "모두보기를 눌렀습니다.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 20, 200);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(), CommentViewAllActivity.class);
                    startActivity(intent);
                }
                return true;

            }
        });

        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        LinearLayout_main = (LinearLayout) findViewById(R.id.LinearLayout_main);

        requestMovieActivity = new RequestMovie(LinearLayout_main, "main", getApplicationContext());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // resultCode => 성공 : 200, 실패 : 400
        if(requestCode == REQUEST_CODE_WRITE){
            if(resultCode == 200){
                if(intent != null){
                    movieScore = intent.getFloatExtra("movieScore", movieScore);
                    movieContents = intent.getStringExtra("movieContents");
                    adapter.addItem(new CommentItem("*^^*", movieScore , movieContents ));
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "전달 받은 내용이 없습니다.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "응답코드가 달라 실패했습니다.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "요청 코드가 다릅니다.", Toast.LENGTH_LONG).show();
        }
    }


    public void showCommentWriteActivity() {
        Intent intent = new Intent(getApplicationContext(), CommentWriteActivity.class);
        startActivityForResult(intent, REQUEST_CODE_WRITE);
    }

    class CommentAdater extends BaseAdapter {

        ArrayList<CommentItem> items = new ArrayList<CommentItem>();


        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(CommentItem item){
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CommentItemView view = null;
            if(convertView == null){
                view = new CommentItemView(getApplicationContext());
            } else {
                view = (CommentItemView) convertView;
            }

            CommentItem item = items.get(position);
            view.setId(item.getId());
            view.setStarScore(item.getStarScore());
            view.setContent(item.getContent());
            return view;
        }
    }

    public void likeDecrCount() {
        requestMovieActivity.likeCount--;
        requestMovieActivity.TextView_like.setText(String.valueOf(requestMovieActivity.likeCount));
        Button_likeicon.setBackgroundResource(R.drawable.ic_thumb_up);
    }

    public void likeIncrCount() {
        requestMovieActivity.likeCount++;
        requestMovieActivity.TextView_like.setText(String.valueOf(requestMovieActivity.likeCount));
        Button_likeicon.setBackgroundResource(R.drawable.ic_thumb_up_selected);

    }

    public void dislikeDecrCount() {
        requestMovieActivity.dislikeCount--;
        requestMovieActivity.TextView_dislike.setText(String.valueOf(requestMovieActivity.dislikeCount));
        Button_dislikeicon.setBackgroundResource(R.drawable.ic_thumb_down);
    }

    public void dislikeIncrCount() {
        requestMovieActivity.dislikeCount++;
        requestMovieActivity.TextView_dislike.setText(String.valueOf(requestMovieActivity.dislikeCount));
        Button_dislikeicon.setBackgroundResource(R.drawable.ic_thumb_down_selected);
    }




}
