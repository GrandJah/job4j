package ru.job4j.max;

	/**
	* Class Max.
	* @author Igor Kovalkov
	* @since 21.05.2017
	* @version 1
	*/
public class Max {
	/**
	* Возрат максимального значения.
	* @param first первый аргумент
	* @param second второй аргумент
	* @return максимальное значение
	*/
	public int max(int first, int second) {
		return first > second ? first : second;
	}
}