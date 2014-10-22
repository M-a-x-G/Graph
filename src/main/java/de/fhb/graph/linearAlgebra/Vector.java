package de.fhb.graph.linearAlgebra;

import java.awt.Point;

public class Vector {

    double x;
    double y;

    public Vector(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    public Vector(Point from, Point to) {
        super();
        this.x = to.x - from.x;
        this.y = to.y - from.y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public double scalarproduct(Vector v) {
        return this.x * v.x + this.y * v.y;
    }

    public double determinant(Vector v) {
        return this.x * v.y - this.y * v.x;
    }

    public double length() {
        return Math.sqrt(scalarproduct(this));
    }

    /**
     * @param p a Point
     * @param q a Point
     * @param r a point
     * @return the distance from Point p to the line qr
     */
    public static double dist(Point p, Point q, Point r) {
        Vector v = new Vector(q, p);
        Vector w = new Vector(q, r);
        return Math.abs((v.determinant(w)) / w.length());
    }


}
