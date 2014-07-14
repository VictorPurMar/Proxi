package View;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;



import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class ProxyMain implements MouseListener, ActionListener{


	public static void main(String[] args) {
		new ProxyMain();

	}
	
    private JFrame jfSdi;
    private JPanel jPanel;
    private JLabel jlImatge;
    private JMenuBar jMenuBar;
    private JMenu jMenuMain, jMenuConfig;
    private JMenuItem jmiMainObrir, jmiMainExit, jmiConfigWordList;
    private JFileChooser jFileChooser;
    JTextArea ta;

    public ProxyMain() {
        this.initComponents();
        this.registerListeners();
    }

    private void initComponents() {
        this.jfSdi = new JFrame();
        this.jfSdi.setTitle("Proxy Comment Analyzer");
        this.jfSdi.setBounds(200, 200, 400, 400);
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
        this.jPanel.add(ta);
        
        
        
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
		    JMenuItem pasteAction = new JMenuItem("paste");
		    pasteAction.addActionListener(this);
		    menu.add(pasteAction);
		    
//		    JTable table = new JTable();
//		    // set data model for the table...
		  
		    // sets the popup menu for the table
		    ta.setComponentPopupMenu(menu);
		    
//			Action copyAction = this.ta.getActionMap().get("copy");
//			Action cutAction = this.ta.getActionMap().get("cut");
//			Action pasteAction = this.ta.getActionMap().get("paste");
//			Action undoAction = this.ta.getActionMap().get("undo");
//			Action selectAllAction = this.ta.getActionMap().get("selectAll");
//			 
//			PopUpDemo.add (undoAction);
//			popup.addSeparator();
//			popup.add (cutAction);
//			 popup.add (copyAction);
//			 popup.add (pasteAction);
//			 popup.addSeperator();
//			 popup.add (selectAllAction);
//			return popup;
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
	public void actionPerformed(ActionEvent arg0) {
		this.ta.paste();
		
	}
}


