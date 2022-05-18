package com.androidavanzado.herramienta_trastorno_habla.Objetos;

public class Evaluacion {
    String fonema, inicial,directa, inversa,intervolica, trabada,fina, gr,gl, gs, observacion, adquirido;

    public Evaluacion() {
    }

    public Evaluacion(String fonema, String inicial, String directa, String inversa, String intervolica, String trabada, String fina, String gr, String gl, String gs, String observacion, String adquirido) {
        this.fonema = fonema;
        this.inicial = inicial;
        this.directa = directa;
        this.inversa = inversa;
        this.intervolica = intervolica;
        this.trabada = trabada;
        this.fina = fina;
        this.gr = gr;
        this.gl = gl;
        this.gs = gs;
        this.observacion = observacion;
        this.adquirido = adquirido;
    }

    public String getFonema() {
        return fonema;
    }

    public void setFonema(String fonema) {
        this.fonema = fonema;
    }

    public String getInicial() {
        return inicial;
    }

    public void setInicial(String inicial) {
        this.inicial = inicial;
    }

    public String getDirecta() {
        return directa;
    }

    public void setDirecta(String directa) {
        this.directa = directa;
    }

    public String getInversa() {
        return inversa;
    }

    public void setInversa(String inversa) {
        this.inversa = inversa;
    }

    public String getIntervolica() {
        return intervolica;
    }

    public void setIntervolica(String intervolica) {
        this.intervolica = intervolica;
    }

    public String getTrabada() {
        return trabada;
    }

    public void setTrabada(String trabada) {
        this.trabada = trabada;
    }

    public String getFina() {
        return fina;
    }

    public void setFina(String fina) {
        this.fina = fina;
    }

    public String getGr() {
        return gr;
    }

    public void setGr(String gr) {
        this.gr = gr;
    }

    public String getGl() {
        return gl;
    }

    public void setGl(String gl) {
        this.gl = gl;
    }

    public String getGs() {
        return gs;
    }

    public void setGs(String gs) {
        this.gs = gs;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getAdquirido() {
        return adquirido;
    }

    public void setAdquirido(String adquirido) {
        this.adquirido = adquirido;
    }
}
