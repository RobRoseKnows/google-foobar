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
 * I could find a pattern or a formula. 
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
 * that checking all the answers wasn't such a bad idea after all. I was going to
 * need a better way to access it however, with the number of integers I am
 * working with collisions are a possibility in a HashMap.
 * 
 * I chose to implement a Linked List to store all the solved values as it
 * would allow me to iterate through the list in linear time.
 */

public class Answer {   
	
	
	private static HashMap<BigInteger, BigInteger> hash = new HashMap<BigInteger, BigInteger>();
	
    public static String answer(String str_S) { 
    	BigInteger solution = BigInteger.valueOf(-1);
        
    	
    	
    	return solution.toString();
    } 
    
    public static BigInteger R(BigInteger x) {
    	BigInteger n = x.divide(BigInteger.valueOf(2));
    	//System.out.println(x.toString());
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
    
    public static void main(String args[]) {
    	for(int i = 0; i < 1000; i++) {
    		System.out.println(R(BigInteger.valueOf(i)));
    	}
    	
    	long start = System.currentTimeMillis();
    	BigInteger sol = R(BigInteger.TEN.pow(25));
    	long end = System.currentTimeMillis();
    	System.out.println(sol + " in " + (end-start) + "ms");
    }
    
    class Node {
    	BigInteger val;
    	BigInteger n;
    	Node next = null;
    	
    	public Node(BigInteger number, BigInteger value) {
    		val = value;
    		n = number;
    	}
    	
    	void addNodeToTail(BigInteger number, BigInteger value) {
    		Node end = new Node(number, value);
    		Node node = this;
    		while(node.next != null)
    			node = node.next;
    		node.next = end;
    	}
    }
}