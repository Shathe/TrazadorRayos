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
public class Figura {
    private Point4d posicion=null;
    Color color=null;
    private int indiceRefraccion=0;
    private int indiceReflectividad=0;
    private int indiceEspecularKS=0;
    private int indiceDifusionKD=0;

    public Figura(Point4d posicion, Color color, int refraccion,
            int reflexion, int kd, int ks) {
        this.color=color; this.posicion=posicion;
        indiceRefraccion=refraccion;indiceReflectividad=reflexion;
        indiceEspecularKS=ks; indiceDifusionKD=kd;
    }

    public Point4d getPosicion() {
        return posicion;
    }

    public Color getColor() {
        return color;
    }

    public int getIndiceRefraccion() {
        return indiceRefraccion;
    }

    public int getIndiceReflectividad() {
        return indiceReflectividad;
    }

    public int getIndiceEspecularKS() {
        return indiceEspecularKS;
    }

    public int getIndiceDifusionKD() {
        return indiceDifusionKD;
    }
    
    
    
}
