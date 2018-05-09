package com.example.juliana.julianalaboratorio_2;

/**
 * Created by Juliana on 09/05/2018.
 */

public class Mark {
    int id;
    double latitud;
    double longitud;
    String descripcion;
    String nombre;

    public Mark(int id, double latitud, double longitud, String descripcion, String nombre) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
