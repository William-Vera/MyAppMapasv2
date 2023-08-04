package com.example.myappmapas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

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
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        lstcoordenadas=new ArrayList<>();
        lineasg= new PolylineOptions();

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa=googleMap;

        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mapa.getUiSettings().setZoomControlsEnabled(true);

        //mover le mapa a una  2D
        CameraUpdate camUpd1 =
                CameraUpdateFactory
                        .newLatLngZoom(new LatLng(40.68982789222906, -74.04509939550061), 18);
        mapa.moveCamera(camUpd1);
        /*
        lineasg=new PolylineOptions();
        lineasg.width(8);
        lineasg.color(Color.RED);
        mapa.addPolyline(lineasg);*/

        /*
        //mover le mapa a una  3D
        LatLng madrid = new LatLng(40.68982789222906, -74.04509939550061);
        CameraPosition camPos = new CameraPosition.Builder()
                .target(madrid)
                .zoom(19)
                .bearing(45) //noreste arriba
                .tilt(70) //punto de vista de la cámara 70 grados
                .build();
        CameraUpdate camUpd3 =
                CameraUpdateFactory.newCameraPosition(camPos);
        mapa.animateCamera(camUpd3);*/
        mapa.setOnMapClickListener(this);

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        //ArrayList<LatLng> listapuntos=new ArrayList<>(6);
        //LatLng punto = new LatLng(latLng.latitude,
        //        latLng.longitude);
        LatLng punto = new LatLng(latLng.latitude, latLng.longitude);
        MarkerOptions marcador= new MarkerOptions();

        marcador.position(latLng);
        marcador.title("punto");
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



        if(lineasg.getPoints().size()==6)
        {
            lineasg.add(lineasg.getPoints().get(0));
            lineasg.color(Color.RED);
            mapa.addPolyline(lineasg);
            lineasg.getPoints().clear();
        }

    }

}