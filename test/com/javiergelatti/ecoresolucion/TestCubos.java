package com.javiergelatti.ecoresolucion;

import com.javiergelatti.ecoresolucion.cubos1.Cubo;
import com.javiergelatti.ecoresolucion.cubos1.Mesa;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

public class TestCubos {

    private Mesa laMesa;

    @Before
    public void setUp() throws Exception {
        laMesa = Ambiente.laMesa();
    }

    @After
    public void tearDown() throws Exception {
        Ambiente.reiniciar();
    }

    @Test
    public void laMesaEstaSatisfecha() throws Exception {
        assertTrue(laMesa.estaSatisfecho());
    }

    @Test
    public void unCuboQueEstaSobreLoQueQuiereEstar_EstaSatisfecho() throws Exception {
        Cubo unCubo = Cubo.sobre(laMesa);

        unCubo.quererEstarSobre(laMesa);

        assertTrue(unCubo.estaSatisfecho());
    }

    @Test
    public void unCuboQueNoEstaSobreLoQueQuiereEstar_NoEstaSatisfecho() throws Exception {
        Cubo unCubo = Cubo.sobre(laMesa);
        Cubo otroCubo = Cubo.sobre(laMesa);

        unCubo.quererEstarSobre(otroCubo);

        assertFalse(unCubo.estaSatisfecho());
    }

    @Test
    public void unCuboSatisfechoNoTieneIncomodidades() throws Exception {
        Cubo unCubo = Cubo.sobre(laMesa);

        unCubo.quererEstarSobre(laMesa);

        assertEquals(Collections.emptySet(), unCubo.incomodidades());
    }

    @Test
    public void unCuboInsatisfechoTieneComoIncomodidadAlDeArriba() throws Exception {
        Cubo unCubo = Cubo.sobre(laMesa);
        Cubo otroCubo = Cubo.sobre(unCubo);

        unCubo.quererEstarSobre(otroCubo);

        assertEquals(Collections.singleton(otroCubo), unCubo.incomodidades());
    }

    @Test
    public void unCuboEstaLibreSiNoHayCubosPorEncima() throws Exception {
        Cubo unCubo = Cubo.sobre(laMesa);
        Cubo otroCubo = Cubo.sobre(unCubo);

        assertTrue(otroCubo.estaSobre(unCubo));
        assertTrue(otroCubo.estaLibre());
        assertFalse(unCubo.estaLibre());
    }

    @Test
    public void alHuir_UnCuboSeVaALaMesa() throws Exception {
        Cubo unCubo = Cubo.sobre(laMesa);
        Cubo otroCubo = Cubo.sobre(unCubo);

        otroCubo.huirDe(unCubo);

        assertFalse(otroCubo.estaSobre(unCubo));
        assertTrue(otroCubo.estaSobre(laMesa));
        assertTrue(otroCubo.estaLibre());
    }

    @Test
    public void cuandoHuye_UnCuboCausaLaHuidaDeLosDeArriba() throws Exception {
        Cubo unCubo = Cubo.sobre(laMesa);
        Cubo otroCubo = Cubo.sobre(unCubo);
        Cubo nochEinCubo = Cubo.sobre(otroCubo);

        otroCubo.huirDe(unCubo);

        assertTrue(otroCubo.estaSobre(laMesa));
        assertTrue(otroCubo.estaLibre());
        assertTrue(nochEinCubo.estaSobre(laMesa));
        assertTrue(nochEinCubo.estaLibre());
    }

    @Test
    public void unCuboPuedeSatisfacerSuObjetivo() throws Exception {
        Cubo unCubo = Cubo.sobre(laMesa);
        Cubo otroCubo = Cubo.sobre(unCubo);
        unCubo.quererEstarSobre(otroCubo);

        unCubo.satisfacerse();

        assertTrue(unCubo.estaSobre(otroCubo));
        assertTrue(otroCubo.estaSobre(laMesa));
        assertTrue(unCubo.estaLibre());
        assertFalse(otroCubo.estaLibre());
    }

    @Test
    public void cuandoYaEstaSatisfecho_NoHaceNadaParaSatisfacerse() throws Exception {
        Cubo unCubo = Cubo.sobre(laMesa);
        Cubo otroCubo = Cubo.sobre(unCubo);
        unCubo.quererEstarSobre(laMesa);

        unCubo.satisfacerse();

        assertTrue(unCubo.estaSobre(laMesa));
        assertTrue(otroCubo.estaSobre(unCubo));
    }

    @Test
    public void cuandoNoTieneObjetivos_NoHaceNadaParaSatisfacerse() throws Exception {
        Cubo unCubo = Cubo.sobre(laMesa);
        Cubo otroCubo = Cubo.sobre(unCubo);

        unCubo.satisfacerse();

        assertTrue(unCubo.estaSobre(laMesa));
        assertTrue(otroCubo.estaSobre(unCubo));
    }

    @Test
    public void testEjemploDiapositivas() throws Exception {
        Cubo a = Cubo.sobre(laMesa);
        Cubo b = Cubo.sobre(a);
        Cubo c = Cubo.sobre(b);

        a.quererEstarSobre(laMesa);
        c.quererEstarSobre(a);
        b.quererEstarSobre(c);

        while (!(a.estaSatisfecho() && b.estaSatisfecho() && c.estaSatisfecho())) {
            a.satisfacerse();
            b.satisfacerse();
            c.satisfacerse();
        }

        assertTrue(a.estaSobre(laMesa));
        assertTrue(b.estaSobre(c));
        assertTrue(c.estaSobre(a));
    }

    @Test
    public void laMesaConoceALosCubosQueTienePorEncima() throws Exception {
        Cubo unCubo = Cubo.sobre(laMesa);
        Cubo otroCubo = Cubo.sobre(laMesa);

        assertEquals(setOf(unCubo, otroCubo), laMesa.cubos());
    }

    private <T> HashSet<T> setOf(T...elementos) {
        return new HashSet<>(Arrays.asList(elementos));
    }
}