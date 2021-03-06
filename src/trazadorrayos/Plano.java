/**
 * Iñigo Alonso - 665959
 * Alejandro Dieste - 541892
 */
package trazadorrayos;

import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;
import java.awt.Color;

public class Plano extends Figura {

    private Point4d punto = null;

    private Vector4d normal = null;
    
    private double D=2;

    public Plano(Point4d punto, Vector4d normal, double refraccion,
                    double reflexion,double transparencia, Color kd, Color ks ) {
            super( refraccion, reflexion,transparencia, kd, ks, false);
            this.punto = punto;
            this.normal = normal;
    }

    public Point4d getPunto() {
        return punto;
    }
    
    @Override
    public Vector4d getNormal(Point4d puntoInterseccion) {
        return normal;
    }
    
    public double getD(){
    	return D;
    }
}
