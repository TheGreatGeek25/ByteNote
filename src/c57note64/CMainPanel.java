package c57note64;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import c57note64.cnote.CNotePanel;

@SuppressWarnings("serial")
public class CMainPanel extends JPanel {
	
	public CNotePanel todoPanel;
	public CNotePanel doingPanel;
	public CNotePanel donePanel;
	public JScrollPane todoScrollPane;
	public JScrollPane doingScrollPane;
	public JScrollPane doneScrollPane;
	
	
	public CInfoPanel infoPanel;
	public CControlPanel controlPanel;
	
	public CMainPanel() {
		super(true);
		setName("Main Panel");
		
		setLayout( new BorderLayout() );
		
		setEnabled(true);
		setVisible(true);
		
		controlPanel = new CControlPanel();
		todoPanel = new CNotePanel();
		doingPanel = new CNotePanel();
		donePanel = new CNotePanel();
		infoPanel = new CInfoPanel();
		
		todoScrollPane = new  JScrollPane(todoPanel);
		todoScrollPane.setPreferredSize( new Dimension((int) (C57note64Main.c57main.getWidth()*CNotePanel.paneToWin), C57note64Main.c57main.getHeight()) );
		todoScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		doingScrollPane = new  JScrollPane(doingPanel);
		doingScrollPane.setPreferredSize( new Dimension((int) (C57note64Main.c57main.getWidth()*CNotePanel.paneToWin), C57note64Main.c57main.getHeight()) );
		doingScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		doneScrollPane = new  JScrollPane(donePanel);
		doneScrollPane.setPreferredSize( new Dimension((int) (C57note64Main.c57main.getWidth()*CNotePanel.paneToWin), C57note64Main.c57main.getHeight()) );
		doneScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		add(controlPanel, BorderLayout.PAGE_START);
		add(todoScrollPane, BorderLayout.LINE_START);
		add(doingScrollPane, BorderLayout.CENTER);
		add(doneScrollPane, BorderLayout.LINE_END);
		add(infoPanel, BorderLayout.PAGE_END);
		
	}
	
	public void c57run() {
		revalidate();
		repaint();
		todoScrollPane.setPreferredSize( new Dimension((int) (C57note64Main.c57main.getWidth()*CNotePanel.paneToWin), C57note64Main.c57main.getHeight()) );
		doingScrollPane.setPreferredSize( new Dimension((int) (C57note64Main.c57main.getWidth()*CNotePanel.paneToWin), C57note64Main.c57main.getHeight()) );
		doneScrollPane.setPreferredSize( new Dimension((int) (C57note64Main.c57main.getWidth()*CNotePanel.paneToWin), C57note64Main.c57main.getHeight()) );
		
		controlPanel.c57run();
		todoPanel.c57run();
		doingPanel.c57run();
		donePanel.c57run();
		infoPanel.c57run();
		
	}
	
	
}
