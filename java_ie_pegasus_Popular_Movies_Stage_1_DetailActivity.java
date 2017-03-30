package ie.pegasus.Popular_Movies_Stage_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Darragh Merrick March 2017.
 * This is the DetailActivity class, whicjh is started when a user clicks on a movie in the mainscreen UI
 * An intent is passed from the click listener in the MainscreenAdapter class, with the movie data passed in a putExtra.
 * **/
public class DetailActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView voteTextView;
    private TextView releaseTextView;
    private ImageView backdropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        //declare a view for each object needed from the movie data
        titleTextView = (TextView) findViewById(R.id.detail_title_view);
        descriptionTextView = (TextView) findViewById(R.id.detail_synopsis_view);
        voteTextView = (TextView) findViewById(R.id.detail_vote_view);
        releaseTextView = (TextView) findViewById(R.id.detail_release_view);
        voteTextView = (TextView) findViewById(R.id.detail_vote_view);
        backdropImageView = (ImageView) findViewById(R.id.backdrop_view);

        extractExtras();
    }

    private void extractExtras() {
        MovieData data = (MovieData) getIntent().getExtras().getSerializable("data");
        //take the serialized data from Extras and populate it into the declared views
        if (data != null) {
            titleTextView.setText(data.getMovie_data_title());
            descriptionTextView.setText(data.getMovie_data_synopsis());
            voteTextView.setText(data.getMovie_data_vote());
            releaseTextView.setText(data.getMovie_data_release());
             Picasso.with(this).load(data.getMovie_data_backdrop()).resize(700, 400).into(backdropImageView);

        }
    }
}
