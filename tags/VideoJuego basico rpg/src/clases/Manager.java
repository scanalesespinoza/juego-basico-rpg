package clases;

import extensiones.StdDungeonMonster;
import jgame.JGColor;
import jgame.JGFont;
import jgame.JGObject;
import jgame.JGPoint;
import jgame.platform.*;


/**
 *
 * @author gerald
 */
public class Manager extends JGEngine {
    public Jugador pj;
    public Npc casa1;
    public Npc casa1Npc;
    public Npc casa2;
    public Npc casa2Npc;
    public Npc casa3;
    public Npc casa3Npc;
    public Npc casa4;
    public Npc casa4Npc;
    public Npc casa5;
    public Npc casa5Npc;
    public StdDungeonMonster casa;
    public String msg;
    //public Arbol arb;
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
        setBGImage("bgimage");
        playAudio("music", "musicaciudad",true);
        }
        catch(Exception ex){
            System.out.println("Error al cargar medios: "+ex);
        }
        //setMsgFont(new JGFont("Helvetica",0,32));
        setFont(new JGFont("Arial", 0, 20));
        setPFSize(80,60);//ventana de juego
        defineImage("arbol", "A", 5, "/media/imagenes/tiles/Tree.png", "-");
        pj = new Jugador(400,400, 1);
        //casa1 = new Npc(680,672,"casa1","casa1",50,0);
        //arb = new Arbol(400, 300);
        // create some tiles. "#" is our marble tile, "." is an empty space.

        setTiles(
                0, // tile x index
                0, // tile y index
                new String[]{   "+++++++++++++++++++++++++++++++++++|||||++++++++++++++++++++|||||+++++++++++++++",//1
                                "+..................................*****....................*****..............+",
                                "+....%%%%%%%%%%%%%%%%%%%%%%%%%%%...*****....................*****..............+",
                                "+.....%%%%%%%%%%%%%%%%%%%%%%%%%%...*****....................*****..............+",
                                "+..%.........................%%%...*****....................*****..............+",//5
                                "+..%%........................%%%...*****....................*****..............+",
                                "+..%%%.......................%%%...*****....................*****..............+",
                                "+..%%%.......................%%%...*****....................*****..............+",
                                "+..%%%.......................%%%...*****....................*****..............+",
                                "+..%%%.......................%%%...*****....................*****..............+",//10
                                "+..%%%........................%%...*****....................*****..............+",
                                "+..%%%.........................%...*****....................*****..............+",
                                "+..%%%%%%%%%%%%%%%%%%%%%%%%%%%.....*****....................*****..............+",
                                "+..%%%%%%%%%%%%%%%%%%%%%%%%%%%%....*****....................*****..............+",
                                "+..%%%%%%%%%%%%%%%%%%%%%%%%%%%%%...*****....................*****..............+",//15
                                "+..................................*****....................*****..............+",
                                "|******************************************************************************|",
                                "|******************************************************************************|",
                                "|******************************************************************************|",
                                "|******************************************************************************|",//20
                                "|******************************************************************************|",
                                "+..................................**********..................................+",
                                "+..................................*****.*****.................................+",
                                "+..................................*****..*****................................+",
                                "+..................................*****...*****...............................+",//25
                                "+..................................*****....*****..............................+",
                                "+..................................*****.....*****.............................+",
                                "+..................................*****......*****............................+",
                                "+..................................*****.......*****...........................+",
                                "+..................................*****........*****..........................+",//30
                                "+..................................*****.........*****.........................+",
                                "+..................................*****..........*****........................+",
                                "+..................................*****...........*****.......................+",
                                "+..................................*****............*****......................+",
                                "+..................................*****.............*****.....................+",//35
                                "+..................................*****..............*****....................+",
                                "+..................................*****...............*****...................+",
                                "+..................................*****................*****..................+",//38
                                "+..................................*****.................*****.................+",
                                "+..................................*****..................*****................+",
                                "+..................................*****...................*****...............+",
                                "+..................................*****..!!!!!!!!!!!!!!....*****..............+",
                                "+..................................*****..!!!!!!!!!!!!!!.....*****.............+",
                                "+..................................*****..!!!!!!!!!!!!!!......*****............+",
                                "+..................................*****..!!!!!!!!!!!!!!.......*****...........+",
                                "+..................................*****..!!!!!!!!!!!!!!........*****..........+",
                                "+..................................*****..!!!!!!!!!!!!!!.........*****.........+",
                                "+..................................*****..!!!!!!!!!!!!!!..........*****........+",
                                "+..................................*****..!!!!!!!!!!!!!!...........*****.......+",
                                "+..................................*****............................*****......+",
                                "+..................................*****.............................*****.....+",
                                "+..................................*****..............................*****....+",
                                "+..................................*****...............................*****...+",
                                "+..................................*****................................*****..+",
                                "+..................................*****.................................*****.+",
                                "+..................................*****..................................*****+",
                                "+..................................*****...................................****|",
                                "+..................................*****....................................***|",
                                "+..................................*****.....................................**|",
                                "+++++++++++++++++++++++++++++++++++|||||++++++++++++++++++++++++++++++++++++++||",


                                }
                );

        // define el borde del mapa
        setTileSettings(
                "+", // tile that is found out of the playfield bounds
                11, // tile cid found out of playfield bounds
                0 // which cids to preserve when setting a tile (not used here).
                );
    }

