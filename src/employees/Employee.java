package employees;

import chance_tools.Chance;
import time_tools.TimeClock;
import time_tools.TimeConverter;

public abstract class Employee extends Thread {
	
	protected int team;
	protected int id;
	protected TimeClock timeClock = null;
	
	protected boolean atWork = false;
	protected int startTime;
	protected int startLunch;
	protected int endLunch;
	
	protected boolean eatingLunch = false;
	
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
	
	public void eatLunch() {
		startLunch = timeClock.currentTime();
		String time = TimeConverter.convertTime(startLunch);
		System.out.println(time + " Developer" + team + id + " is going to lunch. Enjoy!");
		eatingLunch = true;
	}
	
	public boolean isEatingLunch() {
		return eatingLunch;
	}
	
	public boolean endLunch() {
		int currentTime = timeClock.currentTime();
		int maxTime = startLunch + 60;
		boolean endTime = Chance.timeGamble(currentTime, maxTime, 30, 30);
		return endTime;
	}
	
	public void returnFromLunch() {
		endLunch = timeClock.currentTime();
		int lunchLength = endLunch - startLunch;
		String time = TimeConverter.convertTime(endLunch);
		System.out.println(time + " Developer" + team + id 
				+ " is back from lunch. Work Time! (" + lunchLength + ")");
		eatingLunch = false;
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
			
}
