/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lista.modelo;

/**
 *
 * @author usuario
 */
public class NodoHash<K, V> {
    private V dato;
    private K llave;
    private NodoHash<K, V> nodoSiguiente;

    public NodoHash(V dato, K llave, NodoHash<K, V> nodoSiguiente) {
        this.dato = dato;
        this.llave = llave;
        this.nodoSiguiente = nodoSiguiente;
    }

    public NodoHash() {
        this.dato = null;
        this.llave = null;
        this.nodoSiguiente = null;
    }
    
    public V getDato() {
        return dato;
    }

    public void setDato(V dato) {
        this.dato = dato;
    }

    public K getLlave() {
        return llave;
    }

    public void setLlave(K llave) {
        this.llave = llave;
    }

    public NodoHash<K, V> getNodoSiguiente() {
        return nodoSiguiente;
    }

    public void setNodoSiguiente(NodoHash<K, V> nodoSiguiente) {
        this.nodoSiguiente = nodoSiguiente;
    }
    
}
