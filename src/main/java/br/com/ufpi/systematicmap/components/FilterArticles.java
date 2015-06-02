package br.com.ufpi.systematicmap.components;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.ufpi.systematicmap.model.Article;
import br.com.ufpi.systematicmap.model.enums.ClassificationEnum;
import br.com.ufpi.systematicmap.model.enums.FieldEnum;
import br.com.ufpi.systematicmap.utils.Utils;



public class FilterArticles {
	
	private Map<String, String> regexList = new HashMap<String, String>();
	private Set<Article> papers = new LinkedHashSet<Article>();
	private Integer levenshtein;
	private String regex; 
	private Integer limiartitulo;
	private Integer limiarabstract; 
	private Integer limiarkeywords; 
	private Integer limiartotal;
	
	/*
	 * {{
	    put("automatico", "(automat.*|semiautomati.*|semi-automati.*)");
	    put("web", "(web|website|internet|www)");
	    put("usabilidade", "(usability|usable)");
	    put("tecnica", "(evalu.*|assess.*|measur.*|experiment.*|stud.*|test.*|method.*|techni.*|approach.*)");
	}}
	 */
	
	public FilterArticles(Set<Article> set, Integer levenshtein, String regex, 
			Integer limiartitulo, Integer limiarabstract, Integer limiarkeywords, Integer limiartotal){
		super();
		this.papers = set;
		this.levenshtein = levenshtein;
		this.regex = regex;
		this.limiartitulo = limiartitulo;
		this.limiarabstract = limiarabstract;
		this.limiarkeywords = limiarkeywords;
		this.limiartotal = limiartotal;
	}
	
	public void filter(){
		try{
			System.out.println("Regex: "+regex);
			String[] termos = regex.split(";");
			for(String t : termos){
				String[] strings = t.split(":");
				regexList.put(strings[0], strings[1]);
			}
			
			System.out.println("Total de artigos: "+papers.size());
			System.out.println("Probs autores: "+filterAuthors());
			filterRegex(limiartitulo, limiarabstract, limiarkeywords, limiartotal);
			System.out.println("Probs palavras: "+countPapers(ClassificationEnum.WORDS_DONT_MATCH));
			if(levenshtein != -1){
				calcTitleLevenshteinDistance(levenshtein);
			}
			System.out.println("Probs lenshtein: "+countPapers(ClassificationEnum.REPEAT));
		}catch(Exception e){
			e.printStackTrace();
		}
	} 
	
	private int filterAuthors() {
		int count = 0;
		for(Article p : papers){
			if(p.getAuthor().equals("")){
				p.setClassification(ClassificationEnum.WITHOUT_AUTHORS);
				p.setComments(p.getComments() + ClassificationEnum.WITHOUT_AUTHORS.toString());
				count++;
			}
		}
		return count;
	}
	
	private void filterRegex(int limiarTitle, int limiarAbs, int limiarKeys, int limiarTotal) {
		for(Article p : papers){
			Set<String> termos = new HashSet<String>();
			termos = countRegex(p, FieldEnum.TITLE, limiarTitle, termos);
			termos = countRegex(p, FieldEnum.ABS, limiarAbs, termos);
			termos = countRegex(p, FieldEnum.KEYS, limiarKeys, termos);
			
			if(termos.size() < limiarTotal){
				p.setClassification(ClassificationEnum.WORDS_DONT_MATCH);
			}
		}
	}
	
	private void calcTitleLevenshteinDistance(int limiar) {
		double count = 0, size = papers.size();
		for(Article p : papers){
			int minDist = Integer.MAX_VALUE;
			Article minDistPaper = null;
			String pTitle = p.getTitle().toLowerCase();
			
			for(Article p2 : papers){
				if(p.getId() != p2.getId()){
					int dist = Utils.getLevenshteinDistance(pTitle, p2.getTitle().toLowerCase());
					
					if(minDist > dist){
						minDist = dist;
						minDistPaper = p2;
					}
				}
			}
			
			p.setMinLevenshteinDistance(minDist);
			p.setPaperMinLevenshteinDistance(minDistPaper);
			
			
			if(minDist <= limiar){
				p.setClassification(ClassificationEnum.REPEAT);
				p.setComments(p.getComments() + " " + ClassificationEnum.REPEAT.toString());
			}
			count++;
			System.out.println("loading:"+(count/size)*100);
		}
	}
	
	private Set<String> countRegex(Article p, FieldEnum fieldEnum, int limiar, Set<String> termos) {
		String s = "";
		
		if (fieldEnum.equals(FieldEnum.ABS)) {
			s = p.getAbstrct();
		}else if (fieldEnum.equals(FieldEnum.TITLE)) {
			s = p.getTitle();
		}else if (fieldEnum.equals(FieldEnum.KEYS)) {
			s = p.getKeywords();
		}
		
		Pattern pattern;
		Matcher regexMatcher;
		String comment = "";
		int count = 0;
		
		if(s != null && !s.equals("")){
			Set<Entry<String, String>> set = regexList.entrySet();
			for(Entry<String, String> entry : set){
				String regex = entry.getValue();
				pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			    regexMatcher = pattern.matcher(s);
			    
			    boolean containRegex = false;
			    while (regexMatcher.find()) {
			    	containRegex = true;
			    	//System.out.println(regexMatcher.group(1));
			    }
			    
			    if(containRegex){
			    	termos.add(entry.getKey());
			    	count++;
			    }else{
			    	comment += entry.getKey()+", ";
			    }
			}
		}
		
		if (fieldEnum.equals(FieldEnum.ABS)) {
			p.setRegexAbs(count);
		}else if (fieldEnum.equals(FieldEnum.TITLE)) {
			p.setRegexTitle(count);
		}else if (fieldEnum.equals(FieldEnum.KEYS)) {
			p.setRegexKeys(count);
		}
		
		if(count < limiar){
			p.setClassification(ClassificationEnum.WORDS_DONT_MATCH);
			p.setComments(p.getComments()+" "+ClassificationEnum.WORDS_DONT_MATCH.toString()+"-"+fieldEnum.toString()+" DONT contains=("+comment+");");
		}
		
		return termos;
	}
	
	private int countPapers(ClassificationEnum ce){
		int count = 0;
		for (Article paper : papers){
			if (paper.getClassification() != null && paper.getClassification().equals(ce)) {
				count++;
			}
		}
		return count;
	}
	
}
