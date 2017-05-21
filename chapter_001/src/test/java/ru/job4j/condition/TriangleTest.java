package ru.job4j.condition;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

	/**
	* Test.
	*
	* @author Igor Kovalkov
	* @since 21.05.2017
	* @version 1
	*/
public class TriangleTest {
	/**
	* Test area.
	*/
	@Test
	public  void whenNoExistTriangleThenZero() {
		assertThat(areaTest(0, 0, 1, 1, 2, 2), closeTo(0, 0.01));
	}
	/**
	* Test area.
	*/
	@Test
	public void whenExistTriangleThenAreaSix() {
		assertThat(areaTest(0, 1, 3, 1, 2, 5), closeTo(6, 0.01));

	}
        /**
         * Test area.
         */
        @Test
        public void whenExistTriangleAgainThenAreaNine() {
            assertThat(areaTest(5, 1, 3, 6, 7, 5), closeTo(9, 0.01));

        }
	/**
	* Вспомогательный тест метод.
	* @param ax сторона ax
	* @param ay сторона ay
	* @param bx сторона bx
	* @param by сторона by
	* @param cx сторона cx
	* @param cy сторона cy
	* @return area сторона area
	*/
	private double areaTest(int ax, int ay,
							int bx, int by,
							int cx, int cy) {
	Triangle triangle = new Triangle(new Point(ax, ay),
									new Point(bx, by),
									new Point(cx, cy));
	return triangle.area();
	}

}