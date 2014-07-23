package View;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import Controller.Controller;

public class ProxySecond implements ActionListener{

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
    
    //Non visual variables
    public ArrayList<String> urls = null;
    public Controller c;
    public boolean cont = false;

    
    public ProxySecond(Controller c) {
    	this.c = c;
        this.initComponents();
        this.registerListeners();
    }

    private void initComponents() {
        this.jfSdi = new JFrame();
        this.jfSdi.setTitle("Proxy Comment Analyzer");
        this.jfSdi.setSize(600, 200);
        this.jfSdi.setResizable(false);
        this.jfSdi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jfSdi.setLocation(dim.width/2-jfSdi.getSize().width/2, dim.height/2-jfSdi.getSize().height/2);
        
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
//        textPane.setLayout(new BorderLayout());
        jlInstructions = new JLabel("El programa esta realizando operaciones \r\n No Cierre la ventana de mozilla");
        textPane.add(jlInstructions);
           
        //Buttons
        
        this.jbExit = new JButton("Salir");
        this.jbExit.addActionListener(this);
        
        //Button Pane
        this.buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(jbExit);      

        
        this.jPanel.add(textPane);
        this.jPanel.add(buttonPane);
        
 
        
        //Add panel to principal frame
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


