/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagarism_checker;

import java.io.* ;
import static java.lang.Double.max;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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
    double matching[][] ;
    Checking TH ;
    int total , mxl ;
    private TableView<Table_Row>table = new TableView<Table_Row>() ;
    private final ObservableList<Table_Row>list = FXCollections.observableArrayList() ;
    ArrayList<String> file_name ;
    
    @Override
    public void start(Stage primaryStage) {
        TH = new Checking();
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
                if(TH.isAlive())
                {
                    try
                    {
                        TH.stop();
                    }
                    
                    catch(Exception ex)
                    {
                        
                    }
                }
                
                AddProgressBar bar = new AddProgressBar();
                bar.show();
                
                Thread nth = new Thread(tsk);
                nth.start();
                
                bar.window.setOnCloseRequest(event -> {
                    nth.stop();
                });
                
                tsk.setOnSucceeded(event -> {
                    bar.window.close();
                    //System.out.println("done");
                    
                    try{
                    WriteExcel();
                    }
                    
                    catch(Exception ex)
                    {
                        
                    }
                });
                
                set_path_string("");
            }
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
//        System.out.println("our path -- "+path);
    }
    
    class Checking extends Thread
    {
        public void run()
        {
            file_name = new ArrayList<String>();
            File_Name_Collector collector = new File_Name_Collector(folder_dir);
            collector.process();
            
            Pre_Processor processor = new Pre_Processor(collector.files);
            processor.process();
            
            total = processor.file_contents.size() ;
            file_name = processor.file_name ;
            
            mxl = 0 ;
            
            for(int i=0 ; i<file_name.size() ; i++)
                mxl = Math.max(mxl,file_name.get(i).length());
            
            matching = new double[total+7][total+7] ;
            
            for(int i=0 ; i<total ; i++)
            {
                for(int j=i+1 ; j<total ; j++)
                {
                    Edit_Distance dist = new Edit_Distance(processor.file_contents.get(i), processor.file_contents.get(j));
                    matching[i][j] = matching[j][i] = dist.calculate();
                }
            }
            
//            System.out.println("pass");
        }
    }
    
    public Task tsk = new Task<Void>()
    {   
        @Override
        protected Void call() 
        {
            TH.start();
            
            try
            {
                TH.join();
            }
            
            catch(Exception ex)
            {
                //  
            }
            
            return null ;
        } 
    };
    
    public void WriteExcel() throws Exception {
        Writer writer = null;
        try {
            Path path = Paths.get(Plagarism_checker.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            String spath = path.toString() , tstr = "" ;
            
            spath = spath.substring(0, spath.lastIndexOf('\\'));
            spath += "\\result.csv" ;
            //System.out.println(spath);
            File file = new File(spath);
            file.createNewFile();
            writer = new BufferedWriter(new FileWriter(file));
            String text = ""  , ttext ;
            int temp ;
            mxl++;
            
            text = String.format("%" + mxl + "s", "");
            
            for(int i=0 ; i<total ; i++)
            {
                temp = file_name.get(i).length() ; 
                text += String.format("%"+ (mxl-temp) +"s",file_name.get(i));
            }
            //System.out.println("ok ");
            text += "\n";
            writer.write(text);

            for(int i=0 ; i<total ; i++)
            {
                temp = file_name.get(i).length();
                text = String.format("%"+ (mxl-temp) +"s",file_name.get(i));
                
                for(int j=0 ; j<total ; j++)
                {
                    ttext = String.format("%.2f", matching[i][j]); 
                    temp = ttext.length();
                    text += String.format("%"+ (mxl-temp) +"s",ttext);
                }
                
                text += "\n" ;
                writer.write(text);
            }
        } catch (Exception ex) {
           throw ex ;
        }
        finally {
           
            writer.flush();
            writer.close();
        } 
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
