package clases;

import java.lang.String;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgame.JGColor;
import jgame.JGFont;
import jgame.JGPoint;
import jgame.platform.*;
import java.util.HashMap;
import jgame.JGTimer;

/**
 *
 * @author gerald
 */
public class Manager extends JGEngine {

    /*
     *@param idJugador Esta variable idetificador corresponde a la clave
     * principal del personaje que ha seleccionado el usuario para jugar.
     * Permite en una misma sesion de juego recuperar, actualizar y desconectar al personaje Jugador.
     */
    private short idJugador = 1;//Valor en duro, debiera recibirse como parametro desde el sitio web
    private int interactuar = 0;//0=Jugador presente en el juego/1=Jugador ausente e interactuando con Npc/>0 Ejecutando dialogo y acciones de Npc
    private String nomNpcInteractuar;
    public int pausa = 0;// Modo de evitar que se ejecuten acciones por los 60 frames que ocurren por segundo
    //Personajes del juego
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
    public Npc pasto2;
    public Npc arbol1;
    public Npc arbol2;
    public Npc pileta;
    /*
     * Variables para probar funcionalidad "Realizar Mision"
     */
    public boolean hongo1 = false;
    public boolean hongo2 = false;
    public boolean hongo3 = false;
    public boolean hongo4 = false;
    public boolean hongo5 = false;
    public boolean finHongos = false;
    public int tiempoMensaje = 0;
    private HashMap hsDatosPersonaje = new HashMap();//Mapa de datos utilizado para cargar un Jugador cuando su objeto no ha sido instanciado o fue removido
    private dbDelegate conect = new dbDelegate();//Conexion a base de datos
    private menuJuego menu;
    //Valor para determinar si la aplicacion debe cerrarse
    private boolean salir = false;
 private int seg = 0;   
    public static void main(String[] args) {
        new Manager(new JGPoint(800, 540));

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

        try {
            defineMedia("/media/rpg-basico.tbl");
            setBGImage("bgimage");
            playAudio("music", "musicaciudad", true);
        } catch (Exception ex) {
            System.out.println("Error al cargar medios: " + ex);
        }
        new JGTimer(
                60, // number of frames to tick until alarm
                false // true means one-shot, false means run again
                // after triggering alarm
                ) {
            // the alarm method is called when the timer ticks to zero

            @Override
            public void alarm() {
                seg++;
            }
        };
        //setMsgFont(new JGFont("Helvetica",0,32));
        setFont(new JGFont("Arial", 0, 20));
        setPFSize(80, 60);//menuJuego de juego
        //Obtiene los datos del jugador guardados en la DB
        //cargaJugador(0,0); reemplazamos por el metodo nuevo
        this.pj = new Jugador();
        this.pj.cargarDatos(this.idJugador);

        try {


            menu = new menuJuego(null, true, xofs, xofs, xofs, null, pj);

            casa1 = new Npc(680, 660, "casa1", "casa3", 2, 0, (short) 100,
                    new String[]{"Hola amigo",
                        "Miguel: Como estas, espero mejor que yo",
                        "Me doy cuenta que no eres de estos lados",
                        "lamento no poder atenderte,",
                        "pero me siento muy debil,",
                        "tanto asi que no he podido salir por una cura.",
                        "Estoy seguro que con una buena cantidad de hongos",
                        "tendria para cuidarme por unos dias,",
                        "te agradeceria mucho si los traes por mi.",
                        "20 serán suficientes, en la cuidad suelen",
                        "crecer en la humedad de las rocas."
                    });//casa superior
            casa2 = new Npc(80, 400, "casa2", "casa2", 8, 0, (short) 101, new String[]{"Casa 2"});
            casa3 = new Npc(350, 448, "casa3", "casa4", 8, 0, (short) 102, new String[]{"Casa 3"});
            casa4 = new Npc(80, 634, "casa3", "casa3", 8, 0, (short) 103, new String[]{"Casa 3"});
            casa5 = new Npc(350, 682, "casa3", "casa5", 8, 0, (short) 104, new String[]{"Casa 3"});
            alcaldia = new Npc(700, 75, "alcaldia", "casa4", 8, 0, (short) 105, new String[]{"", "Alcalde: Hola forastero,", "actualemente la cuidad", "tiene muchos problemas,", "por favor ve y ayuda a la gente.", "Usualmente se mantienen", "en sus casas, temerosos", "de salir."});//casa superior
            //pasto1 = new Npc(192,128,"pasto","pasto",4,0,new String[]{"Hola amiguirijillo","soy pastillo1"});//pasto
            arbol1 = new Npc(352, 64, "arbol1", "arbol", 4, 0, (short) 106, new String[]{"Hola amiguirijillo", "soy Don Arbol, cuidame"});//
            arbol2 = new Npc(288, 32, "arbol2", "arbol", 4, 0, (short) 107, new String[]{"Hola amiguirijillo", "soy Don Arbol, cuidame"});//
            pileta = new Npc(128, 64, "arbol2", "pileta", 4, 0, (short) 108, new String[]{"Hola amiguirijillo", "soy la fuente magica"});//

        } catch (Exception ex) {
            System.out.println("Extrae datos del HashMapsssssssssssssssss: " + ex);
        }
        /*
         * Mapa completo de tiles que definen el campo de juego.
         * Simbologia presente en el archivo TBL.
         */

        setTiles(
                0, // tile x index
                0, // tile y index
                new String[]{"+++++++++++++++++++++++++++++++++++|||||++++++++++++++++++++|||||+++++++++++++++",//1
                    "+!...............!!!!!!............*****^...................*****..............+",
                    "+!......!!!!!!!..!!!!!!!!!!........*****.^..................*****..............+",
                    "+!......!!!!!!!..!!!!!!!!!!........*****...!!!!!!!!!!!!!!...*****..............+",
                    "+!.....^!!!!!!!..!!!!!!!!!!........*****...!............!...*****..............+",//5
                    "+!......!!!!!!!..!!!!!!!!!!........*****...!............!...*****..............+",
                    "+!.%....!!!!!!!..!!!!!!!!!!........*****...!............!...*****..............+",
                    "+!.%%...!!!!!!!..!!!!!!!!!!....^...*****...!............!...*****..............+",
                    "+!.%%%..!!!!!!!..!!!!!!!!!!........*****...!............!...*****..............+",
                    "+!.%%%%..........!!!!!!!!!!........*****...!............!...*****..............+",//10
                    "+!.%%%%..........!!!!!!!!!!........*****...!............!.^.*****..............+",
                    "+!.%%%%%.............^!!!!!........*****...!............!...*****..............+",
                    "+!.%%%%%%..%.%.%.%.%..!!!!!........*****...!!!..!!!!!!!!!...*****..............+",
                    "+!..%%%%%%%%%%%%%%%%%%%%%%%%%%%....*****...!!!..!!!!!!!!!...*****.............^+",
                    "+!....%%%%%%%%%%%%%%^%%%%%%%%%%%...*****....................*****.............^+",//15
                    "+!^................................*****..^^^^^^............*****.......^^^^^^^+",
                    "+!*****************************************************************************|",
                    "+!*****************************************************************************|",
                    "+!*****************************************************************************|",
                    "+!*****************************************************************************|",//20
                    "+!*****************************************************************************|",
                    "+!.................................**********..................................+",
                    "+!.................................*****.*****.................................+",
                    "+!..!!!!!!!!!!!!!!....^............*****..*****................................+",
                    "+!..!!!!!!!!!!!!!!.................*****...*****...............................+",//25
                    "+!..!!!!!!!!!!!!!!.................*****....*****..............................+",
                    "+!..!!!!!!!!!!!!!!...!!!!!!!!!!!!!!*****.....*****.....^.......................+",
                    "+!..!!!!!!!!!!!!!!...!!!!!!!!!!!!!!*****......*****............................+",
                    "+!..!!!!!!!!!!!!!!...!!!!!!!!!!!!!!*****.......*****...........................+",
                    "+!..!!!!!!!!!!!!!!...!!!!!!!!!!!!!!*****........*****..........................+",//30
                    "+!..!!!!!!!!!!!!!!...!!!!!!!!!!!!!!*****.^.......*****.........................+",
                    "+!..!!!!!!!!!!!!!!...!!!!!!!!!!!!!!*****..........*****........................+",
                    "+!..!!!!!!!!!!!!!!...!!!!!!!!!!!!!!*****...........*****.......................+",
                    "+!..!!!!!!!!!!!!!!...!!!!!!!!!!!!!!*****............*****......................+",
                    "+!...................!!!!!!!!!!!!!!*****.............*****.....................+",//35
                    "+!...................!!!!!!!!!!!!!!*****..............*****....................+",
                    "+!...................!!!!!!!!!!!!!!*****...............*****...................+",
                    "+!.................................*****................*****..................+",//38
                    "+!..!!!!!!!!!!!!!!.................*****.................*****.................+",
                    "+!..!!!!!!!!!!!!!!.................*****..!!!!!!!!!!!!!!..*****................+",
                    "+!..!!!!!!!!!!!!!!..........^......*****..!!!!!!!!!!!!!!...*****...............+",
                    "+!..!!!!!!!!!!!!!!...!!!!!!!!!!!!!!*****..!............!....*****..............+",
                    "+!..!!!!!!!!!!!!!!...!!!!!!!!!!!!!!*****..!............!.....*****.............+",
                    "+!..!!!!!!!!!!!!!!...!!!!!!!!!!!!!!*****..!............!......*****............+",
                    "+!..!!!!!!!!!!!!!!...!!!!!!!!!!!!!!*****..!............!.......*****...........+",
                    "+!..!!!!!!!!!!!!!!...!!!!!!!!!!!!!!*****..!............!........*****..........+",
                    "+!..!!!!!!!!!!!!!!...!!!!!!!!!!!!!!*****..!............!.........*****.........+",
                    "+!..!!!!!!!!!!!!!!...!!!!!!!!!!!!!!*****..!............!..........*****........+",
                    "+!..!!!!!!!!!!!!!!...!!!!!!!!!!!!!!*****..!............!...........*****.......+",
                    "+!............^^^....!!!!!!!!!!!!!!*****..!!!..!!!!!!!!!............*****......+",
                    "+!.............^.....!!!!!!!!!!!!!!*****.............................*****.....+",
                    "+!..^................!!!!!!!!!!!!!!*****..............................*****^^..+",
                    "+!.................................*****...............................*****^..+",
                    "+!.................................*****................^...............*****..+",
                    "+!.................................*****...............^^^...............*****.+",
                    "+!...^.............................*****..................................*****+",
                    "+!.......................^.........*****...................................****|",
                    "+!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*****!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!***|",
                    "+!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*****!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!**|",
                    "+++++++++++++++++++++++++++++++++++|||||++++++++++++++++++++++++++++++++++++++||",});
                    textoPrueba.add("hola");textoPrueba.add("como estas?");textoPrueba.add("necesito dinero");
                    textoPrueba.add("enserio");textoPrueba.add("piola");
    }
    /** View offset. */
    int xofs = 0, yofs = 0;
    public ArrayList<String> textoPrueba = new ArrayList<String>();

