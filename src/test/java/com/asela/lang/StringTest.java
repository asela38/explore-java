package com.asela.lang;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class StringTest {

    @Test
    public void inconsistencyIObserved() throws Exception {

        assertTrue("".split(",").length == 1);
        assertTrue(",".split(",").length == 0);
        assertTrue(",,".split(",").length == 0);
    }

    @Test
    public void split_shouldReturnArrayAlways() {

        // if split regex doesn't match anything in the content it will return
        // single item array
        assertArrayEquals(new String[] { "aaa" }, "aaa".split(","));
        assertArrayEquals(new String[] { "" }, "".split(","));
        assertArrayEquals(new String[] { " " }, " ".split(","));

        assertArrayEquals(new String[] { "aaa", "bbb" }, "aaa,bbb".split(","));
        assertArrayEquals(new String[] { " ", " " }, " , ".split(","));

        // Split will ignore empty content between Split regex
        assertArrayEquals(new String[] {}, ",".split(","));
        assertArrayEquals(new String[] { "aaa", "bbb" }, "aaa,bbb,,".split(","));

    }

    @Test
    public void expectedConsistency() throws Exception {
        assertTrue(Arrays.stream("".split(",")).filter(StringUtils::isNotBlank).toArray(String[]::new).length == 0);
        assertTrue(Arrays.stream(",".split(",")).filter(StringUtils::isNotBlank).toArray(String[]::new).length == 0);
        assertTrue(Arrays.stream(",,".split(",")).filter(StringUtils::isNotBlank).toArray(String[]::new).length == 0);

    }

    @Test
    public void regexLengthCheck() throws Exception {
        assertTrue("123456789012".matches("[0-9]{10,}"));
        assertFalse("123456789".matches("[0-9]{10,}"));
    }
    
    @Test
    public void format() throws Exception {
        // %b
        assertThat(String.format("%b", null) , is("false"));
        assertThat(String.format("%b", Boolean.TRUE) , is("true"));
        assertThat(String.format("%b", "") , is("true"));
        
        // %h
        assertThat(String.format("%h", "a"), is("61"));
        assertThat(String.format("%h", "aa"), is(Integer.toHexString("aa".hashCode())));
        assertThat(String.format("%h", "aka"), is(Integer.toHexString("aka".hashCode())));

        // %c
        assertThat(String.format("%c", 'a'), is("a"));
        assertThat(String.format("%c", 'a' + 1), is("b"));
        assertThat(String.format("%C", 'a'), is("A"));
        
        // %d %o %x
        assertThat(String.format("%d", 100), is("100"));
        assertThat(String.format("%o", 100), is("144"));
        assertThat(String.format("%x", 100), is("64"));
        
        // %e %f %g %a
        assertThat(String.format("%e", 1f/3), is("3.333333e-01"));
        assertThat(String.format("%f", 1f/3), is("0.333333"));
        assertThat(String.format("%g", 1f/3), is("0.333333"));
        assertThat(String.format("%a", 1f/3), is("0x1.555556p-2"));
        
        // %t
        LocalDateTime date = LocalDateTime.of(2010, 01, 22, 13, 10, 56, 21212);
        assertThat(String.format("%tH", date), is("13"));
        assertThat(String.format("%tI", date), is("01"));
        assertThat(String.format("%tp", date), is("pm"));
        
        assertThat(String.format("%tB", date), is("January"));
        assertThat(String.format("%tb", date), is("Jan"));
        
        assertThat(String.format("%tR", date), is("13:10"));
        assertThat(String.format("%tF", date), is("2010-01-22"));
        
        
        assertThat(String.format("%tA", date), is("Friday"));
        assertThat(String.format("%ta", date), is("Fri"));
        
        //   %[argument_index$][flags][width][.precision]conversion
        
        assertThat(String.format("%10tA", date), is("    Friday"));
        assertThat(String.format("%-10tA", date), is("Friday    "));
        
        assertThat(String.format("%5d", 10), is("   10"));
        assertThat(String.format("%-5d", 10), is("10   "));
        assertThat(String.format("%#o", 010), is("010"));
        assertThat(String.format("%o", 010), is("10"));
        
        assertThat(String.format("%#X", 010), is("0X8"));
        assertThat(String.format("%X", 010), is("8"));
        
        assertThat(String.format("%+d", 10), is("+10"));
        assertThat(String.format("%+d", -10), is("-10"));
        
        assertThat(String.format("% d", 10), is(" 10"));
        assertThat(String.format("% d", -10), is("-10"));
        
        assertThat(String.format("%010d", 10),  is("0000000010"));
        assertThat(String.format("%010d", -10), is("-000000010"));
        
        
        assertThat(String.format("%(d", 10),  is("10"));
        assertThat(String.format("%(d", -10), is("(10)"));
        
        
    }
}
