package com.example.juliana.julianalaboratorio_2;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int LOCATION_REQUEST_CODE = 101;
    private GoogleMap mMap;
    DataBaseHelper myDB;
    private String sharedPreferences_estado;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        myDB = new DataBaseHelper(this);
        sharedPreferences =  getSharedPreferences("preferences", Context.MODE_PRIVATE);
        verificarEstadoBase();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    public void verificarEstadoBase(){
        sharedPreferences_estado = sharedPreferences.getString("base","");
        if(sharedPreferences_estado.equals("")){
            insertarPosicionSedes();
            editor = sharedPreferences.edit();
            editor.putString("base","listo");
            editor.apply();
        }

    }

    public void insertarPosicionSedes(){
        //alajuela
        myDB.insertarMarca(10.0204774,-84.1971997,
                "Es un espacio interuniversitario, con un área de construcción de 11.616 metros cuadrados, Incluye un complejo académico-administrativo, aulas, laboratorios, biblioteca, oficinas, comedor para funcionarios y comedor para estudiantes, un espacio para distintas actividades recreativas y parqueo.",
                "Sede Alajuela");
        //limon
        myDB.insertarMarca(9.9986678,-83.0329685,
                "Uno de los acontecimientos que permitieron al TEC estar en Limón fue la aprobación en la Asamblea Legislativa, el 14 de marzo de 2013, de la modificación de la Ley 6.450. Este hecho dotó de más recursos a la universidad, los cuales se utilizaron prioritariamente para abrir la nueva sede.",
                "Sede Limon");
        //cartago
        myDB.insertarMarca(9.857282,-83.9123823,
                "Este campus cuenta con un terreno de 88 hectáreas y 7.000 metros cuadrados. Las instalaciones suman unos 60.000 metros cuadrados de construcción y llegarán a cerca de los 80.000 m2 durante 2017, año en el que se estarán inaugurando las obras construidas con el Proyecto de Mejora Institucional ",
                "Sede Cartago");
        // san carlos
        myDB.insertarMarca(10.3620625,-84.5099851,
                "El Instituto Tecnológico de Costa Rica (ITCR) aprobó la creación de esta nueva unidad académica y la vinculó directamente a la Dirección de la Sede Regional; esto le ha permitido al CTEC tener autonomía y a la vez, poder ubicarse en una zona que se está desarrollando con un gran potencial de crecimiento como es la Región Huetar Norte.",
                "Sede San Carlos");
        //san jose
        myDB.insertarMarca(9.9380884,-84.0754472,
                "El Centro Académico de San José se ubica en Barrio Amón, entre calles 5 y 7 y entre avenidas 9 y 11.  Sus aulas, oficinas administrativas, biblioteca y áreas recreativas se distribuyen entre edificios nuevos y casas antiguas con el valor histórico propio del barrio.",
                "Sede San Jose"); //
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        cargarMark();
        //imprimir();
        /*
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                System.out.println("click");
                mMap.addMarker(new MarkerOptions().position(latLng));
                myDB.insertarMarca(latLng.latitude,latLng.longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });
        */
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                double latitude = marker.getPosition().latitude;
                double longitud = marker.getPosition().longitude;
                mostrarDescripcion(latitude,longitud);
                return false;
            }
        });

        // Add a marker in Sydney and move the camera
        /*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */

        if (mMap != null) {
            int permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);

            if (permission == PackageManager.PERMISSION_GRANTED) {
                //poner marca en la posicion actual
                mMap.setMyLocationEnabled(true);

                //mover camara a localizacion actuak
                LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                Criteria mCriteria = new Criteria();
                String bestProvider = String.valueOf(manager.getBestProvider(mCriteria, true));
                Location mLocation = manager.getLastKnownLocation(bestProvider);
                final double currentLatitude = mLocation.getLatitude();
                final double currentLongitude = mLocation.getLongitude();
                LatLng currentLatLng = new LatLng(currentLatitude, currentLongitude);
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLatLng));

                CameraPosition cameraPosition1 = new CameraPosition.Builder()
                        .target(currentLatLng)
                        .tilt(90)
                        .zoom(9)
                        .build();
                //mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));
                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition1));


            } else {
                requestPermission(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        LOCATION_REQUEST_CODE);
            }
        }
    }
    public void mostrarDescripcion(double latitud, double longitud){
        String descripcion = "decripcion de la sede";
        String nombre = "nombre sede";
        ArrayList<Mark> lista = myDB.getDbInfo();
        System.out.println("len; " + lista.size());
        for (int i=0; i<lista.size();i++){
            if(lista.get(i).getLatitud() == latitud && lista.get(i).getLongitud() == longitud){
                descripcion =lista.get(i).getDescripcion();
                nombre = lista.get(i).getNombre();
            }

        }
        DialogFragment.newInstance(nombre,
                descripcion)
                .show(getSupportFragmentManager(), null);
    }
    public void cargarMark(){
        ArrayList<Mark> lista = myDB.getDbInfo();
        System.out.println("len; " + lista.size());
        for (int i=0; i<lista.size();i++){
            LatLng marca = new LatLng(lista.get(i).getLatitud(), lista.get(i).getLongitud());
            System.out.println("marca: " + marca);
            mMap.addMarker(new MarkerOptions().position(marca));
        }
    }
    public void imprimir(){
        ArrayList<Mark> lista = myDB.getDbInfo();
        for (int i=0; i<lista.size();i++){
            LatLng marca = new LatLng(lista.get(i).getLatitud(), lista.get(i).getLongitud());
            System.out.println("En marca: " + marca);
        }
    }


    protected void requestPermission(String permissionType,
                                     int requestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionType}, requestCode
        );
    }

    // marca la localizacion del telefono en el mapa
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,
                            "Unable to show location - permission required",
                            Toast.LENGTH_LONG).show();
                } else {

                    SupportMapFragment mapFragment =
                            (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                }
            }
        }
    }

}
