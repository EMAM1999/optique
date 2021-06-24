/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design;
import activities.Le_microscope;
import activities.LunetteAstronomique;
import activities.Systeme_Simple;
import activities.Systeme_Simple.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static design.APP.*;

/**

 @author EMAM
 */
public class Scenes {

      public static Scene initMainScene() {
            ObservableList<String> list = FXCollections.observableArrayList();
            list.add("Système Simple");
            list.add("Le microscope");
            list.add("lunette astronomique");
            ComboBox<String> box = new ComboBox(list);
            box.setMaxWidth(200);
            box.setValue("Système Simple");

            TextField ALength = new TextField();
            ALength.setPromptText("Hauteur de l'objet");
            ALength.setMaxWidth(200);

            TextField APlace = new TextField();
            APlace.setPromptText("Position de l'objet");
            APlace.setMaxWidth(200);

            Button enter = new Button("Entre");
            enter.setMaxWidth(200);
            enter.setOnAction((e) -> {
                  if ( box.getValue().equalsIgnoreCase("Système Simple") ) {
                        double AP = -Double.parseDouble(APlace.getText());
                        double AL = -Double.parseDouble(ALength.getText());
                        APP.switchScenes(SCENE.SIMPLE, AP, AL, ((4 * Math.abs(AL) < 500) ? 500 : AL * 4));

                  } else if ( box.getValue().equalsIgnoreCase("Le microscope") ) {
                        double AP = -Double.parseDouble(APlace.getText());
                        double AL = -Double.parseDouble(ALength.getText());
                        APP.switchScenes(SCENE.MICROSCOPE, AP, AL, 2.5 * AL);

                  } else if ( box.getValue().equalsIgnoreCase("lunette astronomique") ) {
                        APP.switchScenes(SCENE.ASTRONOMIQUE, 0, 0, 500);
                  }

            });
            box.setOnAction((e) -> {
                  if ( box.getValue().equalsIgnoreCase("lunette astronomique") ) {
                        ALength.setDisable(true);
                        APlace.setDisable(true);
                  } else {
                        ALength.setDisable(false);
                        APlace.setDisable(false);
                  }

            });

            VBox all = new VBox(50, box, ALength, APlace, enter);
            all.setAlignment(Pos.CENTER);
            all.setPrefWidth(150);

            return new Scene(all, 450, 400);
      }

      public static Scene initSimpleScene(double APlace, double ALength, double merrorHeight) {
            return new Object() {

                  int yy = 500;
//      A
                  double APlace;
                  double ALength;
//      F
                  double F;

                  public Scene initScene(double APlace, double ALength, double merrorHeight) {
                        this.APlace = APlace;
                        this.ALength = ALength;
                        this.F = F;
                        this.yy = (int)merrorHeight;

//            m.setTranslateX(-xx / 2);
//            m.setTranslateY(-yy / 2);
                        Systeme_Simple simpleSystemObject = new Systeme_Simple(Type.MERROR, xx, yy, F, APlace, ALength, 0, false);
                        Group g = simpleSystemObject.draw();
                        thisDraw = g;
                        thisSystem = simpleSystemObject;
//            simpleSystem = new Systeme_Simple(Type.LENTILLE_CONVERGENTE, xx, yy, F, APlace, ALength).draw();

                        root = new Pane(g, APP.initEdges(-750, 750, 0));
                        root.setOnMousePressed(e -> hold(e));
                        root.setOnMouseDragged(e -> drag(e));
                        root.setOnMouseReleased(e -> release());

                        g.setTranslateX(width / 2);
                        g.setTranslateY(height / 2);

                        return new Scene(root, width, height);
                  }

            }.initScene(APlace, ALength, merrorHeight);
      }

      public static Scene initAstronomiqueScene(double yy, double F1, double F2) {
            return new Object() {

                  private Scene initScen() {
                        LunetteAstronomique astronomique = new LunetteAstronomique(APP.xx, yy, F1, F2);
                        Group g = astronomique.draw();
                        thisSystem = astronomique;
                        thisDraw = g;
                        root = new Pane(g, APP.initEdges(-500, -10, 100));
                        root.setOnMousePressed(e -> hold(e));
                        root.setOnMouseDragged(e -> drag(e));
                        root.setOnMouseReleased(e -> release());

                        g.setTranslateX(width / 2);
                        g.setTranslateY(height / 2);

                        return new Scene(root, width, height);
                  }
            }.initScen();
      }

      public static Scene initMicroscopeScene(double _yy, double _F1, double _F2, double _APlace, double _ALength) {
            return new Object() {
//      Axis
                  double yy;
//      A
                  double APlace;
                  double ALength;
//      F1
                  double F1;
//      F2
                  double F2;

//      When hold
                  double mouseX;
                  double mouseY;
                  //      The drawn
                  Le_microscope le_microscope;
//      The drawn
                  Group simpleSystemDraw = new Group();

                  Scene initScene(double _yy, double _F1, double _F2, double _APlace, double _ALength) {
                        yy = _yy;
                        F1 = _F1;
                        F2 = _F2;
                        APlace = _APlace;
                        ALength = _ALength;

                        le_microscope = new Le_microscope(xx, yy, F1, F2, APlace, ALength);
                        Group g = le_microscope.draw();
                        thisDraw = g;
                        thisSystem = le_microscope;

                        root = new Pane(g, initEdges(-500, -10, 100));
                        root.setOnMousePressed(e -> hold(e));
                        root.setOnMouseDragged(e -> drag(e));
                        root.setOnMouseReleased(e -> release());

//                        root.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));
                        g.setTranslateX(width / 2);
                        g.setTranslateY(height / 2);

                        return new Scene(root, width, height);
                  }

            }.initScene(_yy, _F1, _F2, _APlace, _ALength);
      }
//      When hold
      private static double mouseX;
      private static double mouseY;

      private static void hold(MouseEvent e) {
            root.setCursor(Cursor.CLOSED_HAND);
            mouseX = thisDraw.getTranslateX() - e.getSceneX();
            mouseY = thisDraw.getTranslateY() - e.getSceneY();
      }

      private static void drag(MouseEvent e) {
            double difX = e.getSceneX() + mouseX;
            double difY = e.getSceneY() + mouseY;
            if ( difX >= (xx - width) / 2 || difX <= (-xx - width) / 2 ) {
                  System.out.println(difX);
                  return;
            }
            thisDraw.setTranslateX(difX);
            thisDraw.setTranslateY(difY);
      }

      private static void release() {
            root.setCursor(Cursor.DEFAULT);
      }
}
