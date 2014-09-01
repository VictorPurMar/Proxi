/*
 *  ArticleInflater.java
 *  
 *  This file is part of Proxi project.
 *  
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *  
 *  This class inflate the Articles and add they to an entry Diary
 *  Also inflate the Commentaries and add they to the Article
 *  
 *  To do the task this class requires the selenium-server-standalone jar
 *  The process begins opening a Browser thanks to the Selenium library and
 *  parsing the required URLs contained in the required Diary
 *  The Diary contains the necessary RegEx to parse the HTML
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import proxi.model.objects.*;

/**
 * 
 * @author VictorPurMar <vpurcallas@gmail.com>
 * 
 */
public class ArticleInflater {
	// Web driver of Selenium
	// FirefoxDriver in that case
	private static WebDriver driver;

	private String analyzedUrl;
	private Diary diary;
	private Set<Commentary> analyzedComments;
	private int commentCounter;

	// Constructor

	public ArticleInflater() {
		analyzedComments = new HashSet<Commentary>();
		commentCounter = 0;
	}

	// Public Methods

	/**
	 * Start the process to inflate the Articles to add to a recived Diary
	 * 
	 * @param url
	 *            contains the url that will be parsed using selenium
	 *            FirefoxDriver
	 * @param diary
	 *            is a Diary that will be stored as an attribute
	 * @return Article with inflated with content
	 */
	public Article run(String url, Diary diary) {

		this.diary = diary;
		this.analyzedUrl = url;

		// Open or reuse the browser
		if (driver == null) {
			driver = new FirefoxDriver();
			driver.manage().window().setPosition(new Point(-2000, -2000));
			driver.manage().window().setSize(new Dimension(100, 100));

		}

		// Make the article from the web
		Article article = articleController();

		// Order the article commentaries
		orderByTime(article);

		return article;
	}

	public void close() {
		driver.close();
	}
	
	public static void closeDriver(){
		driver.close();
	}
	
	// Private methods

