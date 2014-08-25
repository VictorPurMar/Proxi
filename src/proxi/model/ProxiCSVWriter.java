package proxi.model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import proxi.model.objects.Article;



public class ProxiCSVWriter {

	@SuppressWarnings("deprecation")
	public static void makeTheCSV(Article article) {
		//Serial Excel CSV divider
		final String divider = ";";

		String name = article.getDate().replaceAll("-", "").replaceAll("\\s+","").replaceAll(":","").replaceAll("/","")
				+ "_" + article.getCommentaries().size() + "_"
				+ article.getTitle().replaceAll("^[0-9,.;:-_'\\s]+$", "").replaceAll("\\s+","")
						.substring(0, 20) + ".csv";
		Date hour = new Date();
		try {
			PrintWriter writer = new PrintWriter(name, "UTF-8");
			//Add BOM character to Excel character compatibility
			writer.print('\ufeff');
			
			writer.println("ARTÍCULO");
			writer.println("Titulo" + divider + article.getTitle());
			writer.println("Subtitulo" + divider +article.getSubtitle());
			writer.println("Fecha" + divider +article.getDate());
			writer.println("Autor" + divider +article.getAuthor());
			writer.println("Diario" + divider +article.getDiary());
			writer.println("Enlace" + divider +article.getUrl());
			writer.println();
			writer.println("ANÁLISIS");
			writer.println("Comentarios" + divider +article.getCommentaries().size());
			writer.println("Fecha" + divider + hour);
			writer.println("Hora" + divider + hour.getHours() + ":" + hour.getMinutes() + ":" + hour.getSeconds() );
			writer.println();
			writer.println();
			writer.println("COMENTARIOS");
			writer.println("Nº" + divider + "Fecha" + divider /*+ "Hora" + divider +*/ + "Autor" + divider + "Comentario");

			for (int i = 0; i < article.getCommentaries().size(); i++) {
				String[] date = article.getCommentaries().get(i).getDate().split("T");
//				date[0] = date[0].replaceAll("-", "/");
//				String[] h = date[1].split("\\+");
				writer.println(article.getCommentaries().get(i).getNumber()
						
//						+ divider + date[0] + divider
//						+ h[0] + divider
						+ divider +  article.getCommentaries().get(i).getDate() + divider
						+ article.getCommentaries().get(i).getNickName()
						
						+ divider
						//Change the ";" character to ":"
						//To the csv correct behaviour
						+ article.getCommentaries().get(i).getCommentary().replace(divider, ":").replace("\"", ""));
			}
			writer.close();

		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
