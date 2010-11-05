/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgame.JGColor;
import jgame.JGFont;
import jgame.JGObject;
import jgame.JGTimer;

/**
 *
 * @author gerald
 */
public class Ventana extends JGObject {

    private ArrayList<String> mensajes;
    private int segundos;
    private Boolean esAlerta;
    private String imagen = null;
    final static byte LINEAS = 3;

    /**
     * Muestra una ventana del tipo alerta usada para mostrar mensajes flash
     * 
     * @param mensaje
     */
    public Ventana(String mensaje) {
        super("alerta", false, +255, +305, 0, "calle");
        this.esAlerta = true;
        this.segundos = 3;
        this.desplegarVentana();
        this.desplegarMensaje(mensaje);
        new JGTimer(
                (int) (eng.getFrameRate() * this.getSegundos()), //calculo los frames
                true // true indica que la alarma solo se dispara una vez
                ) {
            //método que se debe redefinir para que ejecute una acción
            //esta vez, desaparecer el objeto ventana

            @Override
            public void alarm() {
                remove();
            }
        };

    }

    public Ventana(double x, double y, ArrayList<String> mensajes) {
        super("alerta", false, x, y, 0, "calle");
        this.mensajes = mensajes;
        this.x = x;
        this.y = y;

        desplegarVentana(x, y);
        desplegarMensaje(x, y, mensajes);
        //remove();
    }

    /**
     * despliega una lista de mensajes en fragmetos de 3 líneas
     * @param mensajes
     * @param x coordenada X donde se ubica la ventana
     * @param y coordenada Y donde se ubica la ventana
     */
    public void desplegarMensaje(double x, double y, ArrayList<String> mensajes) {
        byte separadorLinea = 16;
        byte contadorLinea = 0;
        boolean siguienteTexto = true;
        Iterator iterador = mensajes.listIterator();
        while (iterador.hasNext()) {
            this.limpiarVentana(x, y);
            eng.setColor(JGColor.black);
            eng.setFont(new JGFont("Arial", 0, 10));
            if (siguienteTexto) {
                if (iterador.hasNext()) {
                    desplegarMensaje(x + 60, y + separadorLinea * contadorLinea, iterador.next().toString());
                    contadorLinea++;
                }
                if (iterador.hasNext()) {
                    desplegarMensaje(x + 60, y + separadorLinea * contadorLinea, iterador.next().toString());
                    contadorLinea++;
                }
                if (iterador.hasNext()) {
                    desplegarMensaje(x + 60, y + separadorLinea * contadorLinea, iterador.next().toString());
                    contadorLinea++;
                }
                //siguienteTexto = false;
            }
//            if (eng.getKey(eng.KeyEnter)) {
//                siguienteTexto = true;
//            }
            contadorLinea = 0;

        }

    }

    private void limpiarVentana(double x, double y) {
        eng.setColor(JGColor.yellow);
        eng.drawRect(eng.viewXOfs() + x + 5, eng.viewYOfs() + y + 5, 290, 90, true, false);
    }

    /**
     *despliega mensaje en el centro de la pantalla
     * @param mensaje
     */
    public void desplegarMensaje(String mensaje) {
        eng.setColor(JGColor.red);
        eng.setFont(new JGFont("Arial", 0, 16));
        eng.drawString(mensaje, eng.viewWidth() / 2, eng.viewHeight() / 2 + 45, 0);
    }

    /**
     * despliega mensaje en la posicion indicada
     * @param x
     * @param y
     * @param mensaje
     */
    public void desplegarMensaje(double x, double y, String mensaje) {
        //eng.setColor(JGColor.red);
        //eng.setFont(new JGFont("Arial", 0, 16));
        eng.drawString(mensaje, x, y + 45, 0);

    }

    private void desplegarVentana() {
        //borde externo
        eng.setColor(JGColor.red);
        eng.drawRect(eng.viewXOfs() + 200, eng.viewYOfs() + 250, 300, 100, true, false);
        //interior
        eng.setColor(JGColor.white);
        eng.drawRect(eng.viewXOfs() + 205, eng.viewYOfs() + 255, 290, 90, true, false);
    }

    private void desplegarVentana(double x, double y) {
        //borde externo
        eng.setColor(JGColor.red);
        eng.drawRect(eng.viewXOfs() + x, eng.viewYOfs() + y, 300, 100, true, false);
        //interior
        eng.setColor(JGColor.white);
        eng.drawRect(eng.viewXOfs() + x + 5, eng.viewYOfs() + y + 5, 290, 90, true, false);
    }

    //private void
    /**
     * Get the url value of imagen
     *
     * @return the url value of imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Set the value of imagen
     *
     * @param url new url value of imagen
     */
    public void setImagen(String url) {
        this.imagen = url;
    }

    /**
     * Get the value of esAlerta
     *
     * @return the value of esAlerta
     */
    public Boolean getEsAlerta() {
        return esAlerta;
    }

    /**
     * Set the value of esAlerta
     *
     * @param esAlerta new value of esAlerta
     */
    public void setEsAlerta(Boolean esAlerta) {
        this.esAlerta = esAlerta;
    }

    /**
     * Get the value of segundos
     *
     * @return the value of segundos
     */
    public int getSegundos() {
        return segundos;
    }

    /**
     * Set the value of segundos
     *
     * @param segundos new value of segundos
     */
    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public ArrayList<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList<String> mensajes) {
        this.mensajes = mensajes;
    }
}
