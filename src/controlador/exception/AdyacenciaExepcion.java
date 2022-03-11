/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package controlador.exception;

/**
 *
 * @author JavierSarango
 */
public class AdyacenciaExepcion extends Exception{

    /**
     * Creates a new instance of <code>Adyacenciaexception</code> without detail
     * message.
     */
    public AdyacenciaExepcion() {
    }

    /**
     * Constructs an instance of <code>Adyacenciaexception</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AdyacenciaExepcion(String msg) {
        super(msg);
    }
}
