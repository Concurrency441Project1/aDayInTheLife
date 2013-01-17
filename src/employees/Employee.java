package employees;

import time_tools.TimeClock;
import time_tools.TimeConverter;

public abstract class Employee extends Thread {
	
	protected int team;
	protected int id;
	protected TimeClock timeClock = null;
	
	protected volatile boolean atWork = false;
	protected int startTime;
	protected int startLunch;
	protected int endLunch;
	
	protected boolean lunchTime = false;
	protected boolean isEating = false;
	
	protected boolean finalStandup = false;
	protected boolean isAtStandup = false;
	
	protected boolean waitingQuestion = false;
	
	public void arrive() {
		startTime = timeClock.currentTime();
		atWork = true;
		String time = TimeConverter.convertTime(startTime);
		if(team == 0) {
			System.out.println(time + " The Project Manager has arrived. Welcome!!!");
		} else {
			System.out.println(time + " Developer" + team + id + " has arrived. Good Morning!");
			if(id == 1) {
				timeClock.leadArrives();
			} else {
				timeClock.employeeArrives();
			}
		}
	}
	
	public void isLunchTime() {
		lunchTime = true;
	}
	
	public void haveLunch() {
		startLunch = timeClock.currentTime();
		isEating = true;
		System.out.println(TimeConverter.convertTime(startLunch) + " Developer" + team + id + " has gone to lunch. Enjoy!");
		timeClock.lunchMeeting(startLunch + 60);
		endLunch = timeClock.currentTime();
		int lunchLength = endLunch - startLunch;
		System.out.println(TimeConverter.convertTime(endLunch) + " Developer" + team + id + " is back from lunch. Time to work! (" + lunchLength + ")" );
		lunchTime = false;		
	}
	
	public boolean currentlyEating() {
		return isEating;
	}
	
	public void isFinalStandupTime() {
		finalStandup = true;
	}
	
	public void haveFinalStandup() {
		timeClock.arriveAtStandup();
		System.out.println(TimeConverter.convertTime(timeClock.currentTime()) + " Developer" + team + id + " has arrived at the final standup.");
		timeClock.finalStandupMeeting();
		finalStandup = false;
	}
	
	public boolean isAtFinalStandup() {
		return isAtStandup;
	}
	
	public boolean timeToLeave() {
		boolean imDone = false;
		int timeWorked = timeClock.currentTime() - startTime;
		if(timeWorked >= 510) {
			imDone = true;
		}
		return imDone;
	}
	
	public void leave(){
		int currentTime = timeClock.currentTime();
		atWork = false;
		String time = TimeConverter.convertTime(currentTime);
		if(team == 0) {
			System.out.println(time + " The Project Manager is leaving. Closing Time!!!");
		} else {
			System.out.println(time + " Developer" + team + id + " is leaving. Bye!");
			if(id == 1) {
				timeClock.leadLeaves();
			} else {
				timeClock.employeesLeaves();
			}
		}
	}
	
	public boolean isWorking() {
		return atWork;
	}
	
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	public void hasQuestion() {
		System.out.println(TimeConverter.convertTime(timeClock.currentTime()) 
				+ " Developer" + team + id + " has a question.");
		waitingQuestion = true;
	}
	
	public void questionAnswered() {
		System.out.println(TimeConverter.convertTime(timeClock.currentTime()) 
				+ " Developer" + team + id + " got an answer to their question.");
		waitingQuestion = false;
	}
	
	public boolean currentlyHasQuestion() {
		return waitingQuestion;
	}
	
	public void askProjectManagerQuestion() {
		timeClock.askProjectManager();
	}
			
}
