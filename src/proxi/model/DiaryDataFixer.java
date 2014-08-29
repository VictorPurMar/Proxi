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

public class DiaryDataFixer {
	
	public DiaryDataFixer(Article article){	
		if (article.getDiary().contains("20minutos.es")) articleDateText2DateTime(article);
		else if (article.getDiary().contains("elpais.com")) articleDateText2DateTime(article);
		else if (article.getDiary().contains("elmundo.es")) articleDateText2DateTime(article);
	}
	
	private void articleDateText2DateTime(Article article) {
		
		String articleDate = article.getDate().toLowerCase();
		if (articleDate.contains("actualizado")) articleDate.replace("actualizado", "");
		while (articleDate.startsWith(" ") ||  articleDate.startsWith(":")){
			articleDate.substring(1, articleDate.length());
		}
		
		String[] bruteData = article.getDate().split("\\s");
		int day = Integer.parseInt(bruteData[2]);
		int month = parseTextMonth(bruteData[1]);
		int year = Integer.parseInt(bruteData[5]);
		
		String[] hourBrute = bruteData[3].split(":");
		int hour = Integer.parseInt(hourBrute[0]);
		int minute = Integer.parseInt(hourBrute[1]);
		int second = Integer.parseInt(hourBrute[2]);
		
		DateTime dt = new DateTime(year, month, day, hour, minute, second);
		article.setDateTime(dt);
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
	public DiaryDataFixer(Article article , Commentary commentary){
		if (article.getDiary().contains("20minutos.es")) minutosCommentaryDateText2DateTime(commentary);
		else if (article.getDiary().contains("elpais.com")) paisCommentaryDateText2DateTime(commentary);
		else if (article.getDiary().contains("elmundo.es")) mundoCommentaryDateText2DateTime(commentary);
	}

	private void mundoCommentaryDateText2DateTime(Commentary commentary) {
		String bruteCompletDate = commentary.getDate().replaceAll("\\", "\\s").replaceAll(":", " ");
		String[] dateParts = bruteCompletDate.split("\\s");
		int year = Integer.parseInt(dateParts[2]);
		int month = Integer.parseInt(dateParts[1]);
		int day = Integer.parseInt(dateParts[0]);
		int hour = Integer.parseInt(dateParts[3]);
		int minute = Integer.parseInt(dateParts[4]);
		int second = 00;
		
		DateTime dt = new DateTime(year, month, day, hour, minute, second);
		commentary.setDateTime(dt);
		
		
	}

	private void paisCommentaryDateText2DateTime(Commentary commentary) {
		DateTime dt = new DateTime();
		String date = commentary.getDate().toLowerCase();
		if (date.contains("minutos") || date.contains("minuto")){
			String[] dateParts = date.split("\\s");
			int minutes = Integer.parseInt(dateParts[1]);
			commentary.setDateTime(dt.minusMinutes(minutes));
		}else if (date.contains("horas") || date.contains("hora")){
			String[] dateParts = date.split("\\s");
			int hours = Integer.parseInt(dateParts[1]);
			commentary.setDateTime(dt.minusHours(hours));
		}
	}

	private void minutosCommentaryDateText2DateTime(Commentary commentary) {
		String[] commentDateText = commentary.getDate().split("\\s");
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
		commentary.setDateTime(dt);
		
	}

}
