/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design;

import activities.SimpleSystem;
import activities.SimpleSystem.Type;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**

 @author EMAM
 */
public class JavaFXApplication93 extends Application {

      public Scene initSimpleScene(double APlace, double ALength, double width, double height, double merrorHeight) {
            return new Object() {

//      Axis
                  int xx = 10000;
                  int yy = 500;
//      A
                  double APlace;
                  double ALength;
//      F
                  double F = -50;
//      Width
                  double width;
//      Height
                  double height;

//      When hold
                  double mouseX;
                  double mouseY;
//      The drawn
                  private SimpleSystem simpleSystemObject;
//      The drawn
                  private Group simpleSystemDraw = new Group();
//      The drawn
                  private Group grid = new Group();
//      Group root
                  private Pane root;
//      Slider
                  private Slider s;

                  public Scene initScene(double APlace, double ALength, double width, double height, double merrorHeight) {
                        this.APlace = APlace;
                        this.ALength = ALength;
                        this.F = F;
                        this.width = width;
                        this.height = height;
                        this.yy = (int)merrorHeight;

//            m.setTranslateX(-xx / 2);
//            m.setTranslateY(-yy / 2);
                        simpleSystemObject = new SimpleSystem(Type.MERROR, xx, yy, F, APlace, ALength);
                        simpleSystemDraw.getChildren().add(simpleSystemObject.draw());
//            simpleSystem = new SimpleSystem(Type.LENTILLE_CONVERGENTE, xx, yy, F, APlace, ALength).draw();

                        Group g1 = initSlider();
                        Group g2 = initZoom();
                        Group g3 = initGrid();
                        simpleSystemDraw.getChildren().add(grid);

                        root = new Pane(simpleSystemDraw, initMenu(), g1, g2, g3);
                        simpleSystemDraw.setTranslateX(width / 2);
                        simpleSystemDraw.setTranslateY(height / 2);

                        root.setOnMousePressed(e -> hold(e));
                        root.setOnMouseDragged(e -> drag(e));
                        root.setOnMouseReleased(e -> release());
                        return new Scene(root, width, height);
                  }

                  private Group initMenu() {
                        Group g = new Group();
                        ////////////////////

                        g.getChildren().addAll();
                        ////////////////////

//                        Stage s = new Stage();
//                        s.setScene(new Scene(new TextArea(Explain.getExplain(simpleSystemObject.getType())), 500, 300));
//                        s.show();
                        ////////////////////
                        return g;
                  }

                  private Group drawGrid() {
                        Group g = new Group();
                        for ( int i = -500; i < 500; i++ ) {
                              Line l = new Line(i * 10, 5000, i * 10, -5000);
                              l.setStrokeWidth(0.5);
                              l.setOpacity(0.5);
                              g.getChildren().add(l);
                        }
                        return g;
                  }

                  private Group initGrid() {
                        Group g = new Group();
                        grid = drawGrid();
                        grid.setVisible(false);

                        Button b = new Button("Grid ON");
                        b.setTranslateY(height - 30);
                        b.setId("off");
                        b.setOnAction((e) -> {
//                              if ( b.getId().equalsIgnoreCase("off") ) {
//                                    b.setId("on");
//                                    b.setText("Grid OFF");
//                                    grid.setVisible(true);
//                              } else {
//                                    b.setId("off");
//                                    b.setText("Grid ON");
//                                    grid.setVisible(false);
//                              }
                              Stage s = new Stage();
                              s.setScene(new Scene(new TextArea(Explain.getExplain(simpleSystemObject.getType())), 500, 300));
                              s.show();
                        });
                        g.getChildren().add(b);
                        return g;
                  }

                  private Group initSlider() {
                        Group g = new Group();

                        s = new Slider(-750, 750, 0);
                        s.setShowTickMarks(true);
                        s.setShowTickLabels(true);
                        s.setBlockIncrement(50);
                        s.setMinorTickCount(50);

                        g.setTranslateX(width / 3);
                        g.setTranslateY(height - 50);
                        g.getChildren().add(s);

                        s.setPrefSize(width / 3, 10);
                        s.setCursor(Cursor.HAND);
                        s.setOnMouseDragged((e) -> {
                              if ( s.getValue() > -10 && s.getValue() < 10 ) {
                                    s.setValue(0);
                              }
                              simpleSystemObject.setF(s.getValue());
                              simpleSystemDraw.getChildren().clear();
                              simpleSystemDraw.getChildren().addAll(simpleSystemObject.draw());
                        });
                        return g;
                  }

                  private Group initZoom() {
                        Group g = new Group();
                        Button zoomIn = new Button("+");
                        zoomIn.setPrefSize(35, 35);
                        Button zoomOut = new Button("-");
                        zoomOut.setPrefSize(35, 35);
                        Label zoomValue = new Label("100\n%");
                        zoomValue.setPrefSize(35, 40);

                        zoomOut.setOnMousePressed(e -> {
                              simpleSystemDraw.setScaleX(simpleSystemDraw.getScaleX() - 0.1);
                              simpleSystemDraw.setScaleY(simpleSystemDraw.getScaleY() - 0.1);
                              zoomValue.setText(Math.round(simpleSystemDraw.getScaleX() * 100) + "\n%");
                              s.setDisable(simpleSystemDraw.getScaleX() == 1 ? false : true);
                        });
                        zoomIn.setOnMousePressed(e -> {
                              simpleSystemDraw.setScaleX(simpleSystemDraw.getScaleX() + 0.1);
                              simpleSystemDraw.setScaleY(simpleSystemDraw.getScaleY() + 0.1);
                              zoomValue.setText(Math.round(simpleSystemDraw.getScaleX() * 100) + "\n%");
                              s.setDisable(simpleSystemDraw.getScaleX() == 1 ? false : true);
                        });
                        zoomValue.setOnMousePressed(e -> {
                              simpleSystemDraw.setScaleX(1);
                              simpleSystemDraw.setScaleY(1);
                              zoomValue.setText("100\n%");
                              simpleSystemDraw.setTranslateX(width / 2);
                              simpleSystemDraw.setTranslateY(height / 2);
                              s.setDisable(false);
                        });
                        g.getChildren().add(new VBox(zoomIn, zoomValue, zoomOut));
                        g.setTranslateX(width - 40);
                        g.setTranslateY(height - 115);
                        return g;
                  }

                  private void hold(MouseEvent e) {
                        root.setCursor(Cursor.CLOSED_HAND);
                        mouseX = simpleSystemDraw.getTranslateX() - e.getSceneX();
                        mouseY = simpleSystemDraw.getTranslateY() - e.getSceneY();
                  }

                  private void drag(MouseEvent e) {
                        double difX = e.getSceneX() + mouseX;
                        double difY = e.getSceneY() + mouseY;
                        if ( difX >= (xx - width) / 2 || difX <= (-xx - width) / 2 ) {
                              System.out.println(difX);
                              return;
                        }
                        simpleSystemDraw.setTranslateX(difX);
                        simpleSystemDraw.setTranslateY(difY);
                  }

                  private void release() {
                        root.setCursor(Cursor.DEFAULT);
                  }
            }.initScene(APlace, ALength, width, height, merrorHeight);
      }

/////
/////
/////
/////
/////
/////
/////
/////
/////
/////
/////
/////
/////
/////
/////
/////
/////
      public Scene initMainScene() {
            ObservableList<String> list = FXCollections.observableArrayList();
            list.add("Système Simple");
            list.add("Système Complexe‏");
            ComboBox<String> box = new ComboBox(list);
            box.setMaxWidth(200);
            box.setValue("Système Simple");

            TextField ALength = new TextField();
            ALength.setPromptText("Hauteur de l'objet‏");
            ALength.setMaxWidth(200);

            TextField APlace = new TextField();
            APlace.setPromptText("Position de l'objet");
            APlace.setMaxWidth(200);

            Button enter = new Button("entre");
            enter.setMaxWidth(200);
            enter.setOnAction((e) -> {
                  if ( box.getValue().equalsIgnoreCase("Système Simple") ) {
                        double AP = -Double.parseDouble(APlace.getText());
                        double AL = -Double.parseDouble(ALength.getText());
                        stage.setScene(initSimpleScene(AP, AL, 1500, 900, ((4 * Math.abs(AL) < 500) ? 500 : AL * 4)));
                  }

            });

            VBox all = new VBox(50, box, ALength, APlace, enter);
            all.setAlignment(Pos.CENTER);
            all.setPrefWidth(150);

            return new Scene(all, 450, 400);
      }

      @Override
      public void start(Stage primaryStage) {
            stage = primaryStage;
            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(initMainScene());
            primaryStage.setResizable(false);
            primaryStage.show();

      }

      static Stage stage;

      /**
       @param args the command line arguments
       */
      public static void main(String[] args) {
            launch(args);
      }
}
