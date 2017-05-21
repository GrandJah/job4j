package ru.job4j.condition;

	/**
	* Class Point.
	* @author Igor Kovalkov
	* @since 21.05.2017
	* @version 1
	*/
public class Point {
	/** Координаты. */
	private int x, y;
	/**
	* Точка с координатами x , y.
	* @param x координата x
	* @param y координата y
	*/
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	* Возрат координаты x.
	* @return координата x.
	*/
	public int getX() {
		return this.x;
	}
	/**
	* Возрат координаты y.
	* @return координата y.
	*/
	public int getY() {
		return this.y;
	}
	/**
	* Является ли точка частью графика функции.
	* y(x) = a * x + b
	* @param a первый аргумент
	* @param b второй аргумент
	* @return максимальное значение
	*/
	public boolean is(int a, int b) {
		return this.y == this.x * a + b;
	}
}