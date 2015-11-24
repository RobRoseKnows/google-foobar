package com.google.challenges; 

/**
 * I tried to solve this multiple ways. I figured I'd be doing
 * something recursively so I started from there. I had tried
 * something with queues that didn't work so I ended up looking
 * up the basic statistics. That's when I remembered binomial
 * coefficients. From there I did the rest recursively.
 */

public class Answer {   
    public static String answer(int[] seq) { 

    	long combos = 0;
    	Answer outer = new Answer();
    	Node tree = outer.new Node(seq[0]);
    	for(int i = 1; i < seq.length; i++) {
    		tree.addRabbit(seq[i]);
    	}
    	
    	combos = permutations(tree);
    	
    	return "" + combos;
    }
    
    /*
     * Recursively checks to get the number of permutations in the 
     * tree.
     */
    public static long permutations(Node node) {
    	int lessSize = 0;
    	long lessPerm = 1;
    	
    	int greaterSize = 0;
    	long greaterPerm = 1;
    	
    	if(node.less != null) {
    		lessSize = treeSize(node.less);
    		lessPerm = permutations(node.less);
    	}
    	
    	if(node.greater != null) {
    		greaterSize = treeSize(node.greater);
    		greaterPerm = permutations(node.greater);
    	}
    	
    	long nCk = binoCoef(lessSize + greaterSize, greaterSize);
    	
    	// Number of ways you can order subtree nodes X number of permutations under both subtrees
    	return nCk * lessPerm * greaterPerm;
    }
    
    /* Clever way of doing n choose k in linear time. 
     * Could be even faster if I did it iteratively.
     * Found math on stack overflow trying to figure 
     * out if there was something in the Math library
     * to do it. (There's not)
     */
    public static long binoCoef(int n, int k) {
    	if(k == 0)
    		return 1;
    	else
    		return binoCoef(n, k - 1) * (n - (k - 1)) / k;
    }
    
    // This recursively checks the size of the tree.
    public static int treeSize(Node node) {
    	int sizeLess = 0;
    	int sizeGreater = 0;
    	
    	if(node.less != null)
    		sizeLess = treeSize(node.less);
    	if(node.greater != null)
    		sizeGreater = treeSize(node.greater);
    	
    	return 1 + sizeLess + sizeGreater;
    }
    
    /* For testing:*/
     public static void main(String[] args) {
    	 int[] seq = {1};
    	 System.out.println(answer(seq));
    	 int[] seq2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    	 System.out.println(answer(seq2));
    	 System.out.println(binoCoef(50, 9));
    }
    
    private class Node {
    	// The less than leaf of the tree.
    	private Node less;
    	
    	// The greater than leaf of the tree.
    	private Node greater;
    	
    	// The age of the rabbit (serves as its ID)
    	private int age;
    	
    	public Node(int val) {
    		
    		this.age = val;
    		
    	}
    	
    	/***
    	 * Adds any incoming rabbits to the list.
    	 * @param val The age of the rabbit.
    	 */
    	public void addRabbit(int val) {
    		
    		if(val > this.age) {
    			
    			if(greater != null)
    				greater.addRabbit(val);
    			else
    				greater = new Node(val);
    		
    		}
    		
    		if(val <= this.age) {
    		
    			if(less != null)
    				less.addRabbit(val);
    			else
    				less = new Node(val);
    		
    		}
    		
    		// Omitting check for equality as ages will be distinct
    		
    	}
    }
}