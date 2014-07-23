package Controller;

import java.util.ArrayList;
import java.util.List;

import Model.Article;
import Model.ArticleParser;
import View.ProxyMain;
import View.ProxySecond;

public class Controller {

	private static Controller instance;
	public List<String> analyzedUrls = null;
	public List<Article> articles;
	public ArticleParser ap;

	public static void main(String[] args) {
		Controller c = getInstance();
		ProxyMain pm = new ProxyMain(c);
		
		while (!pm.cont()){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pm.close();
		System.out.println(c.analyzedUrls);
		
		ProxySecond ps = new ProxySecond(c);
		c.parseUrls();
		c.writeCSV();
		ps.close();
	}

	private void writeCSV() {
		for (int i = 0; i < this.articles.size(); i++) {
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
		this.ap = new ArticleParser();

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

}
