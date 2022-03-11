package controlador.grafo;

/**
 *
 * @author usuario
 */
public class GrafoND extends GrafoD {

    public GrafoND(Integer numV) {
        super(numV);
    }

    @Override
    public void insertarArista(Integer i, Integer j, Double peso) {
        try {
            if (i.intValue() <= numVertices() && j.intValue() <= numVertices()) {
                if (!existeArista(i, j)) {
                    numA++;
                    listaAdycente[i].insertarNodo(new Adyacencia(j, peso));
                    listaAdycente[j].insertarNodo(new Adyacencia(i, peso));
                }
            }
        } catch (Exception e) {
            System.out.println("Error en insertar GND");
        }
    }

}
