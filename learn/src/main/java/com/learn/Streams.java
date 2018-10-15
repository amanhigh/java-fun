package com.learn;

import java.util.Random;

/**
 * @author amanpreet.singh
 * @version 1.0.0
 * @since 2018-10-15 14:17.
 */
public class Streams {
    public static void main(String[] args) {
        new Random().ints(1, 100).
                filter((i) -> i > 5).limit(10).forEach(System.out::println);
    }
}
