/*
 *  ProxiMain.java
 *  
 *  This file is part of Proxi project.
 *  
 *  Victor Purcallas Marchesi <vpurcallas@gmail.com>
 *  
 *  This class represents the first Swing Interface
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

package proxi.view.swing;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashSet;

import proxi.controller.Controller;
import proxi.view.ViewInterface;

public class ProxyMain implements MouseListener, ActionListener, ViewInterface{

	//Visual elements
    private JFrame jfSdi;
    private JPanel jPanel, buttonPane, textPane;
    private JLabel jlInstructions;
    private JMenuBar jMenuBar;
    private JMenu jMenuMain, jMenuConfig;
    private JMenuItem jmiMainExit, jmiConfigWordList, jmiPaste;
    private JButton jbAnalyze, jbExit;
    private JFileChooser jFileChooser;
    private JTextArea ta;
    private JScrollPane scroll;
    private ImageIcon ico;
    
    //Non visual variables
    public HashSet<String> urls = null;
    public Controller c;
    public boolean cont = false;

    
    public ProxyMain(Controller c) {
    	this.c = c;
        this.initComponents();
        this.registerListeners();
    }

    @Override
    public void initComponents() {
    	ico = new ImageIcon(this.getClass().getResource("/img/proxi.png"));
//    	ImageIcon icon = new ImageIcon(this.getClass().getResource("/img/proxi_fondo.png"));
        this.jfSdi = new JFrame();
        Image ima = ico.getImage();
        this.jfSdi.setIconImage(ima);
        
        this.jfSdi.setTitle("Proxi 'Improving your research'");
        this.jfSdi.setSize(600, 500);
        this.jfSdi.setResizable(false);
        this.jfSdi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jfSdi.setLocation(dim.width/2-jfSdi.getSize().width/2, dim.height/2-jfSdi.getSize().height/2);
        
//        ImageIcon icon = new ImageIcon("/res/proxi_fondo.png");
//        ImageIcon icon = new ImageIcon(this.getClass().getResource("/res/proxi_fondo.png"));
//        this.jfSdi.setIconImage(icon.getImage());
//        
        //Add Nimbus Look and feel
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }
        
    
        //Add panel
        
        this.jPanel = new JPanel();
        this.jPanel.setLayout(new BoxLayout(this.jPanel, BoxLayout.PAGE_AXIS));
        this.jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        
        this.jFileChooser = new JFileChooser();
        this.jFileChooser.setCurrentDirectory(new File("."));
       
        //MENU BAR
        this.jMenuBar = new JMenuBar();
        this.jfSdi.setJMenuBar(this.jMenuBar);
        
        //Button 1
        this.jMenuMain = new JMenu("Principal");
        this.jMenuBar.add(this.jMenuMain);
        //1.1
        this.jmiMainExit = new JMenuItem("Salir");
        this.jMenuMain.add(this.jmiMainExit);
        
        //Button 2
        this.jMenuConfig = new JMenu("Configuración");
        this.jMenuBar.add(this.jMenuConfig);
        //2.1
        this.jmiConfigWordList = new JMenuItem("Palabras de control");
        this.jMenuConfig.add(this.jmiConfigWordList);
        
        
        //Add Instructions
        textPane = new JPanel();
        textPane.setLayout(new BorderLayout());
        jlInstructions = new JLabel("Introduce las urls que quieres buscar:");
        textPane.add(jlInstructions);
        
        //Add Text Area
        
        ta = new JTextArea(20, 45);
        ta.addMouseListener(this);
        scroll = new JScrollPane (ta, 
        		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        ta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        //Buttons
        this.jbAnalyze = new JButton("Analizar");
        this.jbAnalyze.addActionListener(this);
        
        this.jbExit = new JButton("Salir");
        this.jbExit.addActionListener(this);
        this.jbExit.setPreferredSize(this.jbAnalyze.getPreferredSize());
        
        //Button Pane
        this.buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(jbExit);      
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(jbAnalyze);

        
        this.jPanel.add(textPane);
        this.jPanel.add(scroll);
        this.jPanel.add(buttonPane);
        
 
        this.jfSdi.add(this.jPanel);
        this.jfSdi.setVisible(true);
    }

    private void registerListeners() {
        this.jmiMainExit.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent event) {
                jfSdi.dispose();
            }
        });
    }

	@Override
	public void mouseClicked(MouseEvent e) {	
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {	
	}

	@Override
	public void mouseExited(MouseEvent arg0) {	
	}

	@Override
	public void mousePressed(MouseEvent e) {	
		if (SwingUtilities.isRightMouseButton(e)){
		    JPopupMenu menu = new JPopupMenu();	
		    jmiPaste = new JMenuItem("paste");
		    jmiPaste.addActionListener(this);
		    menu.add(jmiPaste);
		    ta.setComponentPopupMenu(menu);
		} 
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.jmiPaste){
			this.ta.paste();
		}else if (event.getSource() == this.jbAnalyze){
			analyzeTheUrls();
			c.analyzedUrls = this.analyzeTheUrls();
			this.cont = true;
		}else if (event.getSource() == this.jbExit){
			System.exit(0);
		}
		
	}

	private HashSet<String> analyzeTheUrls() {
		HashSet<String> urls = new HashSet<String>();
		String text = this.ta.getText();
		text = text.replace("\n", " ").replace("\r", " ");
		String[] allText = text.split(" ");
		
		for(int i = 0 ; i < allText.length ; i++){
			if(allText[i].length()>10) urls.add(allText[i]);
		}
		return urls;
	}
	
	public void close(){
		this.jfSdi.dispose();
	}

	public boolean returnAttributes() {
		// TODO Auto-generated method stub
		return this.cont;
	}

}


