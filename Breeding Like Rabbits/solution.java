package com.google.challenges;

import java.math.BigInteger;

public class Answer {   
    public static String answer(String str_S) { 
    	BigInteger solution = BigInteger.valueOf(-1);
        
    	
    	
    	return solution.toString();
    } 
    
    public static BigInteger R(BigInteger x) {
    	BigInteger n = x.divide(BigInteger.valueOf(2));
    	System.out.println(x.toString());
    	if(x.equals(BigInteger.ZERO)) 
    		// R(0) = 1
    		return BigInteger.ONE;
    	else if(x.equals(BigInteger.ONE)) 
    		// R(1) = 1
    		return BigInteger.ONE;
    	else if(x.equals(BigInteger.valueOf(2))) 
    		// R(2) = 2
    		return BigInteger.valueOf(2);
    	else if(x.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
    		// R(2n) = R(n) + R(n + 1) + n
    		return R(n).add(R(n.add(BigInteger.ONE))).add(n);
    	else
    		// R(2n + 1) = R(n - 1) + 1
    		return R(n).add(R(n.subtract(BigInteger.ONE)).add(BigInteger.ONE));
    }
    
    public static void main(String args[]) {
    	System.out.println(R(BigInteger.TEN));
    }
}