    @Override
    public void doFrame() {

       
        if (((pj.isInteractuarNpc()) && ((getMouseButton(1)) || (getKey(KeyDown)))) || (interactuar > casa1.obtieneDialogo().length)) {
            //cargaJugador(0, 10);
            removeObjects(getNomNpcInteractuar(), 51);
            pj.setInteractuarNpc(false);
            setInteractuar(0);
        } else if ((pj.isInteractuarNpc()) && (interactuar == 0)) {
            System.out.println(pj.npcInterac.getNomNpc() + "Npc");
            try {
                casa1 = new Npc(viewXOfs() + 380, viewYOfs() + 120, pj.npcInterac.getNomNpc() + "Npc", pj.npcInterac.getNomNpc() + "Npc", 51, 51, (short) (pj.npcInterac.getIdNpc()), pj.npcInterac.obtieneDialogo());
                // casa1.realizaTarea(pj);
            } catch (Exception ex) {
                System.out.println("Extrae datos del HashMap: fsdfsdfsd" + ex);
            }
            setNomNpcInteractuar(pj.npcInterac.getNomNpc() + "Npc");
            setInteractuar(1);
        } else if ((interactuar > 0) && (interactuar < casa1.obtieneDialogo().length) && (getKey(KeyEnter))) {

            if (pausa == 10) {
                interactuar += 3;
            } else if (pausa > 10) {
                pausa = 0;
            }
            pausa += 1;
            //System.out.println("+3 interactuar");

        }
        moveObjects(null, 0);
        // llamada al metodo de colision entre objetos con las siguientes id de colision
        checkCollision(
                4 + 1, // cids of objects that our objects should collide with
                1 // cids of the objects whose hit() should be called
                );
        checkCollision(
                2 + 1, // cids of objects that our objects should collide with
                1 // cids of the objects whose hit() should be called
                );
        checkCollision(
                8 + 1, // cids of objects that our objects should collide with
                1 // cids of the objects whose hit() should be called
                );

        // llamada al metodo de colision entre objeto y escenario con las siguientes id de colision
        checkBGCollision(
                1 + 11 + 13, // collide with the marble and border tiles
                1 // cids of our objects
                );

        int posX = (int) pj.x;
        int posY = (int) pj.y;

        xofs = posX;
        yofs = posY;
        setViewOffset(
                xofs, yofs,
                true);


        /*
         * Comprueba si debe cerrarse la aplicacion
         */
        if (isSalir()) {
            System.exit(0);
        }
    }
   

