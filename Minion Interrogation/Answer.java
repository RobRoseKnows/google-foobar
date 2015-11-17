package com.google.challenges; 

public class Answer {   
    public static int[] answer(int[][] minions) { 

        Minion[] minis = new Minion[minions.length];
        for(int i = 0; i < minis.length; i++) {
        	minis[i] = new Minion(minions[i], i);
        }
        
        MergeSort.sort(minis);
        
        int[] toReturn = new int[minis.length];
        for(int i = 0; i < toReturn.length; i++) {
        	toReturn[i] = minis[i].number;
        }
        
        return toReturn;
    }
    
    /**
     * Inner class to make Minions easier to sort.
     */
    private static class Minion implements Comparable {
    	// Time it takes to question a minion.
    	private int time;
    	
    	// Probability of correctness
    	private double prob;
    	
    	// What's the Minion name?
    	private int number;
    	
    	// The time expected.
    	private double expected;
    	
    	public Minion(int[] arr, int o) {
    		number = o;
    		time = arr[0];
    		prob = (arr[1] * 1.0) / (arr[2] * 1.0);
    		expected = time / prob;
    	}
    	
    	@Override
    	public int compareTo(Object o) {
    		Minion other = (Minion) o;
    		
    		if(this.expected > other.expected) {
    			return 1;
    		} else if(this.expected < other.expected) {
    			return -1;
    		} else {
    			if(this.time > other.time)
    				return 1;
    			else if(this.time < other.time)
    				return -1;
    			else
    				return 0;
    		}
    	}
    }
    
    /**
     * Took a Princeton Coursera course so my implementation looks a lot like theirs.
     * (http://algs4.cs.princeton.edu/22mergesort/Merge.java.html)
     * I used merged sort because it is stable. Since the prompt wanted me to keep equal items in
     * lexiographical order, a stable sort was neccessary.
     */
    private static class MergeSort {
    	private static void merge(Comparable[] arr, Comparable[] aux, int lo, int mid, int hi) {
    		// Copy to the auxiliary array
    		for(int k = lo; k <= hi; k++) {
    			aux[k] = arr[k];
    		}
    		
    		// Merge back
    		int i = lo;
    		int j = mid+1;
    		for(int k = lo; k <= hi; k++) {
    			if(i > mid)			arr[k] = aux[j++];
    			else if (j > hi)	arr[k] = aux[i++];
    			else if (aux[j].compareTo(aux[i]) < 0)	arr[k] = aux[j++];
    			else				arr[k] = aux[i++];
    		}
    	}
    	
    	private static void sort(Comparable[] arr, Comparable[] aux, int lo, int hi) {
    		if(hi <= lo)
    			return;
    		int mid = lo + (hi - lo) / 2;
    		sort(arr, aux, lo, mid);
    		sort(arr, aux, mid + 1, hi);
    		merge(arr, aux, lo, mid, hi);
    	}
    	
    	public static void sort(Comparable[] arr) {
    		Comparable[] aux = new Comparable[arr.length];
    		sort(arr, aux, 0, arr.length - 1);
    	}
    }
}