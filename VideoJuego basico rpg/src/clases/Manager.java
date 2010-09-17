package clases;


import jgame.JGColor;
import jgame.JGFont;
import jgame.JGPoint;
import jgame.platform.*;
import java.util.HashMap;


/**
 *
 * @author gerald
 */
public class Manager extends JGEngine{

    /*
     *@param idJugador Esta variable idetificador corresponde a la clave
     * principal del personaje que ha seleccionado el usuario para jugar.
     * Permite en una misma sesion de juego recuperar, actualizar y desconectar al personaje Jugador.
     */
    private short idJugador = 1;

    

    public Jugador pj;
    public Npc casa1;
    public Npc casa1Npc;
    public Npc alcaldia;
    public Npc alcalde;
    public Npc casa2;
    public Npc casa2Npc;
    public Npc casa3;
    public Npc casa3Npc;
    public Npc casa4;
    public Npc casa4Npc;
    public Npc casa5;
    public Npc casa5Npc;
    public Npc pasto1;

    public String[] dialogo = new String[]{"Hola extranjero","vuelve cuando quieras"};
    public int indDialogo = 0;

    private HashMap hsDatosPersonaje = new HashMap();
    private dbDelegate conect = new dbDelegate();

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

        //Obtiene los datos del jugador guardados en la DB

        try{
        System.out.println("antes obtiene datos peronsaje");
        System.out.println("get id jugador: "+this.getIdJugador());
        hsDatosPersonaje = conect.obtieneDatosPersonaje(this.getIdJugador());
        }
        catch(Exception ex){
            System.out.println("Consulta datos del jugador: "+ex);
        }
        try{
            cargaJugador(0,0);
        }
        catch(Exception ex){
            System.out.println("Extrae datos del HashMap: "+ex);
        }
        casa1 = new Npc(680,672,"casa1","casa1",50,0);
        alcaldia = new Npc(700,80,"alcaldia","casa1",50,0);
        pasto1 = new Npc(192,128,"pasto","pasto",30,0);


        /*
         * Mapa completo de tiles que definen el campo de juego.
         * Simbologia presente en el archivo TBL.
         */

