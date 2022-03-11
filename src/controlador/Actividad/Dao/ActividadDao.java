/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Actividad.Dao;

import modelo.Actividad;

/**
 *
 * @author Javier
 */
public class ActividadDao extends AdaptadorDao<Actividad> {
    private Actividad act = new Actividad();
    
    public ActividadDao() {
        super(Actividad.class);
        listarGrafo();
    }

    public Actividad getAct() {
        if(act == null) act = new Actividad();
        return act;
    }

    public void setAct(Actividad act) {
        this.act = act;
    }
     public  Boolean guardar(){
        Integer aux = (getGrafo() !=null) ? getGrafo().numVertices()+1 : 1;
        act.setId(new Long(String.valueOf(aux)));
        return guardar(act);
    }
    
    public Boolean modificar(){
        return  modificar(act);
    }
}
