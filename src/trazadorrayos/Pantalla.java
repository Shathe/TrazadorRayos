/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;
import java.awt.Color;

/**
 *
 * @author shathe
 */
public class Pantalla {
    //figura:pantalla anchura:640 altura:480
    private double anchura = 0;
    private double altura = 0;
    private double pixelesanchura = 0;
    private double pixelesaltura = 0;
    private Color[][] pantalla = null;

    public Color[][] getPantalla() {
        return pantalla;
    }

    public void setPantalla(Color[][] pantalla) {
        this.pantalla = pantalla;
    }
    
    public Pantalla(double pixelAncho, double pixelAlto,double anchura, double altura){
     pixelesaltura=pixelAlto;pixelesanchura=pixelAncho;
     this.anchura = anchura; this.altura = altura;
        pantalla = new Color[(int)pixelAncho][(int)pixelAncho];
    }

    public double getPixelesanchura() {
        return pixelesanchura;
    }

    public double getPixelesaltura() {
        return pixelesaltura;
    }

    public double getAnchura() {
        return anchura;
    }

    public double getAltura() {
        return altura;
    }

}
