package proxi.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import proxi.model.*;
import proxi.model.objects.*;
import proxi.view.*;

public class Controller {

	private static Controller instance;
	public Set<String> analyzedUrls = null;
//	private List<String> errorUrls;
	private List<Diary> diaries;
	private List<Article> articles;

	private static final int WAIT_FOR_ACTION = 400;

	public static void main(String[] args) {

		Controller c = getInstance();

		// Obtain the diaries list
		c.getTheDiaries();

		// Initialize the interface
		// The view fill the analyzedUrls list with the urls
		ProxySecond ps = initInterface(c);

		c.diaryInflater();

		// To develope
		// c.showError();

		c.diaryAnalyzer();
		c.writeCSV();
		ps.close();
	}

	private void diaryInflater() {
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
//			System.out.println(diary.getUrls());
		}

	}

	private static ProxySecond initInterface(Controller c) {
		ProxyMain pm = new ProxyMain(c);

		// The interface will wait to response
		while (!pm.cont()) {
			try {
				Thread.sleep(WAIT_FOR_ACTION);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pm.close();
//		System.out.println(c.analyzedUrls);

		ProxySecond ps = new ProxySecond(c);
		return ps;
	}

	private void writeCSV() {
		for (int i = 0; i < this.articles.size(); i++) {
			System.out.println(this.articles.get(i));
			ProxiCSVWriter.makeTheCSV(this.articles.get(i));
		}
	}

	// Singleton pattern
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	// Constructor
	public Controller() {
		this.analyzedUrls = new HashSet<String>();
//		this.errorUrls = new ArrayList<String>();
		this.articles = new ArrayList<Article>();
		this.diaries = new ArrayList<Diary>();
	}

	private void diaryAnalyzer() {
		
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
		
		// for (int i = 0; i < this.analyzedUrls.size(); i++) {
		// Article ar = this.ap.run(this.analyzedUrls.get(i));
		// articles.add(ar);
		// }
		// System.out.println(this.articles.toString());
		// // Closing the browser opened in Article Parser
		// this.ap.close();
	}

	private void getTheDiaries() {
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

}
