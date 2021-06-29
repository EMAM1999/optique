/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activities;
import design.APP;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
// 2. add choice to remove wasted rays
// 2. add choice to dash the object

/**

 @author EMAM
 */
public class Systeme_Simple extends System {

      private double[] merrorRays;

//  point d'intersection
      private double x0;
      private double y0;

      private Type type;
      private double APlace;
      private double ALength;

      private final int nOfLentille;
      private boolean removeWastedRays;

      private boolean ABNamed;
      private String ABName;
      private boolean AB_Named;
      private String AB_Name;
      private String data;
//      public Systeme_Simple(Type _type, double _F) {
//            this(_type, _F, _F * 2, 100, 0, false);
//      }

      public Systeme_Simple(Type _type, double _F, double _APlace, double _ALength, int _nOfLentille, boolean _removeWastedRays) {
            this(_type, _F, _APlace, _ALength, _nOfLentille, _removeWastedRays, "");
      }

      public Systeme_Simple(Type _type, double _F, double _APlace, double _ALength, int _nOfLentille, boolean _removeWastedRays, String ABName) {
            this(_type, _F, _APlace, _ALength, _nOfLentille, _removeWastedRays, ABName, "");
      }

      public Systeme_Simple(Type _type, double _F, double _APlace, double _ALength, int _nOfLentille, boolean _removeWastedRays, String ABName, String AB_Name) {
            this(_type, _F, _APlace, _ALength, _nOfLentille, _removeWastedRays, ABName, AB_Name, "");
      }

      public Systeme_Simple(Type _type, double _F, double _APlace, double _ALength, int _nOfLentille, boolean _removeWastedRays, String ABName, String AB_Name, String data) {
            this(_type, _F, _APlace, _ALength, _nOfLentille, _removeWastedRays, ABName, AB_Name, data, Math.abs(2.5 * _ALength));
      }

      public Systeme_Simple(Type _type, double _F, double _APlace, double _ALength, int _nOfLentille, boolean _removeWastedRays, String ABName, String AB_Name, String data, double _yy) {
            this.type = _type;
            super.F = _F;
            this.APlace = _APlace;
            this.ALength = _ALength;
            this.nOfLentille = _nOfLentille;
            this.removeWastedRays = _removeWastedRays;
            setYy(Math.max(2.5 * _ALength, _yy));
            this.ABName = ABName;
            this.ABNamed = ABName.trim().isEmpty() ? false : true;
            this.AB_Name = AB_Name;
            this.AB_Named = AB_Name.trim().isEmpty() ? false : true;
            setData(data);
            super.setDataShown(true);
      }

      public String getABName() {
            return ABName;
      }

      public void setABName(String _ABName) {
            this.ABName = _ABName;
      }

      public String getAB_Name() {
            return AB_Name;
      }

      public void setAB_Name(String _AB_Name) {
            this.AB_Name = _AB_Name;
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

      public String getData() {
            return data;
      }

      public void setData(String _data) {
            this.data = _data;
      }

      @Override
      public void setF(double _F) {
            super.F = _F;
            if ( _F == 0 ) {
                  this.type = Type.MIROIRE;
            } else {
                  this.type = ((getF() < 0 && getAPlace() < 0) || (getF() > 0 && getAPlace() > 0))
                          ? Type.LENTILLE_CONVERGENTE : Type.LENTILLE_DIVERGENTE;
            }
      }

      public Type getType() {
            return type;
      }

      public void setType(Type _type) {
            this.type = _type;
            setF(Math.copySign(getF(), APlace * (_type == Type.LENTILLE_CONVERGENTE ? 1 : -1)));
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
                        Group rays = drawLightRay();
                        g.getChildren().addAll(rays);
                        Group f = drawF();
                        g.getChildren().addAll(f);
                        break;
                  case MIROIRE:
                        x0 = -APlace;
                        y0 = ALength;
                        Group merrorRays = drawMerrorLightRay(3);
                        g.getChildren().addAll(merrorRays);
            }
            if ( isDataShown() ) {
                  Group AB = drawABData();
                  Group data = drawLunetteData();
                  Group f = drawFData();
                  Group s = drawSData();
                  g.getChildren().addAll(AB, data, f, s);
            }
            Group axis = drawAxis();
            Group A = drawA(APlace, ALength, 2.5, false, Color.BLACK);
            Group A0 = drawA(x0, y0, 2.5, Math.abs(APlace) < Math.abs(F) || type == Type.MIROIRE || type == Type.LENTILLE_DIVERGENTE, Color.SIENNA);
            g.getChildren().addAll(axis, A, A0);
            return g;
      }

      public double getX0() {
            return x0;
      }

      public void setX0(double _x0) {
            this.x0 = _x0;
      }

