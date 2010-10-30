/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author gerald
 */
public class ContrincanteHabilidad {

    private short idPersonaje;
    private HashMap<Short, UnaHabilidad> habilidades;//contiene las habilidades del personaje
    private dbDelegate conexion;

    public short getIdPersonaje() {
        return idPersonaje;
    }

    public void setIdPersonaje(short idPersonaje) {
        this.idPersonaje = idPersonaje;
    }

    /*
     * Carga las habilidades desde la base de datos asociadas al personaje
     * las deja en el arreglo "habilidades"
     */
    public void cargarHabilidades(Short id) {
        this.conexion = new dbDelegate();
        System.out.println("Inicio obtiene datos personaje");
        String StrSql = "SELECT * FROM contrincante_habilidad "
                + "WHERE personaje_id = " + id;
        try {
            ResultSet res = conexion.Consulta(StrSql);
            System.out.println(StrSql);
            byte i = 0;
            while (res.next()) {
                UnaHabilidad habilidad = new UnaHabilidad();
                habilidad.setIdHabilidad(res.getShort("habilidad_id"));
                habilidad.setIdPersonaje(res.getShort("personaje_id"));
                habilidad.setNivelHabilidad(res.getShort("nivelhabilidad"));
                this.getHabilidades().put(habilidad.getIdHabilidad(), habilidad);
                i += 1;
            }
        } catch (SQLException ex) {
            System.out.println("Problemas en: clase->personaje , método->cargarHabilidades() " + ex);
        }

    }

    private HashMap<Short, UnaHabilidad> getHabilidades() {
        return habilidades;
    }

    private class UnaHabilidad {

        private short idPersonaje;
        private short idHabilidad;
        private short nivelHabilidad;

        public short getIdHabilidad() {
            return idHabilidad;
        }

        public void setIdHabilidad(short idHabilidad) {
            this.idHabilidad = idHabilidad;
        }

        public short getIdPersonaje() {
            return idPersonaje;
        }

        public void setIdPersonaje(short idPersonaje) {
            this.idPersonaje = idPersonaje;
        }

        public short getNivelHabilidad() {
            return nivelHabilidad;
        }

        public void setNivelHabilidad(short nivelHabilidad) {
            this.nivelHabilidad = nivelHabilidad;
        }
    }
}
