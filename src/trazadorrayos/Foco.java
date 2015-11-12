/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;
import javax.vecmath.Point4d;
/**
 *
 * @author shathe
 */
public class Foco {

    private Point4d posicion=null;
    private int intensidad=0;
    private Color color=null;

    public Point4d getPosicion() {
        return posicion;
    }

    public int getIntensidad() {
        return intensidad;
    }

    public Color getColor() {
        return color;
    }

    public Foco(Point4d posicion, Color color, int ensidad) {
        this.color=color; intensidad=ensidad;this.posicion=posicion;
    }
    
    
    
}
