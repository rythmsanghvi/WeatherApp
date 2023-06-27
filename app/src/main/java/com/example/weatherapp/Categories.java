package com.example.weatherapp;// MainActivity.java

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.menu_categories);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.menu_categories) {
                    return true;
                } else if (itemId == R.id.menu_home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }

                return false;
            }

        });
        LinearLayout cardContainer = findViewById(R.id.card_container);

        // Read the card data from the file
        List<String> cardDataList = readCardData();

        for (String cardData : cardDataList) {
            // Split the card data into image and text
            String[] parts = cardData.split(",");

            if (parts.length == 2) {
                String image = parts[0].trim();
                String text = parts[1].trim();

                // Inflate the card view layout
                LinearLayout cardView = (LinearLayout) getLayoutInflater().inflate(R.layout.card_view, null);

                // Find the ImageView and TextView inside the card view
                ImageView imageView = cardView.findViewById(R.id.image_view);
                TextView textView = cardView.findViewById(R.id.text_view);

                // Set the image and text for the current card
                int imageResource = getResources().getIdentifier(image, "drawable", getPackageName());
                imageView.setImageResource(imageResource);
                textView.setText(text);
                Button button = cardView.findViewById(R.id.text_view);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle button click
                        openBlogActivity(text);
                    }
                });


                // Add the card view to the container
                cardContainer.addView(cardView);
            }
        }
    }


    private void openBlogActivity(String category) {
        // Read the blog content from blog_contents.xml based on the category
        String category_case = category;
        String blogContent = readBlogContent(category_case.toLowerCase());

        // Create an Intent to open the BlogActivity
        Intent intent = new Intent(this, BlogActivity.class);

        // Pass the category and content as extras to the intent
        intent.putExtra("title", category);
        intent.putExtra("content", blogContent);

        // Start the BlogActivity
        startActivity(intent);
    }


    private String readBlogContent(String category) {
        StringBuilder blogContent = new StringBuilder();

        try {
            // Read the file input stream
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(R.raw.blog_content);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Read each line and find the matching category
            String line;
            boolean isCategoryStarted = false;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Trim leading and trailing spaces

                if (line.contains("<category name=\"" + category + "\">")) {
                    isCategoryStarted = true;
                    // Extract the blog content from the starting line
                    int start = line.indexOf(">") + 1;
                    blogContent.append(line.substring(start)).append("\n");
                } else if (isCategoryStarted) {
                    // Append the current line as part of the blog content
                    if (line.contains("</category>")) {
                        // Stop appending lines once closing tag is encountered
                        int end = line.indexOf("</category>");
                        blogContent.append(line.substring(0, end));
                        break;
                    } else {
                        blogContent.append(line).append("\n");
                    }
                }
            }

            // Close the reader and input stream
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return blogContent.toString();
    }











    private List<String> readCardData() {
        List<String> cardDataList = new ArrayList<>();

        try {
            // Read the file input stream
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(R.raw.cards_data);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Read each line and add it to the list
            String line;
            while ((line = reader.readLine()) != null) {
                cardDataList.add(line);
            }

            // Close the reader and input stream
            reader.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cardDataList;
    }
}
