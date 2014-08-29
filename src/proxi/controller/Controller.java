/*
 *  Controller.java
 *  
 *  This file is part of Proxi project.
 *  
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *  
 *  This class acts as a Controller of the Proxi project	
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

package proxi.controller;

import java.util.HashSet;
import java.util.List;

import proxi.model.*;
import proxi.model.objects.*;
import proxi.view.*;
import proxi.view.swing.ProxyMain;
import proxi.view.swing.ProxySecond;

public class Controller {

	private DiariesController dC;
	private static Controller instance;
	public HashSet<String> analyzedUrls;
	private static final int WAIT_FOR_ACTION = 400;

	
	// Constructor
	
	public Controller() {
		this.dC = new DiariesController();
	}
	
	// Singleton pattern
	
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}
	
	// Public Methods
	
	public static void main(String[] args) {
		Controller c = getInstance();
		c.run();
		
	}
	
	//private methods
	
	private void run(){
		// Obtain the diaries list
		this.dC.getTheDiaries();
		
		// INTERFACE
		
		// Shows the Main Interface
		ProxyMain pm = new ProxyMain(this);
		// wait response from MainInterface
		// the response fills this.analyzedUrls attribute
		waitInterfaceResponse(pm);
		// Shows the Second Interface
		ProxySecond ps = new ProxySecond(this);
		
					
		//Fill the diaries
		this.dC.diaryUrlInflater(this.analyzedUrls);

		// To develope
		// c.showError();

		//Article Inflater
		this.dC.diaryArticleInflater();
		
		//Csv writter
	    csvWritter();
	    
	    //Close the interface
		ps.close();
		
	}

	private void waitInterfaceResponse(ProxyMain pm) {
		// The interface will wait to response
		while (!pm.returnAttributes()) {
			try {
				Thread.sleep(WAIT_FOR_ACTION);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pm.close();
	}

	private void csvWritter() {
		List<Article> articles = this.dC.getArticles();
		for (int i = 0; i < articles.size(); i++) {
			System.out.println(articles.get(i));
			ProxiCSVWriter.makeTheCSV(articles.get(i));
		}
	}
}
