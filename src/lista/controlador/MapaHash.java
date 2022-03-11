/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.controlador;

import lista.modelo.Nodo;
import lista.modelo.NodoHash;

/**
 *
 * @author usuario
 */
public class MapaHash<K, V> {

    private NodoHash<K, V> cabecera;

    public boolean estaVacia() {
        return this.cabecera == null;
    }

    /**
     * Permite insertar dato en la lista
     *
     * @param dato El dato a ingresar
     */
    private void insertar(V dato, K llave) {
        NodoHash<K, V> nodo = new NodoHash(dato, llave, cabecera);
        cabecera = nodo;
    }

    private void insertarFinal(V dato, K llave) {
        insertar(dato, llave, tamanio());
    }

    /**
     * Insertar un dato por posicion
     *
     * @param dato El dato a insertar
     * @param pos La posicion a insertar
     */
    private void insertar(V dato, K llave, int pos) {
        if (estaVacia() || pos <= 0) {
            insertar(dato, llave);
        } else {
            NodoHash iterador = cabecera;
            for (int i = 0; i < pos - 1; i++) {
                if (iterador.getNodoSiguiente() == null) {
                    break;
                }
                iterador = iterador.getNodoSiguiente();
            }
            NodoHash tmp = new NodoHash(dato, llave, iterador.getNodoSiguiente());
            iterador.setNodoSiguiente(tmp);
        }
    }

    /**
     * Agregar item a lista ascendete, quiere decir que el primer elemento es la cabecera
     *
     * @param dato El dato a agregar
     */
    public void insertarNodo(V dato, K llave) {
        if (obtenerPorLlave(llave) == null) {
            if (tamanio() > 0) {
                insertarFinal(dato, llave);
            } else {
                insertar(dato, llave);
            }
        } else {
        }

    }

    public int tamanio() {
        int cont = 0;
        NodoHash tmp = cabecera;
        while (!estaVacia() && tmp != null) {
            cont++;
            tmp = tmp.getNodoSiguiente();
        }
        return cont;
    }

    public void imprimir() {
        NodoHash tmp = cabecera;
        while (!estaVacia() && tmp != null) {
            System.out.println("LLAVE: " + tmp.getLlave().toString() + " DATO: " + tmp.getDato());
            tmp = tmp.getNodoSiguiente();
        }
    }

    public V obtenerPorLlave(K llave) {
        V dato = null;
        if (!estaVacia()) {
            NodoHash tmp = cabecera;
            while (!estaVacia() && tmp != null) {
                if (tmp.getLlave().toString().equals(llave.toString())) {
                    dato = (V) tmp.getDato();
                    break;
                }
                tmp = tmp.getNodoSiguiente();
            }
        }
        return dato;
    }

}
