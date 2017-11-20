/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagarism_checker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Show progress Bar
 * @author RONIN-47
 */
public class AddProgressBar 
{

    /**
     *  Stage name window
     */
    public Stage window = new Stage() ;
    
    void show()
    {
//        Image ico = new Image("images/acm.png");
        window.setResizable(false);
//        window.getIcons().add(ico);
        window.setTitle("Progressing");
        ProgressIndicator pb = new ProgressIndicator();
        pb.setPrefSize(400, 130);
        pb.setProgress(-1.0);
        Label lb = new Label("Progressing . . .");
        VBox vb = new VBox();
        vb.getChildren().addAll(lb,pb);
        vb.setPadding(new Insets(10,10,10,10));
        vb.setAlignment(Pos.CENTER);
        
        window.initModality(Modality.APPLICATION_MODAL);
//        vb.setStyle("-fx-background: black;");
        Scene scn = new Scene(vb,400,130);
//        scn.getStylesheets().add("stylesheet/mainwindow.css");
        window.setScene(scn);
        window.show();
    }
}