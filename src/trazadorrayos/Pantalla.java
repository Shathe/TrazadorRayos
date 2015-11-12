/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;

/**
 *
 * @author shathe
 */
public class Pantalla {
    //figura:pantalla anchura:640 altura:480
    private int anchura = 0;
    private int altura = 0;
    private Color[][] pantalla = null;

    public Color[][] getPantalla() {
        return pantalla;
    }

    public void setPantalla(Color[][] pantalla) {
        this.pantalla = pantalla;
    }
    
    public Pantalla(int anchura, int altura){
     this.anchura = anchura; this.altura = altura;
        pantalla = new Color[anchura][altura];
    }

}