/*
    
*/
    /** View offset. */
    int xofs=0,yofs=0;


    public void hit_bg(int tilecid) {
    
            System.out.println("TileCid"+tilecid+"<--");
            //drawString("GUARDIA: NO PUEDES SALIR!, ES PELIGROSO", pfWidth(), pfHeight(),  1, true);
    }

    @Override
    public void doFrame() {

        if((pj.isAusente())&&(getMouseY()>200)&&((getMouseButton(1))||(getKey(KeyDown)))){
            pj = new Jugador(690,785,1);
            removeObjects("casa1Npc",51);
        }else if(pj.isAusente()){
            casa1 = new Npc(750,600,"casa1Npc","casa1Npc",51,51);
        }
        moveObjects(null, 0);
        // check object-object collision
        checkCollision(
                50, // cids of objects that our objects should collide with
                1 // cids of the objects whose hit() should be called
                );
        // check object-tile collision
        checkBGCollision(
                1+11, // collide with the marble and border tiles
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
    @Override
    public void paintFrame() {
        setColor(JGColor.black);
	setFont(new JGFont("Arial",0,20));
        drawString("Coordenada X: "+pj.x+" Coordenada Y: "+pj.y, pfWidth()/2, pfHeight()/2,  1, true);
        drawRect(pj.getTileBBox().x, pj.getTileBBox().y, pj.getTileBBox().width, pj.getTileBBox().height, false, false);
    }
}

  /*  @Override
    public void paintFrame() {
        drawRect(viewXOfs()+500, viewYOfs(), viewWidth()-500, viewHeight(), false, false);
        drawRect(viewXOfs(), viewYOfs()+400, viewWidth(), viewHeight()-400, false, false);

        drawString("mensaje: "+arb.mensaje, viewWidth()/2, viewHeight()/2, 2);
        drawRect(arb.x, arb.y, arb.getBBox().width, arb.getBBox().height, false, false);
        drawRect(arb.click.x, arb.click.y, arb.click.width, arb.click.height, false, false);
    }

    public class CasaUno extends StdDungeonMonster {
            public CasaUno(double x,double y) {
                    super("casa",true,x,y,4,"explo",685);
                    
            }
    }
    public class Arbol extends StdDungeonMonster {
            public Arbol(double x,double y) {
                    super("arbol",true,x,y,5,"arbol",0);
                    setBBox(0, 0, 64, 64);
            }
            public String mensaje= "no paso na";
           public JGRectangle click;
        @Override
        public void hit(JGObject obj) {
            mensaje = "chocaste";
           
           // click = new JGRectangle((int)(getMouseX()+viewXOfs()), (int)(getMouseY()+viewYOfs()),10,10);
           
            }
        }


    }
    */

