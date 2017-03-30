package ie.pegasus.Popular_Movies_Stage_1;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Pegasus on 19/03/2017.
 * This class uses two methods to;
 * 1) retrieve the JSON data from https://api.themoviedb.org, -> protected String[] doInBackground
 * 2) parse the relevant objects from the raw JSON and returning the data in a String[] -> private String[] getMovieDataFromJson
 */

class FetchMovieTask extends AsyncTask<String, Void, String[]> {
        //The arguments for Asynch task are as follows, use void if they are not needed
        //1. Params, the type of the parameters sent to the task upon execution.
        //2. Progress, the type of the progress units published during the background computation.
        //3. Result, the type of the result of the background computation.
        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */

        @Override
        protected String[] doInBackground(String... params) {

            // If there's no movie serach param, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {
                return null;
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            String MOVIE_DATABASE_URL =
                    "https://api.themoviedb.org/3/movie/";

            String PARAM_API = "api_key";
            String api_key = "f9c49fc67bf403d0797e6333b70f2bdf";
            String MovieDB_query = params[0];
            String PARAM_LANGUAGE = "language";
            String language = "en-US";

            // Construct the URL for the themoviedb query
            // Possible parameters are top_rated or popular


            Uri builtUri = Uri.parse(MOVIE_DATABASE_URL).buildUpon()
                    .appendPath(MovieDB_query)
                    .appendQueryParameter(PARAM_API, api_key)
                    .appendQueryParameter(PARAM_LANGUAGE, language)

                    .build();

            URL url = null;
            try {
                url = new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                // Create the request to themoviedb, and open the connection
                assert url != null;
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }


            try {
                // The getMovieDataFromJson will return the parsed data from the raw JSON. This is the String [] that is returned back to the main activity at the end
                return getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }


        // This will only happen if there was an error getting or parsing the forecast.
        private String[] getMovieDataFromJson(String movieJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MDB_RESULTS = "results";  //All of the JSON data is returned in an array named results. results[] has 14 items
            final String MDB_POSTER = "poster_path"; //an object in results
            final String MDB_TITLE = "original_title"; //an object in results
            final String MDB_RELEASE_DATE= "release_date"; //an object in results
            final String MDB_BACKDROP = "backdrop_path"; //an object in results
            final String MDB_VOTE = "vote_average"; //an object in results
            final String MDB_SYNOPSIS = "overview"; //an object in results

            JSONObject movieJson = new JSONObject(movieJsonStr); //The entire JSON results array is contained within this object
            JSONArray movieArray = movieJson.getJSONArray(MDB_RESULTS);

            String[] resultStrs = new String[movieArray.length()+1];

            for(int i = 0; i < movieArray.length(); i++) {

                String poster;
                String title;
                String release;
                String backdrop;
                String vote;
                String synopsis;
                // Get the JSON object representing the movie
                JSONObject singleMovie = movieArray.getJSONObject(i);

                // description is in a array called "results", which is 14 element long.
               // JSONObject movieObject = singleMovie.getJSONArray(MDB_RESULTS).getJSONObject(i);
                poster = singleMovie.getString(MDB_POSTER);
                title = singleMovie.getString(MDB_TITLE);
                release = singleMovie.getString(MDB_RELEASE_DATE);
                backdrop = singleMovie.getString(MDB_BACKDROP);
                vote = singleMovie.getString(MDB_VOTE);
                synopsis = singleMovie.getString(MDB_SYNOPSIS);
                resultStrs[i] = title  + "&&" + "https://image.tmdb.org/t/p/w185"+poster + "&&" + "https://image.tmdb.org/t/p/w500"+backdrop
                        + "&&" + "Plot: "+ synopsis + "&&" + "Release Date: " + release  + "&&" + "Rating: "+ vote;
            }
            return resultStrs;

        }



    }
