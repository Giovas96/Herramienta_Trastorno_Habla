package com.androidavanzado.herramienta_trastorno_habla.Objetos;

public class Citas {
    String fechacita,horacita, duracion,motivo, observacion;

    public Citas() {
    }

    public Citas(String fechacita, String horacita, String duracion, String motivo, String observacion) {
        this.fechacita = fechacita;
        this.horacita = horacita;
        this.duracion = duracion;
        this.motivo = motivo;
        this.observacion = observacion;
    }

    public String getFechacita() {
        return fechacita;
    }

    public void setFechacita(String fechacita) {
        this.fechacita = fechacita;
    }

    public String getHoracita() {
        return horacita;
    }

    public void setHoracita(String horacita) {
        this.horacita = horacita;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
