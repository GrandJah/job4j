package ru.job4j.pretty_interface;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 09.03.2018
 */
public class ControllerAJAXTest {
    /**
     * Mock request.
     */
    private HttpServletRequest request = mock(HttpServletRequest.class);

    /**
     * Mock response.
     */
    private HttpServletResponse response = mock(HttpServletResponse.class);

    /**
     * Test method.
     * @throws IOException  IOException
     */
    @Test
    public void doGet() throws IOException {
        new ControllerAJAX().doGet(this.request, this.response);
        verify(this.response).sendRedirect(anyString());
    }

    /**
     * Test method.
     * @throws IOException  IOException
     */
    @Test
    public void doPost() throws IOException {
        String reqString = "";
        BufferedReader reader = mock(BufferedReader.class);
        when(reader.readLine()).thenReturn(reqString);
        when(this.request.getReader()).thenReturn(reader);
        final String[] respString = {""};
        PrintWriter writer = mock(PrintWriter.class);
        when(this.response.getWriter()).thenReturn(writer);
        doAnswer(i -> respString[0] = i.getArgumentAt(0, String.class)).when(writer).write(anyString());
        new ControllerAJAX().doPost(this.request, this.response);
        assertEquals("{}", respString[0]);
    }
}