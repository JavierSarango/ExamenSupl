/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.controlador;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import lista.modelo.Nodo;
/**
 *
 * @author usuario
 */
public class Lista<T> implements Serializable {

    private Nodo cabecera;
    private Class clazz;
    public static final Integer ASCENDENTE = 1;
    public static final Integer DESCENDENTE = 2;

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public boolean estaVacia() {
        return this.cabecera == null;
    }

    /**
     * Permite insertar dato en la lista
     *
     * @param dato El dato a ingresar
     */
    private void insertar(T dato) {
        Nodo nodo = new Nodo(dato, cabecera);
        cabecera = nodo;
    }

    private void insertarFinal(T dato) {
        insertar(dato, tamanio());//lista 4  pos = 4 - 1 --> 3
    }

    /**
     * Insertar un dato por posicion
     *
     * @param dato El dato a insertar
     * @param pos La posicion a insertar
     */
    public void insertar(T dato, int pos) {
        if (estaVacia() || pos <= 0) {
            insertar(dato);
        } else {
            Nodo iterador = cabecera;
            for (int i = 0; i < pos - 1; i++) {
                if (iterador.getNodoSiguiente() == null) {
                    break;
                }
                iterador = iterador.getNodoSiguiente();
            }
            Nodo tmp = new Nodo(dato, iterador.getNodoSiguiente());
            iterador.setNodoSiguiente(tmp);
        }
    }

    /**
     * Agregar item a lista ascendete, quiere decir que el primer elemento es la
     * cabecera
     *
     * @param dato El dato a agregar
     */
    public void insertarNodo(T dato) {
        if (tamanio() > 0) {
            insertarFinal(dato);
        } else {
            insertar(dato);
        }
    }

    /**
     * Retorna el tamanio de la lista
     *
     * @return numero de elementos
     */
    public int tamanio() {
        int cont = 0;
        Nodo tmp = cabecera;
        while (!estaVacia() && tmp != null) {
            cont++;
            tmp = tmp.getNodoSiguiente();
        }
        return cont;
    }

    /**
     * Permite extrarer el primer dato de la lista
     *
     * @return El dato
     */
    public T extraer() {
        T dato = null;
        if (!estaVacia()) {
            dato = (T) cabecera.getDato();
            cabecera = cabecera.getNodoSiguiente();
        }
        return dato;
    }

    /**
     * permite consultar un dato de la lista por posicion
     *
     * @param pos posicion en la lista
     * @return dato encontrado en la posicion
     */
    public T consultarDatoPosicion(int pos) {
        T dato = null;
        if (!estaVacia() && (pos >= 0 && pos <= tamanio() - 1)) {
            Nodo tmp = cabecera;
            for (int i = 0; i < pos; i++) {
                tmp = tmp.getNodoSiguiente();
                if (tmp == null) {
                    break;
                }
            }
            if (tmp != null) {
                dato = (T) tmp.getDato();
            }
        }
        return dato;
    }

    public void imprimir() {
        Nodo tmp = cabecera;
        while (!estaVacia() && tmp != null) {
            System.out.println(tmp.getDato());
            tmp = tmp.getNodoSiguiente();
        }
    }

    public boolean modificarPorPos(T dato, int pos) {
        if (!estaVacia() && (pos <= tamanio() - 1) && pos >= 0) {
            Nodo iterador = cabecera;
            for (int i = 0; i < pos; i++) {
                iterador = iterador.getNodoSiguiente();
                if (iterador == null) {
                    break;
                }
            }
            if (iterador != null) {
                iterador.setDato(dato);
                return true;
            }
        }
        return false;
    }

