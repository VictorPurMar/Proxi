/*
 *  ProxiCSVWriter.java
 *  
 *  This file is part of Proxi project.
 *  
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *  
 *  This class writes the content of each contained Diary into a CSV
 *  to be managed by the user
 *  
 *  		
 *
 *  Proxi project is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Proxi project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Proxi project.  If not, see <http://www.gnu.org/licenses/>. 
 */

package proxi.model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.joda.time.DateTime;

import proxi.model.objects.Article;
import proxi.model.objects.Commentary;

public class ProxiCSVWriter {

	public static void makeTheCSV(Article article) {
		
		DateTime now = new DateTime();
		String[] dtPartsNow = dateTimeCutter(now);

		// Serial Excel CSV divider
		final String divider = ";";
		String name = "";
		String articleDate = article.getDate();
		String articleHour = article.getDate();

		if (article.getDateTime() != null) {
			DateTime dtArt = article.getDateTime();
			String[] dtParts = dateTimeCutter(dtArt);

			name = ""
					+ dtParts[0]
					+ dtParts[1]
					+ dtParts[2]
					+ "_"
					+ article.getCommentaries().size()
					+ "_"
					+ article.getTitle().toLowerCase()
							.replaceAll("[,.;:-_'\\s]", "").replaceAll("\"", "").substring(0, 20)
					+ ".csv";
			articleDate = "" + dtParts[0] + "/" + dtParts[1] + "/" + dtParts[2];
			articleHour = "" + dtParts[3] + ":" + dtParts[4];

		} else {
			name = article.getDate().replaceAll("-", "").replaceAll("\\s+", "")
					.replaceAll(":", "").replaceAll("/", "")
					+ "_"
					+ article.getCommentaries().size()
					+ "_"
					+ article.getTitle().replaceAll("^[0-9,.;:-_'\\s]+$", "")
							.replaceAll("\\s+", "").substring(0, 20) + ".csv";
		}

		try {
			PrintWriter writer = new PrintWriter(name, "UTF-8");
			// Add BOM character to Excel character compatibility
			writer.print('\ufeff');

			writer.println("ARTÍCULO");
			writer.println("Titulo" + divider + article.getTitle());
			writer.println("Subtitulo" + divider + article.getSubtitle());
			writer.println("Fecha" + divider + articleDate);
			writer.println("Hora" + divider + articleHour);
			writer.println("Autor" + divider + article.getAuthor());
			writer.println("Diario" + divider + article.getDiary());
			writer.println("Enlace" + divider + article.getUrl());
			writer.println();
			writer.println("ANÁLISIS");
			writer.println("Comentarios" + divider
					+ article.getCommentaries().size());
			writer.println("Fecha" + divider + dtPartsNow[0] + "/" + dtPartsNow[1] + "/"
					+ dtPartsNow[2]);
			writer.println("Hora" + divider + dtPartsNow[3] + ":" + dtPartsNow[4]);
			writer.println();
			writer.println();
			writer.println("COMENTARIOS");
			writer.println("Nº" + divider + "Fecha" + divider + "Hora"
					+ divider + "Autor" + divider + "Comentario");

			for (int i = 0; i < article.getCommentaries().size(); i++) {
				Commentary commentary = article.getCommentaries().get(i);
				String commentDate = commentary.getDate().replaceAll(",", " ");
				String commentHour = commentDate;
				try{
				if (commentary.getDateTime() != null) {
					DateTime dtCom = commentary.getDateTime();
					String[] dtParts = dateTimeCutter(dtCom);
					commentDate = "" + dtParts[0] + "/" + dtParts[1] + "/"
							+ dtParts[2];
					commentHour = "" + dtParts[3] + ":" + dtParts[4];
				}
				}catch (Exception e){
					System.err.println("Com "+ i + " dateTime bad configured");
				}

				writer.println(commentary.getNumber() + divider
						+ commentDate
						+ divider
						+ commentHour
						+ divider
						+ commentary.getNickName()
						// Change the ";" character to ":" to the CSV
						// correct behavior
						+ divider
						+ commentary.getCommentary().replace(divider, ":")
								.replace("\"", ""));

			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private static String[] dateTimeCutter(DateTime dt){
		String[] dtParts = new String[6];
		
		String year = "" + dt.getYear();
		String month = fixWith2Chars(dt.getMonthOfYear());
		String day = fixWith2Chars(dt.getDayOfMonth());
		String hour = fixWith2Chars(dt.getHourOfDay());
		String minute = fixWith2Chars(dt.getMinuteOfHour());
		String second = fixWith2Chars(dt.getSecondOfMinute());
		
		dtParts[0] = year;
		dtParts[1] = month;
		dtParts[2] = day;
		dtParts[3] = hour;
		dtParts[4] = minute;
		dtParts[5] = second;
		
		return dtParts;
		
	}

	private static String fixWith2Chars(int number) {
		String x = "";
		if (number < 10)
			x = "0" + number;
		else
			x = "" + number;
		return x;
	}

}
