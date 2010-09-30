package clases;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import jgame.*;


/**
 *
 * @author gerald
 */
public class Jugador extends Personaje {
    private Inventario[] inv;
    public Encargo encargos;
    private short idJugador;
    public Jugador(double x, double y, double speed, short idPj, String nombrePj, short nivelPj, short tipoPj) throws SQLException {
        super(x, y, speed, idPj, nombrePj, nivelPj, tipoPj);
        this.idJugador=idPj;
        //Instancia un ventario del jugador
        cargaInventario(idPj);
        //Instancia encargo del jugador
        encargos = new Encargo(idPj);
        //Instancia habilidades del jugador
    }

    private short vitalidad;
    private short destreza;
    private short sabiduria;
    private short fuerza;
    private short totalPuntosHabilidad;
    private short totalPuntosEstadistica;
    private int limiteSuperiorExperiencia;
    private int experiencia;
    private int pesoSoportado;
    private Date fechaCreacion;
    private boolean esBaneado;
    //Determina si un npc esta activo (se ha colisionado con el desencadenando un dialogo con el jugador)
    private boolean interactuarNpc = false;
    private dbDelegate conect = new dbDelegate();

    public Npc npcInterac;


    /*
     * Carga datos del personaje
     */
    public void cargaDatosPj(){
        System.out.println("cargaDatosPj"+getIdPersonaje());
        HashMap datosPj = new HashMap();
        //Carga datos del jugador desde la base de datos
        try{
            datosPj=conect.obtieneDatosPersonaje(getIdPersonaje());
        }
        catch(Exception ex){
            System.out.println("Error al conectar la DB #Jugador carga datos jugador: "+ex);
        }

        this.setIdPersonaje((Short.valueOf(datosPj.get("id").toString())));
        this.setVitalidad((Short.valueOf(datosPj.get("vit").toString())));
        this.setDestreza((Short.valueOf(datosPj.get("des").toString())));
        this.setSabiduria((Short.valueOf(datosPj.get("sab").toString())));
        this.setFuerza((Short.valueOf(datosPj.get("fue").toString())));
        this.setTotalPuntosHabilidad((Short.valueOf(datosPj.get("ptosHab").toString())));
        this.setTotalPuntosEstadistica((Short.valueOf(datosPj.get("ptosEst").toString())));
        this.setLimiteSuperiorExperiencia(Integer.parseInt(datosPj.get("limExp").toString()));
        this.setExperiencia(Integer.parseInt(datosPj.get("experiencia").toString()));
        this.setPesoSoportado(Integer.parseInt(datosPj.get("peso").toString()));

    }

  /*
   * Salva los datos del Jugador a la base de datos
   */
    public void salvaPj(Jugador This){
        
    }

    public void cargaInventario(short idPj) throws SQLException{

        ResultSet rTamanoInventario=conect.obtieneTamanoInvetario(idPj);
        ResultSet rInventario=conect.obtieneInvetario(idPj);
        System.out.println("CargaIventario");
        if(rTamanoInventario.next()){
            inv = new Inventario[rTamanoInventario.getInt("filas")];
            //System.out.println("rInventario.getInt('filas')"+rInventario.getInt("filas"));
            //System.out.println("Largo Inv: "+inv.length);
        }
        int i=0;
        while(rInventario.next()){
            inv[i] = new Inventario();
            System.out.println("Indice: "+i);
            System.out.println("rInventario.getShort('idPersonaje')"+rInventario.getShort("Personaje_id"));
            inv[i].setIdPersonaje(rInventario.getShort("Personaje_id"));
            System.out.println("inv["+i+"].isPersonaje: "+inv[i].getIdPersonaje());

            System.out.println("rInventario.getShort('idObjeto')"+rInventario.getShort("Objeto_id"));
            inv[i].setIdObjeto(rInventario.getShort("Objeto_id"));
            System.out.println("rInventario.getShort('cantidad')"+rInventario.getShort("cantidad"));
            inv[i].setCantidad(rInventario.getShort("cantidad"));
            System.out.println("rInventario.getShort('estaEquipado')"+rInventario.getShort("estaEquipado"));
            inv[i].setEstaEquipado(rInventario.getShort("estaEquipado"));
            System.out.println("rInventario.getString('nombre')"+rInventario.getString("nombre"));
            inv[i].setNombre(rInventario.getString("nombre"));
            i+=1;
        }

    }


