/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;

import java.util.ArrayList;

/**
 *
 * @author shathe
 */
public class Escena {
    private ArrayList<Figura> figuras= new ArrayList<Figura> ();
    private Pantalla pantalla=null;
    private Camara camara=null;
    private Foco foco=null;


    public Escena(Foco foco, Camara camara, Pantalla pantalla){
        this.foco=foco; this.camara=camara; this.pantalla=pantalla;
    }
    
    public void anadirFigura(Figura figura){
        figuras.add(figura);
    }

    public Pantalla getPantalla() {
        return pantalla;
    }
    
    
}
