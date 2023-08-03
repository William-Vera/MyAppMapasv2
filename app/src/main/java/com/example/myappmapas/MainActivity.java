package com.example.myappmapas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    GoogleMap mapa;
    List<LatLng> lstlongitud;;
    PolylineOptions lineasg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        lstlongitud=new ArrayList<>();

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
        lineasg=new PolylineOptions();


        //mover le mapa a una  3D
        //LatLng madrid = new LatLng(40.68982789222906, -74.04509939550061);
        //CameraPosition camPos = new CameraPosition.Builder()
        //        .target(madrid)
        //        .zoom(19)
        //        .bearing(35) //noreste arriba
        //        .tilt(5) //punto de vista de la cámara 70 grados
        //        .build();
        //CameraUpdate camUpd3 =
        //        CameraUpdateFactory.newCameraPosition(camPos);
        //mapa.animateCamera(camUpd3);

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

        lineasg.add(latLng);

        lstlongitud.add(latLng);
        if (lstlongitud.size()==6)
        {
            PolylineOptions lineas = new
                    PolylineOptions()
                    .add(new LatLng(lstlongitud.get(0).latitude,lstlongitud.get(0).longitude))
                    .add(new LatLng(lstlongitud.get(1).latitude,lstlongitud.get(1).longitude))
                    .add(new LatLng(lstlongitud.get(2).latitude,lstlongitud.get(2).longitude))
                    .add(new LatLng(lstlongitud.get(3).latitude,lstlongitud.get(3).longitude))
                    .add(new LatLng(lstlongitud.get(4).latitude,lstlongitud.get(4).longitude))
                    .add(new LatLng(lstlongitud.get(5).latitude,lstlongitud.get(5).longitude));
            lineas.width(8);
            lineas.color(Color.RED);
            mapa.addPolyline(lineas);
        }

        //if(lineasg.getPoints().size()==6)
        //{
        //    lineasg.add(lineasg.getPoints().get(0));
        //    mapa.addPolyline(lineasg);
        //    lineasg.getPoints().clear();
        //}


        //if(lstlongitud.size()==6)
        //{
        //    for(int i=0;i<lstlongitud.size();i++) {
        //        PolylineOptions lineas2 = new PolylineOptions()
        //                .add(new LatLng(lstlongitud.get(i).latitude, lstlongitud.get(i).longitude));
        //    }
        //}
    }

}