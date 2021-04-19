package com.example.mt.menitest;

import java.io.Serializable;

/**
 * Created by MLADEN on 7/8/2018.
 */

public class Troskovi implements Serializable {

    private int Id;
    private String Vrsta;
    private String Iznos;
    private String Datum;
    private String Tip;
    private int Kartica;
    private Double Prihod;
    private Double Rashod;

    public Troskovi(int id, String vrsta, String iznos, String datum, String tip, int kartica, Double prihod, Double rashod) {
        Id = id;
        Vrsta = vrsta;
        Iznos = iznos;
        Datum = datum;
        Tip = tip;
        Kartica = kartica;
        Prihod = prihod;
        Rashod = rashod;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getVrsta() {
        return Vrsta;
    }

    public void setVrsta(String vrsta) {
        Vrsta = vrsta;
    }

    public String getIznos() {
        return Iznos;
    }

    public void setIznos(String iznos) {
        Iznos = iznos;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }

    public String getTip() {
        return Tip;
    }

    public void setTip(String tip) {
        Tip = tip;
    }

    public int getKartica() {
        return Kartica;
    }

    public void setKartica(int kartica) {
        Kartica = kartica;
    }

    public Double getPrihod() {
        return Prihod;
    }

    public void setPrihod(Double prihod) {
        Prihod = prihod;
    }

    public Double getRashod() {
        return Rashod;
    }

    public void setRashod(Double rashod) {
        Rashod = rashod;
    }
}
