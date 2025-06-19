package com.example.reismiauapp.models;

public class UsuarioModel {
    private String user;
    private int tipo;

    public UsuarioModel() {
        // Necessário para Firestore
    }

    public UsuarioModel(String user, int tipo) {
        this.user = user;
        this.tipo = tipo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}

