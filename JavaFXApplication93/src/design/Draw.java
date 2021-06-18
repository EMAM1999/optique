/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import static design.JavaFXApplication93.ALength;
import static design.JavaFXApplication93.APlace;

/**

 @author EMAM
 */
public class Draw {

      public static Group drawF(int F) {
            Line f = new Line(F, -5, F, 5);
            Line f1 = new Line(-F, -5, -F, 5);
            return new Group(f, f1);
      }

      public static Group drawArrows(int APlace, int ALength, int F, int i) {
            int x = 10000;

            Group AO = drawAO(x, APlace, ALength, Math.abs(APlace) < Math.abs(F));
            Group AA = drawAA(APlace, ALength);
            Group AF = drawAF(x, ALength, F, Math.abs(APlace) < Math.abs(F));
            Group OF = drawOF(APlace, ALength, F);
//            point.setStroke(Color.YELLOWGREEN);
            return new Group(AO, AA, AF, OF);
      }

      private static Group drawAA(int APlace, int ALength) {
            Line X = new Line(APlace, -ALength, 0, -ALength);
            X.setStroke(Color.YELLOWGREEN);
            return new Group(X);
      }

      /*dashed*/
      private static Group drawAF(int x, int ALength, int F, boolean isDashed) {
            Group g = new Group();
//            y = (-ALength/F)x -ALength
            Line FL = new Line(0, -ALength, x, (-ALength / (double)F) * x - ALength);
            FL.setStroke(Color.YELLOWGREEN);
            if ( isDashed ) {
                  x = 250;
                  Line FLd = new Line(0, -ALength, -x, (ALength / (double)F) * x - ALength);
                  g.getChildren().add(FLd);
            }
            g.getChildren().add(FL);
            return g;
      }

      /*dashed*/
      private static Group drawAO(int x, int APlace, int ALength, boolean isDashed) {
            Group g = new Group();
//            y = (-ALength/APlace) x
            Line O = new Line(APlace, -ALength, x, x * (-ALength / (double)APlace));
            if ( isDashed ) {
                  x = 250;
                  Line AOd = new Line(APlace, -ALength, -x, x * (ALength / (double)APlace));
                  g.getChildren().add(AOd);
            }
            O.setStroke(Color.YELLOWGREEN);
            g.getChildren().add(O);
            return g;
      }

      private static Group drawOF(int APlace, int ALength, int F) {
//           (-ALength/APlace) x = (-ALength/F)x -ALength
//          -x ALength/APlace+x ALength/F = -ALength
//            x = F*APlace/(F-APlace)
//            y = (-ALength/APlace) x 
            double x = F * (double)APlace / (F - APlace);
            double y = (-ALength / (double)APlace) * x;
            Line p1 = new Line(x, y - 5, x, y + 5);
            Line p2 = new Line(x + 5, y, x - 5, y);
            return new Group(p1, p2);
      }

      private static Group drawAxis(int xL, int yL) {
            Line x = new Line(-xL / 2, 0, xL / 2, 0);
            Line y = new Line(0, -yL / 2, 0, yL / 2);
            y.setStrokeWidth(5);
            return new Group(x, y);
      }

      public static enum Type {
            MERROR,
            LENTILLE_CONVERGENTE,
            LENTILLE_DIVERGENTE
      }

      public static Group draw(Type t, int xL, int yL, int F) {

            int q = 10;
            Group g = new Group();
            switch ( t ) {
                  case LENTILLE_CONVERGENTE: {
                        Line a1 = new Line(q, yL / 2 - q, 0, yL / 2);
                        a1.setStrokeWidth(5);
                        Line a2 = new Line(-q, yL / 2 - q, 0, yL / 2);
                        a2.setStrokeWidth(5);
                        Line b1 = new Line(q, -yL / 2 + q, 0, -yL / 2);
                        b1.setStrokeWidth(5);
                        Line b2 = new Line(-q, -yL / 2 + q, 0, -yL / 2);
                        b2.setStrokeWidth(5);
                        Group f = Draw.drawF(F);
                        ////////////////////////
                        Group arrows = Draw.drawArrows(APlace, ALength, F, 0);
                        ////////////////////////
                        g.getChildren().addAll(a1, a2, b1, b2, f, arrows);
                        break;
                  }
                  case LENTILLE_DIVERGENTE: {
                        Line a1 = new Line(q, yL / 2 + q, 0, yL / 2);
                        a1.setStrokeWidth(5);
                        Line a2 = new Line(-q, yL / 2 + q, 0, yL / 2);
                        a2.setStrokeWidth(5);
                        Line b1 = new Line(q, -yL / 2 - q, 0, -yL / 2);
                        b1.setStrokeWidth(5);
                        Line b2 = new Line(-q, -yL / 2 - q, 0, -yL / 2);
                        b2.setStrokeWidth(5);
                        Group f = Draw.drawF(F);
                        ////////////////////////
                        Group arrows = Draw.drawArrows(APlace, ALength, -F, 0);
                        ////////////////////////
                        g.getChildren().addAll(a1, a2, b1, b2, f,arrows);
                        break;
                  }
                  case MERROR: {
                  }
            }
            g.getChildren().addAll(drawAxis(xL, yL));
            return g;
      }

      public static Group drawA(int x, int length) {
            Line l = new Line(x, 0, x, -length);
            Line a = new Line(x + 5, -length + 5, x, -length);
            Line b = new Line(x - 5, -length + 5, x, -length);
            return new Group(l, a, b);
      }
}
