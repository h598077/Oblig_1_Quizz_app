package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



    }

    public void onClick(View v) {
        // Code to execute when the button is clicked
       // if (v.getId() == R.id.my_other_button){
       //     Log.d("MainActivity", "onClick: ");
       //     Toast.makeText(MainActivity.this, "my_other_button Clicked!" +v.getId(), Toast.LENGTH_SHORT).show();  // raise Notification
       // }
       // if (v.getId() == R.id.button){
       //     Log.d("MainActivity", "onClick: ");
       //     Toast.makeText(MainActivity.this, "Button Clicked!" +v.getId(), Toast.LENGTH_SHORT).show();  // raise Notification
      //  }
       if(v.getId() == R.id.galery){

          Intent myIntent = new Intent(MainActivity.this, gallery2.class);
           MainActivity.this.startActivity(myIntent);

        }

        if(v.getId() == R.id.quiz){

            Intent myIntent = new Intent(MainActivity.this, quiz.class);
            MainActivity.this.startActivity(myIntent);

        }



    }



}