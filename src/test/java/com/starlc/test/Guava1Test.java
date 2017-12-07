package com.starlc.test;

import org.junit.Test;

import com.google.common.base.Optional;
import com.starlc.guava.GuavaTest;

public class Guava1Test {
	
	@Test
	private void testGuava() {
		GuavaTest guavaTester = new GuavaTest();
        
        Integer invalidInput = null;
        Optional<Integer> a = Optional.of(invalidInput);
        Optional<Integer> b = Optional.of(new Integer(10));
        System.out.println(guavaTester.sum(a, b));

	}
}
