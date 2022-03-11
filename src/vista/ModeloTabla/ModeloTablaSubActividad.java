/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vista.ModeloTabla;


import controlador.grafo.GrafoEtiquetadoND;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author sebas
 */
public class ModeloTablaSubActividad extends AbstractTableModel{

    private GrafoEtiquetadoND grafoD;
    private String[] columnas;

    public GrafoEtiquetadoND getGrafoD() {
        return grafoD;
    }

    public void setGrafoD(GrafoEtiquetadoND grafoD) {
        this.grafoD = grafoD;
        generarColumnas();
    }
    
    @Override
    public int getRowCount() {
        return grafoD.numVertices();
    }

    @Override
    public int getColumnCount() {
        return grafoD.numVertices() + 1;
    }
    
    private String[] generarColumnas() {
        columnas = new String[grafoD.numVertices() + 1];
        columnas[0] = "--";
        for(int i = 1; i < columnas.length;i++) {
            columnas[i] = grafoD.obtenerEtiqueta(i).toString();
        }
        return columnas;
    }

    @Override
    public String getColumnName(int i) {
        return columnas[i]; //To change body of generated methods, choose Tools | Templates.
    }

    
    
    @Override
    public Object getValueAt(int i, int i1) {
        if(i1 == 0) {
            return columnas[i + 1];
        } else {
            try {
                if(grafoD.existeArista((i+1), i1)) {
                    return grafoD.pesoArista(i+1, i1);
                } else {
                    return "--";
                }
            } catch (Exception e) {
                System.out.println("Error en ver arista");
            }
        }
        return "";
    }
    
}
