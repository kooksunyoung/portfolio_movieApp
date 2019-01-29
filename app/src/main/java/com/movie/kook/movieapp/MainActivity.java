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

    Button Button_likeicon;
    Button Button_dislikeicon;
    Button Button_viewAll;
    Button Button_println;

    TextView TextView_write;
    TextView TextView_MoviecontentsView;
    TextView TextView_println;
    TextView TextView_title;
    TextView TextView_date;
    TextView TextView_reviewer_rating;
    TextView TextView_reservation_rate;
    TextView TextView_reservation_grade;
    TextView TextView_genre;
    TextView TextView_duration;
    TextView TextView_audience;
    TextView TextView_synopsis;
    TextView TextView_director;
    TextView TextView_actor;
    TextView TextView_like;
    TextView TextView_dislike;

    ListView ListView_write;

    RatingBar RatingBar_movieScore;
    RatingBar RatingBar_movieScoreView;
    RatingBar RatingBar_audience_rating;

    EditText EditText_movieContents;

    ImageView ImageView_grade;
    ImageView ImageView_image;

    boolean likeState = false;
    boolean dislikeState = false;

    int likeCount;
    int dislikeCount;

    float movieScore;

    String movieContents;

    public static CommentAdater adater;

    MovieInfo movieInfo;



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

        TextView_like = (TextView) findViewById(R.id.TextView_like);
        TextView_dislike = (TextView) findViewById(R.id.TextView_dislike);

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

        RatingBar_movieScore = (RatingBar) findViewById(R.id.RatingBar_movieScore);
        EditText_movieContents = (EditText) findViewById(R.id.EditText_movieContents);

        adater = new CommentAdater();
        adater.addItem(new CommentItem("kooktjsdud1", 3.5f, "핵노잼 ㅡ,.ㅡ"));
        adater.addItem(new CommentItem("kym7171", 5, "적당히 재밌다.오랜만에 잠 안오는 영화 봤네요."));

        ListView_write.setAdapter(adater);

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

        findViewById();
        requestMovie();
    }

    public void findViewById() {

        TextView_title = (TextView) findViewById(R.id.TextView_title);
        TextView_date = (TextView) findViewById(R.id.TextView_date);
        RatingBar_audience_rating = (RatingBar) findViewById(R.id.RatingBar_audience_rating);
        TextView_reviewer_rating = (TextView) findViewById(R.id.TextView_reviewer_rating);
        TextView_reservation_rate = (TextView) findViewById(R.id.TextView_reservation_rate);
        TextView_reservation_grade = (TextView) findViewById(R.id.TextView_reservation_grade);
        ImageView_grade = (ImageView) findViewById(R.id.ImageView_grade);
        ImageView_image = (ImageView) findViewById(R.id.ImageView_image);
        TextView_genre = (TextView) findViewById(R.id.TextView_genre);
        TextView_duration = (TextView) findViewById(R.id.TextView_duration);
        TextView_audience = (TextView) findViewById(R.id.TextView_audience);
        TextView_synopsis = (TextView) findViewById(R.id.TextView_synopsis);
        TextView_director = (TextView) findViewById(R.id.TextView_director);
        TextView_actor = (TextView) findViewById(R.id.TextView_actor);
        TextView_like = (TextView) findViewById(R.id.TextView_like);
        TextView_dislike = (TextView) findViewById(R.id.TextView_dislike);

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
//                        println("응답 받음 => " + response);

                        processResponse(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러 발생 => " + error.getMessage());
                    }
                }

        );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
//        println("영화목록 요청 보냄");

    }

    public void println(String data) {
//        TextView_println.append(data + "\n");
    }

    public void processResponse(String response){
        Gson gson = new Gson();

        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
        if(info.code == 200){
              Movie movie = gson.fromJson(response, Movie.class);
//            println("영화 갯수 : " + movie.result.size());

            for(int i = 0; i<movie.result.size(); i++){
                movieInfo = movie.result.get(i);
                TextView_title.setText(movieInfo.title);
                TextView_date.setText(movieInfo.date);
                RatingBar_audience_rating.setRating(movieInfo.audience_rating / 2);
                TextView_reviewer_rating.setText(movieInfo.reviewer_rating + "");
                TextView_reservation_rate.setText(movieInfo.reservation_rate + "");
                TextView_reservation_grade.setText(movieInfo.reservation_grade + "");
                setImageView_grade();
                setImageView_image();
                TextView_genre.setText(movieInfo.genre);
                TextView_duration.setText(movieInfo.duration + "");
                DecimalFormat df = new DecimalFormat("#,##0");
                TextView_audience.setText(df.format(movieInfo.audience));
                TextView_synopsis.setText(movieInfo.synopsis);
                TextView_director.setText(movieInfo.director);
                TextView_actor.setText(movieInfo.actor);
                TextView_like.setText(movieInfo.like + "");
                likeCount = movieInfo.like;
                TextView_dislike.setText(movieInfo.dislike + "");
                dislikeCount = movieInfo.dislike;
            }

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == 101){
            if(intent != null){
                movieScore = intent.getFloatExtra("movieScore", movieScore);
                movieContents = intent.getStringExtra("movieContents");
                adater.addItem(new CommentItem("*^^*", movieScore , movieContents ));
                adater.notifyDataSetChanged();
            }
        }
    }

    public void setImageView_grade() {

        switch (movieInfo.grade){
            case 12:
                ImageView_grade.setBackgroundResource(R.drawable.ic_12);
                break;
            case 15:
                ImageView_grade.setBackgroundResource(R.drawable.ic_15);
                break;
            case 19:
                ImageView_grade.setBackgroundResource(R.drawable.ic_19);
                break;
            default:
                ImageView_grade.setBackgroundResource(R.drawable.ic_all);
                break;
        }
    }

    public void setImageView_image() {
        String url = movieInfo.image;

        ImageLoadTask task = new ImageLoadTask(url, ImageView_image);
        task.execute();
    }


    public void showCommentWriteActivity() {
        Intent intent = new Intent(getApplicationContext(), CommentWriteActivity.class);
        startActivityForResult(intent, 101);
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
            view.setstarScore(item.getStarScore());
            view.setContent(item.getContent());
            return view;
        }
    }

    public void likeDecrCount() {
        likeCount--;
        TextView_like.setText(String.valueOf(likeCount));
        Button_likeicon.setBackgroundResource(R.drawable.ic_thumb_up);
    }

    public void likeIncrCount() {
        likeCount++;
        TextView_like.setText(String.valueOf(likeCount));
        Button_likeicon.setBackgroundResource(R.drawable.ic_thumb_up_selected);

    }

    public void dislikeDecrCount() {
        dislikeCount--;
        TextView_dislike.setText(String.valueOf(dislikeCount));
        Button_dislikeicon.setBackgroundResource(R.drawable.ic_thumb_down);
    }

    public void dislikeIncrCount() {
        dislikeCount++;
        TextView_dislike.setText(String.valueOf(dislikeCount));
        Button_dislikeicon.setBackgroundResource(R.drawable.ic_thumb_down_selected);
    }




}
