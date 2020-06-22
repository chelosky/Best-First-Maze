/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller1.ia;

import java.util.Scanner;

/**
 *
 * @author marce
 */
public class Taller1IA {
    
    public static int w, h;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Ingrese el ancho de la matriz: ");
        w = in.nextInt();
        System.out.print("Ingrese el alto de la matriz: ");
        h = in.nextInt();
        System.out.println("");
        
        System.out.println("---------------------------");
        
        BestFirst bf = new BestFirst(w, h);
        bf.GenerarLaberintoAletorio();
        bf.ImprimirTotalInformacionLaberinto();
        bf.GenerarObjetivoyAgente();
        bf.ImprimirMinimalistaTablero();
    
        System.out.println("Ejecución del algoritmo BFS");
        System.out.println("---------------------------");
        
        bf.EjecutarAlgoritmo();
        System.out.println("FIN DEL PROGRAMA!");
        System.out.println("---------------------------");
    }
    
}
