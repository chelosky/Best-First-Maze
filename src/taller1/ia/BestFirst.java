/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller1.ia;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author marce
 */
public class BestFirst {
    public SpiritAgente agente;
    public Cuadrante[][] tablero;
    public int ancho;
    public int alto;
    public int posObjetivoX;
    public int posObjetivoY;

    public BestFirst(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        this.tablero = new Cuadrante[ancho][alto];
        this.posObjetivoX = -1;
        this.posObjetivoY = -1;
    }
    
    public void GenerarLaberintoAletorio(){
        for (int i = 0; i < this.ancho; i++) {
            for (int j = 0; j < this.alto; j++) {
                this.tablero[i][j] = new Cuadrante(i,j);
            }
        }
    }
    
    public void ImprimirTotalInformacionLaberinto(){
        System.out.println("-------------------------------------------");
        System.out.println("Información: ");
        System.out.println("A: Terreno Abrupto | L: Terreno Llano");
        System.out.println("H: Obstaculo Horizontal o Pared Inferior");
        System.out.println("V: Obstaculo Vertical o Pared Derecha");
        System.out.println("-------------------------------------------");
        for (int i = 0; i < this.ancho; i++) {
            for (int j = 0; j < this.alto; j++) {
                String valor = (this.tablero[i][j].isEstaSucio())? "[A": "[L";
                valor += (this.tablero[i][j].isParedHorizontal())? ",H": ", ";
                valor += (this.tablero[i][j].isParedVertical())? ",V]": ", ]";
                System.out.print(valor + " ");
            }
            System.out.println("");
        }
        System.out.println("-------------------------------------------");
    }
    
    public void GenerarObjetivoyAgente(){
        System.out.println("-------------------------------------------");
        // posicion spirit
        int posXSpirit =  (int) (Math.random() * (this.ancho-1));
        int posYSpirit = (int) (Math.random() * (this.alto-1));
        this.agente = new SpiritAgente(posXSpirit, posYSpirit);
        System.out.println("Objetivo pos x: " + posXSpirit);
        System.out.println("Objetivo pos y: " + posYSpirit);
        System.out.println("-------------------------------------------");
        // posicion del objetivo
        int posXObjetivo =  (int) (Math.random() * (this.ancho-1));
        int posYObjetivo = (int) (Math.random() * (this.alto-1));
        this.tablero[posXObjetivo][posYObjetivo].setEsObjetivo(true);
        this.posObjetivoX = posXObjetivo;
        this.posObjetivoY = posYObjetivo;
        System.out.println("Objetivo pos x: " + posXObjetivo);
        System.out.println("Objetivo pos y: " + posYObjetivo);
        System.out.println("-------------------------------------------");
    } 
    
    public void ImprimirMinimalistaTablero(){
        for (int i = 0; i < this.ancho; i++) {
            for (int j = 0; j < this.alto; j++) {
                if (i == this.agente.getPosicionX() && j == this.agente.getPosicionY()){
                    System.out.print("> ");
                }
                else if(this.tablero[i][j].isEsObjetivo()){
                    System.out.print("X ");
                } else if(this.tablero[i][j].isVisitado()){
                    System.out.print("o ");
                }
                else{
                    System.out.print("- ");
                }
            }
            System.out.println("");
        }   
        System.out.println("---------------------------");
    }
    
