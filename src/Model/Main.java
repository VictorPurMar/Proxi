package Model;



import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.jws.Oneway;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Main {
	
	public static String analyzedUrl = "http://www.elmundo.es/ciencia/2014/07/10/53beb7d3ca4741f8298b4586.html";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		run();

	}
	
	public static void run(){
		moreComments();
	
//	try{
//		URL url = new URL(analyzedUrl); 
//		Document doc = Jsoup.parse(url,15000);
//		System.out.println("load html");
//		
//		
//		System.out.println("Comments");
//		System.out.println();
//		
//		Iterator<Element> listaTitle = doc.select("article").iterator();
//		
//		while (listaTitle.hasNext()){
//			
//			Element e = listaTitle.next();		
//			String comment = e.select("div[class^=texto-comentario]").select("p").text();		
//			System.out.println(comment);
//		}
//		
//	}catch(Exception e){
//		e.printStackTrace();
//	}
}

	private static void moreComments() {
		try{
			URL url = new URL(analyzedUrl); 
			Document doc = Jsoup.parse(url,15000);
			System.out.println("load html");
			
			
			System.out.println("Comments");
			System.out.println();
			
			Iterator<Element> listaTitle = doc.select("ul[id^=subNavComentarios]>li>a[id^=botonMas]").iterator();
			
			while (listaTitle.hasNext()){
				
				Element e = listaTitle.next();
				String onClick = e.attr("onclick").toString();
				System.out.println(onClick);
//				Jsoup.connect(analyzedUrl).data(onClick);
//				Jsoup.connect(analyzedUrl).data()
				String more = e.toString();
				System.out.println(more);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
