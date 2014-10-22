package de.fhb.graph.gController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.fhb.graph.gView.GraphPanel;
import de.fhb.graph.gView.View;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class ModePanel extends JPanel implements ActionListener {


	private static final long serialVersionUID = 1L;
	
	final static String NEWVERTEX ="+Knoten";
	final static String NEWEDGE = "+Kante";
	final static String MARK = "Markieren";
	
	View view;
	GraphPanel graphPanel;
	
	
	public ModePanel(View view, GraphPanel graphPanel) {
		super();
		this.view = view;
		this.graphPanel = graphPanel;
		
		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		setBorder(new TitledBorder("Aktionen"));
		addButton(MARK);
		addButton(NEWVERTEX);
		addButton(NEWEDGE);

	}
	
	
	private void addButton(String text){
		JButton button = new JButton(text);
		button.addActionListener(this);
		add(Box.createVerticalStrut(5));
		add(button);	
		add(Box.createVerticalStrut(2));
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(MARK))
			graphPanel.setMode(GraphPanel.MARK);
		if (e.getActionCommand().equals(NEWVERTEX))
			graphPanel.setMode(GraphPanel.NEWVERTEX);
		if (e.getActionCommand().equals(NEWEDGE))
			graphPanel.setMode(GraphPanel.NEWEDGE);

	}



}
