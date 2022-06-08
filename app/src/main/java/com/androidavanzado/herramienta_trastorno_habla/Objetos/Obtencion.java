package com.androidavanzado.herramienta_trastorno_habla.Objetos;

public class Obtencion {
    String url, fonema;


    public Obtencion() {
    }

    public Obtencion(String url, String fonema) {

        this.url = url;
        this.fonema = fonema;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFonema() {
        return fonema;
    }

    public void setFonema(String fonema) {
        this.fonema = fonema;
    }
}
