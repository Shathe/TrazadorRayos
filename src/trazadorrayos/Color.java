/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trazadorrayos;

/**
 *
 * @author shathe
 */
public class Color {
    private short red=0;
    private short green=0;
    private short blue=0;

    public Color(short r, short g, short b){
        red=r; blue=b; green=g;
    }
    public short getRed() {
        return red;
    }

    public void setRed(short red) {
        this.red = red;
    }

    public short getGreen() {
        return green;
    }

    public void setGreen(short green) {
        this.green = green;
    }

    public short getBlue() {
        return blue;
    }

    public void setBlue(short blue) {
        this.blue = blue;
    }
    
}
