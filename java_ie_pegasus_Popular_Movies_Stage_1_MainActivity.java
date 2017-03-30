package ie.pegasus.Popular_Movies_Stage_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Darragh Merrick March 2017.
 * This is the MainActivity class of the Popular Movies, Stage 1 application
 * **/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void onStart() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
            super.onStart();
            // Call updateMovies to retrieve the JSON data from www.themoviedb.org and returned the parsed objects from the FetchMovieTask AsyncTask.
            updateMovies();
        }
        else {
           noConnectivity(this);

        }
        }

public void noConnectivity(Context context) {
    Intent detailIntent = new Intent(context, NoNetworkActivity.class);

    // Start the new activity
    context.startActivity(detailIntent);
}


    private void updateMovies() {
        FetchMovieTask movieTask = new FetchMovieTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //The user will choose in a setting whether to retrieve Most popular or top_rated, but on start popular will be the default search
        String search = prefs.getString(getString(R.string.pref_search_key_popular), getString(R.string.pref_search_values_popular));
        movieTask.execute(search);
        String[] results = new String[0];
        try {
            results = movieTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        initMainView(results);
    }

    // This method will initiate the UI of the applications mainscreen
    private void initMainView(String[] results) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movie_grid);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new GridLayoutManager(getApplicationContext(), getSpanCount());
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<MovieData> All_Movies = prepareData(results);
        MainScreenAdapter adapter = new MainScreenAdapter(getApplicationContext(), All_Movies);
        recyclerView.setAdapter(adapter);
    }
    // To change the grid layout to 3 rows for horizontal orientation or use 2 for vertical
    private int getSpanCount() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return 3;
        else return 2;
    }

    private ArrayList<MovieData> prepareData(String[] results) {
       // take the single string json data returned the asynch task and turn it into a string array
        ArrayList<MovieData> All_Movies = new ArrayList<>();
        for (int i = 0; i < results.length - 1; i++) {
            String result = results[i];
            MovieData moviedata = new MovieData();
            //Split the string using the && delimiter, I inserted during it's creation
            String[] movie_objects = result.split("&&");
            moviedata.setMovie_data_title(movie_objects[0]);
            moviedata.setMovie_data_poster(movie_objects[1]);
            moviedata.setMovie_data_backdrop(movie_objects[2]);
            moviedata.setMovie_data_synopsis(movie_objects[3]);
            moviedata.setMovie_data_release(movie_objects[4]);
            moviedata.setMovie_data_vote(movie_objects[5]);
            All_Movies.add(moviedata);
        }
        return All_Movies;
    }
}