/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.Actividad.Dao;


import modelo.SubActividad;

/**
 *
 * @author Javier
 */
public class SActividadDao extends AdaptadorDao<SubActividad> {
    private SubActividad sact = new SubActividad();
    
    public SActividadDao() {
        super(SubActividad.class);
        listarGrafo();
    }

    public SubActividad getSAct() {
        if(sact == null) sact = new SubActividad();
        return sact;
    }

    public void setSAct(SubActividad sact) {
        this.sact = sact;
    }
     public  Boolean guardar(){
        Integer aux = (getGrafo() !=null) ? getGrafo().numVertices()+1 : 1;
        sact.setId(new Long(String.valueOf(aux)));
        return guardar(sact);
    }
    
    public Boolean modificar(){
        return  modificar(sact);
    }
}
