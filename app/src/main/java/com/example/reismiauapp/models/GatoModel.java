package com.example.reismiauapp.models;

import java.util.List;
import java.util.Map;

public class GatoModel {

    public String name;
    public String description;
    public String gender;
    public String age;
    public String race;
    public String size;
    public List<Map<String, String>> photos;
    public int status;

    public GatoModel() {
    }
    public GatoModel(String nome, String descricao) {
        this.name = nome;
        this.description = descricao;
    }
}
