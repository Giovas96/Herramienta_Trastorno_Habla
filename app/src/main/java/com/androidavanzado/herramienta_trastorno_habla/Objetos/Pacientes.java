package com.androidavanzado.herramienta_trastorno_habla.Objetos;



public class Pacientes {
    String  Nombre, Apellidopat, Apellidomat, Fechanac, Lugar, Direccion, Telefono, Escuela;

    public Pacientes() {
    }

    public Pacientes(String nombre, String apellidopat, String apellidomat, String fechanac, String lugar, String direccion, String telefono, String escuela) {

        Nombre = nombre;
        Apellidopat = apellidopat;
        Apellidomat = apellidomat;
        Fechanac = fechanac;
        Lugar = lugar;
        Direccion = direccion;
        Telefono = telefono;
        Escuela = escuela;

    }


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidopat() {
        return Apellidopat;
    }

    public void setApellidopat(String apellidopat) {
        Apellidopat = apellidopat;
    }

    public String getApellidomat() {
        return Apellidomat;
    }

    public void setApellidomat(String apellidomat) {
        Apellidomat = apellidomat;
    }

    public String getFechanac() {
        return Fechanac;
    }

    public void setFechanac(String fechanac) {
        Fechanac = fechanac;
    }

    public String getLugar() {
        return Lugar;
    }

    public void setLugar(String lugar) {
        Lugar = lugar;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getEscuela() {
        return Escuela;
    }

    public void setEscuela(String escuela) {
        Escuela = escuela;
    }

}


