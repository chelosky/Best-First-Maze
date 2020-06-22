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
public class Cuadrante {
    
    private int posX;
    private int posY;
    private boolean paredVertical; // ES LA PARED DE LA DERECHA DEL CUADRANTE
    private boolean paredHorizontal; // ES LA PARED DE ABAJO DEL CUADRANTE
    private boolean estaSucio;
    private boolean visitado;
    private boolean esObjetivo;
    
    private int[] posPadre = new int[2];
    
    private SpiritAgente.Orientacion orientacionPrevio;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int[] getPosPadre() {
        return posPadre;
    }

    public void setPosPadre(int[] posPadre) {
        this.posPadre = posPadre;
    }
    
    

    public SpiritAgente.Orientacion getOrientacionPrevio() {
        return orientacionPrevio;
    }

    public void setOrientacionPrevio(SpiritAgente.Orientacion orientacionPrevio) {
        this.orientacionPrevio = orientacionPrevio;
    }

    public Cuadrante(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        this.paredVertical = ((Math.random() * 4) > 3);
        this.paredHorizontal = ((Math.random() * 4) > 3);
        this.estaSucio = ((Math.random() * 4) > 3);
        this.visitado = false;
        this.esObjetivo = false;
    }

    public boolean isEsObjetivo() {
        return esObjetivo;
    }

    public void setEsObjetivo(boolean esObjetivo) {
        this.esObjetivo = esObjetivo;
    }

    public boolean isParedVertical() {
        return paredVertical;
    }

    public void setParedVertical(boolean paredVertical) {
        this.paredVertical = paredVertical;
    }

    public boolean isParedHorizontal() {
        return paredHorizontal;
    }

    public void setParedHorizontal(boolean paredHorizontal) {
        this.paredHorizontal = paredHorizontal;
    }

    public boolean isEstaSucio() {
        return estaSucio;
    }

    public void setEstaSucio(boolean estaSucio) {
        this.estaSucio = estaSucio;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }
    
    public Cuadrante RetornarCopia(){
        Cuadrante nuevo = new Cuadrante(this.posX,this.posY);
        nuevo.setParedVertical(this.paredVertical);
        nuevo.setParedHorizontal(this.paredHorizontal);
        nuevo.setEstaSucio(this.estaSucio);
        nuevo.setVisitado(this.visitado);
        nuevo.setEsObjetivo(this.esObjetivo);
        nuevo.setPosPadre(this.posPadre);
        return nuevo;
    }
}
