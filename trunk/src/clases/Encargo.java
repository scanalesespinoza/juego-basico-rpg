/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author gerald
 */
public class Encargo {
    public Encargo[] encargos;

    private short idPersonaje;

    private short idMision;

    private Date fechaComienzo;

    private short rolPersonaje;

    private Date fechaFin;

    private dbDelegate conect = new dbDelegate();

    public Encargo(short idPj){
        this.idPersonaje=idPj;
    }

    public void cargaEncargos() throws SQLException{

        ResultSet rCantidadEncargos=conect.Consulta("SELECT count(idPersonaje) filas FROM encargo WHERE idPersonaje="+this.getIdPersonaje());
        ResultSet rEncargos=conect.Consulta("SELECT * FROM encargo WHERE idPersonaje="+this.getIdPersonaje());
        System.out.println("Carga Encargos");
        if(rCantidadEncargos.next()){
            encargos = new Encargo[rCantidadEncargos.getInt("filas")];
        }
        int i=0;
        while(rEncargos.next()){
            encargos[i] = new Encargo(this.getIdPersonaje());
            encargos[i].setIdPersonaje(rEncargos.getShort("idPeronsaje"));
            encargos[i].setIdMision(rEncargos.getShort("idMision"));
            encargos[i].setFechaComienzo(rEncargos.getDate("fechaComienzo"));
            encargos[i].setRolPersonaje(rEncargos.getShort("rolPersonaje"));
            encargos[i].setFechaFin(rEncargos.getDate("fechaFin"));

            i+=1;
        }

    }
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
