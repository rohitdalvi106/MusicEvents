package com.example.rohitdalvi.musicevents;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Data.CustomListviewAdapter;
import Model.Event;
import Util.Prefs;

public class MainActivity extends AppCompatActivity {

    private CustomListviewAdapter adapter;
    private ArrayList<Event> events = new ArrayList<>();
    private String urlLeft = "http://api.jambase.com/events?zipCode=";
    private String urlRight = "&page=1&api_key=h8yucwzvjubryw7rzp2vk28z&o=json";
    private ListView listView;
    private TextView selectedCity;

    //private String url = "http://api.jambase.com/events?zipCode=06604&page=1&api_key=h8yucwzvjubryw7rzp2vk28z&o=json";
    //private String url = "http://api.jambase.com/events?zipCode=06604&page=1&api_key=uwgn98a2xzdvqh9mz3grkg8p&o=json";
    private String url = "http://api.jambase.com/events?zipCode=06604&page=1&api_key=d2a8hbtgp6wx5wzncyb42f2z&o=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Prefs prefs = new Prefs(MainActivity.this);
        String zip = prefs.getZip();


        selectedCity = (TextView) findViewById(R.id.selectedlocationText);
        selectedCity.setText("Selected City: " + zip);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListviewAdapter(MainActivity.this, R.layout.list_row, events);
        listView.setAdapter(adapter);


        getEvents("06604");

    }


    private void getEvents(String city) {
        events.clear();
        String finalurl = urlLeft + city + urlRight;

        JsonObjectRequest eventsRequest = new JsonObjectRequest(Request.Method.GET,
                finalurl, (JSONObject) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject eventsObject = response.getJSONObject("Info");
                    //   Log.v("I", eventsObject.toString());

                    JSONArray eventsArray = response.getJSONArray("Events");
                    for (int i = 0; i < eventsArray.length(); i++) {

                        JSONObject jsonObject = eventsArray.getJSONObject(i);

                        //get Venue object
                        JSONObject venueObject = jsonObject.getJSONObject("Venue");
                        String venueName = venueObject.getString("Name");


                        //Artists name
                        JSONArray artistObject = jsonObject.getJSONArray("Artists");
                        JSONObject name = artistObject.getJSONObject(0);
                        String headlinerText = name.getString("Name");

                        //get Location object
                        String city = venueObject.getString("City");
                        String country = venueObject.getString("Country");
                        String street = venueObject.getString("Address");
                        String postalCode = venueObject.getString("ZipCode");


                        //start Date
                        String startDate = jsonObject.getString("Date");

                        //website
                        String website = venueObject.getString("Url");

                        Event event = new Event();

                        event.setVenueName(venueName);
                        event.setHeadLiner(headlinerText);
                        event.setCity(city);
                        event.setCountry(country);
                        event.setStreet(street);
                        event.setPostalCode(postalCode);
                        event.setStartDate(startDate);
                        event.setWebsite(website);

                        events.add(event);


                    }

                    adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(eventsRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.change_location) {
            showInputDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Change City");
        final EditText cityInput = new EditText(MainActivity.this);
        cityInput.setInputType(InputType.TYPE_CLASS_TEXT);
        cityInput.setHint("06604");
        builder.setView(cityInput);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Prefs cityPreference = new Prefs(MainActivity.this);
                cityPreference.setZip(cityInput.getText().toString());

                String newZip = cityPreference.getZip();


                selectedCity.setText("Selected city: " + newZip);

                showEvents(newZip);


            }
        });
        builder.show();
    }

    private void showEvents(String newCity) {

        getEvents(newCity);
    }
}
