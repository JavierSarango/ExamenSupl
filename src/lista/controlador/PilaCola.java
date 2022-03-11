/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.controlador;

import java.io.Serializable;

/**
 *
 * @author usuario
 */
public class PilaCola<T> extends Lista<T> implements Serializable {
    private Integer nro_elementos;
    private String tipo;

    public PilaCola(Integer nro_elementos, String tipo) {
        this.nro_elementos = nro_elementos;
        this.tipo = tipo;
    }

    public PilaCola(Integer nro_elementos) {
        this.nro_elementos = nro_elementos;
    }
    
    
    public boolean push(T dato) {
        if(tipo.equalsIgnoreCase("PILA")) {
            if(!estaLlena()) {
                insertar(dato, 0);
                return true;
            }
        } else {
            System.out.println("NO ES UNA PILA");
        }
        return false;
    }
    public boolean queue(T dato) {
        if(tipo.equalsIgnoreCase("COLA")) {
            if(!estaLlena()) {
                insertar(dato, tamanio());
                return true;
            }
        } else {
            System.out.println("NO ES UNA COLA");
        }
        return false;
    }
    public boolean estaLlena() {
        return !(tamanio() < nro_elementos);
    }
    
    public static void main(String[] args) {
        PilaCola<String> pila= new PilaCola(3, "PILA");
        for(int i = 0; i < 3; i ++) {
            if(pila.push("Hola "+i));
        }
        pila.imprimir();
        System.out.println("************************");
        PilaCola<String> cola= new PilaCola(3, "COLA");
        for(int i = 0; i < 3; i ++) {
            if(cola.queue("Hola "+i));
        }
        cola.imprimir();
    }
    
}
