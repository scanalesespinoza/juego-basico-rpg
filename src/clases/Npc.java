/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import extensiones.StdDungeonMonster;
import jgame.*;

/**
 *
 * @author Iarwain
 */

public class Npc extends StdDungeonMonster  {
    public String[] dialogo;

    public Npc(double x,double y,String name,String mediaName,int colId,int tamano,String[] dialogo) {
            super(name,true,x,y,colId,mediaName,tamano);
            this.dialogo=dialogo;
         
    }

    public String[] obtieneDialogo(){
        /*
        String[] dialogo = {"Hola amigo",
                            "como estas",
                            "Me doy cuenta que no eres de estos lados",
                            "te advierto que es muy peligroso salir de la cuidad",
                            "hay muchas criaturas peligrosas",
                            "si no tienes donde ir puedes quedarte",
                            "mientras ayudes a mantener la cuidad en pie",
                            "todos te aceptaran sin problema",
                            "pensadolo hay mucho trabajo que hacer",
                            "acomapañame a la plaza y danos una mano",
                            "podras conocer al resto de la gente."
                            };
         * 
         */

        return dialogo;
    }
    @Override
    public void hit(JGObject obj){
            //System.out.println("Hit NPC!"+obj.getName());
    }


    
}