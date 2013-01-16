package employees;

import time_tools.TimeClock;
import time_tools.TimeConverter;

public class ProjectManager extends Employee {
	
	public volatile boolean leadMeetingTime = false;
	public volatile boolean tenMeetingTime = false;
	public volatile boolean lunchTime = false;
	public volatile boolean twoMeetingTime = false;

	public ProjectManager(TimeClock timeClock) {
		this.timeClock = timeClock;
	}
	
	public void isLeadMeetingTime() {
		leadMeetingTime = true;
	}
	
	public void haveLeadMeeting() {
		System.out.println(TimeConverter.convertTime(timeClock.currentTime()) 
				+ " All Dev Leads have arrived. Meeting Time.");
		timeClock.meeting(15);
		System.out.println(TimeConverter.convertTime(timeClock.currentTime()) 
				+ " Dev Lead Meeting has Ended.");
		leadMeetingTime = false;
	}
	
	public void isTenMeetingTime() {
		tenMeetingTime = true;
	}
	
	public void haveTenMeeting() {
		System.out.println(TimeConverter.convertTime(timeClock.currentTime()) 
				+ " The Project Manager is off to his 10:00am meeting.");
		timeClock.meetingWithEnd(660);
		System.out.println(TimeConverter.convertTime(timeClock.currentTime()) 
				+ " Project Manager's 10am meeting is over.");
		tenMeetingTime = false;
	}
	
	public void isLunchTime() {
		lunchTime = true;
	}
	
	public void haveLunch() {
		System.out.println(TimeConverter.convertTime(timeClock.currentTime()) 
				+ " The Project Manager is headed to lunch for an hour.");
		timeClock.meeting(60);
		System.out.println(TimeConverter.convertTime(timeClock.currentTime()) 
				+ " The Project Manager is back from lunch.");
		lunchTime = false;
	}
	
	public void isTwoMeetingTime() {
		twoMeetingTime = true;
	}
	
	public void haveTwoMeeting() {
		System.out.println(TimeConverter.convertTime(timeClock.currentTime()) 
				+ " The Project Manager is off to his 2:00pm meeting.");
		timeClock.meetingWithEnd(900);
		System.out.println(TimeConverter.convertTime(timeClock.currentTime()) 
				+ " Project Manager's 2pm meeting is over.");
		twoMeetingTime = false;
	}
	
	public void run() {
		arrive();
		while(timeClock.currentTime() < 1020) {
			
			if(leadMeetingTime) {
				haveLeadMeeting();
			}
			
			if(tenMeetingTime) {
				haveTenMeeting();
			}
			
			if(lunchTime) {
				haveLunch();
			}
			
			if(twoMeetingTime) {
				haveTwoMeeting();
			}
			
		}
		leave();
	}
	
}
