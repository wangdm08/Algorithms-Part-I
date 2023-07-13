package hello;/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        double wordsCount = 0;
        String champion = "";
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            wordsCount++;
            double probability = 1 / wordsCount;
            if (StdRandom.bernoulli(probability)) {
                champion = word;
            }
        }
        System.out.println(champion);
    }
}
