/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activities;
import activities.Systeme_Simple.Type;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

/**

 @author EMAM
 */
public class LunetteAstronomique extends System {

      double xx;
      double yy;
      double F1;
      double x0;
      double y0;

      double[] rays;

      public LunetteAstronomique(double _xx, double _yy, double _F1, double _F2) {
            this.xx = _xx;
            this.yy = _yy;
            this.F1 = _F1;
            this.x0 = this.y0 = -_F1;
            super.F = _F2;
      }

      @Override
      public Group draw() {
            Group g1 = drawParrarelLines();
            Systeme_Simple simple = new Systeme_Simple(Type.LENTILLE_CONVERGENTE, this.xx, this.yy, super.F, -x0, y0, 2, false);
            Group g2 = simple.draw();
            g2.setTranslateX(2 * Math.abs(this.F1));
            return new Group(g1, g2);
      }

      private Group drawParrarelLines() {
            Group g5 = drawB();
            Group g1 = drawAxis();
            Group g2 = drawRays(2);
            Group g3 = drawF();
            Group g4 = drawA(2.5, Color.BLACK);
            return new Group(g1, g3, g2, g4, g5);
      }

      private Group drawF() {
            int q = 3;
            Line fl1 = new Line(F1, -q, F1, q);
            Label f1 = new Label("F'1");
            f1.setFont(Font.font(20));
            f1.setTranslateX(F1);
            f1.setTranslateY(-q + 10);

            Line fl2 = new Line(-F1, -q, -F1, q);
            Label f2 = new Label("F1");
            f2.setFont(Font.font(20));
            f2.setTranslateX(-F1);
            f2.setTranslateY(-q + 10);

            return new Group(fl1, fl2, f1, f2);
      }

      private Group drawAxis() {
//            Group g = new Group();
            int q = 10;

            Line x = new Line(-xx / 2, 0, xx / 2, 0);
            Line y = new Line(0, -yy / 2, 0, yy / 2);
            y.setStrokeWidth(5);

            Line a1 = new Line(q, yy / 2 - q, 0, yy / 2);
            a1.setStrokeWidth(5);
            Line a2 = new Line(-q, yy / 2 - q, 0, yy / 2);
            a2.setStrokeWidth(5);
            Line b1 = new Line(q, -yy / 2 + q, 0, -yy / 2);
            b1.setStrokeWidth(5);
            Line b2 = new Line(-q, -yy / 2 + q, 0, -yy / 2);
            b2.setStrokeWidth(5);

            return new Group(x, y, a1, a2, b1, b2);
      }

      private Group drawA(double width, Color color) {
            Group g = new Group();
            Line l = new Line(x0, 0, x0, y0);
            l.setStroke(color);
            l.setStrokeWidth(width);
            g.getChildren().add(l);

            int q = 5;
            Line a = new Line(x0 + q, Math.copySign(Math.abs(y0) - q, y0), x0, y0);
            Line b = new Line(x0 - q, Math.copySign(Math.abs(y0) - q, y0), x0, y0);
            a.setStrokeWidth(width);
            a.setStroke(color);
            b.setStrokeWidth(width);
            b.setStroke(color);
            return new Group(g, a, b);
      }

      private Group drawB() {
//                y = x
//                x = F1
            double q = 3;
            Line p1 = new Line(x0, y0 - q, x0, y0 + q);
            Line p2 = new Line(x0 + q, y0, x0 - q, y0);
            return new Group(p1, p2);
      }

      private Group drawRays(int number) {
            rays = rays == null ? new double[number] : rays;
            Group g = new Group();
            g.getChildren().add(new Line(-10_000, -10_000, x0, y0));
            for ( int i = 0; i < number; i++ ) {
                  double q = rays[i];
                  if ( q == 0 ) {
                        q = 50 * (int)(Math.random() * 10 - 5);
                        rays[i] = q;
                  }
                  Line l = new Line(-10_000, -10_000 - q, 0, q);
                  Line l2 = new Line(0, q, x0, y0);
                  g.getChildren().addAll(l, l2);

            }
            return g;
      }

}
