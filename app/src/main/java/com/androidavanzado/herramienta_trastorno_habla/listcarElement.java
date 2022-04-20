package com.androidavanzado.herramienta_trastorno_habla;

public class listcarElement {
    public String color;
    public String nombre;
    public String tutor;
    public String telefono;

    public listcarElement(String color, String nombre, String tutor, String telefono) {
        this.color = color;
        this.nombre = nombre;
        this.tutor = tutor;
        this.telefono = telefono;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
