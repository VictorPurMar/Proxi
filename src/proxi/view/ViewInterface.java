/*
 *  ViewInterface.java
 *  
 *  This file is part of Proxi project.
 *  
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *  
 *  This class represents the Interface of all the Views
 *  that grants that all they uses some required by controller methods 
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
 */package proxi.view;

interface ViewInterface {

	/**
	 * Start the interface
	 */
	void initComponents();
	
	/**
	 * Close the interface
	 */
	public void close();
	
	/**
	 * Shows if all the required by controller attributes
	 * are correctly saved 
	 * @return boolean 
	 */
	public boolean returnAttributes();
	
	
}
