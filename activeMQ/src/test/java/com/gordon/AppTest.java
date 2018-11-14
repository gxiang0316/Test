package com.gordon;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = "2018-08-19 12:30:45";
        Date parse = format.parse(date);
        System.out.println("parse : " + parse.toString());
        String newDate = format.format(new Date());
        System.out.println("newDate : " + newDate);
    }
}
