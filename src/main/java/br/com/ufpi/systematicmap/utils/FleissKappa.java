package br.com.ufpi.systematicmap.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import br.com.ufpi.systematicmap.model.User;
import br.com.ufpi.systematicmap.model.enums.EvaluationStatusEnum;
import br.com.ufpi.systematicmap.model.vo.ArticleCompareVO;

public class FleissKappa {
	public static final boolean DEBUG = true ;
	 
    /**
     * Example on this Wikipedia article data set 
     */
    public static void main(String[] args)
    {
    	/*
        short[][] mat = new short[][]
        {
            {2,0,0},
            {2,0,0},
            {2,0,0},
            {2,0,0},
            {2,0,0},
            {2,0,0},
            {2,0,0},
            {2,0,0},
            {2,0,0},
            {2,0,0},
            {2,0,0},
            {2,0,0},
            {2,0,0},
            {2,0,0}
        } ;
 
        float kappa = computeKappa(mat) ;
        */
    	
    	//combineKappas();
    }
    
    public static float combineKappas(List<ArticleCompareVO> articlesCompare, List<User> users){
    	HashMap<String, Object> map = combineKappasMap(articlesCompare, users);
    	Float kappa = (Float) map.get("kappa");
    	return kappa;
    }
    
    public static HashMap<String, Object> combineKappasMap(List<ArticleCompareVO> articlesCompare, List<User> users){
    	short[][] mat = new short[articlesCompare.size()][3];
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	int idx = 0;
    	for(ArticleCompareVO acvo : articlesCompare){
    		short countacc = 0, countrej = 0, countnoteval = 0;
    		String key = "";
    		
    		for(User u : users){
    			EvaluationStatusEnum evaluation = acvo.getEvaluationClassification(u);
				if(evaluation.equals(EvaluationStatusEnum.ACCEPTED)){
    				countacc++;
    			}else if(evaluation.equals(EvaluationStatusEnum.REJECTED)){
    				countrej++;
    			}else if(evaluation.equals(EvaluationStatusEnum.NOT_EVALUATED)){
    				countnoteval++;
    			}
				key+=u.getLogin()+evaluation;
    		}
    		
    		if (key.length() > 0){
    			Integer val = (Integer) map.get(key);
    			if(val == null){
    				map.put(key, 1);
    			}else{
    				map.put(key, val+1);
    			}
    		}
    		
    		mat[idx][0] = countacc;
    		mat[idx][1] = countrej;
    		mat[idx][2] = countnoteval;
    		System.out.println(idx + " [" + countacc + " ," + countrej + " ," + countnoteval + "];");
    		idx++;
    	}
    	
    	String members = "";
    	
    	for(User u : users){
    		members += u.getName() + " ,";
    	}
    	
    	if (members.length() > 0)
    		members = members.substring(0, members.length()-1);
    	
    	float kappa = computeKappa(mat);
    	if (kappa < 0) kappa = 0.0f;
    	
    	map.put("kappa", kappa);
    	map.put("members", members);
    	
    	return map;
    }
 
    /**
     * Computes the Kappa value
     * @param n Number of rating per subjects (number of human raters)
     * @param mat Matrix[subjects][categories]
     * @return The Kappa value
     */
    public static float computeKappa(short[][] mat)
    {
        final int n = checkEachLineCount(mat) ;  // PRE : every line count must be equal to n
        final int N = mat.length ;          
        final int k = mat[0].length ;       
 
        if(DEBUG) System.out.println(n+" raters.") ;
        if(DEBUG) System.out.println(N+" subjects.") ;
        if(DEBUG) System.out.println(k+" categories.") ;
 
        // Computing p[]
        float[] p = new float[k] ;
        if (n != 0 && N != 0){
        for(int j=0 ; j<k ; j++)
        {
            p[j] = 0 ;
            for(int i=0 ; i<N ; i++)
                p[j] += mat[i][j] ;
            p[j] /= N*n ;
        }
        }
        if(DEBUG) System.out.println("p = "+Arrays.toString(p)) ;
 
        // Computing P[]    
        float[] P = new float[N] ;
        if (n > 1){
        for(int i=0 ; i<N ; i++)
        {
            P[i] = 0 ;
            for(int j=0 ; j<k ; j++)
                P[i] += mat[i][j] * mat[i][j] ;
            P[i] = (P[i] - n) / (n * (n - 1)) ;
        }
        }
        if(DEBUG) System.out.println("P = "+Arrays.toString(P)) ;
 
        // Computing Pbar
        float Pbar = 0 ;
        for(float Pi : P)
            Pbar += Pi ;
        if (N != 0)
        Pbar /= N ;
        if(DEBUG) System.out.println("Pbar = "+Pbar) ;
 
        // Computing PbarE
        float PbarE = 0 ;
        for(float pj : p)
            PbarE += pj * pj ;
        if(DEBUG) System.out.println("PbarE = "+PbarE) ;
 
        final float kappa = (Pbar - PbarE)/(1 - PbarE) ;
        if(DEBUG) System.out.println("kappa = "+kappa) ;
 
        return kappa ;
    }
 
    /**
     * Assert that each line has a constant number of ratings
     * @param mat The matrix checked
     * @return The number of ratings
     * @throws IllegalArgumentException If lines contain different number of ratings
     */
    private static int checkEachLineCount(short[][] mat)
    {
        int n = 0 ;
        boolean firstLine = true ;
 
        for(short[] line : mat)
        {
            int count = 0 ;
            for(short cell : line)
                count += cell ;
            if(firstLine)
            {
                n = count ;
                firstLine = false ;
            }
            if(n != count)
                throw new IllegalArgumentException("Line count != "+n+" (n value).") ;
        }
        return n ;
    }
}
