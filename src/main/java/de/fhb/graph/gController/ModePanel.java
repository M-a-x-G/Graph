package de.fhb.graph.gController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import de.fhb.graph.gView.GraphPanel;
import de.fhb.graph.gView.View;

public class ModePanel extends JPanel implements ActionListener, ItemListener {


    private static final long serialVersionUID = 1L;

    final static String NEWVERTEX = "+Knoten";
    final static String NEWEDGE = "+Kante";
    final static String MARK = "Markieren";

    View view;
    GraphPanel graphPanel;
    ButtonGroup buttonGroup;


    public ModePanel(View view, GraphPanel graphPanel) {
        super();
        this.view = view;
        this.graphPanel = graphPanel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new TitledBorder("Aktionen"));
        buttonGroup = new ButtonGroup();
        addRButton(MARK);
        addRButton(NEWVERTEX);
        addRButton(NEWEDGE);
        //select first radiobutton
        buttonGroup.setSelected(buttonGroup.getElements().nextElement().getModel(), true);

        JCheckBox weightMode = new JCheckBox("Gewichtet");
        weightMode.addItemListener(this);
        add(Box.createVerticalStrut(5));
        add(weightMode);
        add(Box.createVerticalStrut(2));
        weightMode.setSelected(true);

    }

    private void addRButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.addActionListener(this);
        add(Box.createVerticalStrut(5));
        buttonGroup.add(radioButton);
        add(radioButton);
        add(Box.createVerticalStrut(2));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(MARK)) {
            graphPanel.setMode(GraphPanel.MARK);
        }
        if (e.getActionCommand().equals(NEWVERTEX)) {
            graphPanel.setMode(GraphPanel.NEWVERTEX);
        }
        if (e.getActionCommand().equals(NEWEDGE)) {
            graphPanel.setMode(GraphPanel.NEWEDGE);
        }

    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        graphPanel.setWeightedGraph(event.getStateChange() == ItemEvent.SELECTED);
    }
}
