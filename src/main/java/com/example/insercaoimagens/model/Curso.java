package com.example.insercaoimagens.model;

public class Curso {
    private long id;
    private String nomeCurso;

    public Curso(long id, String nomeCurso) {
        this.id = id;
        this.nomeCurso = nomeCurso;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    @Override
    public String toString() {
        return nomeCurso;
    }
}
