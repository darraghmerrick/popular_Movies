package ie.pegasus.Popular_Movies_Stage_1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Darragh Merrick March 2017.
 * This is the MainScreenAdapter class used for taking movie data and displaying it in the Mainscreen UI
 * **/

class MainScreenAdapter extends RecyclerView.Adapter<MainScreenAdapter.ViewHolder> {
    // ArrayList<MovieData> allMovies contains an array of data for all movies
    private ArrayList<MovieData> allMovies;
    private Context context;

    MainScreenAdapter(Context context, ArrayList<MovieData> allMovies) {
        this.allMovies = allMovies;
        this.context = context;
    }

    @Override
    public MainScreenAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainScreenAdapter.ViewHolder viewHolder, int i) {
        MovieData movie = allMovies.get(i);
         // MovieData movie is an array of a single movie's data
        viewHolder.setData(movie);
        Picasso.with(context).load(movie.getMovie_data_poster()).fit().into(viewHolder.movie_poster);    //.resize(width,height) is to device specific!!
    }

    /** ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
    // do it
    }
    }); **/
    @Override
    public int getItemCount() {
        return allMovies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView movie_poster;
        private MovieData data;

        ViewHolder(View view) {
            super(view);

            movie_poster = (ImageView) view.findViewById(R.id.poster_view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Create a new intent to open the {@link DetailActivity}
            Intent detailIntent = new Intent(context, DetailActivity.class);
            detailIntent.putExtra("data", data);
            //This line was added because the app was crashing
            detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            // Start the new activity
            context.startActivity(detailIntent);
        }


        void setData(MovieData data) {
            this.data = data;
        }
    }

}