package trazadorrayos;

import javax.vecmath.Point4d;
import javax.vecmath.Vector4d;

public class Rayo {

	private Vector4d direccion;
	private Point4d punto;
	private Color color;

   
	private double intensidad;

	public Rayo(Vector4d direccion, Point4d punto, Color color,
			double intensidad) {
		this.direccion = direccion;
		this.punto = punto;
		this.color=color;
		this.intensidad=intensidad;
	}
        
         public Vector4d getDireccion() {
        return direccion;
        }

        public void setDireccion(Vector4d direccion) {
            this.direccion = direccion;
        }

        public Point4d getPunto() {
            return punto;
        }

        public void setPunto(Point4d punto) {
            this.punto = punto;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public double getIntensidad() {
            return intensidad;
        }

        public void setIntensidad(double intensidad) {
            this.intensidad = intensidad;
        }

}
