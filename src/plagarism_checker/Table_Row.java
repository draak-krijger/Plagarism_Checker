/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagarism_checker;

import java.awt.image.SampleModel;
import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * we create object of TableRow only for our tableview row .
 * @author RONIN-47
 */
public class Table_Row
{
        private final ArrayList<SimpleStringProperty> row ;
        
        public Table_Row()
        {
            row = new ArrayList<SimpleStringProperty>() ;
        }
        
        public void set_value(ArrayList<String>temp)
        {
            for(int i=0 ; i<temp.size() ; i++)
            {
                SimpleStringProperty p = new SimpleStringProperty(temp.get(i));
                row.add(p);
            }
        }
        
        public String get_value(int ind)
        {
            return row.get(ind).get();
        }
}