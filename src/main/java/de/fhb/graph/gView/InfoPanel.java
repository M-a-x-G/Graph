package de.fhb.graph.gView;

import de.fhb.graph.gModel.Edge;
import de.fhb.graph.gModel.Vertex;
import de.fhb.graph.utility.TextFieldDoc;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

public class InfoPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	
	private JTextField textField1;
    private JTextField textField2;
    private JLabel label1;
    private JLabel label2;

	public InfoPanel() {
		super();
        label1 = new JLabel("");
        label2 = new JLabel("");


        setLayout(new GridBagLayout());
    }

    public void vertexMarked(Vertex vertex){
        removeAll();
        textField1 = new JTextField();
        textField1.setText(vertex.getName());
        textField1.setEditable(false);

        textField2 = new JTextField();
        textField2.setEditable(false);
        textField2.setText(" x:" + Integer.toString(vertex.getLocation().x) + ", y:" + Integer.toString(vertex.getLocation().y));


        label1.setText("Node Name: ");
        label2.setText("Coordinates in space: ");

        add(label1);
        add(textField1);
        add(label2);
        add(textField2);

    }

    public void edgeMarked(Edge edge) {

        textField1 = new JTextField(new TextFieldDoc(((short)3), 10, edge), "", 1);
        textField1.setText(Integer.toString(edge.getWeight()));
        textField1.setEditable(true);
        textField1.setColumns(3);

        label1.setText("Weight of Edge: ");
        removeAll();

        add(label1);
        add(textField1);
        textField1.requestFocus();
    }

    public void unmarked(){
        removeAll();
    }

}
