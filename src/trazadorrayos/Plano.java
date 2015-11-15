package trazadorrayos;

import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;
import java.awt.Color;

public class Plano extends Figura {

    private Point4d punto = null;

    private Vector4d normal = null;
    
    private double D=0.0;

    public Plano(Color color, double refraccion,
                    double reflexion, double kd, double ks, Point4d punto, Vector4d normal) {
            super(color, refraccion, reflexion, kd, ks);
            this.punto = punto;
            this.normal = normal;
    }

    public Point4d getPunto() {
        return punto;
    }

    public Vector4d getNormal() {
        return normal;
    }
    
    public double getD(){
    	return D;
    }
}
