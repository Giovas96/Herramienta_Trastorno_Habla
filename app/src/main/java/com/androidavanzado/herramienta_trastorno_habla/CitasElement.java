package com.androidavanzado.herramienta_trastorno_habla;

public class CitasElement {
    public String color;
    public String nombre;
    public String Fecha;
    public String Hora;
    public String Motivo;

    public CitasElement(String color, String nombre, String fecha, String hora, String motivo) {
        this.color = color;
        this.nombre = nombre;
        Fecha = fecha;
        Hora = hora;
        Motivo = motivo;
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

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getMotivo() {
        return Motivo;
    }

    public void setMotivo(String motivo) {
        Motivo = motivo;
    }
}
