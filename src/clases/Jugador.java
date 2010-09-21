package clases;

import java.sql.Date;
import java.util.HashMap;
import jgame.*;


/**
 *
 * @author gerald
 */
public class Jugador extends Personaje {

    public Jugador(double x, double y, double speed) {
        super(x, y,speed);

    }
    private short idPersonaje;
    private short idCuenta;
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

    


    /*
     * Carga datos del personaje
     */




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
        System.out.println("Nombre del objeto colisionador"+getName());
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

    public short getIdPersonaje() {
        return idPersonaje;
    }

    public void setIdPersonaje(short idPersonaje) {
        this.idPersonaje = idPersonaje;
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

    public short getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(short idCuenta) {
        this.idCuenta = idCuenta;
    }

    public boolean isInteractuarNpc() {
        return interactuarNpc;
    }

    public void setInteractuarNpc(boolean interactuarNpc) {
        this.interactuarNpc = interactuarNpc;
    }


}
