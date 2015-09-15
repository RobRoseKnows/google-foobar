/**
  I lost the prompt for this one. Basically you were given a 'document' and a set
  of keywords. You had to return the shortest string snippet with all the given
  keywords.
*/
package com.google.challenges;
import java.util.*;

public class Answer {

    public static String answer(String document, String[] searchTerms) {
        String[] docArray = document.split(" ");
        List<String> stList = Arrays.asList(searchTerms);
        ArrayList<String> shortestSnippet = new ArrayList<>();
        int lenShortShippet = -1;

        for(int i = 0; i < docArray.length; i++) {
            if(stList.contains(docArray[i])) {
                ArrayList<String> workingSnippet = new ArrayList<>();

                HashMap<String, Integer> keywordCount = new HashMap<>(); // Create a default hashmap to make everything easier.
                for(String val : searchTerms)
                    keywordCount.put(val, 0);

                int k = i;
                while(k < docArray.length && keywordCount.containsValue(0)) {
                    workingSnippet.add(docArray[k]);
                    if(stList.contains(docArray[k])) {
                        keywordCount.put(docArray[k], 1);
                    }
                    k++;
                }

                if(!keywordCount.containsValue(0)) { //Kind of a little hack to get around needing a loop
                    if(lenShortShippet == -1 || workingSnippet.size()<lenShortShippet) {
                        lenShortShippet = workingSnippet.size();
                        shortestSnippet = workingSnippet;
                    }
                }
            }
        }

        String shortAndSnipp = "";

        //Put them back together
        for(String s : shortestSnippet) {
            shortAndSnipp += s;
            shortAndSnipp += " ";
        }

        shortAndSnipp = shortAndSnipp.trim();
        return shortAndSnipp;
    }

}
