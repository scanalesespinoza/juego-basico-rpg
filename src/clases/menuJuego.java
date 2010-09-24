/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.HashMap;
import jgame.JGColor;
import jgame.JGFont;
import jgame.JGObject;



/**
 *
 * @author GetWay
 */
public class menuJuego extends JGObject {

    private Jugador pj;

    public menuJuego(String string, boolean bln, double d, double d1, int i, String string1, Jugador pj) {
        super(string, bln, d, d1, i, string1);
        this.pj=pj;

    }





    public void menuActual() {
            eng.setColor(JGColor.white);
            eng.drawRect(eng.viewXOfs()+550,eng.viewYOfs(),100,eng.viewHeight(), true, false);
            eng.drawRect(eng.viewXOfs(),eng.viewYOfs()+400,eng.viewWidth(),100, true, false);

            eng.setColor(JGColor.black);
            eng.setFont(new JGFont("Arial",0,10));

            eng.drawString("Coordenada X: "+pj.x+" Coordenada Y: "+pj.y, eng.viewWidth()/2, 420, 0);
            eng.drawString("Nombre: "+pj.getNombre().toString(), eng.viewWidth()-50, 10, 0);
            eng.drawString("Fuerza: "+pj.getFuerza(), eng.viewWidth()-50, 30, 0);
            eng.drawString("Destreza: "+pj.getDestreza(), eng.viewWidth()-50, 50, 0);
            eng.drawString("Sabiduria: "+pj.getSabiduria(), eng.viewWidth()-50, 70, 0);
            eng.drawString("Vitalidad: "+pj.getVitalidad(), eng.viewWidth()-50, 90, 0);
    }

    public Jugador getPj() {
        return pj;
    }

    public void setPj(Jugador pj) {
        this.pj = pj;
    }


}
