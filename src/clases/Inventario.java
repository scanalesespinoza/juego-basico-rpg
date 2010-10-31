/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author gerald
 */
public class Inventario {

    private short idPersonaje;
    private dbDelegate conexion;
    private HashMap<Short, Item> objetos;//contiene los objetos del personaje

    public Inventario() {
        this.objetos = new HashMap<Short, Item>();
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
     * 
     */

    public void agregarItem(short idItem, short cantidad) {
        Item item = creaItem(idItem, cantidad, (short) 0);
        if (this.tieneItem(idItem)) {
            this.getObjetos().get(idItem).sumarCantidad(cantidad);
        } else {
            this.getObjetos().put(idItem, item);
        }
    }

    public void agregarItem(short idItem) {
        Item item = creaItem(idItem, (short) 1, (short) 0);
        if (this.tieneItem(idItem)) {
            this.getObjetos().get(idItem).sumarCantidad((short) 1);
        } else {
            this.getObjetos().put(idItem, item);
        }
    }
    /*
     * Borra item según la cantidad indicada
     */

    public void eliminarItem(short idItem, short cantidad) {
        if (tieneItem(idItem, cantidad)) {
            this.getObjetos().get(idItem).restarCantidad(cantidad);
        }
    }
    /*
     * Borra item sin importar la cantidad
     */

    public void eliminarItem(short idItem) {
        if (tieneItem(idItem)) {
            this.getObjetos().remove(idItem);
        }
    }

    /*
     * Verifica si tiene el objeto en cuestión
     */
    public boolean tieneItem(short idObjeto) {
        return this.getObjetos().containsKey(idObjeto);
    }

    /*
     * Verifica si tiene la cantidad requerida del objeto en cuestión
     */
    public boolean tieneItem(short idObjeto, short cantidad) {
        return this.getObjetos().containsKey(idObjeto)
                && this.getObjetos().get(idObjeto).getCantidad() == cantidad;

    }

    /*
     * retorna el el peso total usado por el inventario(suma de los objetos)
     */
    public int getPesoUsado() {
        //objeto la lista de objetos
        int pesoTotal = 0;
        Iterator it = this.getObjetos().entrySet().iterator();
        Objeto obj = new Objeto();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            obj.setObjeto(Short.parseShort(e.getKey().toString()));
            pesoTotal += obj.getPeso();
        }
        return pesoTotal;
    }

    /*
     * retorna el valor en dinero que suman los elementos
     */
    public int getDineroTotal() {
        //objeto la lista de objetos
        int dineroTotal = 0;
        Iterator it = this.getObjetos().entrySet().iterator();
        Objeto obj = new Objeto();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            obj.setObjeto(Short.parseShort(e.getKey().toString()));
            dineroTotal += obj.getValorDinero();
        }
        return dineroTotal;
    }

    /*
     * Crea un item privado para centralizar la creacion y favorecer la modularidad
     */
    private Item creaItem(short idObjeto, short cantidad, short estaequipado) {
        return new Item(this.getIdPersonaje(), idObjeto, cantidad, estaequipado);
    }

//    /*DEBEN IR EN PERSONAJE
//     * Equipa un item
//     */
//    public void equiparItem(short idItem){
//        this.getObjetos().get(idItem).setEstaEquipado((short)1);
//    }
//
//    /*
//     * Desequipa un item
//     */
//    public void desequiparItem(short idItem){
//        this.getObjetos().get(idItem).setEstaEquipado((short)0);
//    }

    /*
     * Devuelve la cantidad de objetos de un determinado tipo
     */
    public int contarItem(short idItem){
        int cuenta = 0;
        if (tieneItem(idItem)){
            cuenta = this.getObjetos().get(idItem).getCantidad();
        }
        return cuenta;
    }
    /*
     * devuele la cantidad en total de todos los objetos
     */

    public int contarTodosItems() {
        //objeto la lista de objetos
        int cuenta = 0;
        Iterator it = this.getObjetos().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            cuenta += this.getObjetos().get(Short.parseShort(e.getKey().toString())).getCantidad();
        }
        return cuenta;
    }

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

        public Item() {
        }

        public Item(short idPersonaje, short idObjeto, short cantidad, short estaEquipado) {
            this.idPersonaje = idPersonaje;
            this.idObjeto = idObjeto;
            this.cantidad = cantidad;
            this.estaEquipado = estaEquipado;
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

        public void sumarCantidad(short cantidad) {
            this.setCantidad((short) (this.getCantidad() + cantidad));
        }
        /*
         * borra la cantidad indicada, si es menor que cero lo deja en cero
         *
         */

        public void restarCantidad(short cantidad) {
            short valor = (short) (this.getCantidad() - cantidad);
            if (this.getCantidad() - cantidad < 0) {
                valor = 0;
            }
            this.setCantidad(valor);
        }
    }
}
