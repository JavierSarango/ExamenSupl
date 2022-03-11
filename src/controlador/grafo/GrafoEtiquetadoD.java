/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.grafo;

import java.util.HashMap;
import lista.controlador.Lista;

/**
 *
 * @author usuario
 */
public class GrafoEtiquetadoD<E> extends GrafoD {

    protected E etiquetas[];
    protected HashMap<E, Integer> dicVertices;

    public GrafoEtiquetadoD(Integer numV) {
        super(numV);
        this.etiquetas = (E[]) new Object[numV + 1];
        dicVertices = new HashMap<>(numV);
    }

    public Boolean existeAristaE(E i, E j) {
        try {
            return this.existeArista(obtenerCodigo(i), obtenerCodigo(j));
        } catch (Exception e) {
            return false;
        }
    }
    
    public void insertarAristaE(E i, E j, Double peso){
        try {
            this.insertarArista(obtenerCodigo(i), obtenerCodigo(j), peso);
        } catch (Exception e) {
        }
    }
    
    public void insertarAristaE(E i, E j){
        try {
            this.insertarArista(obtenerCodigo(i), obtenerCodigo(j));
        } catch (Exception e) {
        }
    }

    public Integer obtenerCodigo(E etiqueta) {
        try {
            return dicVertices.get(etiqueta);
        } catch (Exception e) {
            return -1;
        }
    }

    public Lista<Adyacencia> adyacentesDE(E i){
        return adyacentes(obtenerCodigo(i));
    }
    
    public void etiquetaVertice(Integer codigo, E etiqueta){
        etiquetas[codigo] = etiqueta;
        dicVertices.put(etiqueta, codigo);
    }
    
    public E obtenerEtiqueta(Integer codigo){
        return etiquetas[codigo];
    }
    
    @Override
    public String toString(){
        String grafo = "";
        for (int i = 1; i <= numVertices(); i++) {
            grafo += "Vertice " + i + "E ("+obtenerEtiqueta(i).toString() +")";
            Lista<Adyacencia> lista = adyacentes(i);
            for (int j = 0; j < lista.tamanio(); j++) {
                Adyacencia aux = lista.consultarDatoPosicion(j);
                
                if(aux.getPeso().toString().equalsIgnoreCase(String.valueOf(Double.NaN)))
                    grafo += " --Vertice destino " + aux.getDestino() + "E ("+obtenerEtiqueta(aux.getDestino())+") "+"-- SP";
                else 
                    grafo += " --Vertice destino " + aux.getDestino() + "E ("+obtenerEtiqueta(aux.getDestino())+") "+"-- Peso "+ aux.getPeso()+"--";
                    
            }
            grafo += "\n";
        }
        return grafo;
    }
}
