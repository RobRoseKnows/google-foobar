/**
Zombit antidote
===============
Forget flu season. Zombie rabbits have broken loose and are terrorizing Silicon
Valley residents! Luckily, you've managed to steal a zombie antidote and set up
a makeshift rabbit rescue station. Anyone who catches a zombie rabbit can schedule
a meeting at your station to have it injected with the antidote, turning it back
from a zombit to a fluffy bunny. Unfortunately, you have a limited amount of time
each day, so you need to maximize these meetings. Every morning, you get a list
of requested injection meetings, and you have to decide which to attend fully.
If you go to an injection meeting, you will join it exactly at the start of the
meeting, and only leave exactly at the end.
<p>
Can you optimize your meeting schedule? The world needs your help!
Write a method called answer(meetings) which, given a list of meeting requests,
returns the maximum number of non-overlapping meetings that can be scheduled. If
the start time of one meeting is the same as the end time of another, they are
not considered overlapping.
<p>
Meetings will be a list of lists. Each element of the meetings list will be a two
element list denoting a meeting request. The first element of that list will be
the start time and the second element will be the end time of that meeting request.
All the start and end times will be non-negative integers, no larger than 1000000.
The start time of a meeting will always be less than the end time.
The number of meetings will be at least 1 and will be no larger than 100.
The list of meetings will not necessarily be ordered in any particular fashion.
*/


package com.google.challenges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Answer {
    public static int answer(int[][] meetings) {
        List<Appointment> objAL = new ArrayList<Appointment>();
        for(int i = 0; i < meetings.length; i++) {
            objAL.add(new Appointment(meetings[i]));
        }

        Appointment[] obj = objAL.toArray(new Appointment[objAL.size()]);

        Arrays.sort(obj);

        int n = 1;
        Appointment last = obj[0];
        for(int i = 1; i < obj.length; i++) {
            if(obj[i].start >= last.end) {
                n++;
                last = obj[i];
            }
        }

        return n;
    }

    private static class Appointment implements Comparable {
        int start;
        int end;

        public Appointment(int[] arr) {
            start = arr[0];
            end = arr[1];
        }

        public int compareTo(Object o) {
            Appointment apt = (Appointment) o;
            return end - apt.end;
        }
    }
}
