package clases;

import java.sql.*;
import java.util.HashMap;
/**
 *
 * @author Iarwain
 * @versio 1.0 16/09/10
 * @Descripcion: Delegador de transacciones de datos.
 */
public class dbDelegate {

   static final String bd = "db_trabajo_titulo";
   static final String login = "root";
   static final String password = "gwdesarrollo";
   static String url = "jdbc:mysql://getway.sytes.net:3306/"+bd;

   public Connection conn = null;



   /*
    * dbDelegate Constructor.
    */
   public dbDelegate(){
      try {

         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(url,login,password);
         System.out.println("Conexion ´dbDelegate´ correcta");
         if (conn != null) {
            System.out.println("Conexión a base de datos "+url+" ... Ok");
         }
      }
      catch(SQLException ex) {
         System.out.println("Hubo un problema al intentar conectarse con la base de datos "+url);
      }
      catch(ClassNotFoundException ex) {
         System.out.println(ex);
      }

   }
   public void cierraDbCon() throws Exception{
        conn.close();
   }

    public HashMap obtieneDatosPersonaje(short id){
        System.out.println("Inicio obtiene datos personaje");
        HashMap hs = new HashMap();
        String StrSql = "SELECT pjuno.idPersonaje id, pjuno.nombre nombre, pjuno.nivel nivel, pjuno.posicionX posX, pjuno.posicionY posY, pjdos.vitalidad vit, pjdos.destreza des, pjdos.sabiduria sab, pjdos.fuerza fue, pjdos.totalPuntosHabilidad ptosHab, pjdos.totalPuntosEstadistica ptosEst, pjdos.limiteSuperiorExperiencia limExp, pjdos.experiencia experiencia, pjdos.pesoSoportado peso, pjdos.fechaCreacion, pjdos.esBaneado ban, pjdos.idCuenta cuenta FROM personaje pjuno, jugador pjdos WHERE pjuno.idPersonaje="+id+" and pjdos.idPersonaje="+id;
        try{
            Statement st = conn.createStatement();
            System.out.println("Crea stamento");
            ResultSet res = st.executeQuery(StrSql);
            System.out.println("ejecuta query");
            while (res.next()) {
              System.out.println("consulta primera fila");
              System.out.println("Obtiene ID: "+res.getString("id"));

              hs.put("id",res.getString("id"));
              System.out.println("ID guardada en el hashmap");
              hs.put("nombre",res.getString("nombre"));
              hs.put("nivel",res.getString("nivel"));
              hs.put("posX",res.getString("posX"));
              hs.put("posY",res.getString("posY"));
              hs.put("vit",res.getString("vit"));
              hs.put("des",res.getString("des"));
              hs.put("sab",res.getString("sab"));
              hs.put("fue",res.getString("fue"));
              hs.put("ptosHab",res.getString("ptosHab"));
              hs.put("ptosEst",res.getString("ptosEst"));
              hs.put("limExp",res.getString("limExp"));
              hs.put("experiencia",res.getString("experiencia"));
              hs.put("peso",res.getString("peso"));
              hs.put("fechaCreacion",res.getString("fechaCreacion"));
              hs.put("ban",res.getString("ban"));
              hs.put("cuenta",res.getString("cuenta"));
            }
        }
        catch(SQLException ex) {
        System.out.println("Hubo un problema al intentar conectarse con la base de datos "+ex);
        }
        return hs;

    }

    public short[] obtienePosPersonaje(short id){
        short pos[]=null;
        String StrSql = "SELECT pjuno.idPersonaje id, pjuno.nombre nombre, pjuno.nivel nivel, pjuno.posicionX posX, pjuno.posicionY posY, pjdos.vitalidad vit, pjdos.destreza des, pjdos.sabiduria sab, pjdos.fuerza fue, pjdos.totalPuntosHabilidad ptosHab, pjdos.totalPuntosEstadistica ptosEst, pjdos.limiteSuperiorExperiencia limExp, pjdos.experiencia experiencia, pjdos.pesoSoportado peso, pjdos.fechaCreacion, pjdos.esBaneado ban, pjdos.idCuenta cuenta FROM personaje pjuno, jugador pjdos WHERE pjuno.idPersonaje="+id+" and pjdos.idPersonaje="+id;
        try{
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(StrSql);
            while (res.next()) {
              pos[0] = res.getShort("posicionX");
              pos[1] = res.getShort("posicionY");
            }
        }
        catch(SQLException ex) {
        System.out.println("Hubo un problema al intentar conectarse con la base de datos "+ex);
        }
        return pos;

    }

    public void actualizaPosicionJugador(int id, int posicionX, int posicionY){
        System.out.println("ID :"+id);
        System.out.println("X :"+posicionX);
        System.out.println("Y :"+posicionY);
        String StrSql = "UPDATE personaje SET posicionX=" + posicionX + ", posicionY=" + posicionY + " WHERE idPersonaje="+id;
        try{
            Statement st = conn.createStatement();
            int actualizadas = st.executeUpdate(StrSql);
            System.out.println("Se actualizaron "+actualizadas+" filas");

        }
        catch(SQLException ex) {
        System.out.println("Hubo un problema al intentar conectarse con la base de datos "+ex);
        }



    }

}
