package employees;

import time_tools.TimeClock;

public class Developer extends Employee {

	public Developer(int team, int id, TimeClock timeClock) {
		this.team = team;
		this.id = id;
		this.timeClock = timeClock;
	}

}
