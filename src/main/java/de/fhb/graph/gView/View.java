package de.fhb.graph.gView;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import de.fhb.graph.gController.ButtonPanel;
import de.fhb.graph.gController.ModePanel;
import de.fhb.graph.gModel.Graph;

import javax.swing.JPanel;


public class View extends JPanel  implements Observer{

	private static final long serialVersionUID = 1L;
	private ButtonPanel buttonPanel;
    private ModePanel actionPanel;
    private InfoPanel infoPanel;
    private GraphPanel graphPanel;
    private Graph graph;
	
	
	public View(Graph graph, GFrame frame) {
		this.graph = graph;
		graph.addObserver(this);
		setLayout(new BorderLayout());
		graphPanel = new GraphPanel(graph,this);
		this.add(graphPanel,BorderLayout.CENTER);
		buttonPanel = new ButtonPanel(frame, graphPanel, this, graph);
		this.add(buttonPanel, BorderLayout.NORTH);
		actionPanel = new ModePanel(this, graphPanel);
		this.add(actionPanel,BorderLayout.WEST);

		infoPanel = new InfoPanel();
        this.infoPanel.setPreferredSize(new Dimension(80, 80));
        this.add(infoPanel,BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
        revalidate();
		repaint();
		
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
		graphPanel.setGraph(graph);
		graph.addObserver(this);
		repaint();
	}

    public void deleteGraph(){
        graphPanel.deleteGraph();
    }

	public void clear(){
		graphPanel.delete();
	}

    public GraphPanel getGraphPanel() {
        return graphPanel;
    }

    public InfoPanel getInfoPanel() {
        return infoPanel;
    }
}
