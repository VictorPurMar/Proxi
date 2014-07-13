package Controller;

import java.util.ArrayList;
import java.util.List;

import Model.Article;
import Model.ArticleParser;

public class Controller {
	
	public List<String> analyzedUrls;
	public List<Article> articles;

	public static void main(String[] args) {
		Controller c = new Controller();
		
		//load example data
		c.exampleAnalyzedUrls();
		c.parseUrls();
	}
	
	public Controller(){
		this.analyzedUrls = new ArrayList<String>();
		this.articles = new ArrayList<Article>();
	}

	private void parseUrls() {
		ArticleParser ap = new ArticleParser();
		for (int i = 0 ; i < this.analyzedUrls.size() ; i++){
			Article ar = ap.run(this.analyzedUrls.get(i));
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
