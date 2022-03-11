package controlador.grafo;

import controlador.exception.AdyacenciaExepcion;
import java.io.Serializable;
import java.util.Objects;
import lista.controlador.Cola;
import lista.controlador.Lista;

/**
 *
 * @author usuario
 */
public abstract class Grafo implements Serializable {

    /**
     * Es el numero de vertices del grafo
     *
     * @return Integer numero de vertices
     */
    public abstract Integer numVertices();

    /**
     * Es el numero de aristas del grafo
     *
     * @return Integer numero de aristas
     */
    public abstract Integer numAristas();

    /**
     * Permite ver si entre dos nodos hay conexion (arista)
     *
     * @param i Nodo incial
     * @param j Nodo final
     * @return true si estan conectados
     * @throws Exception Excepcion
     */
    public abstract Boolean existeArista(Integer i, Integer j) throws Exception;

    /**
     * Retorna el peso que hay entre dos vertices
     *
     * @param i Nodo incial
     * @param j Nodo final
     * @return Double peso de la arista
     */
    public abstract Double pesoArista(Integer i, Integer j);

    /**
     * Permite insertar arista sin peso
     *
     * @param i Nodo incial
     * @param j Nodo final
     */
    public abstract void insertarArista(Integer i, Integer j);

    /**
     * Permite insertar arista con peso
     *
     * @param i Nodo incial
     * @param j Nodo final
     * @param peso peso de la arista
     */
    public abstract void insertarArista(Integer i, Integer j, Double peso);

    /**
     * Listado de adycencias de un nodo
     *
     * @param i Nodo a listar sus adyacencias
     * @return Lista
     */
    public abstract Lista<Adyacencia> adyacentes(Integer i);

    @Override
    public String toString() {
        String grafo = "";
        for (int i = 1; i <= numVertices(); i++) {
            grafo += "Vertice " + i;
            Lista<Adyacencia> lista = adyacentes(i);
            for (int j = 0; j < lista.tamanio(); j++) {
                Adyacencia aux = lista.consultarDatoPosicion(j);

                if (aux.getPeso().toString().equalsIgnoreCase(String.valueOf(Double.NaN))) {
                    grafo += " --Vertice destino " + aux.getDestino() + "-- SP ";
                } else {
                    grafo += " --Vertice destino " + aux.getDestino() + "-- Peso " + aux.getPeso() + "--";
                }

            }
            grafo += "\n";
        }
        return grafo;
    }

    public Lista caminoMinimo(Integer verticeI, Integer verticeF) throws AdyacenciaExepcion {
        Lista<Integer> caminos = new Lista<>();
        
            Lista<Double> listarPesos = new Lista<>();
            Integer inicial = verticeI;
            caminos.insertarNodo(inicial);
            Boolean finalizar = false;
            while (!finalizar) {
                Lista<Adyacencia> adyacencias = adyacentes(inicial);
                if (adyacencias==null) 
                    throw new AdyacenciaExepcion("No existe adyacencias");
                
                Double peso = 100000000.0;
                Integer T = -1;//aux Destino
                for (int i = 0; i < adyacencias.tamanio(); i++) {
                    Adyacencia ad = adyacencias.consultarDatoPosicion(i);
                    
                    if (!estaEnCamino(caminos, ad.getDestino())) {
                        Double pesoArista = ad.getPeso();
                        if (verticeF.intValue() == ad.getDestino().intValue()) {
                        T = ad.getDestino();
                        peso = pesoArista;
                        break;
                    } else if (pesoArista < peso) {
                        T = ad.getDestino();
                        peso = pesoArista;
                    }
                    }
                   
                }
                listarPesos.insertarNodo(peso);
                caminos.insertarNodo(T);
                inicial = T;
                if (verticeF.intValue() == inicial.intValue()) {
                    finalizar = true;
                }
            }
       
        return caminos;
    }
    public boolean estaEnCamino(Lista<Integer> lista, Integer vertice){
    Boolean band = false;
        for (int i = 0; i < lista.tamanio(); i++) {
            if (lista.consultarDatoPosicion(i).intValue() == vertice.intValue()) {
                band = true;
                break;
            }
        }
    return band;
    }
    
   public String caminosR(int i, int k, String[][] caminosAux, String caminoRecorrido) {
        if (caminosAux[i][k].equals("")) {
            return "";
        } else {
            caminoRecorrido += caminosR(i, Integer.parseInt(caminosAux[i][k]), caminosAux, caminoRecorrido) + (Integer.parseInt(caminosAux[i][k]) + 1) + ", ";
            return caminoRecorrido;
        }
    }
    public Lista<Object> algoritmoFloyd(long[][] mAdy, int origen, int destino) {
        Lista<Object>lista = new Lista<>();
        int vertices = mAdy.length;
        long matrizAdyacencia[][] = mAdy;
        String caminos[][] = new String[vertices][vertices];
        String caminosAuxiliares[][] = new String[vertices][vertices];
        String caminoRecorrido = "", cadena = "", caminitos = "";
        int i, j, k;
        float temporal1, temporal2, temporal3, temporal4, minimo;
        for (i = 0; i < vertices; i++) {
            for (j = 0; j < vertices; j++) {
                caminos[i][j] = "";
                caminosAuxiliares[i][j] = "";
            }
        }
        for (k = 0; k < vertices; k++) {
            for (i = 0; i < vertices; i++) {
                for (j = 0; j < vertices; j++) {
                    temporal1 = matrizAdyacencia[i][j];
                    temporal2 = matrizAdyacencia[i][k];
                    temporal3 = matrizAdyacencia[k][j];
                    temporal4 = temporal2 + temporal3;
                    minimo = Math.min(temporal1, temporal4);
                    if (temporal1 != temporal4) {
                        if (minimo == temporal4) {
                            caminoRecorrido = "";
                            caminosAuxiliares[i][j] = k + "";
                            caminos[i][j] = caminosR(i, k, caminosAuxiliares, caminoRecorrido) + (k + 1);

                        }
                    }
                    matrizAdyacencia[i][j] = (long) minimo;
                }
            }
        }
        for (i = 0; i < vertices; i++) {
            for (j = 0; j < vertices; j++) {
                if (matrizAdyacencia[i][j] != 1000000000) {
                    if (i != j) {
                        if (caminos[i][j].equals("")) {
                            if ((i + 1) == origen && (j + 1) == destino) {
                                lista.insertarNodo((i+1));
                                lista.insertarNodo((j+1));
                                lista.insertarNodo((i+1));
                                lista.insertarNodo((j+1));
                            }
                        } else {
                            if ((i + 1) == origen && (j + 1) == destino) {
                                lista.insertarNodo((i+1));
                                lista.insertarNodo((j+1));
                                lista.insertarNodo((i+1));
                                lista.insertarNodo((caminos[i][j]));
                                lista.insertarNodo((j+1));
                            }
                        }
                    }
                }
            }
        }
        return lista;
    }
    
