/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import extensiones.StdDungeonMonster;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import jgame.*;

/**
 *
 * @author Iarwain
 */

public class Npc extends StdDungeonMonster  {
    private short idNpc;
    private String grafNpc;
    private String nomNpc;
    private int colId;
    public String[] dialogo;
    private dbDelegate conect = new dbDelegate();
    public Calendar c1;
    public boolean cargado = false;
    private Inventario[] inv;


    public Npc(double x,double y,String name,String mediaName,int colId,int tamano,short idNpc, String[] dialogo) throws SQLException {
            super(name,true,x,y,colId,mediaName,tamano);
            this.dialogo=dialogo;
            this.idNpc=idNpc;
            this.grafNpc=mediaName;
            this.nomNpc=name;
            this.colId=colId;
            System.out.println("contruye NPC");
            cargaInventario(idNpc);
            System.out.println("Inventario Npc Cargado");

         
    }

    public String[] obtieneDialogo(){
        /*
        String[] dialogo = {"Hola amigo",
                            "como estas",
                            "Me doy cuenta que no eres de estos lados",
                            "te advierto que es muy peligroso salir de la cuidad",
                            "hay muchas criaturas peligrosas",
                            "si no tienes donde ir puedes quedarte",
                            "mientras ayudes a mantener la cuidad en pie",
                            "todos te aceptaran sin problema",
                            "pensadolo hay mucho trabajo que hacer",
                            "acomapañame a la plaza y danos una mano",
                            "podras conocer al resto de la gente."
                            };
         * 
         */

        return dialogo;
    }

    public void realizaTarea(short idJugador) throws SQLException{
        c1 = Calendar.getInstance();
        String dia = Integer.toString(c1.get(Calendar.DATE));
        String mes = Integer.toString(c1.get(Calendar.MONTH));
        String annio = Integer.toString(c1.get(Calendar.YEAR));
        HashMap datosPj = new HashMap();
        //Carga datos del jugador desde la base de datos
        try{
            datosPj=conect.datosConstruyePersonaje(this.idNpc);
        }
        catch(Exception ex){
            System.out.println("Error al conectar la DB #Jugador carga datos jugador: "+ex);
        }
        int tipo=Integer.parseInt(datosPj.get("tipo").toString());

        if(tipo==1){//si es Npc de Misiones
            try{
                datosPj=conect.datosMision(this.idNpc);
            }
            catch(Exception ex){
                System.out.println("Error al conectar la DB #Jugador carga datos jugador: "+ex);
            }
            ResultSet encargo=conect.Consulta("SELECT * FROM encargo WHERE idPersonaje="+idJugador+" AND idMision="+String.valueOf(datosPj.get("idMision")));
            if(!encargo.next()){

                String insertar=conect.Ejecutar("INSERT INTO encargo (idPersonaje,idMision,fechaComienzo,rolPersonaje,fechaFin) VALUES ("+idJugador+","+String.valueOf(datosPj.get("idMision"))+",'"+annio+"/"+mes+"/"+dia+"',0,"+null+")");
                System.out.println("Insertar encargo: "+insertar);

            }else if(encargo.next()){
                if(encargo.getDate("fechaFin")==null){
                    Inventario cInv = new Inventario();
                    boolean finMision=cInv.comparaItem(idJugador, idNpc);
                    if(finMision){
                        conect.Ejecutar("Update encargo set fechaFin='"+annio+"/"+mes+"/"+dia+"' WHERE idPersonaje="+idJugador+" AND idMision="+encargo.getShort("idMision"));
                    }
                }else{
                    dialogo=null;
                    dialogo=new String[]{"","Muchas gracias por toda tu ayuda!"};
                }
            }
        }else if(tipo==2){//si es Npc Comerciante

        }else if(tipo==3){//si es Npc Mob

        }
    }

    public void pideItem(short idObjeto, short cantidad){
        
    }

    private void cargaInventario(short idPj) throws SQLException{
        ResultSet rInventario=conect.obtieneInvetario(idPj);
        if(rInventario.next()){
            this.inv=new Inventario[rInventario.getInt("filas")];
            for(int i=0;i<inv.length;i++){
                inv[i].setIdPersonaje(rInventario.getShort("idPersonaje"));
                inv[i].setIdObjeto(rInventario.getShort("idObjeto"));
                inv[i].setCantidad(rInventario.getShort("cantidad"));
                inv[i].setEstaEquipado(rInventario.getShort("estaEquipado"));
                inv[i].setNombre(rInventario.getString("nombre"));
            }
        }
    }

    @Override
    public void hit(JGObject obj){
            //System.out.println("Hit NPC!"+obj.getName());
    }

    public short getIdNpc() {
        return idNpc;
    }

    public void setIdNpc(short idNpc) {
        this.idNpc = idNpc;
    }

    public String getGrafNpc() {
        return grafNpc;
    }

    public void setGrafNpc(String grafNpc) {
        this.grafNpc = grafNpc;
    }

    public String getNomNpc() {
        return nomNpc;
    }

    public void setNomNpc(String nomNpc) {
        this.nomNpc = nomNpc;
    }

    public int getColId() {
        return colId;
    }

    public void setColId(int colId) {
        this.colId = colId;
    }

    public Inventario[] getInv() {
        return inv;
    }

    public void setInv(Inventario[] inv) {
        this.inv = inv;
    }


    
}