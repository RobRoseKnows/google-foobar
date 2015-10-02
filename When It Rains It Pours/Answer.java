package com.google.challenges;

/**
 * @author Robert Rose
 */

public class Answer {

	public static int answer(int[] heights) {
		return recurseAndFillBuckets(heights, 0);
	}
	
	/**
	 * Called recursively to fill one level of hutch-tops with water at a
	 * time. When a hutch-top is filled with water, area is incremented.
	 * @param heights The different heights of hutches.
	 * @param area Cumulative area going in
	 * @return The cumulative area coming out
	 */
	public static int recurseAndFillBuckets(int[] heights, int area) {
		int newArea = area;
		int[] newHeights = heights.clone();
		for(int i = 1; i < heights.length - 1; i++) {  // No need to check the first and last for bucketness
			if(!checkOpenOnLeft(heights, i) && !checkOpenOnRight(heights, i)) {
				newArea++;
				newHeights[i]++;
			}
		}
		
		if(newArea == area)
			return newArea;
		else
			return recurseAndFillBuckets(newHeights, newArea);
	}
	
	/**
	 * Checks to the left of the current hutch to see if it will run off
	 * the side.
	 * @param heights The array of hutch heights
	 * @param checkFrom The current hutch
	 * @return True if water will run off, false otherwise.
	 */
	public static boolean checkOpenOnLeft(int[] heights, int checkFrom) {
		boolean status = true;
		int checkFromHeight = heights[checkFrom];
		for(int i = 0; i < checkFrom && status; i++) { // The && status gives some added efficency
			status &= heights[i] <= checkFromHeight;
		}
		return status;
	}
	
	/**
	 * Checks to the right of the current hutch to see if it will run off
	 * the side.
	 * @param heights The array of hutch heights
	 * @param checkFrom The current hutch
	 * @return True if water will run off, false otherwise.
	 */
	public static boolean checkOpenOnRight(int[] heights, int checkFrom) {
		boolean status = true;
		int checkFromHeight = heights[checkFrom];
		for(int i = checkFrom + 1; i < heights.length && status; i++) { // The && status adds some efficency.
			status &= heights[i] <= checkFromHeight;
		}
		return status;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] testData = {1, 4, 2, 5, 1, 2, 3};
		System.out.println(answer(testData));
		int[] testData2 = {1, 2, 3, 2, 1};
		System.out.println(answer(testData2));
	}

}
