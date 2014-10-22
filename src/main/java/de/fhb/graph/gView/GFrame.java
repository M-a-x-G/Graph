package de.fhb.graph.gView;



import de.fhb.graph.gController.Menuleiste;
import de.fhb.graph.gModel.Graph;

import javax.swing.JFrame;



public class GFrame extends JFrame  {

	private static final long serialVersionUID = 1L;
	
	
	
	public GFrame()  {
		super("Graph");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Graph g = new Graph();
		View view = new View(g,this);
		
		getContentPane().add(view);
		setSize(800,600);
		setLocationRelativeTo(null);
		this.setJMenuBar(new Menuleiste(this, view,g));
//		pack();
	}
	

	public static void main(String[] args) {
		GFrame gf = new GFrame();
		gf.setVisible(true);
	}




}
