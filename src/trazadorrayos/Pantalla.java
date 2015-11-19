/**
 * Iñigo Alonso - 665959
 * Alejandro Dieste - 541892
 */
package trazadorrayos;
import java.awt.Color;

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
