/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Actividad.Servicio;

import controlador.Actividad.Dao.ActividadDao;

import controlador.grafo.GrafoEtiquetadoND;
import modelo.Actividad;

/**
 *
 * @author Javier
 */
public class ActividadServicio {
       private ActividadDao actDao = new ActividadDao();

    public Actividad getActividad() {
        return actDao.getAct();
    }

    public void fijarActividad(Actividad actividad) {
        actDao.setAct(actividad);
    }

    public Boolean guardar() {
        return actDao.guardar();
    }

    public void imprimirGrafoPozo() {

        System.out.println(actDao.getGrafo().toString());

    }

    public GrafoEtiquetadoND<Actividad> getGrafo() {
        return actDao.listarGrafo();
    }

    public GrafoEtiquetadoND<Actividad> getGrafoObjeto() {
        return actDao.getGrafo();
    }

    public Boolean actualizarGrafo(GrafoEtiquetadoND<Actividad> grafoAux) {
        return actDao.actualizarGrafo(grafoAux);
    }

}
