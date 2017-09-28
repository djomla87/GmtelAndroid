package com.example.mt.menitest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mladen.todorovic on 20.09.2017..
 */

public class Task {


    private int IdTask;
    private String SerijskiBroj;
    private String Vozilo;
    private String Istovar;
    private String Roba;
    private String Status;
    private String DatumAzuriranja;
    private String Utovar;

    public List<Task> getAllTask() {
        return new ArrayList<Task>();
    }

    public Task(int idTask, String serijskiBroj, String vozilo, String istovar, String roba, String status, String datumAzuriranja, String utovar) {
        IdTask = idTask;
        SerijskiBroj = serijskiBroj;
        Vozilo = vozilo;
        Istovar = istovar;
        Roba = roba;
        Status = status;
        DatumAzuriranja = datumAzuriranja;
        Utovar = utovar;
    }

    public int getIdTask() {
        return IdTask;
    }

    public void setIdTask(int idTask) {
        IdTask = idTask;
    }
    public String getSerijskiBroj() {
        return SerijskiBroj;
    }

    public void setSerijskiBroj(String serijskiBroj) {
        SerijskiBroj = serijskiBroj;
    }

    public String getVozilo() {
        return Vozilo;
    }

    public void setVozilo(String vozilo) {
        Vozilo = vozilo;
    }

    public String getIstovar() {
        return Istovar;
    }

    public void setIstovar(String istovar) {
        Istovar = istovar;
    }

    public String getRoba() {
        return Roba;
    }

    public void setRoba(String roba) {
        Roba = roba;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDatumAzuriranja() {
        return DatumAzuriranja;
    }

    public void setDatumAzuriranja(String datumAzuriranja) {
        DatumAzuriranja = datumAzuriranja;
    }

    public String getUtovar() {
        return Utovar;
    }

    public void setUtovar(String utovar) {
        Utovar = utovar;
    }
}
