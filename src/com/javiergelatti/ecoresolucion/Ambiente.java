package com.javiergelatti.ecoresolucion;

import com.javiergelatti.ecoresolucion.cubos1.Mesa;

public class Ambiente {
    private static Mesa laMesa;
    static {
        reiniciar();
    }

    public static Mesa laMesa() {
        return laMesa;
    }

    protected static void reiniciar() {
        laMesa = new Mesa();
    }
}
