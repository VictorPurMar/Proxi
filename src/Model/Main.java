package Model;

//import java.net.URL;
import java.util.Iterator;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;

import org.openqa.selenium.*;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.support.ui.ExpectedCondition;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Main {

	public static String analyzedUrl = "http://www.elmundo.es/ciencia/2014/07/10/53beb7d3ca4741f8298b4586.html";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		run();

	}

	public static void run() {
		moreComments();

		// try{
		// URL url = new URL(analyzedUrl);
		// Document doc = Jsoup.parse(url,15000);
		// System.out.println("load html");
		//
		//
		// System.out.println("Comments");
		// System.out.println();
		//
		// Iterator<Element> listaTitle = doc.select("article").iterator();
		//
		// while (listaTitle.hasNext()){
		//
		// Element e = listaTitle.next();
		// String comment =
		// e.select("div[class^=texto-comentario]").select("p").text();
		// System.out.println(comment);
		// }
		//
		// }catch(Exception e){
		// e.printStackTrace();
		// }
	}

	private static void moreComments() {
		Boolean exit = false;
		try {
//			URL url = new URL(analyzedUrl);
//			Document doc = Jsoup.parse(url, 15000);
//			System.out.println("load html");
//
//			System.out.println("Comments");
//			System.out.println();
			WebDriver driver = new FirefoxDriver();	
//			WebDriver driver = new HtmlUnitDriver();
			driver.get(analyzedUrl);

			// Repeat the show more click until all the commentaries are showed
//			System.out.println(driver.getPageSource());		
//			WebElement element = driver
//					.findElement(By
//							.xpath("//ul[@id='subNavComentarios']//li//a[@id='botonMas']"));
//			element.click();
//			
//			Iterator <WebElement> webs = driver.findElements(By.xpath("//div[@class='texto-comentario']/p")).iterator();
//			
//			while (webs.hasNext()) {
//				
//								WebElement e = webs.next();
//								String comment = e.getText();
//								System.out.println(comment);
//			}
			
			
			
			
			System.out.println();
			while (!exit) {
				try {
					WebElement element = driver
							.findElement(By
									.xpath("//ul[@id='subNavComentarios']//li//a[@id='botonMas']"));
					element.click();
				} catch (Exception e) {
					exit = true;
				}
			}

			Iterator<WebElement> listaTitle = driver.findElements(By.xpath("//div[@class='texto-comentario']/p[text()]")).iterator();

			while (listaTitle.hasNext()) {

				WebElement e = listaTitle.next();
				String comment = e.getText();
				System.out.println(comment);
			}
//
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