        setTiles(
                0, // tile x index
                0, // tile y index
                new String[]{   "+++++++++++++++++++++++++++++++++++|||||++++++++++++++++++++|||||+++++++++++++++",//1
                                "+!.................................*****....................*****..............+",
                                "+!...%%%%%%%%%%%%%%%%%%%%%%%%%%%...*****....................*****..............+",
                                "+!....%%%%%%%%%%%%%%%%%%%%%%%%%%...*****..!!!!!!!!!!!!!!!!..*****..............+",
                                "+!.%.........................%%%...*****..!!............!!..*****..............+",//5
                                "+!.%%........................%%%...*****..!!............!!..*****..............+",
                                "+!.%%%.......................%%%...*****..!!............!!..*****..............+",
                                "+!.%%%.......................%%%...*****..!!............!!..*****..............+",
                                "+!.%%%.......................%%%...*****..!!............!!..*****..............+",
                                "+!.%%%.......................%%%...*****..!!............!!..*****..............+",//10
                                "+!.%%%........................%%...*****..!!............!!..*****..............+",
                                "+!.%%%.........................%...*****..!!............!!..*****..............+",
                                "+!.%%%%%%%%%%%%%%%%%%%%%%%%%%%.....*****..!!!!..!!!!!!!!!!..*****..............+",
                                "+!.%%%%%%%%%%%%%%%%%%%%%%%%%%%%....*****....................*****..............+",
                                "+!.%%%%%%%%%%%%%%%%%%%%%%%%%%%%%...*****....................*****..............+",//15
                                "+!.................................*****....................*****..............+",
                                "|******************************************************************************|",
                                "|******************************************************************************|",
                                "|******************************************************************************|",
                                "|******************************************************************************|",//20
                                "|******************************************************************************|",
                                "+!.................................**********..................................+",
                                "+!.................................*****.*****.................................+",
                                "+!.................................*****..*****................................+",
                                "+!.................................*****...*****...............................+",//25
                                "+!.................................*****....*****..............................+",
                                "+!.................................*****.....*****.............................+",
                                "+!.................................*****......*****............................+",
                                "+!.................................*****.......*****...........................+",
                                "+!.................................*****........*****..........................+",//30
                                "+!.................................*****.........*****.........................+",
                                "+!.................................*****..........*****........................+",
                                "+!.................................*****...........*****.......................+",
                                "+!.................................*****............*****......................+",
                                "+!.................................*****.............*****.....................+",//35
                                "+!.................................*****..............*****....................+",
                                "+!.................................*****...............*****...................+",
                                "+!.................................*****................*****..................+",//38
                                "+!.................................*****.................*****.................+",
                                "+!.................................*****..................*****................+",
                                "+!.................................*****.!!!!!!!!!!!!!!!!..*****...............+",
                                "+!.................................*****.!!............!!...*****..............+",
                                "+!.................................*****.!!............!!....*****.............+",
                                "+!.................................*****.!!............!!.....*****............+",
                                "+!.................................*****.!!............!!......*****...........+",
                                "+!.................................*****.!!............!!.......*****..........+",
                                "+!.................................*****.!!............!!........*****.........+",
                                "+!.................................*****.!!............!!.........*****........+",
                                "+!.................................*****.!!............!!..........*****.......+",
                                "+!.................................*****.!!!!..!!!!!!!!!!...........*****......+",
                                "+!.................................*****.............................*****.....+",
                                "+!.................................*****..............................*****....+",
                                "+!.................................*****...............................*****...+",
                                "+!.................................*****................................*****..+",
                                "+!.................................*****.................................*****.+",
                                "+!.................................*****..................................*****+",
                                "+!.................................*****...................................****|",
                                "+!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*****!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!***|",
                                "+!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*****!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!**|",
                                "+++++++++++++++++++++++++++++++++++|||||++++++++++++++++++++++++++++++++++++++||",


                                }
                );

    }


    /** View offset. */
    int xofs=0,yofs=0;


    @Override
    public void doFrame() {

        if((pj.isInteractuarNpc())&&((getMouseButton(1))||(getKey(KeyDown)))){
            cargaJugador(0,10);
            removeObjects("casa1Npc",51);
        }else if(pj.isInteractuarNpc()){
            casa1 = new Npc(viewXOfs()+200,viewYOfs()+100,"casa1Npc","casa1Npc",51,51);
        }
        moveObjects(null, 0);
        // llamada al metodo de colision entre objetos con las siguientes id de colision
        checkCollision(
                30+50, // cids of objects that our objects should collide with
                1+30 // cids of the objects whose hit() should be called
                );

        // llamada al metodo de colision entre objeto y escenario con las siguientes id de colision
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

        setColor(JGColor.white);
        drawRect(viewXOfs()+550,viewYOfs(),100,viewHeight(), true, false);
        drawRect(viewXOfs(),viewYOfs()+400,viewWidth(),100, true, false);
        
        setColor(JGColor.black);
        setFont(new JGFont("Arial",0,10));
  
        drawString("Coordenada X: "+pj.x+" Coordenada Y: "+pj.y, viewWidth()/2, 450, 0);
        drawString("Nombre: "+hsDatosPersonaje.get("nombre").toString(), viewWidth()-50, 10, 0);
        drawString("Fuerza: "+pj.getFuerza(), viewWidth()-50, 30, 0);
        drawString("Destreza: "+pj.getDestreza(), viewWidth()-50, 50, 0);
        drawString("Sabiduria: "+pj.getSabiduria(), viewWidth()-50, 70, 0);
        drawString("Vitalidad: "+pj.getVitalidad(), viewWidth()-50, 90, 0);

        drawRect(viewXOfs()+700,viewYOfs(),100,viewHeight(), true, false);

    }

    /*
     * Crea el objeto Jugador.
     * Carga los datos del personaje consultando a la base de datos.
     * Param varX: Solucion para no cargar el personaje sobre un npc.
     * Param varY: Solucion para no cargar el personaje sobre un npc.
     */
    public void cargaJugador(double varX, double varY){
        hsDatosPersonaje = conect.obtieneDatosPersonaje(this.getIdJugador());
        System.out.println("cuenta obtenida"+hsDatosPersonaje.get("posX"));
        System.out.println("cuenta obtenida"+hsDatosPersonaje.get("posY"));
        pj = new Jugador(Double.valueOf(String.valueOf(hsDatosPersonaje.get("posX")))+varX,Double.valueOf(String.valueOf(hsDatosPersonaje.get("posY")))+varY,1);
        pj.setIdPersonaje((Short.valueOf(hsDatosPersonaje.get("id").toString())));
        pj.setIdCuenta((Short.valueOf(hsDatosPersonaje.get("cuenta").toString())));
        System.out.println("cuenta obtenida");
        pj.setVitalidad((Short.valueOf(hsDatosPersonaje.get("vit").toString())));
        System.out.println("vit obtenida");
        pj.setDestreza((Short.valueOf(hsDatosPersonaje.get("des").toString())));
        System.out.println("des obtenida");
        pj.setSabiduria((Short.valueOf(hsDatosPersonaje.get("sab").toString())));
        System.out.println("sab obtenida");
        System.out.println("fue :"+hsDatosPersonaje.get("fue").toString());
        pj.setFuerza((Short.valueOf(hsDatosPersonaje.get("fue").toString())));
        System.out.println("fue obtenida");
        pj.setTotalPuntosHabilidad((Short.valueOf(hsDatosPersonaje.get("ptosHab").toString())));
        System.out.println("ptoshab obtenida");
        pj.setTotalPuntosEstadistica((Short.valueOf(hsDatosPersonaje.get("ptosEst").toString())));
        System.out.println("ptosest obtenida");
        pj.setLimiteSuperiorExperiencia(Integer.parseInt(hsDatosPersonaje.get("limExp").toString()));
        System.out.println("limexp obtenida");
        pj.setExperiencia(Integer.parseInt(hsDatosPersonaje.get("experiencia").toString()));
        System.out.println("exp obtenida");
        pj.setPesoSoportado(Integer.parseInt(hsDatosPersonaje.get("peso").toString()));
        System.out.println("peso obtenida");
    }

    public short getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(short idJugador) {
        this.idJugador = idJugador;
    }



}