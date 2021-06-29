/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design;

import activities.Systeme_Simple;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static design.APP.switchScenes;

/**

 @author EMAM
 */
public class APP extends Application {

      static String mainName = "Simulation de syst�mes optiques";
      static String projetName = "ISTA-OPT Logiciel de simulation en optique g�om�trique";

      public static double width = 1200;
      public static double height = 650;

      static Stage stage;
      static activities.System thisSystem;
      static Group thisDraw;
      static Pane root;

      public static final double xEdge = 100_000;
      public static final double yEdge = 100_000;
      public static final double edgeWidth = 50;

///////////////
      static Group initEdges(int start, int end, int value) {
            return new Object() {
                  private Group init() {
                        Group g1 = initSlider(start, end, value);
                        Pane g2 = initTitleBar(projetName, width, 20);
                        Group g3 = initZoom();
                        return new Group(g1, g2, g3);
                  }

                  private Group initSlider(int start, int end, int value) {
                        Group g = new Group();

                        Slider s = new Slider(start, end, value);
                        s.setShowTickMarks(true);
                        s.setShowTickLabels(true);
                        s.setMinorTickCount(5);
                        s.setMajorTickUnit((end - start) / 10);

                        g.setTranslateX(width / 3);
                        g.setTranslateY(height - 50);
                        g.getChildren().add(s);

                        s.setPrefSize(width / 3, 10);
                        s.setCursor(Cursor.HAND);
                        s.setOnMousePressed((e) -> {
                              refreshTheDraw(milliToPX(-s.getValue()));
                        });
                        s.setOnMouseDragged((e) -> {
                              refreshTheDraw(milliToPX(-s.getValue()));
                        });
                        return g;
                  }

                  private Label createLabelBtn(String name, String tooltipString, Color color, double w, double h, EventHandler<MouseEvent> e) {
                        Label l = new Label(name);
                        l.setTooltip(new Tooltip(tooltipString));
                        l.setPrefSize(w, h);
                        l.setFont(Font.font(l.getFont().getFamily(), FontWeight.BOLD, 20));
                        l.setTextFill(color);
                        l.setOnMousePressed(e);
                        l.setCursor(Cursor.HAND);

                        return l;
                  }

                  private Pane initTitleBar(String title, double width, int height) {
                        Stage s = new Stage();
                        Pane g = new Pane();
                        Label close = createLabelBtn("X", "Quitter", Color.BROWN, 40, height, (e) -> {
                              stage.close();
                        });
                        Label min = createLabelBtn("-", "R�duire", Color.SLATEGREY, 40, height, (e) -> {
                              stage.setIconified(true);
                        });
                        Label help = createLabelBtn("?", "Aide", Color.BLACK, 40, height, (e) -> {
                              if ( !s.isShowing() ) {
                                    TextArea text = new TextArea(Explain.getExplain(thisSystem));
                                    text.setEditable(false);
                                    s.setScene(new Scene(text, 500, 300));
                                    s.show();
                              }
                        });
                        Label back = createLabelBtn("<-", "Nouveau", Color.BLACK, 40, height, (e) -> {
                              APP.switchScenes(SCENE.MAIN, 0, 0);
                        });
                        Label info = createLabelBtn("i", "Afficher les noms des composants du syst�me ", Color.BLACK, 40, height, (e) -> {
                              APP.switchDataVisiblity();
                        });
                        Label t = createLabelBtn(title, "", Color.BLACK, width - 200, height, (e) -> {
                        });
                        HBox box = new HBox(t, info, back, help, min, close);
                        box.setAlignment(Pos.CENTER);
                        g.getChildren().add(box);
                        return g;
                  }

                  private Group initZoom() {
                        Group g = new Group();
                        Button zoomIn = new Button("+");
                        zoomIn.setPrefSize(35, 35);
                        zoomIn.setFont(Font.font(16));
                        Button zoomOut = new Button("-");
                        zoomOut.setPrefSize(35, 35);
                        zoomOut.setFont(Font.font(16));
                        Label zoomValue = new Label("100\n%");
                        zoomValue.setPrefSize(35, 45);
                        zoomValue.setFont(Font.font(16));
                        zoomValue.setAlignment(Pos.CENTER);

                        zoomOut.setOnMousePressed(e -> {
                              thisDraw.setScaleX(thisDraw.getScaleX() - 0.1);
                              thisDraw.setScaleY(thisDraw.getScaleY() - 0.1);
                              zoomValue.setText(Math.round(thisDraw.getScaleX() * 100) + "\n%");
//                              s.setDisable(thisDraw.getScaleX() == 1 ? false : true);
                        });
                        zoomIn.setOnMousePressed(e -> {
                              thisDraw.setScaleX(thisDraw.getScaleX() + 0.1);
                              thisDraw.setScaleY(thisDraw.getScaleY() + 0.1);
                              zoomValue.setText(Math.round(thisDraw.getScaleX() * 100) + "\n%");
//                              s.setDisable(thisDraw.getScaleX() == 1 ? false : true);
                        });
                        zoomValue.setOnMousePressed(e -> {
                              thisDraw.setScaleX(1);
                              thisDraw.setScaleY(1);
                              zoomValue.setText("100\n%");
                              thisDraw.setTranslateX(width / 2);
                              thisDraw.setTranslateY(height / 2);
                        });
                        g.getChildren().add(new VBox(zoomIn, zoomValue, zoomOut));
                        g.setTranslateX(width - 40);
                        g.setTranslateY(height - 125);
                        return g;
                  }

            }.init();
      }
///////////////

