/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;

/**
 *
 * @author gerald
 */
public class Encargo {

    private short idPersonaje;
    private dbDelegate conexion;
    private HashMap<Short, UnEncargo> misiones;//contiene las misiones del personaje

    public Encargo(short idPj) {
        this.idPersonaje = idPj;
    }

    public Encargo() {
    }

    public short getIdPersonaje() {
        return idPersonaje;
    }

    public void setIdPersonaje(short idPersonaje) {
        this.idPersonaje = idPersonaje;
    }

    private HashMap<Short, UnEncargo> getMisiones() {
        return misiones;
    }

    /*
     * Carga las misiones desde la base de datos asociados al personaje
     * las deja en el arreglo "objetos"
     */
    public void cargarMisiones(Short id) {
        this.conexion = new dbDelegate();
        System.out.println("Inicio obtiene datos personaje");
        String StrSql = "SELECT * FROM encargo "
                + " WHERE personaje_id = " + id
                + " AND updated_at IS NULL";
        System.out.println(StrSql);
        try {
            ResultSet res = conexion.Consulta(StrSql);
            byte i = 0;
            while (res.next()) {
                UnEncargo mision = new UnEncargo();
                mision.setIdMision(res.getShort("mision_id"));
                mision.setIdPersonaje(res.getShort("personaje_id"));
                mision.setFechaComienzo(res.getDate("created_at"));
                mision.setRolPersonaje(res.getShort("rolpersonaje"));
                mision.setFechaFin(null);
                this.misiones.put(mision.getIdMision(), mision);
                i += 1;
            }
        } catch (SQLException ex) {
            System.out.println("Problemas en: clase->Encargo , método->cargarMisiones() " + ex);
        }

    }

    public void agregarMision(short idMision, short rol) {
        if (!this.tieneMision(idMision)) {
            UnEncargo mision = new UnEncargo();
            mision.setIdMision(idMision);
            mision.setRolPersonaje(rol);
            mision.setFechaFin(null);
            mision.setFechaComienzo(Date.valueOf(getFecha()));
        }
    }

    public void abandonaMision(short idMision){
        this.conexion = new dbDelegate();
        System.out.println("Inicio aumentaHabilidad");
        String StrSql = "DELETE FROM contrincante_habilidad "
                + "WHERE habilidad_id = " + idMision;
        conexion.Ejecutar(StrSql);
    }

    /*
     * Verifica si el personaje tiene una mision
     */
    public boolean tieneMision(short idMision) {
        return this.getMisiones().containsKey(idMision);
    }

    public String getFecha() {
        Calendar c1 = Calendar.getInstance();
        String dia = Integer.toString(c1.get(Calendar.DATE));
        String mes = Integer.toString(c1.get(Calendar.MONTH));
        String annio = Integer.toString(c1.get(Calendar.YEAR));
        String hour = Integer.toString(Calendar.HOUR);
        String second = Integer.toString(Calendar.SECOND);
        String minutos = Integer.toString(Calendar.MINUTE);
        String fecha = annio + "/" + mes + "/" + dia + " " + hour + ":" + minutos + ":" + second;
        return fecha;
    }

//    public void cargaEncargos() throws SQLException{
//
//        ResultSet rCantidadEncargos=conect.Consulta("SELECT count(personaje_id) filas FROM encargo WHERE personaje_id="+this.getIdPersonaje());
//        ResultSet rEncargos=conect.Consulta("SELECT * FROM encargo WHERE personaje_id="+this.getIdPersonaje());
//        System.out.println("Carga Encargos");
//        if(rCantidadEncargos.next()){
//            encargos = new Encargo[rCantidadEncargos.getInt("filas")];
//        }
//        int i=0;
//        while(rEncargos.next()){
//            encargos[i] = new Encargo(this.getIdPersonaje());
//            encargos[i].setIdPersonaje(rEncargos.getShort("peronsaje_id"));
//            encargos[i].setIdMision(rEncargos.getShort("mision_id"));
//            encargos[i].setFechaComienzo(rEncargos.getDate("created_at"));
//            encargos[i].setRolPersonaje(rEncargos.getShort("rolPersonaje"));
//            encargos[i].setFechaFin(rEncargos.getDate("updated_at"));
//
//            i+=1;
//        }
//
//    }
    private class UnEncargo {

        private short idPersonaje;
        private short idMision;
        private Date fechaComienzo;
        private short rolPersonaje;
        private Date fechaFin;

        public Date getFechaComienzo() {
            return fechaComienzo;
        }

        public void setFechaComienzo(Date fechaComienzo) {
            this.fechaComienzo = fechaComienzo;
        }

        public Date getFechaFin() {
            return fechaFin;
        }

        public void setFechaFin(Date fechaFin) {
            this.fechaFin = fechaFin;
        }

        public short getIdMision() {
            return idMision;
        }

        public void setIdMision(short idMision) {
            this.idMision = idMision;
        }

        public short getIdPersonaje() {
            return idPersonaje;
        }

        public void setIdPersonaje(short idPersonaje) {
            this.idPersonaje = idPersonaje;
        }

        public short getRolPersonaje() {
            return rolPersonaje;
        }

        public void setRolPersonaje(short rolPersonaje) {
            this.rolPersonaje = rolPersonaje;
        }
    }
}
