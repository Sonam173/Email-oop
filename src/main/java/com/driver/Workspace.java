package com.driver;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class Workspace extends Gmail{

    private ArrayList<Meeting> calendar; // Stores all the meetings

    public Workspace(String emailId) {
        // The inboxCapacity is equal to the maximum value an integer can store.
        super(emailId, Integer.MAX_VALUE);
        this.calendar = new ArrayList<>();

    }

    public void addMeeting(Meeting meeting){
        //add the meeting to calendar
        for (Meeting m : calendar) {
            if (m.getStartTime().isBefore(meeting.getEndTime()) &&
                    m.getEndTime().isAfter(meeting.getStartTime())) {
                throw new IllegalArgumentException("Unable to add meeting: time slot already occupied.");
            }
        }
        calendar.add(meeting);
    }

   // }

    public int findMaxMeetings(){
        // find the maximum number of meetings you can attend
        // 1. At a particular time, you can be present in at most one meeting
        // 2. If you want to attend a meeting, you must join it at its start time and leave at end time.
        // Example: If a meeting ends at 10:00 am, you cannot attend another meeting starting at 10:00 am
        if (calendar.isEmpty()) {
            return 0;
        }

        Collections.sort(calendar, Comparator.comparing(Meeting::getEndTime));

        int maxMeetings = 1;
        Meeting lastMeeting = calendar.get(0);

        for (int i = 1; i < calendar.size(); i++) {
            Meeting currentMeeting = calendar.get(i);
            if (currentMeeting.getStartTime().isAfter(lastMeeting.getEndTime())) {
                maxMeetings++;
                lastMeeting = currentMeeting;
            }
        }

        return maxMeetings;

    }
}