    public void EjecutarAlgoritmo(){
        ArrayList<Cuadrante> listaNodos = new ArrayList<>();
        ArrayList<Cuadrante> caminoLista = new ArrayList<>();
        ArrayList<Float> listaHeuristicas = new ArrayList<>();
        
        
        listaNodos.add(this.tablero[this.agente.getPosicionX()][this.agente.getPosicionY()]);
        listaNodos.get(0).setOrientacionPrevio(this.agente.getOrientacion());
        listaHeuristicas.add(0f);
        
        while(listaNodos.size() > 0){
            
            this.ImprimirMinimalistaTablero();
            int indexMenor = this.RetornarIndiceHeuristicaMenor(listaHeuristicas);
            
            Cuadrante temp = listaNodos.get(indexMenor);
            this.tablero[temp.getPosX()][temp.getPosY()].setVisitado(true);
            caminoLista.add(temp);
            this.agente.setPosicionX(temp.getPosX());
            this.agente.setPosicionY(temp.getPosY());
            this.agente.setOrientacion(temp.getOrientacionPrevio());
            
            listaNodos.remove(indexMenor);
            listaHeuristicas.remove(indexMenor);
            
            // SI ESTA EN OBJETIVO
            if(temp.isEsObjetivo()){
                break;
            }
           
            // VERIFIQUE LAS CUADRANTES DERECHA, IZQUIERDA, ARRIBA Y ABAJO
            // DERECHO POSX + 1 --> (POSX + 1) < W && EL CUADRANTE ACTUAL NO TIENE PARED VERTICAL
            // IZQUIERDO POSX - 1 ---> (POSX - 1) >= 0 && EL CUADRANTE QUE ESTA A LA IZQUIERDA NO TENGA PARED VERTICAL
            // ARRIBA POSY - 1 --> (POSY - 1) >= 0 && EL CUADRANTE DE ARRIBA NO TIENE QUE TENER PARED HORIZONTAL
            // ABAJO POSY + 1 --> (POSY + 1) < H && EL CUADRANTE ACTUAL NO TENGA PARED HORIZONTAL
            
            if(this.VerificarDerecho()){
                Cuadrante cuadranteTMP = this.tablero[this.agente.getPosicionX()][this.agente.getPosicionY()+1].RetornarCopia();
                cuadranteTMP.setOrientacionPrevio(SpiritAgente.Orientacion.ESTE);
                cuadranteTMP.setPosPadre(new int[]{this.agente.getPosicionX(),this.agente.getPosicionY()});
                listaNodos.add(cuadranteTMP);
                listaHeuristicas.add(this.CalcularHeuristicaNodo(cuadranteTMP, "DERECHA"));
            }
            if(this.VerificarIzquierdo()){
                Cuadrante cuadranteTMP = this.tablero[this.agente.getPosicionX()][this.agente.getPosicionY()-1].RetornarCopia();;
                cuadranteTMP.setOrientacionPrevio(SpiritAgente.Orientacion.OESTE);
                cuadranteTMP.setPosPadre(new int[]{this.agente.getPosicionX(),this.agente.getPosicionY()});
                listaNodos.add(cuadranteTMP);
                listaHeuristicas.add(this.CalcularHeuristicaNodo(cuadranteTMP, "IZQUIERDA"));
            }
            if(this.VerificarAbajo()){
                Cuadrante cuadranteTMP = this.tablero[this.agente.getPosicionX()+1][this.agente.getPosicionY()].RetornarCopia();;
                cuadranteTMP.setOrientacionPrevio(SpiritAgente.Orientacion.SUR);
                cuadranteTMP.setPosPadre(new int[]{this.agente.getPosicionX(),this.agente.getPosicionY()});
                listaNodos.add(cuadranteTMP);
                listaHeuristicas.add(this.CalcularHeuristicaNodo(cuadranteTMP, "ABAJO"));
            }
            if(this.VerificarArriba()){
                Cuadrante cuadranteTMP = this.tablero[this.agente.getPosicionX()-1][this.agente.getPosicionY()].RetornarCopia();;
                cuadranteTMP.setOrientacionPrevio(SpiritAgente.Orientacion.NORTE);
                cuadranteTMP.setPosPadre(new int[]{this.agente.getPosicionX(),this.agente.getPosicionY()});
                listaNodos.add(cuadranteTMP);
                listaHeuristicas.add(this.CalcularHeuristicaNodo(cuadranteTMP, "ARRIBA"));
            }
        }
        this.ResultadosFinal(caminoLista);
    }
    
