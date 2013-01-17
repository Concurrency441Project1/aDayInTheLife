package employees;

import chance_tools.Chance;
import time_tools.TimeClock;

public class Developer extends Employee {

	public Developer(int team, int id, TimeClock timeClock) {
		this.team = team;
		this.id = id;
		this.timeClock = timeClock;
	}

	public void run() {
		arrive();
		while(timeClock.currentTime() < 1020 && atWork) {
			timeClock.checkTime();
			
			if(lunchTime) {
				haveLunch();
			}
			
			if(finalStandup) {
				if(Chance.timeGamble(timeClock.currentTime(), 975, 15)) {
					haveFinalStandup();
				}
			}
			
			if(Chance.percentGamble(1)) {
				hasQuestion();
			}
			
			if(timeToLeave()) {
				leave();
			}
		}
	}
}
