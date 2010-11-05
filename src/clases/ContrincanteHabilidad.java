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
            System.out.println("Problemas en: clase->ContrincanteHabilidad , método->cargarHabilidades() " + ex);
        }

    }

    /*
     * Aumenta el nivel de la habilidad del personaje
     */

    public void aumentaNivelHabilidad(short idHabilidad){
        this.conexion = new dbDelegate();
        System.out.println("Inicio aumentaHabilidad");
        String StrSql = "Update contrincante_habilidad "
                + "SET nivelHabilidad = nivelHabilidad+1 "
                + "WHERE personaje_id = " + idPersonaje + "AND"
                + "habilidad_id = " + idHabilidad;
        conexion.Ejecutar(StrSql);
    }

    /*
     * Agrega nuevas habilidades al personaje segun el nivel adquirido
     */
    
    public void agregaNuevaHabilidad(short nivelPersonaje){
        this.conexion = new dbDelegate();
        String StrSqlUno = new String();
        String StrSqlDos = new String();
        if(nivelPersonaje==3){
            StrSqlUno="INSERT INTO contrincante_habilidad VALUES ("+idPersonaje+",3,1)";
            StrSqlDos="INSERT INTO contrincante_habilidad VALUES ("+idPersonaje+",4,1)";
        }else if(nivelPersonaje==7){
            StrSqlUno="INSERT INTO contrincante_habilidad VALUES ("+idPersonaje+",5,1)";
            StrSqlDos="INSERT INTO contrincante_habilidad VALUES ("+idPersonaje+",6,1)";
        }else if(nivelPersonaje==12){
            StrSqlUno="INSERT INTO contrincante_habilidad VALUES ("+idPersonaje+",7,1)";
            StrSqlDos="INSERT INTO contrincante_habilidad VALUES ("+idPersonaje+",8,1)";
        }else if(nivelPersonaje==18){
            StrSqlUno="INSERT INTO contrincante_habilidad VALUES ("+idPersonaje+",9,1)";
            StrSqlDos="INSERT INTO contrincante_habilidad VALUES ("+idPersonaje+",10,1)";
        }else if(nivelPersonaje==25){
            StrSqlUno="INSERT INTO contrincante_habilidad VALUES ("+idPersonaje+",3,1)";
            StrSqlDos="INSERT INTO contrincante_habilidad VALUES ("+idPersonaje+",4,1)";
        }
        conexion.Ejecutar(StrSqlUno);
        conexion.Ejecutar(StrSqlDos);
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
