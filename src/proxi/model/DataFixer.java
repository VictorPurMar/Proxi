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

	public static DateTime dataFixer(Article article) {
		if (article.getDiary().contains("20minutos.es"))
			return minutosArticleDateText2DateTime(article);
		else if (article.getDiary().contains("elpais.com"))
			return paisArticleDateText2DateTime(article);
		else if (article.getDiary().contains("elmundo.es"))
			return mundoArticleDateText2DateTime(article);
		return null;
	}

	public static DateTime dataFixer(Article article, Commentary commentary) {
		if (article.getDiary().contains("20minutos.es"))
			return minutosCommentaryDateText2DateTime(commentary);
		else if (article.getDiary().contains("elpais.com"))
			return paisCommentaryDateText2DateTime(commentary);
		else if (article.getDiary().contains("elmundo.es"))
			return mundoCommentaryDateText2DateTime(commentary);
		return null;
	}

	private static DateTime minutosArticleDateText2DateTime(Article article) {
		String art = article.getDate().replaceAll(" - ", ".")
				.replaceAll("h", "");
		String[] artDateText = art.split(" ");
		String bruteDateText = artDateText[artDateText.length - 1];
		String[] dateArray = bruteDateText.split("\\.");
		int year = Integer.parseInt(dateArray[2]);
		int month = Integer.parseInt(dateArray[1]);
		int day = Integer.parseInt(dateArray[0]);
		int hour = 0;
		try {
			hour = Integer.parseInt(dateArray[3]);
		} catch (Exception e) {
		}
		int minute = 0;
		try {
			minute = Integer.parseInt(dateArray[4]);
		} catch (Exception e) {
		}
		int second = 0;

		DateTime dt = new DateTime(year, month, day, hour, minute, second);
		return dt;
	}

	private static DateTime paisArticleDateText2DateTime(Article article) {
		// Pais 30 AGO 2014 - 16:56 CEST
		String articleDate = article.getDate().toLowerCase();
		if (articleDate.contains("actualizado"))
			articleDate.replace("actualizado", "");
		while (articleDate.startsWith(" ") || articleDate.startsWith(":")) {
			articleDate = articleDate.substring(1, articleDate.length());
		}

		articleDate = articleDate.replaceAll("-", " ").replaceAll(":", " ")
				.replaceAll("  ", "");
		String[] bruteData = articleDate.split(" ");
		int day = Integer.parseInt(bruteData[0]);
		int month = parseTextMonth(bruteData[1]);
		int year = Integer.parseInt(bruteData[2]);
		int hour = Integer.parseInt(bruteData[3]);
		int minute = Integer.parseInt(bruteData[4]);
		int second = 0;

		DateTime dt = new DateTime(year, month, day, hour, minute, second);
		return dt;
	}

	private static DateTime mundoArticleDateText2DateTime(Article article) {

		String articleDate = article.getDate().toLowerCase();
		if (articleDate.contains("actualizado"))
			articleDate = articleDate.replace("actualizado", "");
		while (articleDate.startsWith(" ") || articleDate.startsWith(":")
				|| articleDate.startsWith(".")) {
			articleDate = articleDate.substring(1, articleDate.length());
		}

		String bruteCompletDate = articleDate.replaceAll("/", " ").replaceAll(
				":", " ");
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

	private static DateTime minutosCommentaryDateText2DateTime(
			Commentary commentary) {
		String comment = commentary.getDate().replaceAll(" - ", ".")
				.replaceAll("h", "");
		String[] commentDateText = comment.split(" ");
		String bruteDateText = commentDateText[commentDateText.length - 1];

		String[] dateArray = bruteDateText.split("\\.");

		int year = Integer.parseInt(dateArray[2]);
		int month = Integer.parseInt(dateArray[1]);
		int day = Integer.parseInt(dateArray[0]);
		int hour = 0;
		try {
			hour = Integer.parseInt(dateArray[3]);
		} catch (Exception e) {
		}
		int minute = 0;
		try {
			minute = Integer.parseInt(dateArray[4]);
		} catch (Exception e) {
		}
		int second = 0;

		DateTime dt = new DateTime(year, month, day, hour, minute, second);
		return dt;
	}

	private static DateTime paisCommentaryDateText2DateTime(
			Commentary commentary) {
		DateTime dt = new DateTime();
		String date = commentary.getDate().toLowerCase(); // .replaceAll(" - ",
															// " ").replaceAll(":",
															// " ");
		if (date.contains("menos de")) {
			return dt;
		} else if (date.contains("minutos") || date.contains("minuto")) {
			String[] dateParts = date.split(" ");
			int minutes = Integer.parseInt(dateParts[1]);
			return dt.minusMinutes(minutes);
		} else if (date.contains("horas") || date.contains("hora")) {
			String[] dateParts = date.split(" ");
			int hours = Integer.parseInt(dateParts[1]);
			return dt.minusHours(hours);
		}
		return null;
	}

	private static DateTime mundoCommentaryDateText2DateTime(
			Commentary commentary) {

		try {
			String articleDate = commentary.getDate().toLowerCase();
			if (articleDate.contains("actualizado"))
				articleDate = articleDate.replace("actualizado", "");
			while (articleDate.startsWith(" ") || articleDate.startsWith(":")
					|| articleDate.startsWith(".")) {
				articleDate = articleDate.substring(1, articleDate.length());
			}

			String bruteCompletDate = articleDate.replaceAll("/", " ")
					.replaceAll(":", " ");
			String[] dateParts = bruteCompletDate.split(" ");
			int year = Integer.parseInt(dateParts[2]);
			int month = Integer.parseInt(dateParts[1]);
			int day = Integer.parseInt(dateParts[0]);
			int hour = Integer.parseInt(dateParts[3]);
			int minute = Integer.parseInt(dateParts[4]);
			int second = 0;

			DateTime dt = new DateTime(year, month, day, hour, minute, second);
			return dt;
		} catch (Exception e) {

		}
		return null;

	}

	private static int parseTextMonth(String textMonth) {
		if (textMonth.equals("jan") || textMonth.equals("ene"))
			return 1;
		else if (textMonth.equals("feb"))
			return 2;
		else if (textMonth.equals("mar"))
			return 3;
		else if (textMonth.equals("apr") || textMonth.equals("abr"))
			return 4;
		else if (textMonth.equals("may"))
			return 5;
		else if (textMonth.equals("jun"))
			return 6;
		else if (textMonth.equals("jul"))
			return 7;
		else if (textMonth.equals("aug") || textMonth.equals("ago"))
			return 8;
		else if (textMonth.equals("sep"))
			return 9;
		else if (textMonth.equals("oct"))
			return 10;
		else if (textMonth.equals("nov"))
			return 11;
		else if (textMonth.equals("dec") || textMonth.equals("dic"))
			return 12;
		return 0;
	}

	public static String authorFixer(String diaryName, String author) {
		if (diaryName.contains("20minutos")) {
			author = author.replaceAll(" - ", ".").replaceAll("h", "");
			String[] authorParts = author.split(" ");
			author = author.replaceAll(authorParts[authorParts.length - 1], "");
		}
		return author;
	}
}
