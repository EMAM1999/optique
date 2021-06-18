/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design;
import activities.SimpleSystem.Type;

/**

 @author EMAM
 */
public class Explain {

      public static String getExplain(Type t) {
            switch ( t ) {
                  case LENTILLE_CONVERGENTE:
                        return "LENTILLE_CONVERGENTE";
                  case LENTILLE_DIVERGENTE:
                        return "LENTILLE_DIVERGENTE";
                  case MERROR:
                        return "MERROR";
            }
            return "";
      }
}
