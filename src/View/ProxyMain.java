package View;

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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import Controller.Controller;

public class ProxyMain implements MouseListener, ActionListener{

    private JFrame jfSdi;
    private JPanel jPanel;
    private JLabel jlImatge;
    private JMenuBar jMenuBar;
    private JMenu jMenuMain, jMenuConfig;
    private JMenuItem jmiMainObrir, jmiMainExit, jmiConfigWordList, jmiPaste;
    private JButton jbAnalyze;
    private JFileChooser jFileChooser;
    private JTextArea ta;
    public ArrayList<String> urls = null;
    public Controller c;
    public boolean cont = false;

    public ProxyMain(Controller c) {
    	this.c = c;
        this.initComponents();
        this.registerListeners();
    }

    private void initComponents() {
        this.jfSdi = new JFrame();
        this.jfSdi.setTitle("Proxy Comment Analyzer");
        this.jfSdi.setSize(600, 400);
//        this.jfSdi.setBounds(200, 200, 400, 400);
        this.jfSdi.setResizable(true);
        this.jfSdi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jPanel = new JPanel();
        this.jlImatge = new JLabel();
        this.jPanel.add(this.jlImatge);
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
        
        
        //Add Text Area
        
        ta = new JTextArea(20, 50);
        ta.addMouseListener(this);
        JScrollPane scroll = new JScrollPane (ta, 
        		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        jPanel.add(scroll);
        
        
        //Button
        this.jbAnalyze = new JButton("Analiza");
        this.jbAnalyze.setAlignmentX(1);
        this.jbAnalyze.addActionListener(this);
        
        this.jPanel.add(this.jbAnalyze);
        
        
        
        //Add panel to principal frame
        this.jfSdi.add(this.jPanel);
        this.jfSdi.setVisible(true);
    }

    private void registerListeners() {
//        this.jmiObrir.addActionListener(new ActionListener() {
//
//            public void actionPerformed(final ActionEvent evt) {
//                final int r = jFileChooser.showOpenDialog(jfSdi);
//                if (r == JFileChooser.APPROVE_OPTION) {
//                    final String pathImatge = jFileChooser.getSelectedFile().getPath();
//                    jlImatge.setIcon(new ImageIcon(pathImatge));
//                }
//            }
//        });
        this.jmiMainExit.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent event) {
                jfSdi.dispose();
            }
        });
    }

	@Override
	public void mouseClicked(MouseEvent e) {	
		if (SwingUtilities.isRightMouseButton(e)){
		    JPopupMenu menu = new JPopupMenu();	
		    jmiPaste = new JMenuItem("paste");
		    jmiPaste.addActionListener(this);
		    menu.add(jmiPaste);
		    ta.setComponentPopupMenu(menu);
		} 
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {	
	}

	@Override
	public void mouseExited(MouseEvent arg0) {	
	}

	@Override
	public void mousePressed(MouseEvent arg0) {	
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
		}
		
	}

	private ArrayList<String> analyzeTheUrls() {
		ArrayList<String> urls = new ArrayList<String>();
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

	public boolean cont() {
		// TODO Auto-generated method stub
		return this.cont;
	}
}


