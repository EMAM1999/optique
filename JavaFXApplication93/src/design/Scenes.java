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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
            String x = "Système Simple";
            String y = "Microscope";
            String z = "Lunette Astronomique";
            list.add(x);
            list.add(y);
            list.add(z);
            ComboBox<String> box = new ComboBox(list);
            box.setMaxWidth(200);
            box.setValue(list.get(0));

            TextField ALength = new TextField();
            ALength.setPromptText("Hauteur de l'objet");
            ALength.setMaxWidth(200);

            TextField APlace = new TextField();
            APlace.setPromptText("Position de l'objet");
            APlace.setMaxWidth(200);

            Button enter = new Button("Lancer simulation");
            enter.setMaxWidth(200);
            enter.setOnAction((e) -> {
                  try {

                        if ( box.getValue().equalsIgnoreCase(x) ) {
                              // To convert from milli to pixel multiply by 5
                              double AP = APP.milliToPX(Double.parseDouble(APlace.getText()));
                              double AL = APP.milliToPX(Double.parseDouble(ALength.getText()));
                              if ( AP <= 0 ) {
                                    throw new Exception("La valeur de la position doit être positive");
                              } else if ( AL == 0 ) {
                                    throw new Exception("La hauteur ne doit pas être nulle");
                              } else {
                                    APP.switchScenes(SCENE.SIMPLE, -AP, -AL);
                              }

                        } else if ( box.getValue().equalsIgnoreCase(y) ) {
                              // To convert from milli to pixel multiply by 5
                              double AP = APP.milliToPX(Double.parseDouble(APlace.getText()));
                              double AL = APP.milliToPX(Double.parseDouble(ALength.getText()));
                              if ( AP <= 0 ) {
                                    throw new Exception("La valeur de la position doit être positive");
                              } else if ( AL == 0 ) {
                                    throw new Exception("La hauteur ne doit pas être nulle");
                              } else {
                                    APP.switchScenes(SCENE.MICROSCOPE, -AP, -AL);
                              }

                        } else if ( box.getValue().equalsIgnoreCase(z) ) {
                              APP.switchScenes(SCENE.ASTRONOMIQUE, 0, 0);
                        }
                  } catch ( NumberFormatException ex ) {
                        Alert a = new Alert(AlertType.ERROR, "La zone est vide ou vous avez utilisé des caractéres non autorisés");
                        a.setHeaderText("");
                        a.showAndWait();
                  } catch ( Exception ex ) {
                        Alert a = new Alert(AlertType.ERROR, ex.getMessage());
                        a.setHeaderText("");
                        a.showAndWait();
                  }

            });
            box.setOnAction((e) -> {
                  if ( box.getValue().equalsIgnoreCase("Lunette Astronomique") ) {
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

      public static Scene initSimpleScene(double F, double APlace, double ALength) {
            return new Object() {
                  public Scene initScene() {
                        Systeme_Simple simpleSystemObject = new Systeme_Simple(Type.MIROIRE, F, APlace, ALength, 0, false, "A B", "A' B'");
                        simpleSystemObject.setData(simpleSystemObject.getType().toString());
                        Group g = simpleSystemObject.draw();
                        thisDraw = g;
                        thisSystem = simpleSystemObject;

                        root = new Pane(g, APP.initEdges(-200, 200, APP.PXToMilli(-(int)F)));
                        root.setOnMousePressed(e -> hold(e));
                        root.setOnMouseDragged(e -> drag(e));
                        root.setOnMouseReleased(e -> release());

                        g.setTranslateX(width / 2);
                        g.setTranslateY(height / 2);

                        return new Scene(root, width, height);
                  }

            }.initScene();
      }

      public static Scene initAstronomiqueScene(double F1, double F2) {
            return new Object() {

                  private Scene initScen() {
                        LunetteAstronomique astronomique = new LunetteAstronomique(F1, F2);
                        astronomique.setData("objectif", "oculaire");
                        Group g = astronomique.draw();
                        thisSystem = astronomique;
                        thisDraw = g;
                        root = new Pane(g, APP.initEdges(2, 200, APP.PXToMilli(-(int)F2)));
                        root.setOnMousePressed(e -> hold(e));
                        root.setOnMouseDragged(e -> drag(e));
                        root.setOnMouseReleased(e -> release());

                        g.setTranslateX(width / 2);
                        g.setTranslateY(height / 2);

                        return new Scene(root, width, height);
                  }
            }.initScen();
      }

      public static Scene initMicroscopeScene(double _F1, double _F2, double _APlace, double _ALength) {
            return new Object() {

                  private Scene initScene() {
                        Le_microscope le_microscope = new Le_microscope(_F1, _F2, _APlace, _ALength);
                        le_microscope.setData("Objectif", "Oculaire");
                        Group g = le_microscope.draw();
                        thisSystem = le_microscope;
                        thisDraw = g;

                        root = new Pane(g, APP.initEdges(2, 200, APP.PXToMilli(-(int)_F2)));
                        root.setOnMousePressed(e -> hold(e));
                        root.setOnMouseDragged(e -> drag(e));
                        root.setOnMouseReleased(e -> release());

                        g.setTranslateX(width / 2);
                        g.setTranslateY(height / 2);

                        return new Scene(root, width, height);
                  }

            }.initScene();
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
            if ( difX >= (xEdge - width) / 2 || difX <= (-xEdge - width) / 2 ) {
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
