/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import jgame.JGPoint;
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
    private short idPersonaje;
    private String nombre;
    private short nivel;
    private String tipo;
    public String msg = "";
    public JGRectangle rClick;
    public boolean estadoClick = false;
    double mouseX;
    double mouseY;
    //factor es para convertir el punto del mouse de 800*600 a 1280*960
    //proporcionalmente para que se desplace de forma correcta
    double factor = 1.6;

    public Personaje(double x, double y, double speed) {
        super("player", x, y, 1, "human_", true, false,
                PLAYERBLOCK_T, PLAYER_T, 2.3);
        stopAnim();
    }
    /**
     * Este método es un puente al move de la clase StdDungeonPlayerV2
     */
    public void desplazar() {
        super.move();
    }
}