    @Override
    public void move() {
        /*limpio todas las  teclas que han sido presionadas*/
        setbDownkey(false);
        setbLeftkey(false);
        setbRightkey(false);
        setbUpkey(false);
        
        int entorno = 16; //variable para formar cuadrados a partir de un punto.
        if (eng.getMouseButton(1)) {
            //Obtengo posicion del mouse
            mouseX = eng.getMouseX() + eng.viewXOfs();
            mouseY = eng.getMouseY() + eng.viewYOfs();

            if(interactuarNpc){
                return;
            }

            //creo objeto JGRectangle para ver si llegó al punto deseado
            rClick = new JGRectangle((int) mouseX, (int) mouseY, entorno, entorno);

            this.estadoClick = true;
            eng.clearMouseButton(1);
        }


        if (estadoClick) {

            if (this.x < mouseX - entorno) {
                setbRightkey(true);
            }
            if (this.x > mouseX + entorno) {
                setbLeftkey(true);
            }
            if (this.y < mouseY - entorno) {
                setbDownkey(true);
            }
            if (this.y > mouseY + entorno) {
                setbUpkey(true);
            }
            
            if (rClick.intersects(this.getTileBBox()) || eng.getMouseButton(3) || eng.getKey(eng.KeyLeft)
                    || eng.getKey(eng.KeyDown) || eng.getKey(eng.KeyUp) || eng.getKey(eng.KeyRight)) {
                setbDownkey(false);
                setbLeftkey(false);
                setbRightkey(false);
                setbUpkey(false);
                eng.clearMouseButton(3);
                estadoClick = false;
            }

        }
        if (!estadoClick) {
            setbDownkey(false);
            setbLeftkey(false);
            setbRightkey(false);
            setbUpkey(false);
            if (eng.getKey(eng.KeyUp)) {
                setbUpkey(true);
            }   //else {eng.clearKey(eng.KeyUp);}
            if (eng.getKey(eng.KeyDown)) {
                setbDownkey(true);
            } //else {eng.clearKey(eng.KeyDown);}
            if (eng.getKey(eng.KeyLeft)) {
                setbLeftkey(true);
            } //else {eng.clearKey(eng.KeyLeft);}
            if (eng.getKey(eng.KeyRight)) {
                setbRightkey(true);
            }//else {eng.clearKey(eng.KeyRight);}

        }
        super.desplazar();
    }


    @Override
    public void hit(JGObject obj){
        try{
            conect.actualizaPosicionJugador(this.getIdPersonaje(),(int)this.getLastX(),(int)this.getLastY()+5);
        }
        catch(Exception ex){
            System.out.println("Error al conectar la DB #Jugador.Hit: "+ex);
        }
        
        remove();
        this.setInteractuarNpc(true);
        System.out.println("Nombre del objeto colisionador"+getGraphic()+getName());

        this.npcInterac=(Npc)obj;
    }

    public short getDestreza() {
        return destreza;
    }

    public void setDestreza(short destreza) {
        this.destreza = destreza;
    }

    public boolean isEsBaneado() {
        return esBaneado;
    }

    public void setEsBaneado(boolean esBaneado) {
        this.esBaneado = esBaneado;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public short getFuerza() {
        return fuerza;
    }

    public void setFuerza(short fuerza) {
        this.fuerza = fuerza;
    }

    public int getLimiteSuperiorExperiencia() {
        return limiteSuperiorExperiencia;
    }

    public void setLimiteSuperiorExperiencia(int limiteSuperiorExperiencia) {
        this.limiteSuperiorExperiencia = limiteSuperiorExperiencia;
    }

    public int getPesoSoportado() {
        return pesoSoportado;
    }

    public void setPesoSoportado(int pesoSoportado) {
        this.pesoSoportado = pesoSoportado;
    }

    public short getSabiduria() {
        return sabiduria;
    }

    public void setSabiduria(short sabiduria) {
        this.sabiduria = sabiduria;
    }

    public short getTotalPuntosEstadistica() {
        return totalPuntosEstadistica;
    }

    public void setTotalPuntosEstadistica(short totalPuntosEstadistica) {
        this.totalPuntosEstadistica = totalPuntosEstadistica;
    }

    public short getTotalPuntosHabilidad() {
        return totalPuntosHabilidad;
    }

    public void setTotalPuntosHabilidad(short totalPuntosHabilidad) {
        this.totalPuntosHabilidad = totalPuntosHabilidad;
    }

    public short getVitalidad() {
        return vitalidad;
    }

    public void setVitalidad(short vitalidad) {
        this.vitalidad = vitalidad;
    }

    public boolean isInteractuarNpc() {
        return interactuarNpc;
    }

    public void setInteractuarNpc(boolean interactuarNpc) {
        this.interactuarNpc = interactuarNpc;
    }

    public Inventario[] getInv() {
        return inv;
    }

    public void setInv(Inventario[] inv) {
        this.inv = inv;
    }

    public short getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(short idJugador) {
        this.idJugador = idJugador;
    }


}
