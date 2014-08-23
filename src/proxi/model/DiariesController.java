package proxi.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import proxi.model.objects.Article;
import proxi.model.objects.Diary;

public class DiariesController {
	
	public Set<String> analyzedUrls = null;
	private List<Diary> diaries;
	private List<Article> articles;
	
	
	/**
	 * Constructor class
	 */
	public DiariesController(){
		this.articles = new ArrayList<Article>();
		this.diaries = new ArrayList<Diary>();
	}
	
	/**
	 * Add the String urls to the this.diaries list of Diary
	 * @param analyzedUrls Set<String> 
	 * @return this.diaries
	 */
	public List<Diary> diaryUrlInflater(HashSet<String> analyzedUrls) {
		this.analyzedUrls = analyzedUrls;
		Iterator<String> iterator = null;
		for (int i = 0; i < this.diaries.size(); i++) {
			Diary diary = this.diaries.get(i);
			iterator = this.analyzedUrls.iterator();
			while (iterator.hasNext()) {
				String url = iterator.next();
				if (url.contains(diary.getDiaryBasicUrl())) {
					diary.addUrl(url);
				}
			}
		}
		return this.diaries;

	}
	
	/**
	 * Inflate the diaries with articles
	 */
	public void diaryArticleInflater() {
		
		ArticleInflater ap = new ArticleInflater();
		for (int i = 0 ; i < this.diaries.size() ; i++){
			Diary diary = this.diaries.get(i);
			List<String> urlsList = diary.getUrls();
			for (int j = 0; j < urlsList.size() ;  j++){
				Article article = ap.run(urlsList.get(j), diary);
				articles.add(article);
			}
		}
		
		ap.close();
	}
	
	/**
	 * Make the diaries from data stored
	 */
	public void getTheDiaries() {
		// Future DiaryParser class for permanent Storage
		// /Demo///

		// Diary d1 = new Diary(diaryName, diaryBasicUrl,
		// expandableCommentaries, nextButton, titleRegEx, subtitleRegEx,
		// authorRegEx, dateRegEx, diaryNameRegEx, commentaryRegEx,
		// commentNumberRegEx, commentAuthorRegEx, commentTimeRegEx,
		// commentTextRegEx);

		Diary elMundo = new Diary("El Mundo", "www.elmundo.es", true,
				"//ul[@id='subNavComentarios']//li//a[@id='botonMas']",
				"//h1[@itemprop='headline']", "//p[@class='antetitulo']",
				"//footer/address//span[@itemprop='name']", "//footer/time",
				"El Mundo", "//div[@id='listado_comentarios']/section/article",
				"header/h1/a[text()]",
				"header/div[@class='autor']/span[text()]", "header/time",
				"div[@class='texto-comentario']/p[text()]");
		
//		Diary elPais = new Diary("El Pais", "www.elpais.com" , false, )

		this.diaries.add(elMundo);
	}
	
	//Getters and Setters

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public List<Diary> getDiaries() {
		return diaries;
	}

	public void setDiaries(List<Diary> diaries) {
		this.diaries = diaries;
	}

}
