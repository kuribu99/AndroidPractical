package com.jamesooi.geometry;

import java.io.Serializable;

/**
 * Created by James Ooi on 12/6/2016.
 */
public class Line implements Serializable {
    private Point p1;
    private Point p2;

    public Line() {
        p1 = new Point();
        p2 = new Point();
    }

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public Point getMidPoint() {
        double x = (p1.getX() + p2.getX()) / 2;
        double y = (p1.getY() + p2.getY()) / 2;
        return new Point(x, y);
    }

    @Override
    public String toString() {
        return "P1 " + p1.toString() + ", P2 " + p2.toString();
    }
}
