/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Actividad.Servicio;

import controlador.Actividad.Dao.SActividadDao;
import controlador.grafo.GrafoEtiquetadoND;
import modelo.Actividad;
import modelo.SubActividad;

/**
 *
 * @author Javier
 */
public class SActividadServicio {
       private SActividadDao sactDao = new SActividadDao();

    public SubActividad getSActividad() {
        return sactDao.getSAct();
    }

    public void fijarSActividad(SubActividad Sactividad) {
        sactDao.setSAct(Sactividad);
    }

    public Boolean guardar() {
        return sactDao.guardar();
    }

    public void imprimirGrafoPozo() {

        System.out.println(sactDao.getGrafo().toString());

    }

    public GrafoEtiquetadoND<SubActividad> getGrafo() {
        return sactDao.listarGrafo();
    }

    public GrafoEtiquetadoND<SubActividad> getGrafoObjeto() {
        return sactDao.getGrafo();
    }

    public Boolean actualizarGrafo(GrafoEtiquetadoND<SubActividad> grafoAux) {
        return sactDao.actualizarGrafo(grafoAux);
    }

}
