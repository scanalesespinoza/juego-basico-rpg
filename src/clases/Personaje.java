/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

//import jgame.JGPoint;
import jgame.JGRectangle;

/**
 *
 * @author gerald
 */
public class Personaje extends extensiones.StdDungeonPlayerV2 {
    // object cids: 1=player 2=monster 4=bullet 8=monsterbullet

    public static final int WALL_T = 1;
    public static final int SHWALL_T = 2; // shootable wall
    public static final int DOOR_T = 4;
    public static final int PLAYER_T = 16;
    public static final int MONSTER_T = 32;
    public static final int GEN_T = 64; // generator
    public static final int BONUS_T = 128;
    public static final int KEY_T = 256;
    public static final int HEALTH_T = 512;
    public static final int PLAYERBLOCK_T =
            WALL_T | SHWALL_T | DOOR_T | GEN_T;
    public static final int MONSTERBLOCK_T =
            WALL_T | SHWALL_T | DOOR_T | BONUS_T | MONSTER_T | GEN_T | BONUS_T | KEY_T | HEALTH_T;
    public static final int BULLETBLOCK_T =
            WALL_T | DOOR_T | BONUS_T | BONUS_T | KEY_T | HEALTH_T;
    //Variables de juego de Personaje
    private short idPersonaje;
    private String nombre;
    private short nivel;
    private short tipo;
    
    public JGRectangle rClick;
    public boolean estadoClick = false;
    double mouseX;
    double mouseY;
    //factor es para convertir el punto del mouse de 800*600 a 1280*960
    //proporcionalmente para que se desplace de forma correcta
    double factor = 1.6;

    public Personaje(double x, double y, double speed, short idPersonaje, String nombre, short nivel, short tipo) {
        super("player", x, y, 1, "human_", true, false,
                PLAYERBLOCK_T, PLAYER_T, 2.3);
        stopAnim();
        this.idPersonaje=idPersonaje;
        this.nombre=nombre;
        this.nivel=nivel;
        this.tipo=tipo;
    }
    /**
     * Este método es un puente al move de la clase StdDungeonPlayerV2
     */
    public void desplazar() {
        super.move();
    }

    public short getIdPersonaje() {
        return idPersonaje;
    }

    public void setIdPersonaje(short idPersonaje) {
        this.idPersonaje = idPersonaje;
    }

    public short getNivel() {
        return nivel;
    }

    public void setNivel(short nivel) {
        this.nivel = nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public short getTipo() {
        return tipo;
    }

    public void setTipo(short tipo) {
        this.tipo = tipo;
    }


    
}
