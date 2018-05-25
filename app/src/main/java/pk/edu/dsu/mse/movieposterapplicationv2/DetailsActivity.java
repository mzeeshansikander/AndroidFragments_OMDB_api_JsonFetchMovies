package pk.edu.dsu.mse.movieposterapplicationv2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        String posterLink = intent.getStringExtra("poster");
        Log.e("TAG","Poster" +posterLink);
        ListView lv_names = findViewById(R.id.nameId);
        ImageView imageView = findViewById(R.id.imageId);
        Picasso.get()
                .load(posterLink)
                .into(imageView);



    }
}
