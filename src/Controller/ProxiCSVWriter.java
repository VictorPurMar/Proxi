package Controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import Model.Article;

public class ProxiCSVWriter {

	public static void makeTheCSV(Article article){
		
		String name = article.getDate().replaceAll("-", "") + "_" + article.getTitle().replaceAll("^[0-9,.;:-_'\\s]+$","").substring(0, 20) + ".csv";
		try {
			PrintWriter writer = new PrintWriter(name , "UTF-8");
			writer.println("ARTÍCULO");
			writer.println(article.getTitle());
			writer.println(article.getSubtitle());
			writer.println(article.getDate());
			writer.println(article.getAuthor());
			writer.println(article.getDiary());
			writer.println(article.getUrl());
			writer.println("COMENTARIOS");	
			for (int i = 0 ; i < article.getCommentaries().size(); i++){
				writer.println(article.getCommentaries().get(i).getNumber() + "###" + article.getCommentaries().get(i).getDate() + "###" + article.getCommentaries().get(i).getNickName() + "###" + article.getCommentaries().get(i).getCommentary());
			}
			writer.close();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
