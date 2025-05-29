package com.jaleStore.demo.util;

public enum ColorZapatilla {
    ROJO("Rojo", "#FF0000"),
    VERDE("Verde", "#008000"),
    NEGRO("Negro", "#000000"),
    BLANCO("Blanco", "#FFFFFF"),
    AZUL("Azul", "#0000FF"),
    AMARILLO("Amarillo", "#FFFF00"),
    GRIS("Gris", "#808080"),
    MARRON("Marrón", "#8B4513"),
    ROSA("Rosa", "#FFC0CB"),
    NARANJA("Naranja", "#FFA500");

    private final String nombre;
    private final String codigoHex;

    ColorZapatilla(String nombre, String codigoHex) {
        this.nombre = nombre;
        this.codigoHex = codigoHex;
    }

    public String getNombre() { return nombre; }
    public String getCodigoHex() { return codigoHex; }
}