    private Field getField(String nombre) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase(nombre)) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }

    /*public void testReflect(T dato, String atributo) {
        try {
            System.out.println(getField(atributo).get(dato).toString());
        } catch (Exception e) {
            System.out.println("error "+e);
        }
    }*/
    private Object value(T dato, String atributo) throws Exception {
        return getField(atributo).get(dato);
    }

    public Lista<T> seleccion_clase(String atributo, Integer ordenacion) {
        //Lista<T> a = this;
        try {
            int i, j, k = 0;
            T t = null;
            int n = tamanio();
            for (i = 0; i < n - 1; i++) {
                k = i;
                t = consultarDatoPosicion(i);//persona   -- acceder al atributo

                for (j = i + 1; j < n; j++) {
                    boolean band = false;
                    Object datoT = value(t, atributo);
                    Object datoJ = value(consultarDatoPosicion(j), atributo);
                    if (datoT instanceof Number) {
                        Number aux = (Number) datoT;
                        Number numero = (Number) datoJ;
                        band = (ordenacion.intValue() == Lista.ASCENDENTE.intValue())
                                ? numero.doubleValue() < aux.doubleValue()
                                : numero.doubleValue() > aux.doubleValue();
                        /*if (numero.doubleValue() < aux.doubleValue()) {
                            t = a.consultarDatoPosicion(j);
                            k = j;
                        }*/
                    } else {
                        band = (ordenacion.intValue() == Lista.ASCENDENTE.intValue())
                                ? datoT.toString().compareTo(datoJ.toString()) > 0
                                : datoT.toString().compareTo(datoJ.toString()) < 0;
                        /*if (datoT.toString().compareTo(datoJ.toString()) > 0) {
                            t = a.consultarDatoPosicion(j);
                            k = j;
                        }*/
                    }
                    if (band) {
                        t = consultarDatoPosicion(j);
                        k = j;
                    }
                }
                modificarPorPos(consultarDatoPosicion(i), k);
                modificarPorPos(t, i);
            }
        } catch (Exception e) {
            System.out.println("Error en ordenacion " + e);
        }
        return this;
    }
    public void insert(T dato, int index)
    {
        if(estaVacia()|| index <= 0 )
        {
            insertar(dato);
        }
        else
        {
            if(index <= length())
            {
                Nodo iterator = cabecera;

                for (int i = 0; i < index - 1; i++)
                {
                    if (iterator.getNodoSiguiente() == null)
                    {
                        break;
                    }

                    iterator = iterator.getNodoSiguiente();
                }

                Nodo tmp = new Nodo(dato, iterator.getNodoSiguiente());
                iterator.setNodoSiguiente(tmp);
            }
            else
            {
                System.out.println("La posicion ingresada es mayor al tamaño del arreglo");
            }    
        }
    }
