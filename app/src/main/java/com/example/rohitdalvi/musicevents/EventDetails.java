package com.example.rohitdalvi.musicevents;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import Model.Event;

public class EventDetails extends AppCompatActivity {
    private Event event;
    private TextView artist;
    private TextView venue;
    private TextView where;
    private TextView when;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        event = (Event) getIntent().getSerializableExtra("eventObj");

        artist = (TextView)findViewById(R.id.detsHeadliner);
        venue = (TextView)findViewById(R.id.detsVenue);
        where = (TextView)findViewById(R.id.detsWhere);
        when = (TextView)findViewById(R.id.detsWhen);



        artist.setText("Artist: "+ event.getHeadLiner());
        venue.setText("Venue: " + event.getVenueName());
        when.setText("When: " + event.getStartDate());
        where.setText("Where: " + event.getStreet() + ", " + event.getCity() + ", " + event.getCountry());


        FloatingActionButton floatingActionButton =  (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = event.getWebsite();
                if(! url.equals("")){
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);

                }
                else{
                    Toast.makeText(getApplicationContext(), "No Details Available", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }

}