    @Override
    public void paintFrame() {

        //panel basico
        menu.menuActual(getTeclaMenu());
        
        drawString("SEGUNDOS: " + seg, viewXOfs() + 200 / 2, viewHeight() / 2, 1);
        drawRect(viewXOfs() + 700, viewYOfs(), 100, viewHeight(), true, false);

        
        if (getKey(KeyEsc)) {
            menu.ventanaSalida();
            
            pj.bloquear(60);
            if (getKey(KeyEnter)) {
                menu.setTeclaEscape(false);
                setSalir(true);
            }
        }

       if (interactuar > 0) {

           // ventanaDialogo(pj.npcInterac.obtieneDialogo());
            new Ventana(300, 300, textoPrueba);
        }


        /*
         * Detecta si has encontrado un hongo para la mision
         */

        if ((pj.x == 880.0) && (pj.y == 400.0) && (!hongo1)) {
            hongo1 = true;
            tiempoMensaje = 120;
            try {
                //agrego el hongo al inventario del jugador
                //Inventario[] tempInv = pj.getInv();
                pj.getInventario().agregarItem(pj.getIdPersonaje(), (short) 1);
                //tempInv[0].agregarItem(pj.getIdJugador(),(short)1000,(short)1);
                //pj.cargaInventario(pj.getIdJugador());
                menu = new menuJuego(null, true, xofs, xofs, xofs, null, pj);
            } catch (Exception ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ((pj.x == 928) && (pj.y == 144) && (!hongo2)) {
            hongo2 = true;
            tiempoMensaje = 120;
            try {
                //agrego el hongo al inventario del jugador
                //Inventario[] tempInv = pj.getInv();
                pj.getInventario().agregarItem((short) 1000, (short) 1);
                //tempInv[0].agregarItem(pj.getIdJugador(),(short)1000,(short)1);
                //pj.cargaInventario(pj.getIdJugador());
                menu = new menuJuego(null, true, xofs, xofs, xofs, null, pj);
            } catch (Exception ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ((pj.x == 80) && (pj.y == 48) && (!hongo3)) {
            hongo3 = true;
            tiempoMensaje = 120;
            try {
                //agrego el hongo al inventario del jugador
                //Inventario[] tempInv = pj.getInv();
                pj.getInventario().agregarItem(pj.getIdPersonaje(), (short) 1);
                //tempInv[0].agregarItem(pj.getIdJugador(),(short)1000,(short)1);
                //pj.cargaInventario(pj.getIdJugador());
                menu = new menuJuego(null, true, xofs, xofs, xofs, null, pj);
            } catch (Exception ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ((pj.x == 416) && (pj.y == 624) && (!hongo4)) {
            hongo4 = true;
            tiempoMensaje = 120;
            try {
                //agrego el hongo al inventario del jugador
                //Inventario[] tempInv = pj.getInv();
                pj.getInventario().agregarItem(pj.getIdPersonaje(), (short) 1);
                //tempInv[0].agregarItem(pj.getIdJugador(),(short)1000,(short)1);
                //pj.cargaInventario(pj.getIdJugador());
                menu = new menuJuego(null, true, xofs, xofs, xofs, null, pj);
            } catch (Exception ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ((pj.x == 64) && (pj.y == 800) && (!hongo5)) {
            hongo5 = true;
            tiempoMensaje = 120;
            try {
                //agrego el hongo al inventario del jugador
                //Inventario[] tempInv = pj.getInv();
                pj.getInventario().agregarItem(pj.getIdPersonaje(), (short) 1);
                //tempInv[0].agregarItem(pj.getIdJugador(),(short)1000,(short)1);
                //pj.cargaInventario(pj.getIdJugador());
                menu = new menuJuego(null, true, xofs, xofs, xofs, null, pj);
            } catch (Exception ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ((hongo1) && (hongo2) && (hongo3) && (hongo4) && (hongo5) && (!finHongos)) {
            finHongos = true;
        }
        if (tiempoMensaje > 0) {
            new Ventana("wena choro pillaste una callapampa!");
           // new Ventana(300, 300, textoPrueba);
            

        }
        tiempoMensaje--;

    }

    public void ventanaDialogo(String[] texto) {
        //System.out.println("Ventana dialogo: "+texto[0]);
        setColor(JGColor.blue);
        //drawRect(viewXOfs()+430,viewYOfs()+160,100,viewHeight()/2, true, false);
        drawRect(viewXOfs() + 250, viewYOfs() + 300, viewWidth() / 2 - 50, 81, true, false);
        setColor(JGColor.white);
        //drawRect(viewXOfs()+435,viewYOfs()+165,90,viewHeight()/2-10, true, false);
        drawRect(viewXOfs() + 255, viewYOfs() + 305, viewWidth() / 2 - 60, 71, true, false);

        setColor(JGColor.black);
        setFont(new JGFont("Arial", 0, 10));

        //System.out.println("Ventana dialogo pintada ");
        try {
            int cont = 0;
            while (cont < 3) {
                //System.out.println("cont: "+cont);
                //System.out.println("casa1.obtieneDialogo().length: "+casa1.obtieneDialogo().length);
                if ((interactuar + cont) < casa1.obtieneDialogo().length) {
                    final int i = 320;
                    drawString(texto[interactuar + cont], viewWidth() - 250, i + (cont + 1) * 10, 0);

                }
                cont += 1;
            }
        } catch (Exception ex) {
            System.out.println("Error al cargar ventana de dialogo: " + ex);
        }
        setColor(JGColor.red);
        drawString("Presiona [ENTER]", viewWidth() - 180, 365, 0);

        //System.out.println("Se cargaron 3 filas de dialogo");
        //System.out.println("Interactuar: "+interactuar);

    }

    public int getTeclaMenu() {
        int teclaPres = 0;
        if ((getKey(105)) || (getKey(73))) {
            teclaPres = 3;
        } else if ((getKey(101)) || (getKey(69))) {
            teclaPres = 4;
        } else if ((getKey(109)) || (getKey(77))) {
            teclaPres = 2;
        } else if ((getKey(104)) || (getKey(72))) {
            teclaPres = 1;
        } else if ((getKey(111)) || (getKey(79))) {
            teclaPres = 5;
        }

        return teclaPres;
    }

    public short getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(short idJugador) {
        this.idJugador = idJugador;
    }

    public int getInteractuar() {
        return interactuar;
    }

    public void setInteractuar(int interactuar) {
        this.interactuar = interactuar;
    }

    public boolean isSalir() {
        return salir;
    }

    public void setSalir(boolean salir) {
        this.salir = salir;
    }

    public String getNomNpcInteractuar() {
        return nomNpcInteractuar;
    }

    public void setNomNpcInteractuar(String nomNpcInteractuar) {
        this.nomNpcInteractuar = nomNpcInteractuar;
    }
}
