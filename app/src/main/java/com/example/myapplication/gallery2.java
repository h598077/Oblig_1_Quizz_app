package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class gallery2 extends AppCompatActivity {
   private RecyclerView recyclerView;
    private List<Integer> imageResources;
    private List<String> imageTexts;
    private boolean isAscending;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gallery2);
        // Get the LinearLayout from the XML
         recyclerView = findViewById(R.id.recyclerView);

        FloatingActionButton fabAddImage = findViewById(R.id.fabAddImage);
        isAscending = true; // Flag to track sorting order

        // List of image resources
         imageResources = new ArrayList<>(Arrays.asList(
                R.drawable.cat,
                R.drawable.cat2,
                R.drawable.cat3
        ));

         imageTexts = new ArrayList<>(Arrays.asList(
                "Cat",
                "Cat2",
                "Cat3"
        ));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> savedImageResources = sharedPreferences.getStringSet("imageResources", new HashSet<>());
        Set<String> savedImageTexts = sharedPreferences.getStringSet("imageTexts", new HashSet<>());




        // If data exists, use it
        if (savedImageResources.size()!=0  && savedImageTexts.size()!=0  ) {

            imageResources = new ArrayList<>();
            imageTexts = new ArrayList<>();

            // Ensure we don't go out of bounds and both sets have the same number of items
            Iterator<String> imageResIterator = savedImageResources.iterator();
            Iterator<String> imageTextIterator = savedImageTexts.iterator();

            while (imageResIterator.hasNext() && imageTextIterator.hasNext()) {
                String res = imageResIterator.next();
                String text = imageTextIterator.next();


                    imageResources.add(Integer.parseInt(res));
                     imageTexts.add(text);
            }



        }



        ImageAdapter imageAdapter = new ImageAdapter(this, imageResources, imageTexts);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(imageAdapter);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fabAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageAdapter.addImage();




            }
        });
        Button buttonsort = findViewById(R.id.Sort);
        buttonsort.setOnClickListener(v -> {
            imageAdapter.sortImages(isAscending); // Sort in current order
            isAscending = !isAscending; // Toggle sorting order
            buttonsort.setText(isAscending ? "Sort Z-A" :"Sort A-Z" ); // Update button text
        });
    }


    @Override
    protected void onPause() {
        super.onPause();

        // Get the SharedPreferences instance
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save the image resources and image texts into SharedPreferences
        editor.putStringSet("imageResources", new HashSet<>(imageResources.stream().map(String::valueOf).collect(Collectors.toList())));
        editor.putStringSet("imageTexts", new HashSet<>(imageTexts));
        editor.apply();
    }

}
