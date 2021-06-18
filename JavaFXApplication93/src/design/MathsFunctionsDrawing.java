/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
package MathsFunctionsDrawing;

//import EMAM.calculator.Maths;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**

 @author E.M.A.M
 */
public class MathsFunctionsDrawing extends Application {


          double startDegree = -3600;
          double endDegree = 3600;
          private final int width = 1000;
          private final int height = 600;
//    private PathTransition PT = new PathTransition();
          private Pane root = new Pane();
          private double x, y;
          double teranX;
          double teranY;
          private final Group axisesGroup = new Group();
          private final Group xAxisGroup = new Group();
          private final Group yAxisGroup = new Group();
          private Group functionGroup = new Group();



          private void hold(MouseEvent e) {
                    root.setCursor(Cursor.CLOSED_HAND);
                    x = axisesGroup.getTranslateX() - e.getSceneX();
                    y = axisesGroup.getTranslateY() - e.getSceneY();
          }



          private void drag(MouseEvent e) {
                    double difX = e.getSceneX() + x;
                    double difY = e.getSceneY() + y;
                    if ( difX >= 100000 - width / 2 || difX <= -100000 - width / 2 ) {
                              return;
                    }
                    axisesGroup.setTranslateX(difX);
                    axisesGroup.setTranslateY(difY);
          }



          private void release() {
                    root.setCursor(Cursor.OPEN_HAND);

          }



          public Scene initScene() {
                    configAxisesGroup();

                    axisesGroup.getChildren().addAll(xAxisGroup , yAxisGroup , functionGroup);
                    root = new Pane(axisesGroup /* , configSittings() */);
                    root.setCursor(Cursor.OPEN_HAND);

                    root.setOnMousePressed(e -> hold(e));
                    root.setOnMouseDragged(e -> drag(e));
                    root.setOnMouseReleased(e -> release());
                    return new Scene(root , width , height);
          }


//
//    private Group configLinesGroup() {
//        Line yAxis = new Line(width / 12 , 0 , width / 12 , height);
//        Line xAxis = new Line(0 , height / 2 , width , height / 2);
//        return new Group(
//                new Line(width / 13 , 0 , width / 13 , height) ,
//                new Line(width / 6 , 0 , width / 6 , height) ,
//                new Line(width / 4 , 0 , width / 4 , height) ,
//                new Line(width / 3 , 0 , width / 3 , height) ,
//                new Line(width * 5 / 13 , 0 , width * 5 / 13 , height) ,
//                new Line(width / 2 , 0 , width / 2 , height) ,
//                xAxis);
//    }
//

          private void configAxisesGroup() {
                    configXAxisGroup();
                    configYAxisGroup();
                    functionGroup.getChildren().add(createPath("x=y"));
          }



          private void configXAxisGroup() {
                    Line xAxis = new Line(-100000 , height / 2 , width + 100000 , height / 2);
                    Text x = new Text("X Axis");
                    x.setTranslateX(width - 25);
                    x.setTranslateY((height / 2) - 10);
                    xAxisGroup.getChildren().add(xAxis);

                    for ( int i = -100000 ; i <= 100000 ; i += 25 ) {
                              xAxisGroup.getChildren().add(new Line(i , (height / 2) - 2 , i , (height / 2) + 2));
                              double temp = (i - width / 2.) / 100.;
                              if ( (temp / .25) % 2 == 0 && temp != 0 ) {
                                        Text t = new Text("" + temp);
                                        t.setFont(Font.font(10));
                                        t.setTranslateX(i - 8);
                                        t.setTranslateY((height / 2) + 15);
                                        xAxisGroup.getChildren().add(t);
                              }
                    }
          }



          private void configYAxisGroup() {
                    Line yAxis = new Line(width / 2 , -100000 , width / 2 , height + 100000);
                    Text y = new Text("Y Axis");
                    y.setTranslateX((width / 2) + 10);
                    y.setTranslateY(15);
                    yAxisGroup.getChildren().add(yAxis);

                    for ( int i = -100000 ; i <= 100000 ; i += 25 ) {
                              yAxisGroup.getChildren().add(new Line((width / 2) - 2 , i , (width / 2) + 2 , i));
                              double temp;
                              if ( ((temp = ((-i + height / 2) / 100.)) / .25) % 2 == 0 && temp != 0. ) {
                                        Text t = new Text("" + temp);
                                        t.setFont(Font.font(10));
                                        t.setTranslateX((width / 2) + 5);
                                        t.setTranslateY(i);
                                        yAxisGroup.getChildren().add(t);
                              }
                    }
          }



