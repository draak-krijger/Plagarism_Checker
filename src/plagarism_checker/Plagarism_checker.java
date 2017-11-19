/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagarism_checker;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author Nazim
 */
public class Plagarism_checker extends Application {
    
    String folder_dir ;
    
    @Override
    public void start(Stage primaryStage) {
        Button check = new Button();
        check.setText("Check");
        
        Button choose = new Button();
        choose.setText("choose..");
        
        DirectoryChooser dir = new DirectoryChooser();
        set_path_string("");
        
        choose.setOnAction(e -> {
            File selected = dir.showDialog(primaryStage);
            
            if(selected == null)
                set_path_string("");
            
            else
                set_path_string(selected.getAbsolutePath());
        });
        
        check.setOnAction(e -> {
            if(folder_dir.length() == 0)
            {
                Error er = new Error();
                er.show();
            }
            
            else
            {
                
            }
            
            set_path_string("");
        });
        
        HBox hori_box = new HBox(10);
        hori_box.getChildren().addAll(choose,check);
        hori_box.setAlignment(Pos.CENTER);
        
        Label text = new Label("Please Select Your Folder");
        
        VBox ver_box = new VBox(10);
        ver_box.getChildren().addAll(text,hori_box);
        ver_box.setAlignment(Pos.CENTER);
        
        StackPane root = new StackPane();
        root.getChildren().add(ver_box);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Plagarism Checker");
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    void set_path_string(String path)
    {
        folder_dir = path ;
        System.out.println("our path -- "+path);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
