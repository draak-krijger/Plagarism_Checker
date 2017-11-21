package plagarism_checker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;

public class DynamicTableViewColumnCount 
{
    
    ArrayList<String> file_name ;
    int total ;
    double matching[][] ;
    Stage primaryStage ;
    
    public void show(ArrayList<String> file_name,double matching[][],int total)
    {
        this.matching = matching ;
        this.total = total ;
        primaryStage = new Stage() ;
        TableView<Row> tableView = new TableView<>();

        // make sample data
        List<Row> rows = makeSampleData(file_name, matching, total);
        int max = getMaxCells(rows) ;
        makeColumns(max, tableView);
        
//        System.out.println(rows.size());
        
        tableView.getItems().addAll(rows);
        
        Button save = new Button();
        save.setText("Export");
        
        VBox hbox = new VBox(10);
        hbox.getChildren().addAll(save,tableView);
        
        save.setOnAction(e -> { 
            try
            {
                WriteExcel();
            }
            
            catch(Exception ex)
            {
                
            }
        });
        
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(800);

        // Boilerplate code for showing the TableView
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(hbox,800,500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void makeColumns(int count, TableView<Row> tableView)
    {
        for (int m = 0; m < count; m++)
        {
            TableColumn<Row, String> column ;//= new TableColumn<>(Integer.toString(m));
            if(m == 0)
                column = new TableColumn<>("   ");
            
            else
            {
                if(file_name.get(m-1).length()<=10)
                    column = new TableColumn<>(file_name.get(m-1));
                
                else
                    column = new TableColumn<>(file_name.get(m-1).substring(0,10));
            }
            column.setCellValueFactory(param -> {
//                int index = Integer.parseInt(param.getTableColumn().getText());
                int index = param.getTableView().getColumns().indexOf(param.getTableColumn());
                List<Cell> cells = param.getValue().getCells();
                return new SimpleStringProperty(cells.size() > index ? cells.get(index).toString() : null);
            });
            tableView.getColumns().add(column);
        }
    }

    public int getMaxCells(List<Row> rows)
    {
        int max = 0;
        for (Row row : rows)
            max = Math.max(max, row.getCells().size());
        return max;
    }

    public List<Row> makeSampleData(ArrayList<String> file_name,double matching[][],int total)
    {
        List<Row> rows = new ArrayList<>();
        
        Row e = new Row();
        
//        e.getCells().add(new Cell("    "));
//        
//        for(int i=0 ; i<total ; i++)
//        {
//            if(file_name.get(i).length()<=10)
//                e.getCells().add(new Cell(file_name.get(i)));
//            
//            else
//                e.getCells().add(new Cell(file_name.get(i).substring(0,10)));
//        }
//        
//        rows.add(e);
        
        for (int i = 0; i < total; i++)
        {
            e = new Row();
//            int jMax = random.nextInt(6); // from 0 to 5
            
            if(file_name.get(i).length()<=10)
                e.getCells().add(new Cell(file_name.get(i)));
            
            else
                e.getCells().add(new Cell(file_name.get(i).substring(0,10)));

            for (int j = 0; j < total; j++)
            {
                e.getCells().add(new Cell(String.format("%.2f", matching[i][j])));
            }
            rows.add(e);
        }
        this.file_name = file_name ;
        return rows ;
    }

    static class Row
    {
        private final List<Cell> list = new ArrayList<>();

        public List<Cell> getCells()
        {
            return list;
        }
    }

    static class Cell
    {
        private final String value;

        public Cell(String value)
        {
            this.value = value;
        }

        @Override
        public String toString()
        {
            return value;
        }
    }
    
    public void WriteExcel() throws Exception {
        Writer writer = null;
        int mxl ;
        try {
            DirectoryChooser dir = new DirectoryChooser();
            File selected = dir.showDialog(primaryStage);
            Path path ;
            
            if(selected == null)
                return ;
            
            else
            {
                path = selected.toPath() ;
            }
            
//            path = Paths.get(Plagarism_checker.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            String spath = path.toString() , tstr = "" ;
            
//            spath = spath.substring(0, spath.lastIndexOf('\\'));
            spath += "\\result.csv" ;
            System.out.println(spath);
            File file = new File(spath);
            file.createNewFile();
            writer = new BufferedWriter(new FileWriter(file));
            String text = ""  , ttext ;
            int temp ;
            mxl = 30 ;
            
            text = String.format("%" + mxl + "s", "");
            
            for(int i=0 ; i<total ; i++)
            {
                temp = file_name.get(i).length() ;
                
                if(temp<10)
                    text += String.format("%"+ (mxl-temp) +"s",file_name.get(i));
                
                else
                {
                    temp = Math.min(temp,10);
                    text += String.format("%"+ (mxl-temp) +"s",file_name.get(i).substring(0,10));
                }
            }
            //System.out.println("ok ");
            text += "\n";
            writer.write(text);

            for(int i=0 ; i<total ; i++)
            {
                temp = file_name.get(i).length() ;
                text = "" ;
                
                if(temp<10)
                    text += String.format("%"+ (mxl-temp) +"s",file_name.get(i));
                
                else
                {
                    temp = Math.min(temp,10);
                    text += String.format("%"+ (mxl-temp) +"s",file_name.get(i).substring(0,10));
                }
                
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
}