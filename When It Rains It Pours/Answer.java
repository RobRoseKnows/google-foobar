package com.google.challenges;

/**
 * @author Robert Rose
 * 
 */

public class Answer {

	public static int answer(int[] heights) {
		return recurseAndFillBuckets(heights, 0);
	}
	
	/**
	 * Called recursively to fill one level of hutch-tops with water at a
	 * time. When a hutch-top is filled with water, area is incremented.
	 * @param heights
	 * @param area
	 * @return
	 */
	public static int recurseAndFillBuckets(int[] heights, int area) {
		int newArea = area;
		int[] newHeights = heights.clone();
		for(int i = 1; i < heights.length - 1; i++) {  // No need to check the first and last for bucketness
			if(heights[i] < heights[i-1] && heights[i] < heights[i+1]) {
				newArea++;
				newHeights[i]++;
			}
		}
		
		if(newArea == area)
			return newArea;
		else
			return recurseAndFillBuckets(newHeights, newArea);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] testData = {1, 4, 2, 5, 1, 2, 3};
		System.out.println(answer(testData));
		int[] testData2 = {1, 2, 3, 2, 1};
		System.out.println(answer(testData2));
	}

}
