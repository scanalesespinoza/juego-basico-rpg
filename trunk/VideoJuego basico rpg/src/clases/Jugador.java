package clases;

import java.sql.Date;
import jgame.JGRectangle;

/**
 *
 * @author gerald
 */
public class Jugador extends Personaje {

    public Jugador(double x, double y, double speed) {
        super(x, y, speed);

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
    private short idCuenta;

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
}
