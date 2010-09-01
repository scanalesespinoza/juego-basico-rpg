/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
    public Personaje pj;
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

    public void initCanvas() {
        // we set the background colour to same colour as the splash background
        setCanvasSettings(40, 30, 16, 16, JGColor.black, new JGColor(255, 246, 199), null);

    }

    public void initGame() {
        setFrameRate(60, 2);
        defineMedia("/media/rpg.tbl");
        defineImage("bgimage","-",0,"/media/imagenes/tiles/adoquines2.gif","-");
        setBGImage("bgimage");
        setMsgFont(new JGFont("Helvetica",0,32));

        setPFSize(80,60);//ventana de juego
        setPFWrap(
                false, // horizontal wrap
                false,  // vertical wrap
                -10, -10 // shift the center of the view to make objects wrap at
                       // the right moment (sprite size / 2).
        );
        /* Busca Personaje segun idPersonaje*/

        pj = new Personaje(pfWidth()/2, pfHeight()/2, 1, KeyUp, KeyDown, KeyLeft, KeyRight);
        casa = new CasaUno(-400,-200);

        // create some tiles. "#" is our marble tile, "." is an empty space.

        setTiles(
                2, // tile x index
                2, // tile y index
                new String[]{"####", "#"} // A series of tiles. Each String represents a line of tiles.
                );
/*
        setTiles(13, 2, new String[]{"#####", "....#", "....#", "....#"});
        setTiles(13, 9, new String[]{"....#", "....#", "....#", "#####"});

        setTiles(2, 9, new String[]{"#....", "#....", "#....", "#####"});
        // define the off-playfield tiles
        setTileSettings(
                "#", // tile that is found out of the playfield bounds
                2, // tile cid found out of playfield bounds
                0 // which cids to preserve when setting a tile (not used here).
                );*/


    }

    public class CasaUno extends StdDungeonMonster {
            public CasaUno(double x,double y) {
                    super("casa",true,x,y,4,"explo",685);
                    if (isMidlet()) this.expiry = suspend_off_view;
            }
    }

    /** View offset. */
    int xofs=0,yofs=0;

    @Override
    public void doFrame() {

        moveObjects(null, 0);
        // check object-object collision
        checkCollision(
                1, // cids of objects that our objects should collide with
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
		// the Y offset changes proportional to the offset of the mouse
		// position from the center of the window.  If the mouse is in the
		// center, we don't scroll, if it is close to the upper or lower
		// border of the window, it scrolls quickly in that direction.
		yofs = posY;
		// Set the view offset.  Note that if our offset is out of the
		// playfield bounds, the position is clipped so that it is inside.
		// (this is only relevant for non-wrappable axes; a wrappable
		// axis is never out of bounds!)
		setViewOffset(
			xofs,yofs, // the position within the playfield
			true       // true means the given position is center of the view,
			           // false means it is topleft.
		);
    }

    @Override
    public void paintFrame() {
        drawString("click ("+getMouseX()+", "+getMouseY()+")", pfWidth()/2, 5, 0);
        drawString("pj: ("+pj.x+", "+pj.y+") ", pfWidth()/2, pfHeight()-50, 0);
        //drawString( pj.msg, pfWidth()/2, pfHeight()/2, 0);
        // drawRect(pj.rClick.x, pj.rClick.y, pj.rClick.width, pj.rClick.height, false, false);
        //drawRect(pj.getBBox().x, pj.getBBox().y, pj.getBBox().width, pj.getBBox().height, false, false);
        drawRect(pfWidth()-100, 0, 100, pfHeight(), false, false);
        drawRect(0, pfHeight()-100, pfWidth(), 100, false, false);

    }

    
}

