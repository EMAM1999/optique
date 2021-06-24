/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activities;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
// 2. add choice to remove wasted rays
// 2. add choice to dash the object

/**

 @author EMAM
 */
public class Systeme_Simple extends System {

//  point d'intersection
      public double x0;
      public double y0;

      private Type type;
      private double xx;
      private double yy;
      private double APlace;
      private double ALength;

      private final int maxX = 10000;
      private final int nOfLentille;
      private boolean removeWastedRays;

      private final double xEdge = 100_000;
      private final double yEdge = 100_000;

      public Systeme_Simple(Type _type, double _xx, double _yy, double _F) {
            this(_type, _xx, _yy, _F, _F * 2, 100, 0, false);
      }

      public Systeme_Simple(Type _type, double _xx, double _yy, double _F, double _APlace, double _ALength, int _nOfLentille, boolean _removeWastedRays) {
            this.type = _type;
            this.xx = Math.abs(_xx);
            this.yy = Math.abs(_yy);
            super.F = _F;
            this.APlace = _APlace;
            this.ALength = _ALength;
            this.nOfLentille = _nOfLentille;
            this.removeWastedRays = _removeWastedRays;
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

      @Override
      public void setF(double _F) {
            super.F = _F;
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

      public double getXx() {
            return xx;
      }

      public void setXx(int _xx) {
            this.xx = _xx;
      }

      public double getYy() {
            return yy;
      }

      public void setYy(int _yy) {
            this.yy = _yy;
      }

      @Override
      public Group draw() {
            Group g = new Group();
            ////////////////////// Draw an edges  to fix the size of the drawn to control the zoom
            Group e = drawEdges();
            g.getChildren().addAll(e);
            //////////////////////

            switch ( type ) {
                  case LENTILLE_DIVERGENTE:
                  case LENTILLE_CONVERGENTE:
                        Group f = drawF();
                        Group rays = drawLightRay();
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

      private Group drawEdges() {
            Line x1 = new Line(-xEdge, -yEdge, xEdge, -yEdge);
            Line x2 = new Line(-xEdge, yEdge, xEdge, yEdge);
            Line y1 = new Line(-xEdge, yEdge, -xEdge, -yEdge);
            Line y2 = new Line(xEdge, yEdge, xEdge, -yEdge);
            return new Group(x1, x2, y1, y2);
      }

      private Group drawMerrorLightRay() {
            Group g = new Group();

            for ( int i = 0; i < 3; i++ ) {
                  double a = Math.random() * ALength + ALength;
                  double a0 = -(Math.random() * 2 * ALength - ALength / 3);
                  double a1 = Math.random() > 0.5 ? a : a0;
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
            Line fl1 = new Line(F, -q, F, q);
            Label f1 = new Label("F'" + (nOfLentille == 0 ? "" : nOfLentille));
            f1.setFont(Font.font(20));
            f1.setTranslateX(F);
            f1.setTranslateY(-q + 10);

            Line fl2 = new Line(-F, -q, -F, q);
            Label f2 = new Label("F" + (nOfLentille == 0 ? "" : nOfLentille));
            f2.setFont(Font.font(20));
            f2.setTranslateX(-F);
            f2.setTranslateY(-q + 10);

            return new Group(fl1, fl2, f1, f2);
      }

      private Group drawLightRay() {

            Group OF = drawOF();
            Group AO = drawAO(Math.abs(APlace) < Math.abs(F) && type == Type.LENTILLE_CONVERGENTE);
            Group AA = drawAA();
            Group AF = drawAF(Math.abs(APlace) < Math.abs(F) || type == Type.LENTILLE_DIVERGENTE);
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
            double x = maxX;
            double y = (ALength / F) * maxX + ALength;
            if ( Math.abs(y) > Math.abs(yEdge) ) {
                  y = Math.copySign(yEdge, y);
                  x = y / (ALength / APlace);
            }

            if ( removeWastedRays ) {
                  x = Double.isInfinite(x0) ? x : x0;
                  y = Double.isInfinite(y0) ? y : y0;
            }
            Line FL = new Line(0, ALength, x, y);
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
            double x = maxX;
            double y = maxX * (ALength / APlace);
            if ( Math.abs(y) > Math.abs(yEdge) ) {
                  y = Math.copySign(yEdge, y);
                  x = y / (ALength / APlace);
            }

            if ( removeWastedRays ) {
                  x = Double.isInfinite(x0) ? x : x0;
                  y = Double.isInfinite(y0) ? y : y0;
            }

            Line O = new Line(APlace, ALength, x, y);
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
