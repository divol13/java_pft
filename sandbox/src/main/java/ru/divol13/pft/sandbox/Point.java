package ru.divol13.pft.sandbox;

public class Point {

    double x,y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double distance(Point p){
        return Math.sqrt((this.y - p.y) * (this.y - p.y) + (this.x - p.x) * (this.x - p.x));
    }

    public static void main(String[] args) {
        Point point1 = new Point(-5,5);
        Point point2 = new Point(10,6);

        double result = distance(point1, point2);
        System.out.println("result is: " + result);

        result = point1.distance(point2);
        System.out.println("result is: " + result);
    }

    public static double distance(Point p1, Point p2) {
        return Math.sqrt((p2.y - p1.y) * (p2.y - p1.y) + (p2.x - p1.x) * (p2.x - p1.x));
    }
}
