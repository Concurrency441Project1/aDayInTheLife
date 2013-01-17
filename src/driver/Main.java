package driver;

import java.util.ArrayList;
import java.util.ListIterator;

import offices.ProjectManagerOffice;

import employees.Developer;
import employees.Team;
import employees.TeamLead;
import time_tools.TimeClock;

public class Main extends Thread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ProjectManagerOffice projectManagerOffice = null;
		ArrayList<Team> teams = new ArrayList<Team>();
		TimeClock timeClock = new TimeClock();
				
		for(int team = 0; team < 4; team++) {
			if( team == 0 ) {
				projectManagerOffice = new ProjectManagerOffice(timeClock);
			} else {
				teams.add(new Team(team,timeClock));
				for(int id = 1; id < 5; id++) {
					if(id == 1) {
						teams.get(team - 1).setTeamLead(new TeamLead(team, id, timeClock));
					} else {
						teams.get(team - 1).addDeveloper(new Developer(team, id, timeClock));
					}
				}
			}
		}
		
		
		while( timeClock.currentTime() <= 1020 ) {
			int currentTime = timeClock.currentTime();
			if( currentTime == 480 ) {
				projectManagerOffice.start();
				ListIterator<Team> iterator = teams.listIterator();
				while(iterator.hasNext()) {
					iterator.next().start();
				}
			}
			
			try {
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timeClock.tick();
		}
	}

}
