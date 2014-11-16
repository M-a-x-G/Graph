package de.fhb.graph.gView;

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.fhb.graph.gModel.Edge;
import de.fhb.graph.gModel.Vertex;
import de.fhb.graph.utility.TextFieldDoc;

public class InfoPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public InfoPanel() {
        super();
        setLayout(new GridBagLayout());
    }

    public void vertexMarked(Vertex vertex) {
        removeAll();

        JTextField vertexNameField = new JTextField();
        vertexNameField.setText(vertex.getName());
        vertexNameField.setEditable(false);

        JTextField positionField = new JTextField();
        positionField.setEditable(false);
        positionField.setText(" x:" + Integer.toString(vertex.getLocation().x) + ", y:" + Integer.toString(vertex.getLocation().y));

        JLabel vertexNameLabel = new JLabel("Vertex name: ");
        JLabel positionLabel = new JLabel("Coordinates in space: ");

        add(vertexNameLabel);
        add(vertexNameField);
        add(positionLabel);
        add(positionField);

    }

    public void edgeMarked(Edge edge) {
        removeAll();
        JTextField edgeWeightField = new JTextField(new TextFieldDoc(((short) 3), edge), "", 1);
        edgeWeightField.setText(Integer.toString(edge.getWeight()));
        edgeWeightField.setEditable(true);
        edgeWeightField.setColumns(3);

        JLabel edgeWeightLabel = new JLabel("Weight of Edge: ");

        add(edgeWeightLabel);
        add(edgeWeightField);
        edgeWeightField.requestFocus();
    }

    public void unmarked() {
        removeAll();
    }
}
