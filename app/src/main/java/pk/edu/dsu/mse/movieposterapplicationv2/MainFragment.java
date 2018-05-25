package pk.edu.dsu.mse.movieposterapplicationv2;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    List<String> movie_names = new ArrayList<>();
    List<String> poster_list = new ArrayList<>();
    ListView lv_movies;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        final Activity activity = getActivity();
        final EditText et_movie = activity.findViewById(R.id.movieId);
        Button findBtn = activity.findViewById(R.id.findId);
        lv_movies = activity.findViewById(R.id.nameId);

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieName;
                String url;


                movieName = et_movie.getText().toString();
                Log.e("TAG",movieName);
                url = "https://omdbapi.com/?s="+movieName+"&apikey=6084c3f2";
                parseJson(url);
            }
        });

        lv_movies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   if (isPortrait()){

                            Intent intent = new Intent(getActivity().getApplicationContext(),DetailsActivity.class);
                            intent.putExtra("poster",poster_list.get(position));
                            startActivity(intent);
                            /*intent.putStringArrayListExtra("movie_names",(ArrayList<String>) movie_names);
                            intent.putExtra("url",url);
                            startActivity(intent);*/

                        }
                        else{

                            DetailsFragment detailsFragment = (DetailsFragment)getActivity().getFragmentManager().findFragmentById(R.id.details_fragment_land_id);
                            detailsFragment.loadDetails(poster_list.get(position));
                        }

            }
        });


    }
    private void parseJson(final String url){

        Ion.with(this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.e("TAG",result+"");
                        JsonElement response = result.get("Response");
                        String res = response.toString();


                        res= res.charAt(1)+"";
                        Log.e("TAG","CHECK "+res);

                        if (res.equals("T")){
                            Toast.makeText(getActivity().getApplicationContext(),"Found",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(),"not Found",Toast.LENGTH_SHORT).show();
                        }

                        JsonArray array = result.getAsJsonArray("Search");

                        movie_names.clear();
                        poster_list.clear();

                        for (int i =0;i<array.size();i++) {

                            JsonObject jsonRes = (JsonObject) array.get(i);
                            JsonElement title = jsonRes.get("Title");
                            String poster = jsonRes.get("Poster").getAsString();

                            Log.e("TAG", title + "");
                            movie_names.add(title+"");
                            poster_list.add(poster+"");


                        }
                        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,movie_names);
                        lv_movies.setAdapter(adapter);

                      /*  if (isPortrait()){

                            Intent intent = new Intent(getActivity().getApplicationContext(),DetailsActivity.class);
                            intent.putStringArrayListExtra("movie_names",(ArrayList<String>) movie_names);
                            intent.putExtra("url",url);
                            startActivity(intent);

                        }
                        else{

                            DetailsFragment detailsFragment = (DetailsFragment)getActivity().getFragmentManager().findFragmentById(R.id.details_fragment_land_id);
                            detailsFragment.loadDetails(movie_names);
                        }*/

                    }
                });




    }
    private boolean isPortrait(){
        return (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }
}
