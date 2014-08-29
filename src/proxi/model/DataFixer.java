/*
 *  DiaryDataFixer.java
 *  
 *  This file is part of Proxi project.
 *  
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *  
 *  Fixes the data attribute from a given Article or Comentary
 *  to a DateTime class using JodaTime
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


import org.joda.time.DateTime;

import proxi.model.objects.Article;
import proxi.model.objects.Commentary;

public class DataFixer {
	
	
	public DateTime dataFixer(Article article){	
		if (article.getDiary().contains("20minutos.es")) return articleDateText2DateTime(article);
		else if (article.getDiary().contains("elpais.com")) return articleDateText2DateTime(article);
		else if (article.getDiary().contains("elmundo.es")) return mundoArticleDateText2DateTime(article);
		return null;
	}
	
	public DateTime dataFixer(Article article , Commentary commentary){
	if (article.getDiary().contains("20minutos.es")) return minutosCommentaryDateText2DateTime(commentary);
	else if (article.getDiary().contains("elpais.com")) return paisCommentaryDateText2DateTime(commentary);
	else if (article.getDiary().contains("elmundo.es")) return mundoCommentaryDateText2DateTime(commentary);
	return null;
	}
	

	private DateTime articleDateText2DateTime(Article article) {
		
		String articleDate = article.getDate().toLowerCase();
		if (articleDate.contains("actualizado")) articleDate.replace("actualizado", "");
		while (articleDate.startsWith(" ") ||  articleDate.startsWith(":")){
			articleDate = articleDate.substring(1, articleDate.length());
		}
		
		String[] bruteData = article.getDate().split(" ");
		int day = Integer.parseInt(bruteData[2]);
		int month = parseTextMonth(bruteData[1]);
		int year = Integer.parseInt(bruteData[5]);
		
		String[] hourBrute = bruteData[3].split(":");
		int hour = Integer.parseInt(hourBrute[0]);
		int minute = Integer.parseInt(hourBrute[1]);
		int second = Integer.parseInt(hourBrute[2]);
		
		DateTime dt = new DateTime(year, month, day, hour, minute, second);
		return dt;
	}
	private int parseTextMonth(String textMonth) {
		if (textMonth.equals("Jan")) return 1;
		else if (textMonth.equals("Feb")) return 2;
		else if (textMonth.equals("Mar")) return 3;
		else if (textMonth.equals("Apr")) return 4;
		else if (textMonth.equals("May")) return 5;
		else if (textMonth.equals("Jun")) return 6;
		else if (textMonth.equals("Jul")) return 7;
		else if (textMonth.equals("Aug")) return 8;
		else if (textMonth.equals("Sep")) return 9;
		else if (textMonth.equals("Oct")) return 10;
		else if (textMonth.equals("Nov")) return 11;
		else if (textMonth.equals("Dec")) return 12;	
		return 0;
	}


	private DateTime mundoCommentaryDateText2DateTime(Commentary commentary) {
		
		try{
		String articleDate = commentary.getDate().toLowerCase();
		if (articleDate.contains("actualizado")) articleDate = articleDate.replace("actualizado", "");
		while (articleDate.startsWith(" ") ||  articleDate.startsWith(":") || articleDate.startsWith(".")){
			articleDate = articleDate.substring(1, articleDate.length());
		}
		
		String bruteCompletDate = articleDate.replaceAll("/", " ").replaceAll(":", " ");
		String[] dateParts = bruteCompletDate.split(" ");
		int year = Integer.parseInt(dateParts[2]);
		int month = Integer.parseInt(dateParts[1]);
		int day = Integer.parseInt(dateParts[0]);
		int hour = Integer.parseInt(dateParts[3]);
		int minute = Integer.parseInt(dateParts[4]);
		int second = 00;
		
		DateTime dt = new DateTime(year, month, day, hour, minute, second);
		return dt;
		}catch (Exception e){
			
		}
		return null;

		
		
	}
	private DateTime mundoArticleDateText2DateTime(Article article) {
		
		String articleDate = article.getDate().toLowerCase();
		if (articleDate.contains("actualizado")) articleDate = articleDate.replace("actualizado", "");
		while (articleDate.startsWith(" ") ||  articleDate.startsWith(":") || articleDate.startsWith(".")){
			articleDate = articleDate.substring(1, articleDate.length());
		}
		
		String bruteCompletDate = articleDate.replaceAll("/", " ").replaceAll(":", " ");
		String[] dateParts = bruteCompletDate.split(" ");
		int year = Integer.parseInt(dateParts[2]);
		int month = Integer.parseInt(dateParts[1]);
		int day = Integer.parseInt(dateParts[0]);
		int hour = Integer.parseInt(dateParts[3]);
		int minute = Integer.parseInt(dateParts[4]);
		int second = 00;
		
		DateTime dt = new DateTime(year, month, day, hour, minute, second);
		return dt;
		
		
	}

	private DateTime paisCommentaryDateText2DateTime(Commentary commentary) {
		
		DateTime dt = new DateTime();
		String date = commentary.getDate().toLowerCase();
		if (date.contains("minutos") || date.contains("minuto")){
			String[] dateParts = date.split(" ");
			int minutes = Integer.parseInt(dateParts[1]);
			commentary.setDateTime(dt.minusMinutes(minutes));
		}else if (date.contains("horas") || date.contains("hora")){
			String[] dateParts = date.split(" ");
			int hours = Integer.parseInt(dateParts[1]);
			commentary.setDateTime(dt.minusHours(hours));
		}
		return dt;
	}

	private DateTime minutosCommentaryDateText2DateTime(Commentary commentary) {
		String[] commentDateText = commentary.getDate().split(" ");
		String bruteDateText = commentDateText[1];
		bruteDateText = bruteDateText.replace(" - ", ".");
		bruteDateText.replace("h", "");
		
		String[] dateArray = bruteDateText.split(".");
		
		int year = Integer.parseInt(dateArray[2]);
		int month = Integer.parseInt(dateArray[1]);
		int day = Integer.parseInt(dateArray[0]);
		int hour = Integer.parseInt(dateArray[3]);
		int minute = Integer.parseInt(dateArray[4]);
		int second = 0;
		
		DateTime dt = new DateTime(year, month, day, hour, minute, second);
		return dt;

		
	}

}
