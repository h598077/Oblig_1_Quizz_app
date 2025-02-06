package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class quiz extends AppCompatActivity {
    private ImageView imageView;

    private Button buttonOption1, buttonOption2, buttonOption3;
    private List<Integer> imageResources;
    private List<String> imageTexts;

    private TextView scoreTextView;
    private int currentScore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);


        imageView = findViewById(R.id.imageView);
        buttonOption1 = findViewById(R.id.buttonOption1);
        buttonOption2 = findViewById(R.id.buttonOption2);
        buttonOption3 = findViewById(R.id.buttonOption3);
        scoreTextView = findViewById(R.id.scoreTextView);
        // Load the image data from SharedPreferences
        loadImageData();

        // Start the quiz
        showNextQuestion();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void loadImageData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> savedImageResources = sharedPreferences.getStringSet("imageResources", new HashSet<>());
        Set<String> savedImageTexts = sharedPreferences.getStringSet("imageTexts", new HashSet<>());

        imageResources = new ArrayList<>();
        imageTexts = new ArrayList<>();

        if (savedImageResources != null && savedImageTexts != null) {
            for (String res : savedImageResources) {
                imageResources.add(Integer.parseInt(res));
            }

            imageTexts.addAll(savedImageTexts);
        }
    }
    // Method to show the next question
    private void showNextQuestion() {
        Random random = new Random();
        int correctIndex = random.nextInt(imageResources.size());

        // Get the correct image and name
        int correctImage = imageResources.get(correctIndex);
        String correctName = imageTexts.get(correctIndex);

        // Show the image in the ImageView
        imageView.setImageResource(correctImage);

        // Prepare the options (correct + two random wrong answers)
        List<String> options = new ArrayList<>();
        options.add(correctName);

        // Add two random wrong answers
        while (options.size() < 3) {
            String wrongAnswer = imageTexts.get(random.nextInt(imageTexts.size()));
            if (!options.contains(wrongAnswer)) {
                options.add(wrongAnswer);
            }
        }

        // Shuffle the options so they are in random order
        java.util.Collections.shuffle(options);

        // Set the options on the buttons
        buttonOption1.setText(options.get(0));
        buttonOption2.setText(options.get(1));
        buttonOption3.setText(options.get(2));

        // Set click listeners for the buttons
        buttonOption1.setOnClickListener(v -> checkAnswer(options.get(0), correctName));
        buttonOption2.setOnClickListener(v -> checkAnswer(options.get(1), correctName));
        buttonOption3.setOnClickListener(v -> checkAnswer(options.get(2), correctName));
    }
    // Method to check if the answer is correct
    private void checkAnswer(String selectedAnswer, String correctAnswer) {


        if (selectedAnswer.equals(correctAnswer)) {
            currentScore++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong! Correct answer: " + correctAnswer, Toast.LENGTH_SHORT).show();
        }

        // Update score
        updateScore();

        // Show next question
        showNextQuestion();
    }
    // Method to update and display the score
    private void updateScore() {
        String scoreText = "Score: " + currentScore  ;
        scoreTextView.setText(scoreText);
    }
}