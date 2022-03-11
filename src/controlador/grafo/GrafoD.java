package controlador.grafo;

import lista.controlador.Lista;

/**
 *
 * @author usuario
 */
public class GrafoD extends Grafo {

    private Integer numV;
    protected Integer numA;
    protected Lista<Adyacencia> listaAdycente[];

    public GrafoD(Integer numV) {
        this.numV = numV;
        numA = 0;
        listaAdycente = new Lista[this.numV + 1];
        for (int i = 1; i <= this.numV; i++) {
            listaAdycente[i] = new Lista<>();
        }
    }

    @Override
    public Integer numVertices() {
        return this.numV;
    }

    @Override
    public Integer numAristas() {
        return this.numA;
    }

    @Override
    public Boolean existeArista(Integer i, Integer j) throws Exception {
        Boolean esta = false;
        //G = 4 V ------ V5    V3
        if (i.intValue() <= numV && j.intValue() <= numV) {
            Lista<Adyacencia> lista_adycentes = listaAdycente[i];
            for (int k = 0; k < lista_adycentes.tamanio(); k++) {
                Adyacencia aux = lista_adycentes.consultarDatoPosicion(k);
                if (aux.getDestino().intValue() == j.intValue()) {
                    esta = true;
                    break;
                }
            }
        }
        return esta;
    }

    @Override
    public Double pesoArista(Integer i, Integer j) {
        Double peso = Double.NaN;//0.0  ----< devolver 0
        try {
            if (existeArista(i, j)) {
                Lista<Adyacencia> adyacentes = listaAdycente[i];
                for (int k = 0; k < adyacentes.tamanio(); k++) {
                    Adyacencia aux = adyacentes.consultarDatoPosicion(k);
                    if (aux.getDestino().intValue() == j.intValue()) {
                        peso = aux.getPeso();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("No se puede encontrar el dato");
        }
        return peso;
    }

    @Override
    public void insertarArista(Integer i, Integer j) {
        insertarArista(i, j, Double.NaN);
    }

    @Override
    public void insertarArista(Integer i, Integer j, Double peso) {
        try {
            if (i.intValue() <= numV && j.intValue() <= numV) {
                if (!existeArista(i, j)) {
                    numA++;
                    listaAdycente[i].insertarNodo(new Adyacencia(j, peso));
                }
            }
        } catch (Exception e) {
            System.out.println("Error en insertarArista");
        }
    }

    @Override
    public Lista<Adyacencia> adyacentes(Integer i) {
        
        try {
            return listaAdycente[i];
        } catch (Exception e) {
            return null;
        }
    }

}
