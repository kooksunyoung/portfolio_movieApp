package com.movie.kook.movieapp;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

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
import com.movie.kook.movieapp.data.ImageLoadTask;
import com.movie.kook.movieapp.data.Movie;
import com.movie.kook.movieapp.data.MovieInfo;
import com.movie.kook.movieapp.data.ResponseInfo;

import java.text.DecimalFormat;

public class RequestMovie {

    public Button Button_viewAll;

    public TextView TextView_write;
    public TextView TextView_title;
    public TextView TextView_date;
    public TextView TextView_reviewer_rating;
    public TextView TextView_reservation_rate;
    public TextView TextView_reservation_grade;
    public TextView TextView_genre;
    public TextView TextView_duration;
    public TextView TextView_audience;
    public TextView TextView_synopsis;
    public TextView TextView_director;
    public TextView TextView_actor;
    public static TextView TextView_like;
    public static TextView TextView_dislike;

    public RatingBar RatingBar_movieScore;
    public RatingBar RatingBar_movieScoreView;
    public RatingBar RatingBar_audience_rating;

    public ImageView ImageView_grade;
    public ImageView ImageView_image;

    static int likeCount;
    static int dislikeCount;

    MovieInfo movieInfo;

    public RequestMovie(LinearLayout layout, String activity, Context context) {

        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(context);
        }

        findViewById(layout, activity);
        requestMovie(activity);
    }


    public void findViewById(LinearLayout layout, String activity) {

        if(activity.equals("main")){
            TextView_title = (TextView) layout.findViewById(R.id.TextView_title);
            TextView_date = (TextView) layout.findViewById(R.id.TextView_date);
            RatingBar_audience_rating = (RatingBar) layout.findViewById(R.id.RatingBar_audience_rating);
            TextView_reviewer_rating = (TextView) layout.findViewById(R.id.TextView_reviewer_rating);
            TextView_reservation_rate = (TextView) layout.findViewById(R.id.TextView_reservation_rate);
            TextView_reservation_grade = (TextView) layout.findViewById(R.id.TextView_reservation_grade);
            ImageView_grade = (ImageView) layout.findViewById(R.id.ImageView_grade);
            ImageView_image = (ImageView) layout.findViewById(R.id.ImageView_image);
            TextView_genre = (TextView) layout.findViewById(R.id.TextView_genre);
            TextView_duration = (TextView) layout.findViewById(R.id.TextView_duration);
            TextView_audience = (TextView) layout.findViewById(R.id.TextView_audience);
            TextView_synopsis = (TextView) layout.findViewById(R.id.TextView_synopsis);
            TextView_director = (TextView) layout.findViewById(R.id.TextView_director);
            TextView_actor = (TextView) layout.findViewById(R.id.TextView_actor);
            TextView_like = (TextView) layout.findViewById(R.id.TextView_like);
            TextView_dislike = (TextView) layout.findViewById(R.id.TextView_dislike);
        } else if(activity.equals("commentViewAll")){
            TextView_title = (TextView) layout.findViewById(R.id.TextView_title);
            RatingBar_audience_rating = (RatingBar) layout.findViewById(R.id.RatingBar_audience_rating);
            TextView_reviewer_rating = (TextView) layout.findViewById(R.id.TextView_reviewer_rating);
            ImageView_grade = (ImageView) layout.findViewById(R.id.ImageView_grade);
        } else if(activity.equals("commentWrite")){
            TextView_title = (TextView) layout.findViewById(R.id.TextView_title);
            ImageView_grade = (ImageView) layout.findViewById(R.id.ImageView_grade);
        }


    }

    public void requestMovie(final String activity) {

        String url = "http://" + AppHelper.host + ":" + AppHelper.port + "/movie/readMovie";
        url += "?" + "id=1";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponse(response, activity);
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
//        println("영화목록 요청 보냄");

    }

    public void processResponse(String response, String activity){
        Gson gson = new Gson();

        ResponseInfo info = gson.fromJson(response, ResponseInfo.class);
        if(info.code == 200){
            Movie movie = gson.fromJson(response, Movie.class);

            for(int i = 0; i<movie.result.size(); i++){
                movieInfo = movie.result.get(i);
                if(activity.equals("main")){
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
                } else if(activity.equals("commentViewAll")){
                    TextView_title.setText(movieInfo.title);
                    RatingBar_audience_rating.setRating(movieInfo.audience_rating / 2);
                    TextView_reviewer_rating.setText(movieInfo.reviewer_rating + "");
                    setImageView_grade();
                } else if(activity.equals("commentWrite")){
                    TextView_title.setText(movieInfo.title);
                    setImageView_grade();
                }
            }

        }
    }

    public void setImageView_grade() {

        switch (movieInfo.grade){
            case 12: ImageView_grade.setBackgroundResource(R.drawable.ic_12); break;
            case 15: ImageView_grade.setBackgroundResource(R.drawable.ic_15); break;
            case 19: ImageView_grade.setBackgroundResource(R.drawable.ic_19); break;
            default: ImageView_grade.setBackgroundResource(R.drawable.ic_all); break;
        }
    }

    public void setImageView_image() {
        String url = movieInfo.image;

        ImageLoadTask task = new ImageLoadTask(url, ImageView_image);
        task.execute();
    }


}
