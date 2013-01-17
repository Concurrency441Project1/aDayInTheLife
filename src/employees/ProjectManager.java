package employees;

import chance_tools.Chance;
import time_tools.TimeClock;
import time_tools.TimeConverter;

public class ProjectManager extends Employee {
	
	public volatile boolean leadMeetingTime = false;
	public volatile boolean tenMeetingTime = false;
	public volatile boolean twoMeetingTime = false;
	public volatile boolean question = false;

	public ProjectManager(TimeClock timeClock) {
		this.timeClock = timeClock;
	}
	
	public void isLeadMeetingTime() {
		leadMeetingTime = true;
	}
	
	public void haveLeadMeeting() {
		System.out.println(TimeConverter.convertTime(timeClock.currentTime()) 
				+ " All Dev Leads have arrived. Meeting Time.");
		timeClock.leadMeeting(15);
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
	
	@Override
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
	
	public void haveFinalStandup() {
		timeClock.arriveAtStandup();
		System.out.println(TimeConverter.convertTime(timeClock.currentTime()) 
				+ " The Project Manager is at the final standup.");
		timeClock.finalStandupMeeting();
		System.out.println(TimeConverter.convertTime(timeClock.currentTime()) 
				+ " The final standup of the day is over");
		finalStandup = false;
	}
	
	public void questionWaiting() {
		question = true;
	}
	
	public void answerQuestion() {
		System.out.println(TimeConverter.convertTime(timeClock.currentTime()) 
				+ " The Project Manager is answering a developer question.");
		timeClock.meeting(10);
		System.out.println(TimeConverter.convertTime(timeClock.currentTime()) 
				+ " The Project Manager is done answering questions.");
		question = false;
		timeClock.answerQuestion();
	}
	
	public void run() {
		arrive();
		while(timeClock.currentTime() < 1020) {
			timeClock.checkTime();
			
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
			
			if(finalStandup) {
				if(Chance.timeGamble(timeClock.currentTime(), 975, 15)) {
					haveFinalStandup();
				}
			}
			
			if(question) {
				answerQuestion();
			}
			
		}
		while(timeClock.currentEmployees() != 0) {}
		leave();
	}
	
}
