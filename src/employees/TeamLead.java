package employees;

import time_tools.TimeClock;

public class TeamLead extends Employee {
	
	public TeamLead(int team, int id, TimeClock timeClock) {
		this.team = team;
		this.id = id;
		this.timeClock = timeClock;
	}

}
