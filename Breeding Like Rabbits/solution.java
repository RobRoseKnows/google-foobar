package com.google.challenges;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * The first thing I did was to write a method that would do the recursive calls
 * in order to calculate the number of rabbits. I was unsure if I would need it or
 * not but I figured it was a good place to start.
 * 
 * I figure I could make this work by making an extremely large array full of all
 * the possible numbers. I have a feeling this is not what the question wanted and
 * the resulting program would take up a lot of memory and time so I'd like to
 * find another way to get it working.
 *
 * I decided to start plugging in numbers into an Excel spreadsheet to see if 
 * I could find a pattern or a formula. That didn't work.
 * 
 * I decided to try to do recursion augmented by HashTables in order to speed
 * up calculations. This is functional programming technique. By keeping all the
 * values you get, the R() method can check to see if a recursive calculation has
 * already been done and if it has, it can save a considerable amount of
 * computational time.
 * 
 * Results of using functional programming on runtime of R():
 * Number     | w/o FP |  w/ FP
 * 100,000    | 20ms   | 0ms
 * 1,000,000  | 162ms  | 1ms
 * 10,000,000 | 1495ms | 1ms
 * I was also able to run the function with 2^63 in 2ms. And 10^25 in 3ms.
 * 
 * After seeing how fast it can run using functional programming I decided
 * that I should just search for the answer through all possible answers.
 * I then realized that I didn't have to build the entire set of numbers! I just
 * needed to build the ones that I needed and I can do that by implementing
 * a modified binary sort.
 */

public class Answer {   
	
	// The functional programming HashMap 
	private static HashMap<BigInteger, BigInteger> hash = new HashMap<BigInteger, BigInteger>();

	
    public static String answer(String str_S) {
    	BigInteger searchFor = new BigInteger(str_S);
    	BigInteger location = binarySearch(searchFor, BigInteger.ZERO, BigInteger.TEN.pow(25));
    	if(location.equals(BigInteger.valueOf(-1))) {
    		return "None";
    	} else {
    		return location.toString();
    	}
    } 
    
    public static BigInteger binarySearch(BigInteger searchFor, BigInteger min, BigInteger max) {    	
    	BigInteger distance = max.subtract(min);
    	BigInteger checkSpot = min.add(distance.divide(BigInteger.valueOf(2)));
    	
    	BigInteger rabbits = R(checkSpot);
    	
    	if(searchFor.equals(rabbits)) { // rabbits == number of rabbits we want
    		return checkSpot;
    	} else if(distance.equals(BigInteger.ONE)){ // There is nowhere left to search
    		return BigInteger.valueOf(-1);
    	} else if(rabbits.compareTo(searchFor) > 0) { // too many rabbits (r > sF)
    		return binarySearch(searchFor, min, checkSpot);
    	} else if(rabbits.compareTo(searchFor) < 0) { // too few rabbits (r < sF)
    		return binarySearch(searchFor, checkSpot, max);
    	}
    	
    	return BigInteger.valueOf(-1);
    }
    
    /**
     * This takes a time and computes the number of rabbits at that time.
     * Augmented by the power of HashTables.
     * @param x A time to check
     * @return Number of rabbits alive at x time.
     */
    public static BigInteger R(BigInteger x) {
    	BigInteger n = x.divide(BigInteger.valueOf(2));
    	//System.out.println(x.toString());
    	
    	if(hash.containsKey(x))
    		return hash.get(x);
    	
    	if(x.equals(BigInteger.ZERO)) {
    		// R(0) = 1
    		return BigInteger.ONE;
    	} else if(x.equals(BigInteger.ONE)) {
    		// R(1) = 1
    		return BigInteger.ONE;
    	} else if(x.equals(BigInteger.valueOf(2))) {
    		// R(2) = 2
    		return BigInteger.valueOf(2);
    	} else if(x.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
    		// R(2n) = R(n) + R(n + 1) + n
    		
    		// Check first element.
    		BigInteger rn;
    		if(hash.containsKey(n)) {
    			rn = hash.get(n);
    		} else {
    			rn = R(n);
    			hash.put(n, rn);
    		}
    		
    		BigInteger rnp1;
    		BigInteger np1 = n.add(BigInteger.ONE);
    		if(hash.containsKey(np1)) {
    			rnp1 = hash.get(np1);
    		} else {
    			rnp1 = R(np1);
    			hash.put(np1, rnp1);
    		}
    		
    		return rn.add(rnp1).add(n);
    	} else {
    		// R(2n + 1) = R(n - 1) + 1
    		
    		BigInteger rn;
    		if(hash.containsKey(n)) {
    			rn = hash.get(n);
    		} else {
    			rn = R(n);
    			hash.put(n, rn);
    		}
    		
    		BigInteger rnm1;
    		BigInteger nm1 = n.subtract(BigInteger.ONE);
    		if(hash.containsKey(nm1)) {
    			rnm1 = hash.get(nm1);
    		} else {
    			rnm1 = R(nm1);
    			hash.put(nm1, rnm1);
    		}
    		
    		return rn.add(rnm1).add(BigInteger.ONE);
    	}
    }
    
    /*
    public static void main(String args[]) {

    	System.out.println("100: " + answer("100"));
    	System.out.println("7: " + answer("7"));
    }*/
}