/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagarism_checker;

import java.io.* ;
import java.util.* ;

/**
 *
 * @author n_a_z
 */
public class File_Name_Collector {
    
    String directory ;
    ArrayList<File>files ;

    public File_Name_Collector() {
        directory = "" ;
        files = new ArrayList<File>();
    }
    
    public File_Name_Collector(String str)
    {
        directory = str ;
        files = new ArrayList<File>();
    }
    
    public void process()
    {
        if(directory.length() > 0)
            rec(directory);
    }
    
    protected void rec(String directoryName)
    {
        File current_dir = new File(directoryName);
        File flist[] = current_dir.listFiles();
        
        for(File file : flist)
        {
            if(file.isFile())
                files.add(file);
            
            else if(file.isDirectory())
                rec(file.getAbsolutePath());
        }
    }
}
