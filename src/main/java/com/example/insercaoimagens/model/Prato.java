package com.example.insercaoimagens.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Prato {
    private final int id;
    private final String nome;

    public Prato(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }

    public static ObservableList<Prato> getOpcoesPadrao() {
        return FXCollections.observableArrayList(
                new Prato(1, "Hambúrguer"),
                new Prato(2, "Pizza"),
                new Prato(3, "Salada"),
                new Prato(4, "Sushi"),
                new Prato(5, "Massas")
        );
    }
}
