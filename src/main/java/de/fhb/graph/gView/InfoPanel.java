package de.fhb.graph.gView;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class InfoPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	
	private JTextField textField;

	public InfoPanel() {
		super();
		textField = new JTextField("");
		textField.setEditable(false);
		setLayout(new FlowLayout());
		setBorder(new TitledBorder("Info Panel"));
        add(textField, FlowLayout.LEFT);

    }
	
	public void sendInfo(String text){
		textField.setText(text);
	}
	
	public void clear(){
		textField.setText("");
	}

}
