package com.example.reismiauapp.models;

import java.util.List;
import java.util.Map;

public class GatoModel {

    public String petId;

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
    public GatoModel(String name, String description, String gender, String age, String race, String size, int status) {
        this.name = name;
        this.description = description;
        this.gender = gender;
        this.age = age;
        this.race = race;
        this.size = size;
        this.status = status;
    }

    public GatoModel(String name, String description, String gender, String age, String race, String size, int status, List<Map<String, String>> photos) {
        this.name = name;
        this.description = description;
        this.gender = gender;
        this.age = age;
        this.race = race;
        this.size = size;
        this.status = status;
        this.photos = photos;
    }

}