public void add(T dato)
    {
        if(clazz == null)
        {
            setClazz(dato.getClass());
        }
        insert(dato, length());
    }
 public int length()
    {
        int tamano = 0;
        Nodo tmp = cabecera;

        while (!estaVacia()&& tmp != null)
        {
            tmp = tmp.getNodoSiguiente();
            tamano++;
        }

        return tamano;
    }
  public T getByIndex(int pos)
    {
        T dato = null;
        
        if(!estaVacia() && pos >= 0 && pos < length())
        {
            Nodo tmp = cabecera;
            
            for (int i = 0; i < pos; i++)
            {
                tmp = tmp.getNodoSiguiente();
                
                if(tmp == null) break;
            }
            
            if(tmp != null) dato = (T)tmp.getDato();
        }
   
        return dato;
    }
   public void replace(T dato, int index)
    {
        if(index < length() && index >= 0)
            {
                Nodo iterator = cabecera;

                for (int i = 0; i < index; i++)
                {
                    if (iterator.getNodoSiguiente() == null)
                    {
                        break;
                    }

                    iterator = iterator.getNodoSiguiente();
                }

                iterator.setDato(dato);
            }
            else
            {
                System.out.println("La posicion ingresada no existe");
            }  
    }
 public void sortBySelection(String atributo, int orden)
    {
        int i,j,k;
        Object t;
        int n = length();

        for (i = 0; i < n - 1; i++)
        {
            try
            {
                k = i;
                t = getByIndex(i);
                
                for (j = i + 1; j < n; j++)
                {
                    try 
                    {
                        
                        boolean band = false;
                        Object datoT = getField(atributo).get(t);
                        Object datoJ = getField(atributo).get(getByIndex(j));
                        
                        if (datoT instanceof String)
                        {
                            band = (orden == 1) ? datoT.toString().compareTo(datoJ.toString()) > 0
                                                : datoT.toString().compareTo(datoJ.toString()) < 0;   
                        }
                        else if (datoT instanceof Number)
                        {
                            Number aux = (Number) datoT;
                            Number numero = (Number) datoJ;

                            band = (orden == 1) ? numero.doubleValue() < aux.doubleValue()
                                                : numero.doubleValue() > aux.doubleValue();
                        }
                        
                        if (band)
                        {
                            t = getByIndex(j);
                            k = j;
                        }
                            
                    } 
                    catch (SecurityException ex) 
                    {
                        Logger.getLogger(Lista.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                replace(getByIndex(i), k);
                replace((T) t, i);
                
            }
            catch (IllegalArgumentException | IllegalAccessException ex)
            {
                Logger.getLogger(Lista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void sortByInsertion(String atributo, int orden)
    {
        Object t;
        Object aux;
        
        for (int i = 1; i < length(); i++)
        {
            try 
            {
                int j = i - 1;
                boolean band = false;
                t = getByIndex(j);
                aux = getByIndex(i);
             
                if(getField(atributo).get(t) instanceof String)
                {
                    band = true;
                    
                    String auxString = getField(atributo).get(aux).toString();

                    while (band)
                    {
                        band = (orden == 1)? (j >= 0 && auxString.compareTo(getField(atributo).get(getByIndex(j)).toString()) < 0)
                                           : (j >= 0 && auxString.compareTo(getField(atributo).get(getByIndex(j)).toString()) > 0);
                        
                        if (band)
                        {
                            replace(getByIndex(j), j + 1);
                            j--;
                        }
                    }
                }
                else if(getField(atributo).get(t) instanceof Number)
                {
                    band = true;
                    Number auxNumber = (Number)getField(atributo).get(aux);
                    

                    while (band)
                    {
                        band = (orden == 1)? (j >= 0 && auxNumber.doubleValue() < ((Number) getField(atributo).get(getByIndex(j))).doubleValue())
                                            : (j >= 0 && auxNumber.doubleValue() > ((Number) getField(atributo).get(getByIndex(j))).doubleValue());
                        
                        if(band)
                        {
                            replace(getByIndex(j), j + 1);
                            j--;
                        }
                    }
                }
                replace((T) aux, j+1);
 
            }
            catch (IllegalArgumentException | IllegalAccessException ex)
            {
                Logger.getLogger(Lista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void sortByShell(String atributo, int orden)
    {
        int salto, i;
        Object aux;
        boolean cambiando;
        boolean band;
  
        for (salto = length() / 2; salto != 0; salto /= 2)
        {
            cambiando = true;
            
            while (cambiando)
            {                                            
                cambiando = false;
                for (i = 0; i + salto < length(); i++)   
                {
                    try 
                    {
                        if(getField(atributo).get(getByIndex(i)) instanceof String)
                        {
                            String t = getField(atributo).get(getByIndex(i)).toString();
                            String tSalto = getField(atributo).get(getByIndex(i + salto)).toString();
                             
                            band = (orden == 1)? (t.compareTo(tSalto) > 0)
                                               : (t.compareTo(tSalto) < 0);
                            
                            if (band)
                            {
                                aux = getByIndex(i + salto);
                                replace(getByIndex(i), i + salto);
                                replace((T) aux, i);
                                cambiando = true;
                            }
                        }
                        else if(getField(atributo).get(getByIndex(i)) instanceof Number)
                        {
                            Number t = (Number)getField(atributo).get(getByIndex(i));
                            Number tSalto = (Number)getField(atributo).get(getByIndex(i + salto));

                            band = (orden == 1) ? (t.doubleValue() > tSalto.doubleValue())
                                                : (t.doubleValue() < tSalto.doubleValue());   
                            
                            if (band)
                            {
                                aux = getByIndex(i + salto);
                                replace(getByIndex(i), i + salto);
                                replace((T) aux, i);
                                cambiando = true;
                            }
                        }
                        
                    } 
                    catch (IllegalArgumentException | IllegalAccessException ex)
                    {
                        Logger.getLogger(Lista.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        }
    }
    
    public void sortByQuickSort(String atributo, int orden, int izq, int der)
    {
        try
        {
            Object t,q;
            Object pivote = getByIndex(izq);
            Object aux;
            
            int i = izq;
            int j = der;
            
            
            while (i < j) 
            {
                boolean band1 = true, band2 = true;
                try
                {
                    while(band1 || band2)
                    {
                        t = getField(atributo).get(getByIndex(i));
                        q = getField(atributo).get(getByIndex(j));

                        if (t instanceof Number)
                        {

                            Number tNumber = (Number) t;
                            Number qNumber = (Number) q;
                            Number pivoteNumber = (Number) getField(atributo).get(pivote);

                            band1 = (orden == 1) ? (tNumber.doubleValue() <= pivoteNumber.doubleValue() && i < j)
                                                 : (tNumber.doubleValue() >= pivoteNumber.doubleValue() && i < j);
                            if (band1) i++;
                               

                            band2 = (orden == 1) ? (qNumber.doubleValue() > pivoteNumber.doubleValue())
                                                 : (qNumber.doubleValue() < pivoteNumber.doubleValue());

                            if (band2)j--;
                        }
                        else if(t instanceof String)
                        {
                            String tString = ((String) t);
                            String qString = q.toString();
                            String pivoteString = getField(atributo).get(pivote).toString();
                            
                            band1 = (orden == 1) ? (tString.compareToIgnoreCase(pivoteString) <= 0 && i < j)
                                                 : (tString.compareToIgnoreCase(pivoteString) >= 0 && i < j);
                            if (band1) i++;
                               

                            band2 = (orden == 1) ? (qString.compareToIgnoreCase(pivoteString) >  0)
                                                 : (qString.compareToIgnoreCase(pivoteString) <  0);

                            if (band2)j--;
                        }
                    }

                    if (i < j)
                    {
                        aux = getByIndex(i);
                        replace(getByIndex(j), i);
                        replace((T) aux, j);
                    }
                }
                catch (IllegalArgumentException | IllegalAccessException ex) 
                {
                    Logger.getLogger(Lista.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            replace(getByIndex(j), izq);
            replace((T) pivote, j);
            
            if (izq < j - 1)
            {
                sortByQuickSort(atributo, orden, izq, j - 1);
            }
            if (j + 1 < der) {
                sortByQuickSort(atributo, orden, j + 1, der);
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Lista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