      public double getY0() {
            return y0;
      }

      public void setY0(double _y0) {
            this.y0 = _y0;
      }

      public boolean isABNamed() {
            return ABNamed;
      }

      public void setABNamed(boolean _ABNamed) {
            this.ABNamed = _ABNamed;
      }

      public boolean isAB_Named() {
            return AB_Named;
      }

      public void setAB_Named(boolean _AB_Named) {
            this.AB_Named = _AB_Named;
      }

      private Group drawEdges() {
            Line x1 = new Line(-APP.xEdge, -APP.yEdge, APP.xEdge, -APP.yEdge);
            x1.setStrokeWidth(APP.edgeWidth);
            Line x2 = new Line(-APP.xEdge, APP.yEdge, APP.xEdge, APP.yEdge);
            x2.setStrokeWidth(APP.edgeWidth);
            Line y1 = new Line(-APP.xEdge, APP.yEdge, -APP.xEdge, -APP.yEdge);
            y1.setStrokeWidth(APP.edgeWidth);
            Line y2 = new Line(APP.xEdge, APP.yEdge, APP.xEdge, -APP.yEdge);
            y2.setStrokeWidth(APP.edgeWidth);
            return new Group(x1, x2, y1, y2);
      }

      private Group drawFData() {
            Group g = new Group();
            if ( F != 0 ) {
                  int fs = 15;

                  Text f1 = new Text("F" + (nOfLentille == 0 ? "" : nOfLentille));
                  f1.setFont(Font.font(fs));
                  f1.setTranslateX(F);
                  f1.setTranslateY(20);

                  Text f2 = new Text("F'" + (nOfLentille == 0 ? "" : nOfLentille));
                  f2.setFont(Font.font(fs));
                  f2.setTranslateX(-F);
                  f2.setTranslateY(20);

                  g.getChildren().addAll(f1, f2);
            }
            return g;
      }

      private Group drawSData() {
            Group g = new Group();
            int fs = 15;
            Text s = new Text("S" + (nOfLentille == 0 ? "" : nOfLentille));
            s.setFont(Font.font(fs));
            s.setTranslateX(5);
            s.setTranslateY(15);

            g.getChildren().addAll(s);
            return g;
      }

      private Group drawMerrorLightRay(int numberOfRays) {
            Group g = new Group();
            merrorRays = merrorRays == null ? new double[numberOfRays] : merrorRays;

            for ( int i = 0; i < numberOfRays; i++ ) {
                  double r = merrorRays[i];
                  if ( r == 0 ) {
                        double a = Math.random() * ALength * 0.25 + ALength;
                        double a0 = -(Math.random() * ALength - ALength / 3);
                        r = Math.random() > 0.5 ? a : a0;
                        merrorRays[i] = r;
                  }

                  Line l1 = new Line(APlace, ALength, 0, r);
                  l1.setStroke(Color.YELLOWGREEN);

                  Line l2 = new Line(0, r, APlace, r - ALength + r);
                  l2.setStroke(Color.YELLOWGREEN);
                  g.getChildren().addAll(l1, l2);

                  // y = (y0-r/x0)x + r
                  for ( int j = 5; j < Math.abs(y0); j += 10 ) {
                        Line l3 = new Line(j - 5, ((y0 - r) / x0) * (j - 5) + r, j, ((y0 - r) / x0) * j + r);
                        l3.setStroke(Color.SIENNA);
                        l3.setOpacity(0.5);
                        g.getChildren().addAll(l3);
                  }
            }

            return g;
      }

      public static enum Type {
            MIROIRE,
            LENTILLE_CONVERGENTE,
            LENTILLE_DIVERGENTE
      }

      private Group drawA(double x, double y, double width, boolean isDashed, Color color) {
            Group g = new Group();
            if ( Math.abs(x) > Math.abs(APP.xEdge) || Math.abs(y) > Math.abs(APP.yEdge) ) {
                  return g;
            }
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
            Line fl2 = new Line(-F, -q, -F, q);

            return new Group(fl1, fl2);
      }

      private Group drawABData() {
            Group g = new Group();
            int fs = 15;
            if ( ABNamed ) {
                  Text A = new Text(ABName.split(" ")[0]);
                  A.setFont(Font.font(fs));
                  A.setTranslateX(getAPlace());
                  A.setTranslateY((getALength() > 0 ? -10 : 15));
                  Text B = new Text(ABName.split(" ")[1]);
                  B.setFont(Font.font(fs));
                  B.setTranslateX(getAPlace());
                  B.setTranslateY(getALength() - (getALength() > 0 ? -20 : 15));
                  g.getChildren().addAll(A, B);
            }
            if ( AB_Named ) {
                  Text A_ = new Text(AB_Name.split(" ")[0]);
                  A_.setFont(Font.font(fs));
                  A_.setTranslateX(getX0());
                  A_.setTranslateY((getY0() > 0 ? -10 : 15));

                  Text B_ = new Text(AB_Name.split(" ")[1]);
                  B_.setFont(Font.font(fs));
                  B_.setTranslateX(getX0());
                  B_.setTranslateY(getY0() - (getY0() > 0 ? -20 : 15));
                  g.getChildren().addAll(A_, B_);
            }

            return g;
      }

