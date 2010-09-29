/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gerald
 */
public class Inventario {

    private short idPersonaje;
    private String nombre;
    private short idObjeto;
    private short cantidad;
    private short estaEquipado;

    private dbDelegate conect = new dbDelegate();

    public Inventario() {
        //Buscar los objetos que tiene asociado el personaje segun la ID entregada
    }

    public boolean comparaItem(short idJugador, short idNpc) throws SQLException{
        boolean tiene =false;
        ResultSet rs =conect.comparaInvetario(idJugador, idNpc);
        if(rs.next()){
            tiene=true;
        }
        System.out.println("Tiene: "+tiene);
        return tiene;
    }

    public void agregarItem(short idJugador, short idItem,short cantidad) throws SQLException{
        conect.agregarItem(idJugador, idItem, cantidad);
    }

    public short getCantidad() {
        return cantidad;
    }

    public void setCantidad(short cantidad) {
        this.cantidad = cantidad;
    }

    public short getEstaEquipado() {
        return estaEquipado;
    }

    public void setEstaEquipado(short estaEquipado) {
        this.estaEquipado = estaEquipado;
    }


    public short getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(short idObjeto) {
        this.idObjeto = idObjeto;
    }

    public short getIdPersonaje() {
        return idPersonaje;
    }

    public void setIdPersonaje(short idPersonaje) {
        this.idPersonaje = idPersonaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
