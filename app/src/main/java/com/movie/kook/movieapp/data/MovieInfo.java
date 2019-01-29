package com.movie.kook.movieapp.data;

/*
"title":"꾼"
"id":1
"date":"2017-11-22"
"user_rating":4.2
"audience_rating":8.36
"reviewer_rating":4.33
"reservation_rate":61.69
"reservation_grade":1
"grade":15
"thumb":"http://movie2.phinf.naver.net/20171107_251/1510033896133nWqxG_JPEG/movie_image.jpg?type=m99_141_2"
"image":"http://movie.phinf.naver.net/20171107_251/1510033896133nWqxG_JPEG/movie_image.jpg"
"photos":"http://movie.phinf.naver.net/20171010_164/1507615090097Sml1w_JPEG/movie_image.jpg?type=m665_443_2
          http://movie.phinf.naver.net/20171010_127/1507615090528Dgk7z_JPEG/movie_image.jpg?type=m665_443_2
          http://movie.phinf.naver.net/20171010_165/1507615090907eGO1R_JPEG/movie_image.jpg?type=m665_443_2
          http://movie.phinf.naver.net/20171010_39/1507615091158cqVLU_JPEG/movie_image.jpg?type=m665_443_2
          http://movie.phinf.naver.net/20171010_210/1507615091408Co9EB_JPEG/movie_image.jpg?type=m665_443_2"
"videos":"https://youtu.be/VJAPZ9cIbs0
          https://youtu.be/y422jVFruic
          https://youtu.be/JNL44p5kzTk"
"outlinks":null
"genre":"범죄"
"duration":117
"audience":476810
"synopsis":
"director":"장창원"
"actor":"현빈(황지성), 유지태(박희수 검사), 배성우(고석동)"
"like":2138
"dislike":393

 */

public class MovieInfo {


    public String title;
    public int id;
    public String date;
    public float user_rating;
    public float audience_rating;
    public float reviewer_rating;
    public float reservation_rate;
    public int reservation_grade;
    public int grade;
    public String thumb;
    public String image;
    public String photos;
    public String videos;
    public String outlinks;
    public String genre;
    public int duration;
    public int audience;
    public String synopsis;
    public String director;
    public String actor;
    public int like;
    public int dislike;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(float user_rating) {
        this.user_rating = user_rating;
    }

    public float getAudience_rating() {
        return audience_rating;
    }

    public void setAudience_rating(float audience_rating) {
        this.audience_rating = audience_rating;
    }

    public float getReviewer_rating() {
        return reviewer_rating;
    }

    public void setReviewer_rating(float reviewer_rating) {
        this.reviewer_rating = reviewer_rating;
    }

    public float getReservation_rate() {
        return reservation_rate;
    }

    public void setReservation_rate(float reservation_rate) {
        this.reservation_rate = reservation_rate;
    }

    public int getReservation_grade() {
        return reservation_grade;
    }

    public void setReservation_grade(int reservation_grade) {
        this.reservation_grade = reservation_grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getOutlinks() {
        return outlinks;
    }

    public void setOutlinks(String outlinks) {
        this.outlinks = outlinks;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAudience() {
        return audience;
    }

    public void setAudience(int audience) {
        this.audience = audience;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }
}
