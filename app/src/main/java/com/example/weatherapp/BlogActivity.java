package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BlogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        // Retrieve the title and content data from the intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        // Find the TextViews in the blog activity layout
        ImageView imageView = findViewById(R.id.image_view);
        TextView titleTextView = findViewById(R.id.title_text_view);
        TextView contentTextView = findViewById(R.id.content_text_view);

        // Set the title and content in the TextViews
        String image = title.toLowerCase();
        int imageResource = getResources().getIdentifier(image, "drawable", getPackageName());
        imageView.setImageResource(imageResource);
        titleTextView.setText(title);
        contentTextView.setText(content);
    }
}
