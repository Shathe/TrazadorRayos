/**
 * Iñigo Alonso - 665959
 * Alejandro Dieste - 541892
 */
package trazadorrayos;

import java.util.ArrayList;

/**
 * Clase que representa la escena con la camara, la pantalla, el foco de luz y
 * la lista de figuras
 *
 */
public class Escena {
	private ArrayList<Figura> figuras = new ArrayList<Figura>();
	private Pantalla pantalla = null;
	private Camara camara = null;
	private Foco foco = null;

	public Escena(Foco foco, Camara camara, Pantalla pantalla) {
		this.foco = foco;
		this.camara = camara;
		this.pantalla = pantalla;
	}

	public void anadirFigura(Figura figura) {
		figuras.add(figura);
	}

	public void anadirSetFiguras(ArrayList<Figura> figuras) {
		this.figuras = figuras;
	}

	public Pantalla getPantalla() {
		return pantalla;
	}

	public ArrayList<Figura> getFiguras() {
		return figuras;
	}

	public Camara getCamara() {
		return camara;
	}

	public Foco getFoco() {
		return foco;
	}

}
