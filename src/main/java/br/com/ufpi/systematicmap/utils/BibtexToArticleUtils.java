package br.com.ufpi.systematicmap.utils;

import java.util.Map;

import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;
import org.jbibtex.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.ufpi.systematicmap.model.Article;
import br.com.ufpi.systematicmap.model.enums.ArticleSourceEnum;

public class BibtexToArticleUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(BibtexToArticleUtils.class);
	
	static public Article bibtexToArticle(BibTeXEntry entry, ArticleSourceEnum sourceEnum){
		Article article = new Article();
		Map<Key, Value> fields = entry.getFields();
		
		article.setSource(sourceEnum.toString());
		
		article.setAuthor(getAttr(fields, BibTeXEntry.KEY_AUTHOR));
		article.setTitle(getAttr(fields, BibTeXEntry.KEY_TITLE));
		article.setJournal(getAttr(fields, BibTeXEntry.KEY_JOURNAL));
		article.setVolume(getAttrInt(fields, BibTeXEntry.KEY_VOLUME));
		article.setPages(getAttr(fields, BibTeXEntry.KEY_PAGES));
		article.setDoi(getAttr(fields, BibTeXEntry.KEY_DOI));
		
		article.setAbstrct(getAttr(fields, new Key("abstract")));
		article.setKeywords(getAttr(fields, new Key("keywords")));
		article.setLanguage(getAttr(fields, new Key("language")));
		
		if(sourceEnum.equals(ArticleSourceEnum.SCOPUS)){
			article.setDocType(getAttr(fields, new Key("document_type")));
			article.setNote("author keywords: "+getAttr(fields, new Key("authos_keywords")));
		}else if(sourceEnum.equals(ArticleSourceEnum.WEB_OF_SCIENCE)){
			article.setDocType(getAttr(fields, new Key("type")));
		}
		
		return article;
	}
	
	static private Integer getAttrInt(Map<Key, Value> fields, Key key){
		try{
			return Integer.parseInt(fields.get(key).toUserString());
		}catch(Exception e){
			logger.error(e.getMessage());
			return -1;
		}
	}
	
	static private String getAttr(Map<Key, Value> fields, Key key){
		try{
			return fields.get(key).toUserString();
		}catch(Exception e){
			logger.error(e.getMessage());
			return "";
		}
	}
}