    public boolean EsOrientacionOpuesta(SpiritAgente.Orientacion ori1, SpiritAgente.Orientacion ori2){
        switch (ori1) {
            case ESTE:
                if(ori2 == SpiritAgente.Orientacion.OESTE) return true;
                return false;
            case OESTE:
                if(ori2 == SpiritAgente.Orientacion.ESTE) return true;
                return false;
            case SUR:
                if(ori2 == SpiritAgente.Orientacion.NORTE) return true;
                return false;
            case NORTE:
                if(ori2 == SpiritAgente.Orientacion.SUR) return true;
                return false;
            default:
                throw new AssertionError();
        }
    }
    
    public Cuadrante ObtenerCuadrantePadreSolucion(ArrayList<Cuadrante> listado, int[] posiciones){
        for(int i=0; i< listado.size(); i++){
            if(listado.get(i).getPosX() == posiciones[0] && listado.get(i).getPosY() == posiciones[1]){
                return listado.get(i);
            }
        }
        return null;
    }
    
    public void ResultadosFinal(ArrayList<Cuadrante> caminoLista){
        if(this.agente.getPosicionX() == this.posObjetivoX &&
                this.agente.getPosicionY() == this.posObjetivoY){
            System.out.println("Llego al objetivo!");
            Cuadrante estadoActual = caminoLista.get(caminoLista.size() - 1);
            ArrayList<Cuadrante> listaFinal = new ArrayList<>();
            listaFinal.add(estadoActual);
            for(int j = caminoLista.size() - 1; j >=0 ; j--){
                if(caminoLista.get(j).getPosX() == estadoActual.getPosPadre()[0] &&
                        caminoLista.get(j).getPosY() == estadoActual.getPosPadre()[1]){
                    listaFinal.add(0, caminoLista.get(j));
                    estadoActual = caminoLista.get(j);
                }
            }
            for(int yiyo = 0; yiyo < listaFinal.size(); yiyo++){
                System.out.print("[" + listaFinal.get(yiyo).getPosX()+ "," + listaFinal.get(yiyo).getPosY() + "]");
                if(yiyo != listaFinal.size()-1){
                    System.out.print(" -> ");
                }
            }
            System.out.println("");
            SpiritAgente.Orientacion orientacionTMP = SpiritAgente.Orientacion.ESTE;
            float contador = 0f;
            for(int yiyo = 1; yiyo < listaFinal.size(); yiyo++){
                if(orientacionTMP != listaFinal.get(yiyo).getOrientacionPrevio()){
                    if(EsOrientacionOpuesta(orientacionTMP, listaFinal.get(yiyo).getOrientacionPrevio())){
                        contador += 8;
                    }else{
                        contador += 4;
                    }
                }
                if(listaFinal.get(yiyo).isEstaSucio()){
                    contador += 0.5;
                }else{
                    contador += 1.2;
                }
                orientacionTMP = listaFinal.get(yiyo).getOrientacionPrevio();
                System.out.print("[" + listaFinal.get(yiyo).getOrientacionPrevio().toString() + "]");
                if(yiyo != listaFinal.size()-1){
                    System.out.print(" -> ");
                }
            }
            System.out.println("");
            System.out.println("COSTO FINAL: " + contador);
            System.out.println("--------------------------");
        }else{
            System.out.println("No llego al objetivo! :( ");
        }
    }
    
