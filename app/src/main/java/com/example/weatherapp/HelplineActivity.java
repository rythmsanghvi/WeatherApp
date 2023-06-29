package com.example.weatherapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.weatherapp.Helpline;
import com.example.weatherapp.NavigationManager;
import com.example.weatherapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HelplineActivity extends AppCompatActivity {

    private List<Helpline> helplineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_helpline);


        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int itemId = item.getItemId();
                        NavigationManager.navigate(HelplineActivity.this, itemId);
                        return true;
                    }
                }
        );

        // Load helplines from XML
        loadHelplines();
        // Display helplines in the layout
        displayHelplines();
    }

    private void loadHelplines() {
        helplineList = new ArrayList<>();
        XmlPullParserFactory xmlPullParserFactory;
        XmlPullParser xmlPullParser;
        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParser = xmlPullParserFactory.newPullParser();
            InputStream inputStream = getResources().openRawResource(R.raw.helplines);
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);
            int eventType = xmlPullParser.getEventType();
            Helpline helpline = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("helpline")) {
                            helpline = new Helpline();
                            String title = xmlPullParser.getAttributeValue(null, "title");
                            String number = xmlPullParser.getAttributeValue(null, "number");
                            helpline.setTitle(title);
                            helpline.setNumber(number);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase("helpline") && helpline != null) {
                            helplineList.add(helpline);
                        }
                        break;
                }
                eventType = xmlPullParser.next();
            }
            inputStream.close();
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    private void displayHelplines() {
        LinearLayout helplineContainer = findViewById(R.id.helpline_container);
        int cardMarginPx = getResources().getDimensionPixelSize(R.dimen.card_margin);

        for (final Helpline helpline : helplineList) {
            View helplineView = getLayoutInflater().inflate(R.layout.helpline_view, helplineContainer, false);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(cardMarginPx, cardMarginPx, cardMarginPx, cardMarginPx);
            helplineView.setLayoutParams(layoutParams);

            TextView titleTextView = helplineView.findViewById(R.id.title);
            TextView phoneNumberTextView = helplineView.findViewById(R.id.phonenumber);
            ImageButton imageButton = helplineView.findViewById(R.id.imageButton);

            titleTextView.setText(helpline.getTitle());
            phoneNumberTextView.setText(helpline.getNumber());
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phoneNumber = helpline.getNumber();
                    Uri phoneUri = Uri.parse("tel:" + phoneNumber);
                    Intent intent = new Intent(Intent.ACTION_DIAL, phoneUri);
                    startActivity(intent);
                }
            });

            helplineContainer.addView(helplineView);
        }
    }


}
