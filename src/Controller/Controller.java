package Controller;

import java.util.ArrayList;
import java.util.List;

import Model.Article;
import Model.ArticleParser;
import Model.Diary;
import View.ProxyMain;
import View.ProxySecond;

public class Controller {

	private static Controller instance;
	public List<String> analyzedUrls = null;
	public List<Diary> diaries;
	public List<Article> articles;
	public ArticleParser ap;

	private static final int WAIT_FOR_ACTION = 400;

	public static void main(String[] args) {
		Controller c = getInstance();
		ProxyMain pm = new ProxyMain(c);

		while (!pm.cont()) {
			try {
				Thread.sleep(WAIT_FOR_ACTION);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pm.close();
		System.out.println(c.analyzedUrls);

		// Obtain the diaries list
		c.getTheDiaries();

		ProxySecond ps = new ProxySecond(c);

		c.parseUrls();
		c.writeCSV();
		ps.close();
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
		this.analyzedUrls = new ArrayList<String>();
		this.articles = new ArrayList<Article>();
		this.diaries = new ArrayList<Diary>();
		this.ap = new ArticleParser(this.diaries);

	}

	private void parseUrls() {
		for (int i = 0; i < this.analyzedUrls.size(); i++) {
			Article ar = this.ap.run(this.analyzedUrls.get(i));
			articles.add(ar);
		}
		System.out.println(this.articles.toString());
		// Closing the browser opened in Article Parser
		this.ap.close();
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
				"El Mundo",
				"//div[@id='listado_comentarios']/section/article",
				"header/h1/a[text()]",
				"header/div[@class='autor']/span[text()]", "header/time",
				"div[@class='texto-comentario']/p[text()]");

		this.diaries.add(elMundo);
	}

}
