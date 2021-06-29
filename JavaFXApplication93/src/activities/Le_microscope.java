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

      private double distance;

      private double F1;
      private double APlace;
      private double ALength;
      private String data1;
      private String data2;

      public Le_microscope(double _F1, double _F2, double _APlace, double _ALength) {
            this(_F1, _F2, _APlace, _ALength, "", "");
      }

      public Le_microscope(double _F1, double _F2, double _APlace, double _ALength, String data1, String data2) {
            this(_F1, _F2, _APlace, _ALength, data1, data2, Math.abs(2.5 * _ALength));
      }

      public Le_microscope(double _F1, double _F2, double _APlace, double _ALength, String data1, String data2, double _yy) {
            this.F1 = _F1;
            super.F = _F2;
            this.APlace = _APlace;
            this.ALength = _ALength;
            setYy(_yy);
            this.data1 = data1;
            this.data2 = data2;
            distance = 500;
            java.lang.System.out.println(getYy());
//            distance = -2 * APlace;
      }

      @Override
      public Group draw() {
            Systeme_Simple system = new Systeme_Simple(Type.LENTILLE_CONVERGENTE, F1, APlace, ALength, 1, true, "A B", "", getData()[0], getYy());
            system.setDataShown(this.isDataShown());
            system.setData(data1);
            Group g = system.draw();

            double x = system.getX0();
            double y = system.getY0();

            system = new Systeme_Simple(Type.LENTILLE_CONVERGENTE, getF(), -distance + x, y, 2, false, "A1 B1", "A' B'", getData()[1], getYy() + 50);
            system.setDataShown(this.isDataShown());
            system.setData(data2);
            Group g2 = system.draw();
            g2.setTranslateX(distance);
            return new Group(g, g2);
      }

      public double getALength() {
            return ALength;
      }

      public void setALength(double _ALength) {
            this.ALength = _ALength;
      }

      public double getAPlace() {
            return APlace;
      }

      public void setAPlace(double _APlace) {
            this.APlace = _APlace;
      }

      public String[] getData() {
            return new String[] { data1, data2 };
      }

      public void setData(String _data1, String _data2) {
            this.data1 = _data1;
            this.data2 = _data2;
      }

      public double getDistance() {
            return distance;
      }

      public void setDistance(double _distance) {
            this.distance = _distance;
      }

      public void setF1(double _F1) {
            this.F1 = _F1;
      }

}