        public Lista<Integer> busquedaAnchura(Integer vI)
    {
        Lista<Integer> camino = new Lista();
        Integer nodosVisitados[] = new Integer[numVertices()];
        Cola<Integer> recorrido = new Cola<>(numVertices());
        
        Integer actual, nodoSiguiente;

        recorrido.queue(vI);
        nodosVisitados[vI] = 0;
        camino.add(vI);
        
        while(!recorrido.isEmpty())
        {
            actual = recorrido.dequeue();
            
            for (int i = 0; i < adyacentes(actual).length(); i++)
            {
                nodoSiguiente = adyacentes(actual).getByIndex(i).getDestino();
                if(nodosVisitados[nodoSiguiente] == null)
                {
                    nodosVisitados[nodoSiguiente] = 0;
                    recorrido.queue(nodoSiguiente);
                    camino.add(nodoSiguiente);
                }
                
            }
            
        }
 
        return camino;
    }
    
    
    public Lista<Integer> busquedaProfundidad(Integer vI)
    {
        Lista<Integer> camino = new Lista<>();
        Boolean nodosVisitados[] = new Boolean[numVertices()];
        
        ejectuarBusquedaEnProfundidad(vI, camino, nodosVisitados);
        
        return camino;
    }
    
    private void ejectuarBusquedaEnProfundidad(Integer v, Lista<Integer> camino, Boolean nv[])
    {
        int nodoSiguiente;
        
        nv[v] = true;
        camino.add(v);
        
        for (int i = 0; i < adyacentes(v).length(); i++) 
        {
            nodoSiguiente = adyacentes(v).getByIndex(i).getDestino();
            
            if(nv[nodoSiguiente] == null)
            {
                ejectuarBusquedaEnProfundidad(nodoSiguiente, camino, nv);
            }
        }
        
    }
    
    public Integer[] algoritmoDijkstra(Integer vI, Integer vF) throws Exception
    {
        Double[][] grafo = convertirGrafoMatriz();
        Boolean[] nodosVisitados = new Boolean[grafo.length];
        Double[] distancia = new Double[grafo.length];
        System.out.println("hola");
        
        for (int i = 0; i < grafo.length; i++)
        {
            distancia[i] = Double.MAX_VALUE;
            nodosVisitados[i] = false;
        }
        
        distancia[vI] = 0.0;
        
        Integer recorrido[] = new Integer[grafo.length];
        recorrido[vI] = Integer.MIN_VALUE;
        
        for (int i = 0; i < grafo.length; i++)
        {
            Integer nodoActual = distanciaMinima(distancia, nodosVisitados);
            nodosVisitados[nodoActual] = true;
            
            for (int j = 0; j < grafo.length; j++)
            {
               if(!nodosVisitados[j] && grafo[nodoActual][j] != 0.0 && distancia[nodoActual] != Double.MAX_VALUE && (distancia[nodoActual] + grafo[nodoActual][j]) < distancia[j])
               {
                   recorrido[j] = nodoActual;
                   distancia[j] = distancia[nodoActual] + grafo[nodoActual][j];
               }
                
            }
        }

        Integer caminoCorto[] = new Integer[recorrido.length];
        Integer i = 0;
        
        while(!Objects.equals(vI, vF))
        {
            caminoCorto[i] = vF;
            vF = recorrido[vF];
            i++;
        }

        caminoCorto[i] = vI;
        
        return caminoCorto;
    }
    private Integer distanciaMinima(Double dis[], Boolean nVisit[])
    {
        Double valorMinimo = Double.MAX_VALUE;
        Integer indiceMinimo = Integer.MIN_VALUE;
        
        for (int i = 0; i < numVertices(); i++)
        {
            if(!nVisit[i] && dis[i] <= valorMinimo)
            {
                valorMinimo = dis[i];
                indiceMinimo = i;
            }
        }
        return indiceMinimo;
    }
     public Double[][] convertirGrafoMatriz()
    {
        Double matrizGrafo[][] = new Double[numVertices()][numVertices()];
        try
        {
            for (int i = 0; i < matrizGrafo.length; i++)
            {
                for (int j = 0; j < matrizGrafo.length; j++)
                {
                    if (existeArista(i, j))
                    {
                        matrizGrafo[i][j] = pesoArista(i, j);
                    }
                    else
                    {
                        matrizGrafo[i][j] = 0.0; 
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        return matrizGrafo;
    }
}
