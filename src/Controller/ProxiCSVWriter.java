package Controller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import Model.Article;

public class ProxiCSVWriter {

	public static void makeTheCSV(Article article) {
		final String divider = ";";

		String name = article.getDate().replaceAll("-", "")
				+ "_"
				+ article.getTitle().replaceAll("^[0-9,.;:-_'\\s]+$", "").replaceAll("\\s+","")
						.substring(0, 20) + ".xml";
		try {
//			PrintWriter writer = new PrintWriter(name, "ISO-8859-3");
			PrintWriter writer = new PrintWriter(name, "UTF-8");
			writer.println("ARTÍCULO");
			writer.println();
			writer.println("Titulo" + divider + article.getTitle());
			writer.println("Subtitulo" + divider +article.getSubtitle());
			writer.println("Fecha" + divider +article.getDate());
			writer.println("Autor" + divider +article.getAuthor());
			writer.println("Diario" + divider +article.getDiary());
			writer.println("Enlace" + divider +article.getUrl());
			writer.println();
			writer.println();
			writer.println("COMENTARIOS");

			for (int i = 0; i < article.getCommentaries().size(); i++) {
				writer.println(article.getCommentaries().get(i).getNumber()
						+ divider + article.getCommentaries().get(i).getDate()
						+ divider
						+ article.getCommentaries().get(i).getNickName()
						+ divider
						+ article.getCommentaries().get(i).getCommentary());
			}
			writer.close();

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
