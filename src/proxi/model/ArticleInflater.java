package proxi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import proxi.model.objects.*;

public class ArticleInflater {

	private String analyzedUrl;
	private Diary diary;
	private static WebDriver driver;

	// Constructor

	public ArticleInflater() {
	}

	// Public Methods

	public Article run(String url, Diary diary) {
		
		
		this.diary = diary;
		this.analyzedUrl = url;

		// Open or reuse the browser
		if (driver == null) {
//			Change profile in Firefox, its not used right now
//			ProfilesIni profile = new ProfilesIni();
//			FirefoxProfile myProfile = profile.getProfile("default");
//			myProfile.setPreference("capability.policy.default.Window.frameElement.get","allAccess");
//			driver = new FirefoxDriver(myProfile);
			
			driver = new FirefoxDriver();
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

	// Private methods

	private Article articleController() {
		Article article = null;

		try {
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

	private Article articleBasicMaker() {

		// Article common data inflater
		String title = driver.findElement(By.xpath(this.diary.getTitleRegEx()))
				.getText();
		String subtitle = driver.findElement(
				By.xpath(this.diary.getSubtitleRegEx())).getText();
		String author = driver.findElement(
				By.xpath(this.diary.getAuthorRegEx())).getText();
		
		//Modificar
//		String date = driver.findElement(By.xpath(this.diary.getDateRegEx()))
//				.getAttribute("datetime");
		String date = new Date().toString();
		
		String url = analyzedUrl;

		String[] cleanDiary = analyzedUrl.split("/");
		String diary = cleanDiary[2];

		// Making the article
		Article article = new Article(title, subtitle, author, date, url, diary);
		return article;
	}

	private Article commentInflater(Article article) {
		int commentDisplayType = this.diary.getDisplayType();
		
		if (this.diary.getCommentsPage() != null){
			WebElement element = driver.findElement(By.xpath(this.diary.getCommentsPage()));
			element.click();
			try {
				//wait to page load
				driver.wait(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
		if (commentDisplayType == 1){
			
		}else if (commentDisplayType == 2){
			 //Repeat the show more click until all the commentaries are showed
			loopNextClicker();

			// Article Commentaries adder
			article = showedCommentsFiller(article);
			
		}else if (commentDisplayType == 3){
		}
		return article;
	}

	private void loopNextClicker() {
		Boolean exit = false;
		while (!exit) {
			try {
				Thread.sleep(200); // Correct one unexpected behavior and
									// allow to process the URL to the
									// browser
				WebElement element = driver.findElement(By.xpath(this.diary
						.getNextButton()));
				element.click();
			} catch (Exception e) {
				exit = true;
			}
		}
	}

	private Article showedCommentsFiller(Article article) {
		Iterator<WebElement> commentary = driver.findElements(
				By.xpath(this.diary.getCommentaryRegEx())).iterator();

		while (commentary.hasNext()) {
			WebElement e = commentary.next();

			// number
			int n = Integer.parseInt(e.findElement(
					By.xpath(this.diary.getCommentNumberRegEx())).getText());
			// Author
			String commentaryAuthor = e.findElement(
					By.xpath(this.diary.getCommentAuthorRegEx())).getText();
			// Time
			String time = e
					.findElement(By.xpath(this.diary.getCommentTimeRegEx()))
					.getAttribute("datetime").toString();
			// Commentary
			String comment = e.findElement(
					By.xpath(this.diary.getCommentTextRegEx())).getText();

			Commentary c = new Commentary(commentaryAuthor, time, n, comment);
			article.addCommentary(c);
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
