package br.com.ufpi.systematicmap.utils;

import java.io.IOException;
import java.util.Map;

import org.jbibtex.BibTeXDatabase;
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
//		article.setNumber(number);
		
		String author = getAttr(fields, BibTeXEntry.KEY_AUTHOR);
		
		if (author.length() > 2000){
			author = author.substring(0, 1994) + "(...)";
		}
		article.setAuthor(author);
		
		String title = getAttr(fields, BibTeXEntry.KEY_TITLE);
		
		if (title.length() > 2000){
			title = title.substring(0, 1994) + "(...)";
		}
		
		article.setTitle(title);
		
		article.setJournal(getAttr(fields, BibTeXEntry.KEY_JOURNAL));
		article.setVolume(getAttrInt(fields, BibTeXEntry.KEY_VOLUME));
		article.setPages(getAttr(fields, BibTeXEntry.KEY_PAGES));
		article.setDoi(getAttr(fields, BibTeXEntry.KEY_DOI));
		article.setYear(Integer.getInteger(getAttr(fields, BibTeXEntry.KEY_YEAR)));
		
		article.setAbstrct(getAttr(fields, new Key("abstract")));
		
		String keywords = getAttr(fields, new Key("keywords"));
		//System.out.println("Key: " + keywords.length());
		if (keywords.length() > 2000){
			keywords = keywords.substring(0, 1994) + "(...)";
			//System.out.println(keywords);
		}
		
		article.setKeywords(keywords);
		
		article.setLanguage(getAttr(fields, new Key("language")));
		
		if(sourceEnum.equals(ArticleSourceEnum.SCOPUS)){
			article.setDocType(getAttr(fields, new Key("document_type")));
			article.setNote("author keywords: "+getAttr(fields, new Key("authos_keywords")));
		}else if(sourceEnum.equals(ArticleSourceEnum.WEB_OF_SCIENCE)){
			article.setDocType(getAttr(fields, new Key("type")));
		}
		
		return article;
	}
	
	static public BibTeXDatabase articleToBibTeX(Article article) throws IOException {
		
		BibTeXDatabase bib = new BibTeXDatabase();
		
		bib.addObject(new BibTeXEntry(BibTeXEntry.KEY_AUTHOR, new Key(article.getAuthor())));
		bib.addObject(new BibTeXEntry(BibTeXEntry.KEY_TITLE, new Key(article.getTitle())));
		bib.addObject(new BibTeXEntry(BibTeXEntry.KEY_JOURNAL, new Key(article.getJournal())));
		bib.addObject(new BibTeXEntry(BibTeXEntry.KEY_VOLUME, new Key(article.getVolume().toString())));
		bib.addObject(new BibTeXEntry(BibTeXEntry.KEY_PAGES, new Key(article.getPages())));
		bib.addObject(new BibTeXEntry(BibTeXEntry.KEY_DOI, new Key(article.getDoi())));
		
		bib.addObject(new BibTeXEntry(new Key("abstract"), new Key(article.getAbstrct())));
		bib.addObject(new BibTeXEntry(new Key("keywords"), new Key(article.getKeywords())));
		bib.addObject(new BibTeXEntry(new Key("language"), new Key(article.getLanguage())));
		
//		BibTeXInclude teste = new BibTeXInclude(new StringValue("teste", Style.QUOTED), bib);
		
		return bib;
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
