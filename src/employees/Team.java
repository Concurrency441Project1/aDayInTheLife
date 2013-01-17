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
		while(timeClock.currentTime() < 1020) {
			timeClock.checkTime();
			
			int currentTime = timeClock.currentTime();
			if((currentTime >= 480) && (currentTime <= 510)) {
				if(Chance.timeGamble(currentTime, 510, 30) && !teamLead.isWorking()) {
					teamLead.start();
					devsAvailable++;
				}
				
				ListIterator<Developer> iterator = developers.listIterator();
				while(iterator.hasNext()) {
					Developer currentDev = iterator.next();
					if(Chance.timeGamble(currentTime, 510, 30) && !currentDev.isWorking()) {
						currentDev.start();
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
				if(Chance.timeGamble(currentTime, 750, 30) && !teamLead.currentlyEating()) {
					teamLead.isLunchTime();
				}
				
				ListIterator<Developer> iterator = developers.listIterator();
				while(iterator.hasNext()) {
					Developer currentDev = iterator.next();
					if(Chance.timeGamble(currentTime, 750, 30) && !currentDev.currentlyEating()) {
						currentDev.isLunchTime();
					}
				}
			}
			
			if(currentTime == 960) {
				teamLead.isFinalStandupTime();
				
				ListIterator<Developer> iterator = developers.listIterator();
				while(iterator.hasNext()) {
					iterator.next().isFinalStandupTime();
				}
			}
			
			ListIterator<Developer> iterator = developers.listIterator();
			while(iterator.hasNext()) {
				Developer currentDev = iterator.next();
				if(currentDev.currentlyHasQuestion()) {
					boolean hasAnswer = teamLead.askTeamLeadQuestion();
					if(hasAnswer) {
						currentDev.questionAnswered();
					} else {
						// teamLead.askProjectManagerQuestion();
					}
				}
			}
		
		}
	}

}
