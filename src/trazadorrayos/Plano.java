package trazadorrayos;

import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;
import java.awt.Color;

public class Plano extends Figura {

    private Point4d punto = null;

    private Vector4d normal = null;
    
    private double D=2;

    public Plano(Point4d punto, Vector4d normal,Color color, double refraccion,
                    double reflexion, double kd, double ks ) {
            super(color, refraccion, reflexion, kd, ks);
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
