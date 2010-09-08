package clases;

import extensiones.StdDungeonMonster;
import jgame.JGColor;
import jgame.JGFont;
import jgame.JGPoint;
import jgame.platform.JGEngine;


/**
 *
 * @author gerald
 */
public class Manager extends JGEngine {
    public Jugador pj;
    public Npc casa1;
    public Npc casa1Npc;
    public StdDungeonMonster casa;
    public String msg;
    public static void main(String[] args) {
        new Manager(new JGPoint(800, 600));
    }

    /** Application constructor. */
    public Manager(JGPoint size) {
        initEngine(size.x, size.y);
    }

    /** Applet constructor. */
    public Manager() {
        initEngineApplet();
    }

    @Override
    public void initCanvas() {
        // we set the background colour to same colour as the splash background
        setCanvasSettings(40, 30, 16, 16, JGColor.black, new JGColor(255, 246, 199), null);

    }

    @Override
    public void initGame() {
        setFrameRate(60, 2);

        try{
        defineMedia("/media/rpg-basico.tbl");
        defineImage("bgimage","-",0,"/media/imagenes/tiles/Adoquin_16_base.png","-");
        setBGImage("bgimage");
        playAudio("music", "musicaciudad",true);
        }
        catch(Exception ex){
            System.out.println("Error al cargar medios: "+ex);
        }
        //setMsgFont(new JGFont("Helvetica",0,32));
        setFont(new JGFont("Arial", 0, 20));
        setPFSize(80,60);//ventana de juego
        
        pj = new Jugador(400,400, 1);
        casa1 = new Npc(100,100,"casa1","casa1",50,50);

        // create some tiles. "#" is our marble tile, "." is an empty space.
/*
        setTiles(
                100, // tile x index
                100, // tile y index
                new String[]{"####", "#"} // A series of tiles. Each String represents a line of tiles.
                );
        setTiles(13, 2, new String[]{"#####", "....#", "....#", "....#"});
        setTiles(13, 9, new String[]{"....#", "....#", "....#", "#####"});

        setTiles(2, 9, new String[]{"#....", "#....", "#....", "#####"});*/
        /*
        // define the off-playfield tiles
        setTileSettings(
                "#", // tile that is found out of the playfield bounds
                2, // tile cid found out of playfield bounds
                0 // which cids to preserve when setting a tile (not used here).
                );*/


    }
/*
    public class CasaUno extends StdDungeonMonster {
            public CasaUno(double x,double y) {
                    super("casa",true,x,y,4,"explo",685);
                    if (isMidlet()) this.expiry = suspend_off_view;
            }
    }
*/
    /** View offset. */
    int xofs=0,yofs=0;

    @Override
    public void doFrame() {

        if((pj.isAusente())&&(getMouseButton(1))&&(getMouseY()>200)){
            pj = new Jugador(180,210 , 1);
            removeObjects("casa1Npc", 51);
        }else if(pj.isAusente()){
            casa1 = new Npc(300,100,"casa1Npc","casa1Npc",51,51);
        }
        moveObjects(null, 0);
        // check object-object collision
        checkCollision(
                50, // cids of objects that our objects should collide with
                1 // cids of the objects whose hit() should be called
                );
        // check object-tile collision
        checkBGCollision(
                1 + 2, // collide with the marble and border tiles
                1 // cids of our objects
                );
        int posX = (int) pj.x;
        int posY = (int) pj.y;

        xofs =  posX;
        yofs = posY;
        setViewOffset(
                xofs,yofs,
                true   
         );
    }
}

