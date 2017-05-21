package ru.job4j.condition;

/**
 * Class Triangle.
 * @author Igor Kovalkov
 * @since 21.05.2017
 * @version 1
 */
public class Triangle {
    /** Вершины. */
    private Point a, b, c;
    /**
     * Треугольник с вершинами a, b, c.
     * @param a вершина a
     * @param b вершина b
     * @param c вершина c
     */
    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    /** Площадь треугольника.
     * @return Площадь или 0 если треугольник не существует
     */
    public double area() {
        Point vectorAB = new Point(this.b.getX() - this.a.getX(), this.b.getY() - this.a.getY());
        Point vectorAC = new Point(this.c.getX() - this.a.getX(), this.c.getY() - this.a.getY());
        return  Math.abs(vectorAB.getX() * vectorAC.getY() - vectorAB.getY() * vectorAC.getX()) / 2;
    }
}