/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller1.ia;

/**
 *
 * @author marce
 */
public class SpiritAgente {
    private int posicionInicialX;
    private int posicionInicialY;
    private int posicionX;
    private int posicionY;
    private Orientacion orientacion;
            
    public enum Orientacion{
        NORTE,
        OESTE,
        ESTE,
        SUR
    }

    public SpiritAgente(int posicionX, int posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.posicionInicialX = posicionX;
        this.posicionInicialY = posicionY;
        this.orientacion = Orientacion.ESTE;
    }

    public int getPosicionInicialX() {
        return posicionInicialX;
    }

    public void setPosicionInicialX(int posicionInicialX) {
        this.posicionInicialX = posicionInicialX;
    }

    public int getPosicionInicialY() {
        return posicionInicialY;
    }

    public void setPosicionInicialY(int posicionInicialY) {
        this.posicionInicialY = posicionInicialY;
    }
    
    

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }
    
    public Orientacion getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(Orientacion orientacion) {
        this.orientacion = orientacion;
    }
    
    
}
