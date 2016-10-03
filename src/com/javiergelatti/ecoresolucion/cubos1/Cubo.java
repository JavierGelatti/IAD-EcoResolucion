package com.javiergelatti.ecoresolucion.cubos1;

import com.javiergelatti.ecoresolucion.Ambiente;
import com.javiergelatti.ecoresolucion.nucleo.EcoAgente;

import java.util.Collections;
import java.util.Set;

public class Cubo extends EcoAgente {
    private EcoAgente objetivo;
    private EcoAgente agentePorDebajo = agenteNulo;
    private EcoAgente agentePorEncima = agenteNulo;

    public static Cubo sobre(EcoAgente unAgente) {
        Cubo elCuboNuevo = new Cubo();
        elCuboNuevo.recibirPorDebajo(unAgente);
        return elCuboNuevo;
    }

    public void quererEstarSobre(EcoAgente otroAgente) {
        objetivo = otroAgente;
    }

    @Override
    public boolean estaSatisfecho() {
        return noTieneObjetivo() || elObjetivoEstaCumplido();
    }

    private boolean noTieneObjetivo() {
        return objetivo == null;
    }

    private boolean elObjetivoEstaCumplido() {
        return estaSobre(objetivo);
    }

    @Override
    public void recibirPorEncima(EcoAgente unAgente) {
        if (estaAbajoDe(unAgente)) return;

        agentePorEncima = unAgente;
        unAgente.recibirPorDebajo(this);
    }

    private boolean estaAbajoDe(EcoAgente unAgente) {
        return agentePorEncima.equals(unAgente);
    }

    @Override
    public void recibirPorDebajo(EcoAgente unAgente) {
        if (estaSobre(unAgente)) return;

        agentePorDebajo = unAgente;
        unAgente.recibirPorEncima(this);
    }

    public boolean estaSobre(EcoAgente unAgente) {
        return agentePorDebajo.equals(unAgente);
    }

    @Override
    public void huirDe(EcoAgente otroAgente) {
        agentePorEncima.huirDe(this);

        moverseA(Ambiente.laMesa());
    }

    @Override
    public void satisfacerse() {
        if (estaSatisfecho()) return;

        for (EcoAgente incomodidad : incomodidades())
            incomodidad.huirDe(this);

        moverseA(objetivo);
    }

    private void moverseA(EcoAgente objetivo) {
        agentePorDebajo.recibirPorEncima(EcoAgente.agenteNulo);
        objetivo.recibirPorEncima(this);
    }

    public Set<EcoAgente> incomodidades() {
        if (estaSatisfecho())
            return Collections.emptySet();
        else
            return Collections.singleton(agentePorEncima);
    }

    public boolean estaLibre() {
        return estaAbajoDe(EcoAgente.agenteNulo);
    }
}
