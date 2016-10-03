package com.javiergelatti.ecoresolucion.nucleo;

public abstract class EcoAgente {

    protected static final EcoAgente agenteNulo = new EcoAgente() {
        @Override
        public boolean estaSatisfecho() {
            return false;
        }

        @Override
        public void recibirPorEncima(EcoAgente unAgente) {
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
    };

    public abstract boolean estaSatisfecho();

    public abstract void recibirPorEncima(EcoAgente unAgente);

    public abstract void huirDe(EcoAgente otroAgente);

    public abstract void satisfacerse();

    public abstract void recibirPorDebajo(EcoAgente unAgente);
}
