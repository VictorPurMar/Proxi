package proxi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import proxi.model.objects.*;

public class ArticleInflater {
	//Web driver of Selenium
	//FirefoxDriver in that case
	private static WebDriver driver;
	
	private String analyzedUrl;
	private Diary diary;
	private Set<String> analyzedComments;
	private int commentCounter;

	// Constructor

	public ArticleInflater() {
		analyzedComments = new HashSet<String>();
		commentCounter = 0;
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
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
		if (this.diary.getNextButton() == null){
			
			// Article Commentaries adder
			article = showedCommentsFiller(article);
			
		}else {
			 //Repeat the show more click until all the commentaries are showed
			loopNextClicker(article);
			
		}
		
		return article;
	}

	private void loopNextClicker(Article article) {
		Boolean exit = false;
		while (!exit) {
			try {
				WebElement element = driver.findElement(By.xpath(this.diary
						.getNextButton()));
				element.click();
				Thread.sleep(200); 	// Correct one unexpected behavior and
									// allow to process the URL to the
									// browser
				
				// Article Commentaries adder
				article = showedCommentsFiller(article);
				
			} catch (Exception e) {
				exit = true;
			}
		}
	}

	private Article showedCommentsFiller(Article article) {
		Iterator<WebElement> commentary = driver.findElements(
				By.xpath(this.diary.getCommentaryRegEx())).iterator();
		
		int nu = 0;

		while (commentary.hasNext()) {
			WebElement e = commentary.next();
			nu++;

			// Commentary
			String comment = e.findElement(
					By.xpath(this.diary.getCommentTextRegEx())).getText();

			// This condition add only non repeated comments
			if (this.analyzedComments.add(comment)) {
			
				// number
				
				int n = 0;
				try{
					n = Integer.parseInt(e.findElement(By.xpath(this.diary.getCommentNumberRegEx())).getText());
				}catch(Exception x){
					n = commentCounter;
					commentCounter ++;
				}
				
				// Author
				String commentaryAuthor = e.findElement(
						By.xpath(this.diary.getCommentAuthorRegEx())).getText();
				// Time
				
				String time = "";
				try{
					time = e.findElement(By.xpath(this.diary.getCommentTimeRegEx())).getText();  //NOMBRE
				}catch(Exception x){
					try{
					time = e
							.findElement(By.xpath(this.diary.getCommentTimeRegEx()))
							.getAttribute("datetime").toString();
					}catch(Exception y){
						time = "Fecha erronea";
					}
				}
				
				Commentary c = new Commentary(commentaryAuthor, time, n,
						comment);
				article.addCommentary(c);
			}
		}
		System.out.println(nu);
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