          private Group configSittings() {
                    TextField input = new TextField();
                    input.setPromptText("Enter The equation ");
                    input.setTranslateX(150);

                    Button reset = new Button("reset");
                    reset.setCursor(Cursor.HAND);
//        reset.setTranslateX(25);
//        reset.setTranslateY(height - 35);
                    reset.setPrefSize(100 , 50);

                    Slider xScaleSlider = new Slider(.25 , 4 , 6);
                    xScaleSlider.setMajorTickUnit(.5);
                    xScaleSlider.setShowTickLabels(true);
                    xScaleSlider.setSnapToTicks(true);
                    xScaleSlider.setPrefSize(350 , 50);
                    xScaleSlider.setOrientation(Orientation.HORIZONTAL);
                    xScaleSlider.setTranslateX(25);
                    xScaleSlider.setTranslateY(height - 35);
                    xScaleSlider.setValue(1);
                    xScaleSlider.setCursor(Cursor.HAND);

                    Slider yScaleSlider = new Slider(.25 , 4 , 6);
                    yScaleSlider.setMajorTickUnit(.5);
                    yScaleSlider.setShowTickLabels(true);
                    yScaleSlider.setSnapToTicks(true);
                    yScaleSlider.setPrefSize(50 , 250);
                    yScaleSlider.setOrientation(Orientation.VERTICAL);
                    yScaleSlider.setTranslateY((height / 2) + 20);
                    yScaleSlider.setValue(1);
                    yScaleSlider.setCursor(Cursor.HAND);

                    reset.setOnAction(e -> {
                              xScaleSlider.setValue(1);
                              yScaleSlider.setValue(1);
                              reset();
                    });
                    input.setOnKeyPressed(e -> {
                              if ( e.getCode() == KeyCode.ENTER ) {
                                        functionGroup.getChildren().clear();
                                        functionGroup.getChildren().add(createPath(input.getText()));

                              }
                    });
                    yScaleSlider.setOnMouseReleased(e -> {
                              yAxisGroup.setScaleY(yScaleSlider.getValue());
                              functionGroup.setScaleY(yScaleSlider.getValue());
                    });
                    xScaleSlider.setOnMouseReleased(e -> {
                              xAxisGroup.setScaleX(xScaleSlider.getValue());
                              functionGroup.setScaleX(xScaleSlider.getValue());
                    });
                    return new Group(yScaleSlider , xScaleSlider , reset , input);
          }



          private void reset() {
                    yAxisGroup.setScaleY(1);
                    xAxisGroup.setScaleX(1);
                    functionGroup.setScaleY(1);
                    axisesGroup.setTranslateX(0);
                    axisesGroup.setTranslateY(0);
          }



          private HBox configTextGroup() {
                    HBox TextGroup = new HBox(45);
                    TextGroup.setPrefWidth(width);
                    TextGroup.setAlignment(Pos.CENTER);
                    TextGroup.setTranslateY(10);
                    for ( double i = startDegree ; i <= endDegree ; i++ ) {
                              if ( i % 90 == 0 ) {
                                        int temp = (int) (i / 90);
                                        String text = ((temp == -1 || temp == -2) ? "-" : (temp == 1 || temp == 2) ? "" : temp % 2 == 0 ? (temp / 2) : temp)
                                                + ((temp == 0) ? "   " : '\u03c0' + (temp % 2 == 0 ? "" : "/2"));
                                        Label label = new Label(text);
                                        label.setPrefWidth(30);
                                        label.setAlignment(Pos.CENTER);
                                        TextGroup.getChildren().add(label);
                                        i += 89;
                              }
                    }
                    TextGroup.setTranslateY(height / 2);
                    return TextGroup;
          }



          private Group createPath(String equation) {
                    Polyline path = new Polyline();
                    path.setStroke(Color.BLACK);
                    new Thread(() -> {
                              for ( double i = -1000 ; i <= 1000 ; i += 10 ) {
                                        path.getPoints().add(i);
                                        double di = 5;
//                                        double di = Maths.solveEquation(equation.replaceAll("[xX]" , "(" + i / 10 + ")").replaceAll("[yY]" , "x"));
                                        path.getPoints().add(-di * 10);
                                        System.out.println(i + "\t" + di);
                              }
                    }).
                            start();
                    Group group = new Group(path);
                    group.setTranslateX(width / 2);
                    group.setTranslateY(height / 2);
                    return group;
          }



          @Override
          public void start(Stage primaryStage) {
                    primaryStage.setTitle("Sine Path");
                    primaryStage.setResizable(false);
                    primaryStage.setScene(initScene());
                    primaryStage.show();
          }



          /**
           @param args the command line arguments
           */
          public static void main(String[] args) {
                    launch(args);
          }

}
