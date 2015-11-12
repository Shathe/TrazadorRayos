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
public class Esfera extends Figura{

    private double radio=0.0;
    public Esfera(Point4d posicion, Color color, int refraccion, int reflexion, 
            int kd, int ks, double radio) {
        super(posicion, color, refraccion, reflexion, kd, ks);
        this.radio=radio;
    }
    
}
