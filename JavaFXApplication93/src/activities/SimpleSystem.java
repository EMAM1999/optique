/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activities;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**

 @author EMAM
 */
public class SimpleSystem {

//  point d'intersection
      double x0;
      double y0;

      private Type type;
      private int xx;
      private int yy;
      private double F;
      private double APlace;
      private double ALength;

      private final int maxX = 10000;
      private final int maxY = 5000;

      public SimpleSystem(Type _type, int _xx, int _yy, double _F) {
            this(_type, _xx, _yy, _F, _F * 2, 100);
      }

      public SimpleSystem(Type _type, int _xx, int _yy, double _F, double _APlace, double _ALength) {
            this.type = _type;
            this.xx = _xx;
            this.yy = _yy;
            this.F = Math.copySign(_F, _APlace * (_type == Type.LENTILLE_CONVERGENTE ? 1 : -1));
            this.APlace = _APlace;
            this.ALength = _ALength;
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

      public double getF() {
            return F;
      }

      public void setF(double _F) {
            this.F = _F;
            if ( _F == 0 ) {
                  this.type = Type.MERROR;
            } else {
                  this.type = ((getF() < 0 && getAPlace() < 0) || (getF() > 0 && getAPlace() > 0))
                          ? Type.LENTILLE_CONVERGENTE : Type.LENTILLE_DIVERGENTE;
            }
      }

      public int getMaxX() {
            return maxX;
      }

      public Type getType() {
            return type;
      }

      public void setType(Type _type) {
            this.type = _type;
            setF(Math.copySign(getF(), APlace * (_type == Type.LENTILLE_CONVERGENTE ? 1 : -1)));
      }

      public int getXx() {
            return xx;
      }

      public void setXx(int _xx) {
            this.xx = _xx;
      }

      public int getYy() {
            return yy;
      }

      public void setYy(int _yy) {
            this.yy = _yy;
      }

      public Group draw() {
            Group g = new Group();

            switch ( type ) {
                  case LENTILLE_DIVERGENTE:
                  case LENTILLE_CONVERGENTE:
                        ////////////////////////
                        Group f = drawF();
                        Group rays = drawLightRay();
                        ////////////////////////
                        g.getChildren().addAll(f, rays);
                        break;
                  case MERROR:
                        x0 = -APlace;
                        y0 = ALength;
                        Group merrorRays = drawMerrorLightRay();
                        g.getChildren().addAll(merrorRays);
            }
            Group axis = drawAxis();
            Group A = drawA(APlace, ALength, 2.5, false, Color.BLACK);
            Group A0 = drawA(x0, y0, 2.5, Math.abs(APlace) < Math.abs(F) || type == Type.MERROR || type == Type.LENTILLE_DIVERGENTE, Color.SIENNA);
            g.getChildren().addAll(axis, A, A0);
            return g;
      }

      private Group drawMerrorLightRay() {
            Group g = new Group();

            for ( int i = 0; i < 3; i++ ) {
                  double a1 = (Math.random() * ALength + ALength) * (Math.random() > .5 ? -1 : 1);
                  Line l1 = new Line(APlace, ALength, 0, a1);
                  l1.setStroke(Color.YELLOWGREEN);

                  Line l2 = new Line(0, a1, APlace, a1 - ALength + a1);
                  l2.setStroke(Color.YELLOWGREEN);

                  Line l3 = new Line(0, a1, x0, y0);
                  l3.setStroke(Color.SIENNA);
                  l3.setOpacity(0.5);
                  g.getChildren().addAll(l1, l2, l3);
            }

            return g;
      }

      public static enum Type {
            MERROR,
            LENTILLE_CONVERGENTE,
            LENTILLE_DIVERGENTE
      }

      private Group drawA(double x, double y, double width, boolean isDashed, Color color) {
            Group g = new Group();
            if ( isDashed ) {
                  for ( int i = 0; i < Math.abs(y); i += 10 ) {
                        Line l = new Line(x, Math.copySign(i, y), x, Math.copySign(i + 2, y));
                        l.setStrokeWidth(width);
                        l.setStroke(color);
                        g.getChildren().add(l);
                  }
            } else {
                  Line l = new Line(x, 0, x, y);
                  l.setStroke(color);
                  l.setStrokeWidth(width);
                  g.getChildren().add(l);
            }
            int q = 5;
            Line a = new Line(x + q, Math.copySign(Math.abs(y) - q, y), x, y);
            Line b = new Line(x - q, Math.copySign(Math.abs(y) - q, y), x, y);
            a.setStrokeWidth(width);
            a.setStroke(color);
            b.setStrokeWidth(width);
            b.setStroke(color);
            return new Group(g, a, b);
      }

      private Group drawF() {
            int q = 3;
            Line f = new Line(F, -q, F, q);
            Line f1 = new Line(-F, -q, -F, q);
            return new Group(f, f1);
      }

      private Group drawLightRay() {

            Group AO = drawAO(Math.abs(APlace) < Math.abs(F) && type == Type.LENTILLE_CONVERGENTE);
            Group AA = drawAA();
            Group AF = drawAF(Math.abs(APlace) < Math.abs(F) || type == Type.LENTILLE_DIVERGENTE);
            Group OF = drawOF();
//            point.setStroke(Color.YELLOWGREEN);
            return new Group(AO, AA, AF, OF);
      }

      private Group drawAA() {
            Line X = new Line(APlace, ALength, 0, ALength);
            X.setStroke(Color.YELLOWGREEN);
            return new Group(X);
      }

      /*dashed*/
      private Group drawAF(boolean isDashed) {
            Group g = new Group();
//            y = (ALength/F)x +ALength
            Line FL = null;
            if ( (ALength / F) * maxX + ALength > maxY ) {
                  FL = new Line(0, ALength, (maxY - ALength) / (ALength / F), maxY);
            } else {
                  FL = new Line(0, ALength, maxX, (ALength / F) * maxX + ALength);
            }
            FL.setStroke(Color.YELLOWGREEN);
            if ( isDashed ) {
                  Line FLd = new Line(0, ALength, -FL.getEndX(), ALength - (ALength / F) * FL.getEndX());
                  FLd.setStroke(Color.YELLOWGREEN);
                  g.getChildren().add(FLd);
            }
            g.getChildren().add(FL);
            return g;
      }

      /*dashed*/
      private Group drawAO(boolean isDashed) {
            Group g = new Group();
//            y = (ALength/APlace) x
            Line O = null;
            if ( maxX * (ALength / (double)APlace) > maxY ) {
                  O = new Line(APlace, ALength, maxY / (ALength / APlace), maxY);
            } else {
                  O = new Line(APlace, ALength, maxX, maxX * (ALength / APlace));
            }
            if ( isDashed ) {
                  Line AOd = new Line(APlace, ALength, -O.getEndX(), O.getEndX() * (-ALength / APlace));
                  AOd.setStroke(Color.TURQUOISE);
                  g.getChildren().add(AOd);
            }
            O.setStroke(Color.TURQUOISE);
            g.getChildren().add(O);
            return g;
      }

      private Group drawOF() {
//           (-ALength/APlace) x = (-ALength/F)x -ALength
//          -x ALength/APlace+x ALength/F = -ALength
//            x = F*APlace/(F-APlace)
//            y = (-ALength/APlace) x 
            int q = 3;
            x0 = F * APlace / (F - APlace);
            y0 = (ALength / APlace) * x0;

            Line p1 = new Line(x0, y0 - q, x0, y0 + q);
            Line p2 = new Line(x0 + q, y0, x0 - q, y0);
            return new Group(p1, p2);
      }

      private Group drawAxis() {
            Group g = new Group();
            int q = 10;

            Line x = new Line(-xx / 2, 0, xx / 2, 0);
            Line y = new Line(0, -yy / 2, 0, yy / 2);
            y.setStrokeWidth(5);
            g.getChildren().addAll(x, y);
            switch ( type ) {
                  case LENTILLE_CONVERGENTE: {
                        Line a1 = new Line(q, yy / 2 - q, 0, yy / 2);
                        a1.setStrokeWidth(5);
                        Line a2 = new Line(-q, yy / 2 - q, 0, yy / 2);
                        a2.setStrokeWidth(5);
                        Line b1 = new Line(q, -yy / 2 + q, 0, -yy / 2);
                        b1.setStrokeWidth(5);
                        Line b2 = new Line(-q, -yy / 2 + q, 0, -yy / 2);
                        b2.setStrokeWidth(5);
                        g.getChildren().addAll(a1, a2, b1, b2);
                  }
                  break;
                  case LENTILLE_DIVERGENTE: {
                        Line a1 = new Line(q, yy / 2 + q, 0, yy / 2);
                        a1.setStrokeWidth(5);
                        Line a2 = new Line(-q, yy / 2 + q, 0, yy / 2);
                        a2.setStrokeWidth(5);
                        Line b1 = new Line(q, -yy / 2 - q, 0, -yy / 2);
                        b1.setStrokeWidth(5);
                        Line b2 = new Line(-q, -yy / 2 - q, 0, -yy / 2);
                        b2.setStrokeWidth(5);
                        g.getChildren().addAll(a1, a2, b1, b2);
                  }
                  break;

                  case MERROR:

            }
            return g;
      }
}
