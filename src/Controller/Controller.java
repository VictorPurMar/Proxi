package Controller;

import java.util.ArrayList;
import java.util.List;

import Model.Article;
import Model.ArticleParser;

public class Controller {
	
	private static Controller instance;
	public List<String> analyzedUrls;
	public List<Article> articles;
	public ArticleParser ap;

	public static void main(String[] args) {
		Controller c = getInstance();	
		//load example data
		c.exampleAnalyzedUrls();
		c.parseUrls();
		c.writeCSV();
	}
	
	private void writeCSV() {
		for(int i = 0 ; i < this.articles.size(); i++){
			ProxiCSVWriter.makeTheCSV(this.articles.get(i));
		}
	}

	//Singleton pattern
	public static Controller getInstance() {
		if (instance == null) {
		    instance = new Controller();
		}
		return instance;
	}
	
	//Constructor
	public Controller(){
		this.analyzedUrls = new ArrayList<String>();
		this.articles = new ArrayList<Article>();
		this.ap = new ArticleParser();
		
	}

	private void parseUrls() {
		for (int i = 0 ; i < this.analyzedUrls.size() ; i++){
			Article ar = this.ap.run(this.analyzedUrls.get(i));
			articles.add(ar);
		}
		System.out.println(this.articles.toString());
	}

	//example data
	private void exampleAnalyzedUrls() {
		this.analyzedUrls.add("http://www.elmundo.es/ciencia/2014/07/10/53beb7d3ca4741f8298b4586.html");
		this.analyzedUrls.add("http://www.elmundo.es/espana/2014/07/13/53c248e6ca4741037c8b456d.html");
	}

}
