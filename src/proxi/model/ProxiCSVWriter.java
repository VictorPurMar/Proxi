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
		
		//Serial Excel CSV divider
		final String divider = ";";
		String name = "";
		String date = "";
		String hour = "";

		if (article.getDateTime()!=null){
			DateTime dt = article.getDateTime();
			
			String month = fixWith2Chars(dt.getMonthOfYear());
			String day = fixWith2Chars(dt.getDayOfMonth());
			String h = fixWith2Chars(dt.getHourOfDay());
			String m = fixWith2Chars(dt.getMinuteOfHour());
					
			name = "" + dt.getYear() + month + day 
					+ "_" + article.getCommentaries().size() + "_"
					+ article.getTitle().toLowerCase().replaceAll("[,.;:-_'\\s]", "").substring(0, 20) + ".csv";
			date = "" + dt.getYear() +"/" + month +"/" + day;
			hour = "" + h + ":" + m; 
			
		}else{
			name = article.getDate().replaceAll("-", "").replaceAll("\\s+","").replaceAll(":","").replaceAll("/","")
					+ "_" + article.getCommentaries().size() + "_"
					+ article.getTitle().replaceAll("^[0-9,.;:-_'\\s]+$", "").replaceAll("\\s+","")
							.substring(0, 20) + ".csv";
			date = article.getDate();
			hour = article.getDate();
		}
		
		DateTime now = new DateTime();
		String nowM = fixWith2Chars(now.getMonthOfYear());
		String nowD = fixWith2Chars(now.getDayOfMonth());
		String nowH = fixWith2Chars(now.getHourOfDay());
		String nowMin = fixWith2Chars(now.getMinuteOfHour());
		
		try {
			PrintWriter writer = new PrintWriter(name, "UTF-8");
			//Add BOM character to Excel character compatibility
			writer.print('\ufeff');
			
			writer.println("ARTÍCULO");
			writer.println("Titulo" + divider + article.getTitle());
			writer.println("Subtitulo" + divider +article.getSubtitle());
			writer.println("Fecha" + divider + date);
			writer.println("Hora" + divider + hour);
			writer.println("Autor" + divider +article.getAuthor());
			writer.println("Diario" + divider +article.getDiary());
			writer.println("Enlace" + divider +article.getUrl());
			writer.println();
			writer.println("ANÁLISIS");
			writer.println("Comentarios" + divider +article.getCommentaries().size());
			writer.println("Fecha" + divider + now.getYear() + "/" + nowM + "/" + nowD);
			writer.println("Hora" + divider + nowH + ":" + nowMin );
			writer.println();
			writer.println();
			writer.println("COMENTARIOS");
			writer.println("Nº" + divider + "Fecha" + divider + "Hora" + divider + "Autor" + divider + "Comentario");

			for (int i = 0; i < article.getCommentaries().size(); i++) {
				Commentary commentary = article.getCommentaries().get(i);
				
				if (commentary.getDateTime()!=null){
				writer.println(
						commentary.getNumber()
						+ divider + date 
						+ divider + hour
						+ divider + commentary.getNickName()
						//Change the ";" character to ":" to the CSV correct behavior
						+ divider + commentary.getCommentary().replace(divider, ":").replace("\"", ""));
				}else{
					writer.println(
							commentary.getNumber()
							+ divider + commentary.getDate() 
							+ divider + commentary.getDate()
							+ divider + commentary.getNickName()
							//Change the ";" character to ":" to the CSV correct behavior
							+ divider + commentary.getCommentary().replace(divider, ":").replace("\"", ""));
				}
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private static String fixWith2Chars(int number){
		String x ="";
		if (number<10) x = "0"+number;
		else x = "" + number;
		return x;
	}

}
