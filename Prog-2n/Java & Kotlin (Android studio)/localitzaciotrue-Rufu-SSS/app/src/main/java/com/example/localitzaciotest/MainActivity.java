package com.example.localitzaciotest;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private TextView latitudeText, longitudeText, timestampText, astronautsCountText, statusText;
    private Button updateButton;
    private CardView locationCard, spaceCard;
    private View progressIndicator;
    private SharedPreferences prefs;

    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private Runnable fetchDataRunnable;
    private boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupLocationManager();
        checkPermissions();
        loadLastKnownData();
        startPeriodicUpdates();
    }

    private void initViews() {
        latitudeText = findViewById(R.id.latitudeText);
        longitudeText = findViewById(R.id.longitudeText);
        timestampText = findViewById(R.id.timestampText);
        astronautsCountText = findViewById(R.id.astronautsCountText);
        statusText = findViewById(R.id.statusText);
        updateButton = findViewById(R.id.updateButton);
        locationCard = findViewById(R.id.locationCard);
        spaceCard = findViewById(R.id.spaceCard);
        progressIndicator = findViewById(R.id.progressIndicator);

        updateButton.setOnClickListener(v -> refreshAllData());

        prefs = getSharedPreferences("appPrefs", MODE_PRIVATE);
    }

    private void setupLocationManager() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
        } else {
            refreshAllData();
        }
    }

    private void loadLastKnownData() {
        String lastTime = prefs.getString("lastUpdate", "---");
        String lastLat = prefs.getString("lastLat", "---");
        String lastLon = prefs.getString("lastLon", "---");
        int lastAstronauts = prefs.getInt("lastAstronauts", 0);

        timestampText.setText(lastTime);
        latitudeText.setText(lastLat);
        longitudeText.setText(lastLon);

        if (lastAstronauts > 0) {
            astronautsCountText.setText(String.valueOf(lastAstronauts));
        }
    }

    private void refreshAllData() {
        if (isUpdating) return;

        isUpdating = true;
        showLoading(true);
        statusText.setText(R.string.status_updating);

        updateLocation();
        fetchAstronautData();
    }

    private void showLoading(boolean show) {
        progressIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
        updateButton.setEnabled(!show);
    }

    private void updateLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showLoading(false);
            isUpdating = false;
            return;
        }

        try {
            Location lastKnown = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnown != null) {
                onLocationChanged(lastKnown);
            }
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);
        } catch (Exception e) {
            try {
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
            } catch (Exception ex) {
                statusText.setText(R.string.error_location);
                showLoading(false);
                isUpdating = false;
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        String lat = String.format(Locale.getDefault(), "%.6f°", location.getLatitude());
        String lon = String.format(Locale.getDefault(), "%.6f°", location.getLongitude());

        latitudeText.setText(lat);
        longitudeText.setText(lon);

        String now = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(new Date());
        timestampText.setText(now);

        prefs.edit()
                .putString("lastUpdate", now)
                .putString("lastLat", lat)
                .putString("lastLon", lon)
                .apply();

        statusText.setText(R.string.status_success);

        // Animació subtil de la card
        locationCard.animate().scaleX(1.02f).scaleY(1.02f).setDuration(100)
                .withEndAction(() -> locationCard.animate().scaleX(1f).scaleY(1f).setDuration(100).start());
    }

    @Override public void onStatusChanged(String provider, int status, Bundle extras) {}
    @Override public void onProviderEnabled(@NonNull String provider) {}
    @Override public void onProviderDisabled(@NonNull String provider) {}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            refreshAllData();
        } else {
            statusText.setText(R.string.permission_denied);
            showLoading(false);
        }
    }

    private void fetchAstronautData() {
        executor.execute(() -> {
            String resultText;
            HttpURLConnection conn = null;
            try {
                URL url = new URL("https://api.open-notify.org/astros.json");
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();

                    JSONObject json = new JSONObject(sb.toString());
                    int number = json.getInt("number");
                    resultText = String.valueOf(number);
                } else {
                    resultText = null;
                }

            } catch (Exception e) {
                e.printStackTrace();
                resultText = null;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            final String finalResult = resultText;
            mainHandler.post(() -> {
                if (finalResult != null) {
                    try {
                        int num = Integer.parseInt(finalResult);
                        astronautsCountText.setText(String.valueOf(num));
                        prefs.edit().putInt("lastAstronauts", num).apply();

                        // Animació de la card d'astronautes
                        spaceCard.animate().scaleX(1.02f).scaleY(1.02f).setDuration(100)
                                .withEndAction(() -> spaceCard.animate().scaleX(1f).scaleY(1f).setDuration(100).start());

                    } catch (NumberFormatException e) {
                        statusText.setText(R.string.error_fetch);
                    }
                } else {
                    statusText.setText(R.string.error_fetch);
                }

                showLoading(false);
                isUpdating = false;
            });
        });
    }

    private void startPeriodicUpdates() {
        fetchDataRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isUpdating) {
                    fetchAstronautData();
                }
                mainHandler.postDelayed(this, 600000); // 10 minuts
            }
        };
        mainHandler.postDelayed(fetchDataRunnable, 600000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainHandler.removeCallbacks(fetchDataRunnable);
        executor.shutdown();

        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }
}