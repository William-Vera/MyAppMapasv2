package com.example.myappmapas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    GoogleMap mapa;
    List<LatLng> lstcoordenadas;;
    PolylineOptions lineasg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        lstcoordenadas = new ArrayList<>();
        lineasg = new PolylineOptions();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;

        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mapa.getUiSettings().setZoomControlsEnabled(true);

        // Mover el mapa a una vista 2D
        CameraUpdate camUpd1 =
                CameraUpdateFactory.newLatLngZoom(new LatLng(40.68982789222906, -74.04509939550061), 18);
        mapa.moveCamera(camUpd1);

        mapa.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        LatLng punto = new LatLng(latLng.latitude, latLng.longitude);
        MarkerOptions marcador = new MarkerOptions();
        marcador.position(latLng);
        marcador.title("Punto");
        mapa.addMarker(marcador);

        lstcoordenadas.add(latLng);
        lineasg.add(latLng);
        /*
        if (lstcoordenadas.size()==6)
        {
            PolylineOptions lineas = new PolylineOptions();

            for (int i = 0; i < lstcoordenadas.size(); i++) {
                LatLng coordenada = new LatLng(lstcoordenadas.get(i).latitude, lstcoordenadas.get(i).longitude);
                lineas.add(coordenada);
            }
            LatLng coordenada = new LatLng(lstcoordenadas.get(0).latitude, lstcoordenadas.get(0).longitude);
            lineas.add(coordenada);
            lineas.width(8);
            lineas.color(Color.RED);
            mapa.addPolyline(lineas);
            lstcoordenadas.clear();
        }*/

        if (lineasg.getPoints().size() == 6) {
            lineasg.add(lineasg.getPoints().get(0));
            lineasg.color(Color.RED);
            mapa.addPolyline(lineasg);
            lineasg.getPoints().clear();
        }
    }

    private void obtenerDistancia() {
        String origenes = "";

        for (LatLng latLng : lstcoordenadas) {
            origenes += latLng.latitude + "," + latLng.longitude + "|";
        }
        origenes = origenes.substring(0, origenes.length() - 1);

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?" +
                "origins=" + origenes +
                "&destinations=" + origenes +
                "&units=meters" +
                "&key=AIzaSyCZ08ZGZLtgd4ZHQRQqAEuL-RJ10zIybxI";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray rows = response.getJSONArray("rows");

                        double distanciaTotal = 0;

                        // obtener distacias individuales
                        for (int i = 0; i < rows.length(); i++) {
                            JSONArray elements = rows.getJSONObject(i).getJSONArray("elements");
                            for (int j = 0; j < elements.length(); j++) {
                                JSONObject distance = elements.getJSONObject(j).getJSONObject("distance");
                                double distanceValue = distance.getDouble("value");
                                distanciaTotal += distanceValue;
                            }
                        }

                        mostrarDistancia(distanciaTotal);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void mostrarDistancia(double distanciatotal) {

        Toast.makeText(this, "Distancia total: " + distanciatotal + " metros", Toast.LENGTH_LONG).show();
    }
}