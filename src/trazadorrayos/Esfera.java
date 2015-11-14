/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;
import java.awt.Color;

import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;

/**
 *
 * @author shathe
 */
public class Esfera extends Figura {

	private double radio = 0.0;
        private Point4d centro = null;

	public Esfera(Point4d centro, Color color, double refraccion,
			double reflexion, double kd, double ks, double radio) {
		super(color, refraccion, reflexion, kd, ks);
		this.radio = radio;this.centro=centro;
                
	}
        
        public Vector4d getNormal(Point4d puntoInterseccion){
            Vector4d normal=new Vector4d();
            normal.x=puntoInterseccion.x-centro.x;
            normal.y=puntoInterseccion.y-centro.y;
            normal.z=puntoInterseccion.z-centro.z;
            normal.w=0;
            return normal;
        }
        
        public double getRadio() {
            return radio;
        }
        public Point4d getCentro() {
        return centro;
    }
}
