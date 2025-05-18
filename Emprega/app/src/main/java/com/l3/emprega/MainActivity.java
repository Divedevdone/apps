package com.l3.emprega;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** @noinspection CallToPrintStackTrace*/
public class MainActivity extends AppCompatActivity {

    EditText searchInput;
    ListView jobListView;
    ArrayAdapter<String> adapter;
    ArrayList<String> jobList = new ArrayList<>();

    final String API_KEY = "692fc0eabamsh8150b0cec0d7c71p13409fjsnb65bdfdd45fd";
    final String API_HOST = "jsearch.p.rapidapi.com";

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    FusedLocationProviderClient fusedLocationClient;

    String userCity = "Paraná"; // default in case location fails

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchInput = findViewById(R.id.searchInput);
        jobListView = findViewById(R.id.jobListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jobList);
        jobListView.setAdapter(adapter);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        requestLocationPermission();
    }

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            getUserLocation();
        }
    }

    private void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permissions if not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                getCityFromLocation(location);
            } else {
                Toast.makeText(this, "Local não disponível", Toast.LENGTH_SHORT).show();
                fetchJobs(""); // fallback
            }
        });
    }


    private void getCityFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            assert addresses != null;
            if (!Objects.requireNonNull(addresses).isEmpty()) {
                userCity = addresses.get(0).getLocality();
                fetchJobs(""); // fetch jobs with empty query = any job
            }
        } catch (Exception e) {
            e.printStackTrace();
            fetchJobs(""); // fallback if geocoder fails
        }
    }

    private void fetchJobs() {
        fetchJobs(null);
    }

    /** @noinspection CallToPrintStackTrace*/
    private void fetchJobs(String query) {
        executor.execute(() -> {
            ArrayList<String> results = new ArrayList<>();
            try {
                JSONArray data = getJsonArray(query);

                for (int i = 0; i < data.length(); i++) {
                    JSONObject job = data.getJSONObject(i);
                    String title = job.getString("job_title");
                    String company = job.getString("employer_name");
                    String location = job.getString("job_city");
                    results.add(title + " @ " + company + " - " + location);
                }

            } catch (Exception e) {
                results.add("Erro ao buscar vagas.");
                e.printStackTrace();
            }

            handler.post(() -> {
                jobList.clear();
                jobList.addAll(results);
                adapter.notifyDataSetChanged();
            });
        });
    }

    @NonNull
    private JSONArray getJsonArray(String query) throws IOException, JSONException {
        String encodedQuery = query.isEmpty() ? "job" : query;
        String encodedCity = userCity.replace(" ", "%20");
        String apiUrl = "https://jsearch.p.rapidapi.com/search?query=" + encodedQuery + "&location=" + encodedCity + "&page=1&num_pages=1";

        URL url = new URL(apiUrl);
        JSONObject json = getJsonObject(url);
        JSONArray data;
        data = json.getJSONArray("data");
        return data;
    }

    @NonNull
    private JSONObject getJsonObject(URL url) throws IOException, JSONException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-RapidAPI-Key", API_KEY);
        conn.setRequestProperty("X-RapidAPI-Host", API_HOST);

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) response.append(line);
        reader.close();

        JSONObject json;
        json = new JSONObject(response.toString());
        return json;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // ✅ important!

        if (requestCode == 100 &&
                grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getUserLocation();
        } else {
            Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show();
            fetchJobs(""); // fallback if location permission is denied
        }
    }

}
