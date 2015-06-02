package br.com.ufpi.systematicmap.utils;

import java.util.Comparator;

import br.com.ufpi.systematicmap.model.Article;

public class ArticleComparator implements Comparator<Article>{
 
    @Override
    public int compare(Article e1, Article e2) {
    	return e1.getTitle().compareToIgnoreCase(e2.getTitle());
    }
}
