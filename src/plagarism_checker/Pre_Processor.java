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
public class Pre_Processor {
    ArrayList<File>files ;
    ArrayList<String> file_contents ;
    
    Pre_Processor(ArrayList<File>files)
    {
        this.files = files ;
        file_contents = new ArrayList<String>() ;
    }
    
    public String readFile(String filename)
    {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try{
        reader.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return content;
    }
    
    private boolean is_valid(char ch)
    {
        if(ch >= 'A' && ch <= 'Z')
            return false ;
        
        if(ch >= 'a' && ch <= 'z')
            return false ;
        
        if(ch >= '0' && ch <= '9')
            return false ;
        
        if(ch == '_' || ch == '\n' || ch == ' ' || ch == '\r')
            return false ;
        
        return true ;
    }
    
    private String be_ready_for_compare(String contents)
    {
        String fstr = "" ;
        int ln = contents.length();
        int flag = 0 ;
        //System.out.println("total "+ln);
        for(int i=0 ; i<ln ; i++)
        {
            while(i<ln && contents.charAt(i) != '\n')
            {
                //System.out.println("while "+i+" "+contents.charAt(i)+" "+flag);
                if(contents.charAt(i) == ' ' || flag == 10)
                {
                    i++;
                    continue ;
                }
                
                if(flag == 0)
                {
                    if(contents.charAt(i) == '#'){
                        flag = 10 ;
                        i++;
                        continue ;
                    }
                    
                    if(i+1<ln && contents.charAt(i) == '/' && contents.charAt(i+1) == '/')
                    {
                        i++;
                        i++;
                        flag = 10 ;
                        continue ;
                    }
                }
                
                if(flag == 1)
                {
                    if(contents.charAt(i) == '/' && contents.charAt(i-1) == '*')
                        flag = 0 ;
                    
                    i++;
                    continue ;
                }
                
                if(i+1<ln && contents.charAt(i) == '/' && contents.charAt(i+1) == '*')
                {
                    i+=2 ;
                    flag = 1 ;
                    continue ;
                }
                
                if(is_valid(contents.charAt(i)) == true)
                {
                    fstr += contents.charAt(i);
                    //System.out.println("last "+contents.charAt(i)+" "+i);
                }
                i++;
            }
            
            if(flag != 1)
                flag = 0 ;
        }
        //System.out.println("output "+fstr+" "+fstr.length());
        return fstr ;
    }
    
    public void process()
    {
        for(int i=0 ; i<files.size() ; i++)
        {
            if(files.get(i).isFile())
                file_contents.add(be_ready_for_compare(readFile(files.get(i).getAbsolutePath())));
        }
        //System.out.println(is_valid(','));
    }
}
