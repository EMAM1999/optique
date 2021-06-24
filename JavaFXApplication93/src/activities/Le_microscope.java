/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activities;
import activities.Systeme_Simple.Type;
import javafx.scene.Group;

/**

 @author EMAM
 */
public class Le_microscope extends System {

      private double xx;
      private double yy;
      private double F1;
      private double APlace;
      private double ALength;

      public Le_microscope(double _xx, double _yy, double _F1, double _F2, double _APlace, double _ALength) {
            this.xx = _xx;
            this.yy = _yy;
            this.F1 = _F1;
            super.F = _F2;
            this.APlace = _APlace;
            this.ALength = _ALength;
      }

      @Override
      public Group draw() {
            Systeme_Simple system = new Systeme_Simple(Type.LENTILLE_CONVERGENTE, xx, yy, F1, APlace, ALength, 1, true);
            Group g = system.draw();

            double x = system.x0;
            double y = system.y0;

            system = new Systeme_Simple(Type.LENTILLE_CONVERGENTE, xx, yy * 2, getF(), 2 * APlace + x, y, 2, false);
            Group g2 = system.draw();
            g2.setTranslateX(APlace * -2);
            return new Group(g, g2);
      }
}
