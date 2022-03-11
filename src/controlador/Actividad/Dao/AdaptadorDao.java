/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.Actividad.Dao;


import controlador.Actividad.Dao.InterfazDao;
import controlador.grafo.Adyacencia;
import controlador.grafo.GrafoEtiquetadoND;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import lista.controlador.Lista;

/**
 *
 * @author usuario
 */
public class AdaptadorDao<T> implements InterfazDao<T> {

    private Class<T> clazz;
    private String carpeta = "datos" + File.separatorChar;
    private GrafoEtiquetadoND<T> grafo;

    public AdaptadorDao(Class<T> clazz) {
        this.clazz = clazz;
        carpeta = this.clazz.getSimpleName().toLowerCase() + ".txt";

    }

    public GrafoEtiquetadoND<T> getGrafo() {
        return grafo;
    }

    public void setGrafo(GrafoEtiquetadoND<T> grafo) {
        this.grafo = grafo;
    }
    

    private void anadirVertice() {
        if (grafo == null) {
            grafo = new GrafoEtiquetadoND<>(1);
        } else {
            GrafoEtiquetadoND aux = new GrafoEtiquetadoND(grafo.numVertices() + 1);

            for (int i = 1; i <= grafo.numVertices(); i++) {
                aux.etiquetaVertice(i, grafo.obtenerEtiqueta(i));
                //grafo += "Vertice " + i + "E (" + obtenerEtiqueta(i).toString() + ")";
                Lista<Adyacencia> lista = grafo.adyacentes(i);
                for (int j = 0; j < lista.tamanio(); j++) {
                    Adyacencia ad = lista.consultarDatoPosicion(j);
                    aux.insertarArista(i, ad.getDestino(), ad.getPeso());
                }
            }
            grafo = aux;
        }
    }
    public Boolean actualizarGrafo(GrafoEtiquetadoND<T> grafoAux){
        try {
            
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(carpeta));
            oos.writeObject(grafoAux);
            oos.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error en actualizar Grafo");
        }
        return false;
    }

    @Override
    public boolean guardar(T dato) {
        try {
            listarGrafo();
            anadirVertice();
            grafo.etiquetaVertice(grafo.numVertices(), dato);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(carpeta));
            oos.writeObject(grafo);
            //aux.setClazz(clazz);///////////////
            oos.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error en guardar");
        }
        return false;
    }

    @Override
    public boolean modificar(T dato) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(carpeta));
            grafo = listarGrafo();
            //aux.modificarPorPos(dato, pos);
            oos.writeObject(grafo);
            oos.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error en guardar");
        }
        return false;
    }

    @Override
    public GrafoEtiquetadoND<T> listarGrafo() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(carpeta));
            grafo =  (GrafoEtiquetadoND<T>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("Error en listar Grafo "+ e);
        }
        return grafo;
    }

    

}
