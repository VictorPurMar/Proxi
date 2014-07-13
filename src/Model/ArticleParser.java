package Model;

import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ArticleParser {

	private String analyzedUrl;
	
	public Article run(String url) {
		Article ar = null ;
		this.analyzedUrl = url;
		// Filter the parser depending the url
		if (analyzedUrl.contains("www.elmundo.es")) {
			ar = mundoParser();
		} else if (analyzedUrl.contains("elpais.com")) {
			ar = paisParser();
		} else if (analyzedUrl.contains("www.20minutos.es")) {
			ar = minutosParser();
		}
		return ar;
	}

	// To develope
	private Article minutosParser() {
		System.out.println("20 Minutos");
		return null;

	}

	// To develope
	private Article paisParser() {
		System.out.println("El Pais");
		return null;
	}

	// To develope
	private Article mundoParser() {
		Boolean exit = false;
		Article article = null;
		try {
			WebDriver driver = new FirefoxDriver();
			driver.get(analyzedUrl);

			// Repeat the show more click until all the commentaries are showed
			System.out.println();
			while (!exit) {
				try {
					WebElement element = driver
							.findElement(By
									.xpath("//ul[@id='subNavComentarios']//li//a[@id='botonMas']"));
					element.click();
					Thread.sleep(1);	//Correct one unexpected behavior and allow to process the URL to the browser
				} catch (Exception e) {
					exit = true;
				}
			}
			
			//Article
			
			String title = driver.findElement(By.xpath("//h1[@itemprop='headline']")).getText();
			String subtitle = driver.findElement(By.xpath("//p[@class='antetitulo']")).getText();
//			String author = driver.findElement(By.xpath("//footer/address//span[@itemprop='name']/a")).getText();
			String author = driver.findElement(By.xpath("//footer/address//span[@itemprop='name']")).getText();
			String date = driver.findElement(By.xpath("//footer/time")).getAttribute("datetime");
			String url = analyzedUrl;
			String[] cleanDiary = analyzedUrl.split("/");
			String diary = cleanDiary[2];
			
			article = new Article(title, subtitle, author, date, url, diary);
			
			
		
			
			//Article Commentaries
			
			Iterator<WebElement> commentary = driver.findElements(
					By.xpath("//div[@id='listado_comentarios']/section/article"))
					.iterator();
			
			while (commentary.hasNext()) {
				WebElement e = commentary.next();
				
				//number
				int n = Integer.parseInt(e.findElement(By.xpath("header/h1/a[text()]")).getText());
				//Author
				String commentaryAuthor = e.findElement(By.xpath("header/div[@class='autor']/span[text()]")).getText();
				//Time
				String time = e.findElement(By.xpath("header/time")).getAttribute("datetime").toString();
				//Commentary
				String comment = e.findElement(By.xpath("div[@class='texto-comentario']/p[text()]")).getText();
				
				Commentary c = new Commentary(commentaryAuthor, time, n, comment);
				article.addCommentary(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return article;
	}

}
