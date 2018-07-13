package com.morse.httpframe;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test(){
        List<String> strs=new ArrayList<>();

        initStrs(strs);
    }

    private void initStrs(List<String> strs) {
        strs.add("1");
        strs.add("2");
    }
}