      static double milliToPX(double milli) {
            return milli * 5;
      }

      static int PXToMilli(int px) {
            return px / 5;
      }

      static void switchScenes(SCENE scene, double APlace, double ALength) {
            stage.close();
            switch ( scene ) {
                  case MAIN:
                        stage = new Stage();
                        stage.setTitle(mainName);
                        stage.setScene(Scenes.initMainScene());
                        break;
                  case SIMPLE:
                        stage = new Stage(StageStyle.TRANSPARENT);
                        stage.setScene(Scenes.initSimpleScene(0, APlace, ALength));
                        break;
                  case MICROSCOPE:
                        stage = new Stage(StageStyle.TRANSPARENT);
                        double f1 = -APlace <= 150 ? APlace / 2 : -150;
                        stage.setScene(Scenes.initMicroscopeScene(f1, -100, APlace, ALength));
                        break;
                  case ASTRONOMIQUE:
                        stage = new Stage(StageStyle.TRANSPARENT);
                        stage.setScene(Scenes.initAstronomiqueScene(-150, -100));

            }
//            stage.setScene(initMicroscopeScene(xx, 400, -150, -100, -300, -100));
            stage.setResizable(false);
//            stage.setAlwaysOnTop(true);
            stage.show();
      }

      public void start(Stage primaryStage) {
            stage = primaryStage;
            root = new Pane();
            switchScenes(SCENE.MAIN, 0, 0);
      }

      static enum SCENE {
            MAIN, SIMPLE, ASTRONOMIQUE, MICROSCOPE
      }

      public static void main(String[] args) {
            launch(args);
      }

      public static void refreshTheDraw(double value, boolean switchDataVisiblity) {
            refreshTheDraw(value);
            if ( switchDataVisiblity ) {
                  switchDataVisiblity();
            }
      }

      public static void refreshTheDraw(double value) {
            int x = 10;

            if ( value > -x && value < x ) {
                  value = 0;
            }
            thisSystem.setF(value);
            thisDraw.getChildren().clear();
            if ( thisSystem instanceof Systeme_Simple ) {
                  ((Systeme_Simple)thisSystem).setData(((Systeme_Simple)thisSystem).getType().toString());
            }
            thisDraw.getChildren().addAll(thisSystem.draw());
      }

      public static void switchDataVisiblity() {
            thisSystem.setDataShown(!thisSystem.isDataShown());
            thisDraw.getChildren().clear();
            thisDraw.getChildren().addAll(thisSystem.draw());
      }
}