      private Group drawLunetteData() {
            Text data = new Text(getData());
            data.setFont(Font.font(data.getFont().getFamily(), FontWeight.BOLD, 20));
            data.setTranslateX(-(data.getLayoutBounds().getWidth() / 2));
            data.setTranslateY(-(getYy() / 2 + 50));
            return new Group(data);
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
            X.setStroke(Color.RED);
            return new Group(X);
      }

      private Group drawAF(boolean isDashed) {
            Group g = new Group();
//            y = (ALength/F)x +ALength
            double x = APP.xEdge;
            double y = (ALength / F) * APP.xEdge + ALength;
            if ( Math.abs(y) > Math.abs(APP.yEdge) ) {
                  y = Math.copySign(APP.yEdge, y);
                  x = (y - ALength) / (ALength / F);
            }

            if ( removeWastedRays ) {
                  x = Double.isInfinite(x0) ? x : x0;
                  y = Double.isInfinite(y0) ? y : y0;
            }
            Line FL = new Line(0, ALength, x, y);
            FL.setStroke(Color.RED);
            g.getChildren().add(FL);

            if ( isDashed ) {
                  for ( int i = -5; i > x0 - 100; i -= 10 ) {
                        Line FLd = new Line(i + 5, (ALength / F) * (i + 5) + ALength, i, (ALength / F) * (i) + ALength);
                        FLd.setStroke(Color.RED);
                        g.getChildren().add(FLd);
                  }
            }

            return g;
      }

      private Group drawAO(boolean isDashed) {
            Group g = new Group();
//            y = (ALength/APlace) x
            double x = APP.xEdge;
            double y = APP.xEdge * (ALength / APlace);
            if ( Math.abs(y) > Math.abs(APP.yEdge) ) {
                  y = Math.copySign(APP.yEdge, y);
                  x = y / (ALength / APlace);
            }

            if ( removeWastedRays ) {
                  x = Double.isInfinite(x0) ? x : x0;
                  y = Double.isInfinite(y0) ? y : y0;
            }

            Line O = new Line(APlace, ALength, x, y);
            if ( isDashed ) {
                  for ( int i = -5; i > x0 - 100; i -= 10 ) {
                        Line AOd = new Line(i - 5, (i - 5) * (ALength / APlace), i, i * (ALength / APlace));
                        AOd.setStroke(Color.YELLOWGREEN);
                        g.getChildren().add(AOd);
                  }

            }
            O.setStroke(Color.YELLOWGREEN);
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

            Line x = new Line(-APP.xEdge, 0, APP.xEdge, 0);
            Line y = new Line(0, -getYy() / 2, 0, getYy() / 2);
            y.setStrokeWidth(5);
            g.getChildren().addAll(x, y);
            switch ( type ) {
                  case LENTILLE_CONVERGENTE: {
                        int sw = 5;
                        Line a1 = new Line(q, getYy() / 2 - q, 0, getYy() / 2);
                        a1.setStrokeWidth(sw);
                        Line a2 = new Line(-q, getYy() / 2 - q, 0, getYy() / 2);
                        a2.setStrokeWidth(sw);
                        Line b1 = new Line(q, -getYy() / 2 + q, 0, -getYy() / 2);
                        b1.setStrokeWidth(sw);
                        Line b2 = new Line(-q, -getYy() / 2 + q, 0, -getYy() / 2);
                        b2.setStrokeWidth(sw);
                        g.getChildren().addAll(a1, a2, b1, b2);
                  }
                  break;
                  case LENTILLE_DIVERGENTE: {
                        Line a1 = new Line(q, getYy() / 2 + q, 0, getYy() / 2);
                        a1.setStrokeWidth(5);
                        Line a2 = new Line(-q, getYy() / 2 + q, 0, getYy() / 2);
                        a2.setStrokeWidth(5);
                        Line b1 = new Line(q, -getYy() / 2 - q, 0, -getYy() / 2);
                        b1.setStrokeWidth(5);
                        Line b2 = new Line(-q, -getYy() / 2 - q, 0, -getYy() / 2);
                        b2.setStrokeWidth(5);
                        g.getChildren().addAll(a1, a2, b1, b2);
                  }
                  break;

                  case MIROIRE:

            }
            return g;
      }
}
