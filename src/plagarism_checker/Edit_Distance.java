/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plagarism_checker;

/**
 *
 * @author n_a_z
 */
public class Edit_Distance 
{
    String str1 , str2 ;
    int dp[][] = new int[3007][3007] ;
    
    Edit_Distance(String str1,String str2)
    {
        this.str1 = str1 ;
        this.str2 = str2 ;
    }
    
    double calculate()
    {
        int ln1 = str1.length();
        int ln2 = str2.length();
        
        for(int i=0 ; i<=Math.max(ln1,ln2) ; i++)
            dp[0][i] = i ;
        
        for(int i=0 ; i<=Math.max(ln1, ln2) ; i++)
            dp[i][0] = i ;
        
        for(int i=1 ; i<=ln2 ; i++)
        {
            for(int j=1 ; j<=ln1 ; j++)
            {
                dp[i][j] = Math.min(dp[i-1][j],dp[i][j-1])+1 ;
                
                if(str2.charAt(i-1) == str1.charAt(j-1))
                    dp[i][j] = Math.min(dp[i][j],dp[i-1][j-1]);
            }
        }
        
        double mismatch = dp[ln2][ln1]/(1.0*(ln1+ln2)) ;
        double match = 1.0 - mismatch ;
        match = match*100 ;
//        System.out.println(match);
        return match ;
    }
}
