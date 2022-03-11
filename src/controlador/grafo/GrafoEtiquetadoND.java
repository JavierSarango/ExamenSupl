/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.grafo;

import java.io.Serializable;

/**
 *
 * @author usuario
 */
public class GrafoEtiquetadoND <E> extends GrafoEtiquetadoD<E> implements Serializable{

    public GrafoEtiquetadoND(Integer numV) {
        super(numV);
    }
    @Override
    public void insertarArista(Integer i, Integer j, Double peso) {
        try {
            if (i.intValue() <= numVertices() && j.intValue() <= numVertices()) {
                if (!existeArista(i, j)) {
                    numA++;
                    listaAdycente[i].insertarNodo(new Adyacencia(j, peso));
                    listaAdycente[j].insertarNodo(new Adyacencia(i, peso));
                }
            }
        } catch (Exception e) {
            System.out.println("Error en GNDE");
        }
    }

    public void insertarArista(E i, E j, Double peso) {
        try {
            insertarArista(obtenerCodigo(i), obtenerCodigo(j), peso);
            insertarArista(obtenerCodigo(j), obtenerCodigo(i), peso);
        } catch (Exception e) {
        }
    }
    
    
}
