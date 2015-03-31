/*
 *  DiariesController.java
 *  
 *  This file is part of Proxi project.
 *  
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *  
 *  This class acts as a Diary Controller of the Proxi project
 *  This class make the necessary Diaries (Diary) from the getDiaries method
 *  Also add the urls of each specific diary, that where introduced by user
 *  using the View (User Graphic Interface)
 *  		
 *
 *  Proxi project is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  Finally calls the ArticleInflater method to inflate the Diaries with the 
 *  Articles and Comments.
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import proxi.model.objects.Article;
import proxi.model.objects.Diary;

public class DiariesController {

	public Set<String> analyzedUrls = null;
	private List<Diary> diaries;
	private List<Article> articles;

	/**
	 * Constructor class
	 */
	public DiariesController() {
		this.articles = new ArrayList<Article>();
		this.diaries = new ArrayList<Diary>();
	}

	/**
	 * Add the String urls to the this.diaries list of Diary
	 * 
	 * @param analyzedUrls
	 *            Set<String>
	 * @return this.diaries
	 */
	public List<Diary> diaryUrlInflater(HashSet<String> analyzedUrls) {
		this.analyzedUrls = analyzedUrls;
		Iterator<String> iterator = null;
		for (int i = 0; i < this.diaries.size(); i++) {
			Diary diary = this.diaries.get(i);
			iterator = this.analyzedUrls.iterator();
			while (iterator.hasNext()) {
				String url = iterator.next();
				if (url.contains(diary.getDiaryBasicUrl())) {
					diary.addUrl(url);
				}
			}
		}
		return this.diaries;

	}

	/**
	 * Inflate the diaries with articles
	 */
	public void diaryArticleInflater() {

		ArticleInflater ap = new ArticleInflater();
		for (int i = 0; i < this.diaries.size(); i++) {
			Diary diary = this.diaries.get(i);
			List<String> urlsList = diary.getUrls();
			for (int j = 0; j < urlsList.size(); j++) {
				Article article = ap.run(urlsList.get(j), diary);
				articles.add(article);
			}
		}

		ap.close();
	}

	/**
	 * Make the diaries from data stored
	 */
	public void getTheDiaries() {
		// Future DiaryParser class for permanent Storage
		// /Demo///

		// Diary d1 = new Diary(diaryName, diaryBasicUrl, displayType,
		// commentsPage, showComments, nextButton, titleRegEx, subtitleRegEx,
		// authorRegEx, dateRegEx, diaryNameRegEx, commentaryRegEx,
		// commentNumberRegEx, commentAuthorRegEx, commentTimeRegEx,
		// commentTextRegEx);

		Diary elMundo = new Diary("El Mundo", "www.elmundo.es", null, null,
				"//ul[@id='subNavComentarios']//li//a[@id='botonMas']",
				"//h1[@itemprop='headline']", "//p[@class='antetitulo']",
				"//footer/address//span[@itemprop='name']", "//footer/time",
				"//div[@id='listado_comentarios']/section/article",
				"header/h1/a[text()]",
				"header/div[@class='autor']/span[text()]", "header/time",
				"div[@class='texto-comentario']/p[text()]");

		// Diary d = new Diary(diaryName, diaryBasicUrl, commentsPage,
		// showComments, nextButton, titleRegEx, subtitleRegEx, authorRegEx,
		// dateRegEx, commentaryRegEx, commentNumberRegEx, commentAuthorRegEx,
		// commentTimeRegEx, commentTextRegEx)

		//OLD EL PAIS
//		Diary elPais = new Diary("El Pais", "elpais.com",
//				"//div[@class='encabezado estirar']/a[@class='conversacion']",
//				null, null,
//				"//h1[@id='titulo_noticia']",
//				"//div[@id='subtitulo_noticia']/h2",
//				"//span[@class='firma']/span[@class='autor']/a",
//				"//span[@class='firma']/a[@class='actualizado']",
//				"//div[@class='mensajes']/div", "", // Provisional
//				"div/div/div//p", "div//span[@class='fecha']", 
//				"div//div[@class='contenedorcolumnas']/div[@class='comentario']/p");
		
		Diary elPais = new Diary(
				"El Pais", 
				"elpais.com",
				"//div[@class='contenido']/iframe",
				null, 
				"//a[@class='flecha' @title='Página siguiente']",
				"//h1[@id='titulo_noticia']",
				"//div[@id='subtitulo_noticia']/h2",
				"//span[@class='firma']/span[@class='autor']/a",
				"//span[@class='firma']/a[@class='actualizado']",
				"//div[@id='contenedor_mensajes']//div[@class='comentario']", 
				"", // Provisional
				"p[@class='autor']",
				"span[@class='fecha']/a", 
				"p[contains(@id,'contenidomensaje')]");
		
		Diary veinteMinutos = new Diary(
				"20 Minutos",
				"20minutos.es",
				null,
				null,
				"//li[@class='item nextPage']/a",
				"//div[@id='content']/div[@class='article inner']/h1",
				"//div[@id='content']/div[@class='article inner']/div[@class='lead']/ul/li[1]",
				"//div[@id='content']/div[@class='article inner']/ul[@class='article-author']/li",
				"//div[@id='content']/div[@class='article inner']/ul[@class='article-author']/li/a",
				"//div[@id='listado_kment']/div[@id='comentarios']/div[@id]/div[@class='article-comment' or @class='article-comment featured']",
				"div[@class='comment-header' or @class='comment-header featured']/span/div[@class='comments ordinal']/span",
				"div[@class='comment-header' or @class='comment-header featured']/div[@class='user']//span[@class='vlink']",
				"div[@class='comment-header' or @class='comment-header featured']/div[@class='user']//span[@class='user-data']",
				"div[@class='comment-text']/p");
		

		this.diaries.add(elMundo);
		this.diaries.add(elPais);
		this.diaries.add(veinteMinutos);
	}

	// Getters and Setters

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public List<Diary> getDiaries() {
		return diaries;
	}

	public void setDiaries(List<Diary> diaries) {
		this.diaries = diaries;
	}

}
