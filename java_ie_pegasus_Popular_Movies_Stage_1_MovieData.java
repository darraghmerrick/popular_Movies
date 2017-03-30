package ie.pegasus.Popular_Movies_Stage_1;

import java.io.Serializable;

/**
 * Created by Darragh Merrick March 2017.
 * This is the MovieData class. It used to create objects for each piece of movie data.
 * It is used by the adapters to populate the views
 * The movie data is set(setMovie_data_</object>) by the preparedata() method from the main activity.
 * The movie data is retieved (getMovie_data_</object>) by the MainScreenAdapter and DetailActivity
 * **/

class MovieData implements Serializable{

    private String movie_data_poster;
    private String movie_data_title;
    private String movie_data_release;
    private String movie_data_backdrop;
    private String movie_data_vote;
    private String movie_data_synopsis;

    String getMovie_data_title() {
        return movie_data_title;
    }

    void setMovie_data_title(String movie_data_title) {
        this.movie_data_title = movie_data_title;
    }

    String getMovie_data_poster() {
        return movie_data_poster;
    }

    void setMovie_data_poster(String movie_data_poster) {
        this.movie_data_poster = movie_data_poster;
    }

    String getMovie_data_release() {return movie_data_release;}
    void setMovie_data_release(String movie_data_release) {
        this.movie_data_release = movie_data_release;
    }

    String getMovie_data_backdrop() {
        return movie_data_backdrop;
    }
    void setMovie_data_backdrop(String movie_data_backdrop) {
        this.movie_data_backdrop = movie_data_backdrop;
    }

    String getMovie_data_vote() {
        return movie_data_vote;
    }
    void setMovie_data_vote(String movie_data_vote) {
        this.movie_data_vote = movie_data_vote;
    }
    String getMovie_data_synopsis() {
        return movie_data_synopsis;
    }
    void setMovie_data_synopsis(String movie_data_synopsis) {
        this.movie_data_synopsis = movie_data_synopsis;
    }
}
