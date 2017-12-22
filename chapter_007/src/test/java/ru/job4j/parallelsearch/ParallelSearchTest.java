package ru.job4j.parallelsearch;

//import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * junior.
 *
 * @author Igor Kovalkov
 * @version 0.1
 * @since 09.12.2017
 */
public class ParallelSearchTest {
    /**
     * Test method.
     */
//    @Test
    public void whenSearchMD5StringThenSearhThatFile() {
        List<String> exts = new ArrayList<>();
        Collections.addAll(exts, "txt", "java");
        ParallelSearch search = new ParallelSearch(".\\",
                "e408e3646b9296204a328fbf56d86071",
                exts);

        String result = new File(search.result().get(0)).getName().split("\\.")[0];
        String expected = this.getClass().getSimpleName();
        assertThat(result, is(expected));
    }
}