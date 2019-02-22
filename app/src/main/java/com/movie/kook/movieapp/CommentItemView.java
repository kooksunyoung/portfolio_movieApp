package com.movie.kook.movieapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class CommentItemView extends LinearLayout {

    TextView TextView_id;
    RatingBar RatingBar_movieScoreView;
    TextView TextView_MoviecontentsView;

    public CommentItemView(Context context) {
        super(context);
        init(context);
    }

    public CommentItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.comment_item_view, this, true);


        TextView_id = (TextView) findViewById(R.id.TextView_id);
        RatingBar_movieScoreView = (RatingBar) findViewById(R.id.RatingBar_movieScoreView);
        TextView_MoviecontentsView = (TextView) findViewById(R.id.TextView_MoviecontentsView);
    }

    public void setId(String id){ TextView_id.setText(id); }

    public void setStarScore(float starScore){
        RatingBar_movieScoreView.setRating(starScore);
    }

    public void setContent(String content){
        TextView_MoviecontentsView.setText(content);
    }


}
