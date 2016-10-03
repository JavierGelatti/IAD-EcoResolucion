package com.javiergelatti.ecoresolucion.cubos1;

import com.javiergelatti.ecoresolucion.nucleo.EcoAgente;

import java.util.HashSet;
import java.util.Set;

public class Mesa extends EcoAgente {
    private Set<EcoAgente> cubos = new HashSet<>();

    @Override
    public boolean estaSatisfecho() {
        return true;
    }

    @Override
    public void recibirPorEncima(EcoAgente unAgente) {
        cubos.add(unAgente);
        unAgente.recibirPorDebajo(this);
    }

    @Override
    public void huirDe(EcoAgente otroAgente) {
    }

    @Override
    public void satisfacerse() {
    }

    @Override
    public void recibirPorDebajo(EcoAgente unAgente) {
    }

    public Set<EcoAgente> cubos() {
        return cubos;
    }
}
