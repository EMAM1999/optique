/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package design;

import design.Draw.Type;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**

 @author EMAM
 */
public class JavaFXApplication93 extends Application {

      static int x = 1000;
      static int y = 500;

      static int APlace = -100;
      static int ALength = 100;

      static int F = -50;

      static double sceneX;
      static double sceneY;

      @Override
      public void start(Stage primaryStage) {
            MenuItem systems = new MenuItem("systems");
            Menu file = new Menu("file");
            file.getItems().add(systems);
            MenuBar m = new MenuBar(file);
            m.setTranslateX(-x / 2);
            m.setTranslateY(-y / 2);

            Group axes = Draw.draw(Type.LENTILLE_DIVERGENTE, x, y, F);
            Group a = Draw.drawA(APlace, ALength);
            Group root = new Group(axes, a);
//            root.setTranslateX(x / 2);
//            root.setTranslateY(y / 2);

//            root.setOnMousePressed((e) -> {
//                  sceneX = axes.getTranslateX() - e.getSceneX();
//                  sceneY = axes.getTranslateY() - e.getSceneY();
//            });
//            root.setOnMouseDragged((e) -> {
//                  double difX = e.getSceneX() + sceneX;
//                  double difY = e.getSceneY() + sceneY;
////                  if ( difX >= 100000 - x / 2 || difX <= -100000 - x / 2 ) {
////                        return;
////                  }
//                  axes.setTranslateX(difX);
//                  axes.setTranslateY(difY);
//            });
            Scene scene = new Scene(new VBox(m, root), x + 100, y + 100);

            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
//            primaryStage.setResizable(false);
            primaryStage.show();

      }

      /**
       @param args the command line arguments
       */
      public static void main(String[] args) {
            launch(args);
      }

}
