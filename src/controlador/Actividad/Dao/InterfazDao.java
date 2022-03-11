/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.Actividad.Dao;

import controlador.grafo.GrafoEtiquetadoND;
import lista.controlador.Lista;

/**
 *
 * @author usuario
 */
public interface InterfazDao <T>{
    public boolean guardar(T dato);
    public boolean modificar(T dato);
    public  GrafoEtiquetadoND<T>  listarGrafo();
    //public T buscarId();
}
