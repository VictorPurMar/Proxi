package proxi.view;

interface ViewConector {

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
