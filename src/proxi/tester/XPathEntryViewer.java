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

package proxi.tester;

import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 * 
 * @author VictorPurMar <vpurcallas@gmail.com>
 * 
 */



public class XPathEntryViewer {
	// Web driver of Selenium
	// FirefoxDriver in that case
	private static WebDriver driver;
	
	private static boolean GET_COMMENTS = true;
	private static boolean GET_NEXT_PAGE = false;
	private static String URL = "http://www.20minutos.es/noticia/2455694/0/accidente-avion/sevilla-airbus/proyecto-militar-europa/";
//	private static String URL = "http://elpais.com/Comentarios/comentarios.html?ghi=1428780469-2e0e29f14460e98d3d83a63d8fa55c5b&gww=460&gcss=/estilos/v1.x/v1.4/eskup_widget_comentarios_noticia.css&gjs=1&ghw=1";
	//private static String CONTAINER_XPATH = "//div[@class='comentario']";
	private static String CONTAINER_XPATH = "//div[@class='fyre-comment-wrapper']";
	//private static String ELEMENT_XPATH = "p[contains(@id,'contenidomensaje_')]";
	private static String ELEMENT_XPATH = "section//div[@class='fyre-comment']/p";
	//private static String NEXT_PAGE = "//a[@class='flecha'][contains(@title,' siguiente')]";
	private static String NEXT_PAGE = "//div[@class='fyre-stream-more-container']";
	
	
	public static void main(String[] args) {
//		Controller c = getInstance();
//		c.run();
		
		XPathEntryViewer xv = new XPathEntryViewer();
		xv.runGetComments();
		
	}

	// Constructor

	public XPathEntryViewer() {
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
	public void runGetComments() {

		// Open or reuse the browser
		if (driver == null) {
			driver = new FirefoxDriver();
			//driver = new HtmlUnitDriver();
//			driver.manage().window().setPosition(new Point(-2000, -2000));
//			driver.manage().window().setSize(new Dimension(100, 100));
		}
		
		driver.get(URL);
	
		if (GET_NEXT_PAGE)getNextPage();
		if (GET_COMMENTS)getComments();	
		
		
//		driver.quit();

	}
	
	private void getNextPage(){
		WebElement tmpElement = null;
		
		try{
			tmpElement= driver.findElement(By.xpath(NEXT_PAGE));
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", tmpElement);
		}catch (Exception e){
			
		}
		
		if (tmpElement != null){
			getNextPage();
		}
//		JavascriptExecutor js = (JavascriptExecutor) driver; 
////		js.executeScript("document.getElementsByClassName('post-tag')[0].click();");
//		
//		WebElement elem = driver.findElement(By.xpath("//body"));
//		String jsC = "arguments[0].style.height='auto'; arguments[0].style.visibility='visible';";
//		js.executeScript(jsC, elem);
//		
//		
//		WebElement element = driver.findElement(By.xpath(NEXT_PAGE));
//		element.click();
//		
		
	}

	private void getComments() {
		//		// Article common data inflater
				JavascriptExecutor js = (JavascriptExecutor) driver; 
				//String container = (String) js.executeScript("return arguments[0].innerHTML", element);
				
		
				Iterator<WebElement> comments = driver.findElements(
						By.xpath(CONTAINER_XPATH)).iterator();
				
				int c = 0;
				
				while (comments.hasNext()) {
					WebElement comment = comments.next();
		
		//			// Commentary
					WebElement commentText = comment.findElement(
							By.xpath(ELEMENT_XPATH));
					
					String commentTextSt = (String) js.executeScript("return arguments[0].textContent", commentText);
					
					System.out.println(c + " :" + commentTextSt);
					c ++;
				}
	}
	
	//OLD EL PAIS
	//	Diary elPais = new Diary("El Pais", "elpais.com",
	//			"//div[@class='encabezado estirar']/a[@class='conversacion']",
	//			null, null,
	//			"//h1[@id='titulo_noticia']",
	//			"//div[@id='subtitulo_noticia']/h2",
	//			"//span[@class='firma']/span[@class='autor']/a",
	//			"//span[@class='firma']/a[@class='actualizado']",
	//			"//div[@class='mensajes']/div", "", // Provisional
	//			"div/div/div//p", "div//span[@class='fecha']", 
	//			"div//div[@class='contenedorcolumnas']/div[@class='comentario']/p");

	public void close() {
		driver.close();
	}
	
	public static void closeDriver(){
		driver.close();
	}
	


	
}
