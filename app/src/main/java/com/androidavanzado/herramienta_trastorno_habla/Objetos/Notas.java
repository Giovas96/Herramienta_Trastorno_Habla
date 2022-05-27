package com.androidavanzado.herramienta_trastorno_habla.Objetos;

import android.widget.TextView;

public class Notas {
    String titulo, asunto, resumen, descripcion, fecha;

    public Notas() {
    }

    public Notas(String titulo, String fecha, String asunto, String resumen, String descripcion) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.asunto = asunto;
        this.resumen = resumen;
        this.descripcion = descripcion;

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
