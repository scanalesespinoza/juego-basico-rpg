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
public class Inventario {

    private short idPersonaje;
    private dbDelegate conexion;
    private HashMap<Short, Item> objetos;//contiene los objetos del personaje

    public Inventario() {
        //Buscar los objetos que tiene asociado el personaje segun la ID entregada
    }
    
    /*
     * Carga los objetos desde la base de datos asociados al personaje
     * las deja en el arreglo "objetos"
     */
    public void cargarInventario(short id) {
        this.conexion = new dbDelegate();
        System.out.println("Inicio obtiene datos personaje");
        String StrSql = "SELECT   inv.Personaje_id idPersonaje, inv.Objeto_id idObjeto,"
                + " inv.cantidad cantidad, obj.nombre nombre, inv.estaEquipado estaEquipado "
                + "  FROM inventario inv, objeto obj "
                + " WHERE inv.Personaje_id=" + id
                + "   AND inv.Objeto_id=obj.id";
        System.out.println(StrSql);
        try {
            ResultSet res = conexion.Consulta(StrSql);
            byte i = 0;
            while (res.next()) {
                Item item = new Item();
                item.setIdObjeto(res.getShort("idObjeto"));
                item.setIdPersonaje(res.getShort("idPersonaje"));
                item.setCantidad(res.getShort("cantidad"));
                item.setEstaEquipado(res.getShort("estaequipado"));
                this.objetos.put(item.getIdObjeto(), item);
                i += 1;
            }
        } catch (SQLException ex) {
            System.out.println("Problemas en: clase->Inventario , método->cargarInventario() " + ex);
        }

    }
    /*
     * agrega un item al inventario, si el item está, se suma la cantidad
     * FALTA VALIDAR QUE NO SUPERE EL PESO
     */
    public void agregarItem(short idItem, short cantidad){
        Item item = new Item();
        item.setCantidad(cantidad);
        item.setEstaEquipado((short)0);//se asume que item nuevo no se equipa
        item.setIdObjeto(idItem);
        item.setIdPersonaje(this.idPersonaje);
        if (this.tieneItem(idItem)){
            this.getObjetos().get(idItem).sumarCantidad(cantidad);
        }else this.getObjetos().put(idItem, item);
    }
    /*
     * Borra item según la cantidad indicada
     */
    public void borrarItem(short idItem, short cantidad){
        if (this.getObjetos().containsKey(idItem)){
            this.getObjetos().get(idItem).restarCantidad(cantidad);
        }
    }
    /*
     * Borra item sin importar la cantidad
     */
    public void borrarItem(short idItem){
        if (this.getObjetos().containsKey(idItem)){
            this.getObjetos().remove(idItem);
        }
    }

    /*
     * Verifica si tiene el objeto en cuestión
     */
    public boolean tieneItem(short  idObjeto){
        return this.getObjetos().containsKey(idObjeto);
    }
    
//    public boolean comparaItem(short idJugador, short idNpc) throws SQLException {
//        boolean tiene = false;
//        ResultSet rs = conexion.comparaInvetario(idJugador, idNpc);
//        if (rs.next()) {
//            tiene = true;
//        }
//        System.out.println("Tiene: " + tiene);
//        return tiene;
//    }

    public short getIdPersonaje() {
        return idPersonaje;
    }

    public void setIdPersonaje(short idPersonaje) {
        this.idPersonaje = idPersonaje;
    }

    private HashMap<Short, Item> getObjetos() {
        return objetos;
    }

    private class Item {

        private short idPersonaje;
        private short idObjeto;
        private short cantidad;
        private short estaEquipado;

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

        public void sumarCantidad(short cantidad){
            this.setCantidad((short) (this.getCantidad() + cantidad));
        }
        /*
         * borra la cantidad indicada, si es menor que cero lo deja en cero
         *
         */
        public void restarCantidad(short cantidad){
            short valor = (short) (this.getCantidad() - cantidad);
            if(this.getCantidad() - cantidad < 0){
                valor = 0;
            }
            this.setCantidad(valor);
        }

   }
}
