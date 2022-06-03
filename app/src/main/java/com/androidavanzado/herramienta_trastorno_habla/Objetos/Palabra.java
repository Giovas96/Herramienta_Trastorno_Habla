package com.androidavanzado.herramienta_trastorno_habla.Objetos;

public class Palabra {

    String  position , name, sentence, imageUrl;

    public Palabra(){

    }

    public Palabra (String position, String name, String sentence, String imageUrl){

        this.position = position;
        this.name = name;
        this.sentence = sentence;
        this.imageUrl = imageUrl;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}