	/**
	 * Inflates the Article with his content First send start the article basic
	 * Maker then inflates the comments to the Article
	 * 
	 * @return Article with all his content
	 */
	private Article articleController() {
		Article article = null;

		try {
			if (!analyzedUrl.contains("http://"))
				analyzedUrl = "http://" + analyzedUrl;
			driver.get(analyzedUrl);

			// Article maker and basic data inflater
			article = articleBasicMaker();

			// Article commentary inflater
			article = commentInflater(article);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return article;
	}

	/**
	 * Makes an Article using parsing the web, using the necessary RegEx
	 * contained in this.diary.
	 * 
	 * @return an Article without comments
	 */
	private Article articleBasicMaker() {

		// Article common data inflater
		String title = driver.findElement(By.xpath(this.diary.getTitleRegEx()))
				.getText();
		String subtitle = driver.findElement(
				By.xpath(this.diary.getSubtitleRegEx())).getText();
		String author = DataFixer.authorFixer(this.diary.getDiaryBasicUrl(),
				driver.findElement(By.xpath(this.diary.getAuthorRegEx()))
						.getText());

		String date = "";
		try {
			date = driver.findElement(By.xpath(this.diary.getDateRegEx()))
					.getText(); // NOMBRE
		} catch (Exception x) {
			try {
				date = driver
						.findElement(By.xpath(this.diary.getCommentTimeRegEx()))
						.getAttribute("datetime").toString();
			} catch (Exception y) {
				date = "Fecha erronea";
			}
		}

		String url = analyzedUrl;

		// Uses the name of the first part of the url as diaryName
		String[] cleanDiary = analyzedUrl.split("/");
		String diaryName = cleanDiary[2];

		// Making the article
		Article article = new Article(title, subtitle, author, date, url,
				diaryName);

		try {
			DateTime dt = DataFixer.dataFixer(article);
			article.setDateTime(dt);
		} catch (Exception e) {
			System.err.print("Impossible to add DateTime to article");
		}
		return article;
	}

	/**
	 * Inflates the given Article with comments Inflates it in the proper way
	 * depending at the Diary specifications
	 * 
	 * @param article
	 *            Article
	 * @return Article inflated with comments
	 */
	private Article commentInflater(Article article) {

		if (this.diary.getCommentsPage() != null) {
			WebElement element = driver.findElement(By.xpath(this.diary
					.getCommentsPage()));
			element.click();

			try {
				// wait to page load
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (this.diary.getNextButton() != null) {
			// Repeat the show more click until all the commentaries are showed
			loopNextClicker(article);

		} else {
			// Article Commentaries adder
			article = showedCommentsFiller(article);

		}
		return article;
	}

	/**
	 * Receives an Article with a Diary that uses the next button in his diary.
	 * That loops a process to take all the comments
	 * 
	 * @param article
	 *            Article with all the comments
	 */
	private void loopNextClicker(Article article) {
		Boolean exit = false;
		while (!exit) {
			// Article Commentaries adder
			article = showedCommentsFiller(article);
			try {
				WebElement element = driver.findElement(By.xpath(this.diary
						.getNextButton()));
				element.click();
				Thread.sleep(500); // Correct one unexpected behavior and
									// allow to process the URL to the
									// browser
			} catch (Exception e) {
				exit = true;
			}
		}
	}

	/**
	 * Add showed comments to an Article For that propose makes each Comment
	 * using the RegEx contained in the this.diary attributes
	 * 
	 * @param article
	 *            Article
	 * @return Article with the showed comments added
	 */
	private Article showedCommentsFiller(Article article) {
		Iterator<WebElement> commentary = driver.findElements(
				By.xpath(this.diary.getCommentaryRegEx())).iterator();

		// Count the commentaries
		while (commentary.hasNext()) {
			commentary.next();
			this.commentCounter++;
		}

		commentary = driver.findElements(
				By.xpath(this.diary.getCommentaryRegEx())).iterator();

		while (commentary.hasNext()) {
			WebElement e = commentary.next();

			// Commentary
			String comment = e.findElement(
					By.xpath(this.diary.getCommentTextRegEx())).getText();
			comment = comment.replaceAll("\\n+", "");

			// number
			int n = 0;
			try {
				n = Integer
						.parseInt(e.findElement(
								By.xpath(this.diary.getCommentNumberRegEx()))
								.getText());
			} catch (Exception x) {
				n = commentCounter;
				commentCounter--;
			}

			// Author
			String commentaryAuthor = e.findElement(
					By.xpath(this.diary.getCommentAuthorRegEx())).getText();
			// Time

			String time = "";
			try {
				time = e.findElement(By.xpath(this.diary.getCommentTimeRegEx()))
						.getText();
			} catch (Exception x) {
				try {
					time = e.findElement(
							By.xpath(this.diary.getCommentTimeRegEx()))
							.getAttribute("datetime").toString();
				} catch (Exception y) {
					time = "Fecha erronea";
				}
			}
			// This condition add only non repeated comments
			Commentary c = new Commentary(commentaryAuthor, time, n, comment);
			if (!this.analyzedComments.contains(c)) {
				this.analyzedComments.add(c);
				DateTime dt = DataFixer.dataFixer(article, c);
				c.setDateTime(dt);

				article.addCommentary(c);
			}
		}
		return article;
	}

	/**
	 * Order the commentaries ArrayList from the article
	 * 
	 * @param ar
	 *            Article
	 */
	private void orderByTime(Article ar) {
		ArrayList<Commentary> commentaries = (ArrayList<Commentary>) ar
				.getCommentaries();
		Collections.sort(commentaries, new Comparator<Commentary>() {
			public int compare(Commentary s1, Commentary s2) {
				if (s1.getNumber() - s2.getNumber() > 0)
					return 1;
				return -1;
			}
		});
		ar.setCommentaries(commentaries);
	}
}
