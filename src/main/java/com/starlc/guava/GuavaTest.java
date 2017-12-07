/******************************************************************************
 * Copyright (C) 2014 xx
 *****************************************************************************/

package com.starlc.guava;

import com.google.common.base.Optional;

/**
 * xx
 *
 * @author xx
 * @since jdk1.6
 * @version 2017年12月1日 xx
 */
public class GuavaTest {
    
    /**
     * xx
     * 
     * @param args xx
     */
    public static void main(String[] args) {
        GuavaTest guavaTester = new GuavaTest();
        
        Integer invalidInput = null;
        Optional<Integer> a = Optional.of(invalidInput);
        Optional<Integer> b = Optional.of(new Integer(10));
        System.out.println(guavaTester.sum(a, b));
    }
    
    /**
     * xx
     * 
     * @param a a
     * @param b b
     * @return xx
     */
    public Integer sum(Optional<Integer> a, Optional<Integer> b) {
        return a.get() + b.get();
    }
}
