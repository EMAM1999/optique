/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design;
import activities.Le_microscope;
import activities.LunetteAstronomique;
import activities.Systeme_Simple;

/**

 @author EMAM
 */
public class Explain {

      public static String getExplain(activities.System s) {
            if ( s instanceof Systeme_Simple ) {
                  switch ( ((Systeme_Simple)s).getType() ) {
                        case LENTILLE_CONVERGENTE:
                              return "LENTILLE_CONVERGENTE";
                        case LENTILLE_DIVERGENTE:
                              return "LENTILLE_DIVERGENTE";
                        case MERROR:
                              return "MERROR";
                  }
            } else if ( s instanceof Le_microscope ) {
                  return "Le_microscope";
            } else if ( s instanceof LunetteAstronomique ) {
                  return "LunetteAstronomique";
            }
            return null;
      }
}
