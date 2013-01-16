package employees;

import java.util.ArrayList;
import java.util.ListIterator;

import chance_tools.Chance;

import time_tools.TimeClock;

public class Team extends Thread {
	public int id;
	private TeamLead teamLead = null;
	private ArrayList<Developer> developers = new ArrayList<Developer>();
	private TimeClock timeClock = null;
	
	private int devsAvailable = 0;
	private boolean hadMorningMeeting = false;
	
	public Team (int id, TimeClock timeClock) {
		this.id = id;
		this.timeClock = timeClock;
	}
	
	public void setTeamLead(TeamLead teamLead) {
		this.teamLead = teamLead;
	}
	
	public void addDeveloper(Developer developer) {
		developers.add(developer);
	}
	
	public void run() {
		while(timeClock.currentTime() <= 1020) {
			int currentTime = timeClock.currentTime();
			if((currentTime >= 480) && (currentTime <= 510)) {
				if(Chance.timeGamble(currentTime, 510, 30) && !teamLead.isWorking()) {
					teamLead.arrive();
					devsAvailable++;
				}
				
				ListIterator<Developer> iterator = developers.listIterator();
				while(iterator.hasNext()) {
					Developer currentDev = iterator.next();
					if(Chance.timeGamble(currentTime, 510, 30) && !currentDev.isWorking()) {
						currentDev.arrive();
						devsAvailable++;
					}
				}
				
			}
			
			if((devsAvailable == 4) && !hadMorningMeeting 
					&& !timeClock.leadMeetingActive() 
					&& timeClock.leadMeetingComplete()) {
				hadMorningMeeting = timeClock.morningTeamMeeting(id);
			}
			
			
			if((currentTime >= 720) && (currentTime <= 750)) {
				if(Chance.timeGamble(currentTime, 750, 30) && !teamLead.isEatingLunch()) {
					teamLead.eatLunch();
				}
				
				ListIterator<Developer> iterator = developers.listIterator();
				while(iterator.hasNext()) {
					Developer currentDev = iterator.next();
					if(Chance.timeGamble(currentTime, 750, 30) && !currentDev.isEatingLunch()) {
						currentDev.eatLunch();
					}
				}
			}
			
			
			if(currentTime <= 810) {
				if(teamLead.endLunch() && teamLead.isEatingLunch()) {
					teamLead.returnFromLunch();
				}
				
				ListIterator<Developer> iterator = developers.listIterator();
				while(iterator.hasNext()) {
					Developer currentDev = iterator.next();
					if(currentDev.endLunch() && currentDev.isEatingLunch()) {
						currentDev.returnFromLunch();
					}
				}
			}
			
			
			
			if((currentTime >= 990) && (currentTime <= 1020)) {
				if(teamLead.isWorking() && teamLead.timeToLeave()) {
					teamLead.leave();
				}
				
				ListIterator<Developer> iterator = developers.listIterator();
				while(iterator.hasNext()) {
					Developer currentDev = iterator.next();
					if(currentDev.isWorking() && currentDev.timeToLeave()) {
						currentDev.leave();
					}
				}
				
			}
			
		
			timeClock.checkTime();
		}
	}

}
