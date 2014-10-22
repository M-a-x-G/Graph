package de.fhb.graph.gView;

import de.fhb.graph.gModel.Edge;
import de.fhb.graph.gModel.Vertex;
import de.fhb.graph.gModel.Graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import de.fhb.graph.linearAlgebra.Vector;

public class GraphPanel extends JPanel implements  MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	
	Graph graph;


	View view;

	private Vertex dragged;
	private Vertex markedVertex;
	private Edge markedEdge;
	private Point movingTo; 
	private int mode;
	
	
	
	public static final int MARK = 0;
	public static final int NEWVERTEX = 1;
	public static final int NEWEDGE = 2;
		
	public GraphPanel(Graph graph,View view) {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		this.graph = graph;
		this.view = view;
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		init();
	}
	
	
	public void setMode(int mode) {
		this.mode = mode;
	}


	private void init(){
		markedVertex = null;
		markedEdge = null;
		dragged = null;		
	}
	
	public void setGraph(Graph graph) {
		this.graph = graph;
		init();
	}


	

	protected void paintComponent(Graphics g){		
		for (Vertex v : graph.getVertices())
			paintVertex(v,g);
		for (Edge e : graph.getEdges())
			paintEdge(e,g);		
		if (dragged != null && movingTo != null)
			g.drawLine(dragged.getLocation().x, dragged.getLocation().y, movingTo.x, movingTo.y);
	}
	
	private void paintVertex(Vertex v, Graphics g){
		int radius = Config.VERTEXRADIUS;
		Color color = g.getColor();
		int x = v.getLocation().x - radius;
		int y = v.getLocation().y - radius;
		Color newColor = (v.isMarked() ? Config.ACTIVEVERTEXCOLOUR : Config.VERTEXCOLOUR);
		if (v.getColour() > 0) newColor = Config.COLOURS[v.getColour()%Config.COLOURS.length];
		g.setColor(newColor);
		g.fillOval(x, y, 2*radius, 2*radius);	
		g.setColor(Color.BLACK);		
		g.drawString(v.getName(), x, y + 3*radius );
		g.setColor(color);
	}
	
	private void paintEdge(Edge e, Graphics g){
		Color color = g.getColor();
		Color newColor = (e.isMarker() ? Config.ACTIVEVERTEXCOLOUR : Config.EDGECOLOUR);
		g.setColor(newColor);
		Point from = e.getfrom().getLocation();
		Point to = e.getTo().getLocation();
		g.drawLine(from.x,from.y,to.x,to.y);
		g.setColor(color);
	}
	
	public void delete(){
		if (markedVertex != null) graph.removeVertex(markedVertex);
		if (markedEdge != null) graph.removeEdge(markedEdge);
	}
	

	
	private void addEdge(Vertex from, Vertex to){
		graph.addEdge(from, to);		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		Point mousePoint = e.getPoint();
		if (markedVertex != null) {
			markedVertex.setMarked(false);
			markedVertex = null;}
		if (markedEdge != null) {
			markedEdge.setMarker(false);
			markedEdge = null;}
		markedVertex = null;
		markedEdge = null;
		if (mode == GraphPanel.MARK){
			Vertex v = mouseMeetsVertex(mousePoint);
			markedVertex = v;
			if (v != null) v.setMarked(true);
			else {
				Edge edge = mouseMeetsEdge(mousePoint);
				markedEdge = edge;
				if (edge != null) edge.setMarker(true);
			}
		}
		if (mode == GraphPanel.NEWVERTEX){
			graph.addVertex(mousePoint);
		}
		view.repaint();
	}
	
	


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		dragged = mouseMeetsVertex(e.getPoint());
	}
	
	private Vertex mouseMeetsVertex(Point point){
		for (Vertex v : graph.getVertices())
			if (isInside(v, point))  return v;
		return  null;
	}
	
	private Edge mouseMeetsEdge(Point point){
		for (Edge e : graph.getEdges())
			if (meets(e,point)) return e;
		return null;
	}
	
	private boolean isInside(Vertex v, Point p){
		return (p.distance(v.getLocation()) < Config.VERTEXRADIUS);
	}

	private boolean meets(Edge e, Point p){
		Point q = e.getfrom().getLocation();
		Point r = e.getTo().getLocation();
		Vector v = new Vector(q,p);
		Vector w = new Vector(q,r);
		return (v.scalarproduct(w) > 0 && Vector.dist(p, q, r) < Config.EPSILON);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Vertex to = this.mouseMeetsVertex(e.getPoint());
		if (mode == GraphPanel.NEWEDGE && dragged != null && to != null){
			addEdge(dragged, to);
		}
		dragged = null;		
		movingTo = null;
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		Graphics g = this.getGraphics();
		if (dragged == null) return;
		if (mode == GraphPanel.MARK) dragged.setLocation(e.getPoint());
		if (mode == GraphPanel.NEWEDGE){
			view.repaint();
			g.drawLine(dragged.getLocation().x, dragged.getLocation().y, e.getX(), e.getY());
			movingTo = e.getPoint();
			
		}
	}


	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}




}
