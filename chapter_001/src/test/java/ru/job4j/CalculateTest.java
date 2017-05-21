package ru.job4j;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

	/**
	* Test
	*
	* @author Igor Kovalkov
	* @since 21.05.2017
	* @version 1
	*/
public class CalculateTest{
	
	/**
	* Test main
	*/
	@Test
	public  void whenMainNullThenHelloWorld(){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Calculate.main(null);
		assertThat(out.toString(), is(String.format(
				"Hello World%s",System.getProperty("line.separator"))));
	}
}