package de.fhb.graph.gController;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JPanel;

import de.fhb.graph.gModel.Graph;
import de.fhb.graph.gView.GFrame;
import de.fhb.graph.gView.GraphPanel;
import de.fhb.graph.gView.View;

public class ButtonPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    final static String CLEAR = "Löschen";

    Graph graph;
    GraphPanel graphPanel;
    View view;
    File file;
    File directory;
    GFrame frame;
//	boolean changed;


    public ButtonPanel(GFrame frame, GraphPanel graphPanel, View view, Graph graph) {
        this.frame = frame;
        this.graphPanel = graphPanel;
        this.graph = graph;
        this.view = view;
        directory = null;

        this.setLayout(new FlowLayout(FlowLayout.LEFT));
//		addButton(CLEAR);

    }


//	private void addButton(String text){
//		JButton button = new JButton(text);
//		button.addActionListener(this);
//		add(Box.createVerticalStrut(5));
//		add(button);	
//		add(Box.createVerticalStrut(2));
//	}
//

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(CLEAR))
            graphPanel.delete();

    }
}
