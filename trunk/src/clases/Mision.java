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
public class Mision {
    public Mision[] misiones;

    private Short idMision;

    private String nombre;

    private String descripcion;

    private short nivelRequerido;

    private short recompensaExp;

    private boolean repetible;

    private short idPersonajeConcluyeMision;

    private dbDelegate conect = new dbDelegate();

    public void Mision(){

    }

//    public void cargaMisiones(short idPj) throws SQLException{
//
//        ResultSet rTamanoInventario=conect.obtieneTamanoInvetario(idPj);
//        ResultSet rInventario=conect.obtieneInvetario(idPj);
//        System.out.println("CargaIventario");
//        if(rTamanoInventario.next()){
//            misiones = new Mision[rTamanoInventario.getInt("filas")];
//            //System.out.println("rInventario.getInt('filas')"+rInventario.getInt("filas"));
//            //System.out.println("Largo Inv: "+inv.length);
//        }
//        int i=0;
//        while(rInventario.next()){
//            misiones[i] = new Mision();
//            System.out.println("Indice: "+i);
//            System.out.println("rInventario.getShort('idPersonaje')"+rInventario.getShort("idPersonaje"));
//            //misiones[i].setIdPersonaje(rInventario.getShort("idPersonaje"));
//            //System.out.println("inv["+i+"].isPersonaje: "+inv[i].getIdPersonaje());
//
//            System.out.println("rInventario.getShort('idObjeto')"+rInventario.getShort("idObjeto"));
//            //misiones[i].setIdObjeto(rInventario.getShort("idObjeto"));
//            System.out.println("rInventario.getShort('cantidad')"+rInventario.getShort("cantidad"));
//            //misiones[i].setCantidad(rInventario.getShort("cantidad"));
//            System.out.println("rInventario.getShort('estaEquipado')"+rInventario.getShort("estaEquipado"));
//            //misiones[i].setEstaEquipado(rInventario.getShort("estaEquipado"));
//            System.out.println("rInventario.getString('nombre')"+rInventario.getString("nombre"));
//            misiones[i].setNombre(rInventario.getString("nombre"));
//            i+=1;
//        }
//
//    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Short getIdMision() {
        return idMision;
    }

    public void setIdMision(Short idMision) {
        this.idMision = idMision;
    }

    public short getIdPersonajeConcluyeMision() {
        return idPersonajeConcluyeMision;
    }

    public void setIdPersonajeConcluyeMision(short idPersonajeConcluyeMision) {
        this.idPersonajeConcluyeMision = idPersonajeConcluyeMision;
    }

    public short getNivelRequerido() {
        return nivelRequerido;
    }

    public void setNivelRequerido(short nivelRequerido) {
        this.nivelRequerido = nivelRequerido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public short getRecompensaExp() {
        return recompensaExp;
    }

    public void setRecompensaExp(short recompensaExp) {
        this.recompensaExp = recompensaExp;
    }

    public boolean isRepetible() {
        return repetible;
    }

    public void setRepetible(boolean repetible) {
        this.repetible = repetible;
    }

    public dbDelegate getConect() {
        return conect;
    }

    public void setConect(dbDelegate conect) {
        this.conect = conect;
    }

    
}
