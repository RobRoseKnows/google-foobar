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
 * would allow me to iterate through the list in linear time. I will also be
 * able to add all the entries in linear time by using a pointer.
 * 
 * I also realized that the LinkedList won't initially be sorted due to the
 * nature of how the formulas work. Since it would be much easier to be able to
 * stop the loop once we pass where the function should be, I decided to set up
 * a sorting system to sort the list prior to iterating through. 
 * 
 * I chose to use Merge Sort as the sorting function because it guarantees 
 * O(nlogn) time. Unlike Merge Sort with arrays, Merge Sort with LinkedLists does
 * not require O(N) space (which would've been un desirable as it would've been
 * huge!
 */

public class Answer {   
	
	// The functional programming HashMap 
	private static HashMap<BigInteger, BigInteger> hash = new HashMap<BigInteger, BigInteger>();
	
	// The root of a LinkedList containing every R(n) up to n = 10^25
	private static Node root;

	
    public static String answer(String str_S) {
    	// If the LinkedList hasn't been made yet, make it.
    	if(root == null){
    		root = new Node(BigInteger.ZERO, BigInteger.ONE);
    		
    		// Use a pointer so that adding is O(1) and in a linear loop.
    		Node pointer = root; 
    		
        	long start = System.currentTimeMillis();
    		// Since str_S will be limited to numbers 10^25 and less, we can build only the numbers up to (10^24)/2 because R of it is 1.03 * 10^25
    		for(BigInteger i = BigInteger.ONE; i.compareTo(BigInteger.TEN.pow(24).divide(BigInteger.valueOf(2))) <= 0; i.add(BigInteger.ONE)) {
    			System.out.println("Call: " + i);
    			pointer.addNode(i, R(i));
    			pointer = pointer.next; // Move pointer forward
    			if(i.mod(BigInteger.TEN).equals(BigInteger.ZERO))
    				System.out.println(i + " at " + (System.currentTimeMillis() - start));
    		}
    		System.out.println("Done building LL");
    	}
    	
    	String answer = "None"; // Default to "None"
    	BigInteger searchFor = new BigInteger(str_S);
    	
    	Node on = root;
    	while(on.next != null) {
    		// Check to see if we're on the right one. 
    		if(searchFor.equals(on.val)) {
    			answer = "" + on.i;
    			break;
    		}
    		
    		on = on.next;
    	}
    	
    	
    	return answer;
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

    	/*
    	long start = System.currentTimeMillis();
    	BigInteger sol = R(BigInteger.TEN.pow(25));
    	long end = System.currentTimeMillis();
    	System.out.println(sol + " in " + (end-start) + "ms");
    	*/
    	System.out.println("100: " + answer("100"));
    	System.out.println("7: " + answer("7"));
    }
    
    public static class Node implements Comparable<Object> {
    	private BigInteger val;
    	private BigInteger i;
    	private Node next = null;
    	
    	/**
    	 * Constructor
    	 * @param index The X value for R(X)
    	 * @param value R(X)
    	 */
    	public Node(BigInteger index, BigInteger value) {
    		//System.out.println("i: " + index + " v: " + value);
    		val = value;
    		i = index;
    	}
    	
    	/**
    	 * This function adds a new Node to the current Node.
    	 * @param index The X value for R(X) 
    	 * @param value R(X)
    	 */
    	public void addNode(BigInteger index, BigInteger value) {
    		Node end = new Node(index, value);
    		this.next = end;
    	}

		/**
		 * All that's necessary is to call the compareTo method on the value
		 * since that's what we're comparing.
		 */
		public int compareTo(Object node) {
			return val.compareTo(((Node) node).val);
		}
    }
}