    public int RetornarIndiceHeuristicaMenor(ArrayList<Float> listado){
//        System.out.println(java.util.Arrays.toString(listado.toArray()));
        float min = Collections.min(listado);
//        System.out.println(min);
        for(int i=0 ; i < listado.size(); i++){
            if (listado.get(i) == min){
//                System.out.println(i);
                return i;
            }
        }
        return -1;
    }
    /*
    [L, , ] [L, , ] [A, , ] 
    [L, , ] [A, , ] [L,H, ] 
    [L, , ] [A, ,V] [L,H, ] 
    [L, ,V] [A,H,V] [A, ,V] 
    [L,H,V] [L, ,V] [A, ,V] 
    */
    //[2,1] -> [3,1] -> [1,1] -> [0,1] -> [0,2] -> [1,2] -> [1,0] -> [0,0] -> [1,2] -> [2,0] ->   
    public float CalcularHeuristicaNodo(Cuadrante cuadrante, String movimiento){
        switch(this.agente.getOrientacion()){
            case ESTE -> {
                if(movimiento.equals("DERECHA")){
                    return (cuadrante.isEstaSucio()) ? Utils.abrupto: Utils.llano;
                }else if(movimiento.equals("ABAJO") || movimiento.equals("ARRIBA")){
                    return ((cuadrante.isEstaSucio()) ? Utils.abrupto: Utils.llano) + Utils.giro;
                }else{
                    return ((cuadrante.isEstaSucio()) ? Utils.abrupto: Utils.llano) + (2*Utils.giro);
                }
            }
            case OESTE -> {
                if(movimiento.equals("IZQUIERDA")){
                    return (cuadrante.isEstaSucio()) ? Utils.abrupto: Utils.llano;
                }else if(movimiento.equals("ABAJO") || movimiento.equals("ARRIBA")){
                    return ((cuadrante.isEstaSucio()) ? Utils.abrupto: Utils.llano) + Utils.giro;
                }else{
                    return ((cuadrante.isEstaSucio()) ? Utils.abrupto: Utils.llano) + (2*Utils.giro);
                }
            }
            case NORTE -> {
                if(movimiento.equals("ARRIBA")){
                    return (cuadrante.isEstaSucio()) ? Utils.abrupto: Utils.llano;
                }else if(movimiento.equals("DERECHA") || movimiento.equals("IZQUIERDA")){
                    return ((cuadrante.isEstaSucio()) ? Utils.abrupto: Utils.llano) + Utils.giro;
                }else{
                    return ((cuadrante.isEstaSucio()) ? Utils.abrupto: Utils.llano) + (2*Utils.giro);
                }
            }
            case SUR -> {
                if(movimiento.equals("ABAJO")){
                    return (cuadrante.isEstaSucio()) ? Utils.abrupto: Utils.llano;
                }else if(movimiento.equals("DERECHA") || movimiento.equals("IZQUIERDA")){
                    return ((cuadrante.isEstaSucio()) ? Utils.abrupto: Utils.llano) + Utils.giro;
                }else{
                    return ((cuadrante.isEstaSucio()) ? Utils.abrupto: Utils.llano) + (2*Utils.giro);
                }
            }
            default -> {
                System.out.println("Ups! Algo a salido horriblemente mal :( ");
                return -1;
            }
        }
    }
    
    
    /// SI SALE TRUE PUEDE AVANZAR A DERECHO
    public boolean VerificarDerecho(){
        return ((this.agente.getPosicionY() + 1) < this.alto &&
                !this.tablero[this.agente.getPosicionX()][this.agente.getPosicionY() + 1 ].isVisitado() &&
                !this.tablero[this.agente.getPosicionX()][this.agente.getPosicionY()].isParedVertical());
    }
    /// SI SALE TRUE PUEDE AVANZAR A IZQUIERDO
    public boolean VerificarIzquierdo(){
        return ( (this.agente.getPosicionY() - 1) >= 0 &&
                !this.tablero[this.agente.getPosicionX()][this.agente.getPosicionY() - 1].isVisitado() &&
                !this.tablero[this.agente.getPosicionX()][this.agente.getPosicionY() - 1].isParedVertical());
    }
    
    public boolean VerificarAbajo(){
        return ((this.agente.getPosicionX() + 1) < this.ancho &&
                !this.tablero[this.agente.getPosicionX() + 1][this.agente.getPosicionY()].isVisitado() &&
                !this.tablero[this.agente.getPosicionX()][this.agente.getPosicionY()].isParedHorizontal());
    }
    
    public boolean VerificarArriba(){
        return ((this.agente.getPosicionX() - 1) >=0 &&
                !this.tablero[this.agente.getPosicionX()-1][this.agente.getPosicionY()].isVisitado() &&
                !this.tablero[this.agente.getPosicionX()-1][this.agente.getPosicionY()].isParedHorizontal());
    }
}
