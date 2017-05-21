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
	* @return Площадь или -1 если треугольник не существует
	*/
	public double area() {
		double area = -1;
		double ab = distance(this.a, this.b);
		double bc = distance(this.b, this.c);
		double ca = distance(this.c, this.a);
		if (this.isExist(ab, bc, ca)) {
			double semiperimeter = (ab + bc + ca) / 2;
			area = Math.sqrt(semiperimeter
						* (semiperimeter - ab)
						* (semiperimeter - bc)
						* (semiperimeter - ca));
		}
		return area;
	}
	/**
	* Является ли треугольник возможным.
	* @param ab сторона ab
	* @param bc сторона bc
	* @param ca сторона ca
	* @return true если существует
	*/
	private boolean isExist(double ab, double bc, double ca) {
		return ((ab + bc) > ca)
				&& ((ca + ab) > bc)
				&& ((bc + ca) > ab);
	}
	/**
	* Расстояние между вершинами.
	* @param a вершина a
	* @param b вершина b
	* @return расстояние
	*/
	private double distance(Point a, Point b) {
		double deltaX = a.getX() - b.getX();
		double deltaY = a.getY() - b.getY();
		return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
	}
}