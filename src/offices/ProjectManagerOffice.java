package offices;

import employees.ProjectManager;
import time_tools.TimeClock;

public class ProjectManagerOffice extends Thread {
	
	private ProjectManager projectManager = null;
	private TimeClock timeClock = null;
		
	private boolean hadMorningMeeting = false;	
	private boolean hadTenMeeting = false;
	private boolean hadLunch = false;
	private boolean hadTwoMeeting = false;
		
	public ProjectManagerOffice( TimeClock timeClock ) {
		this.projectManager = new ProjectManager(timeClock);
		this.timeClock = timeClock;
	}
	
	public void run() {
		int currentTime = 0;
		projectManager.start();
		while(currentTime < 1020) {
			currentTime = timeClock.currentTime();
			
			if((timeClock.currentLeads() == 3) && !hadMorningMeeting) {
				projectManager.isLeadMeetingTime();
				hadMorningMeeting = true;
			}
			
			if(currentTime >= 600 && !hadTenMeeting) {
				projectManager.isTenMeetingTime();
				hadTenMeeting = true;
			}
			
			if(currentTime >= 720 && !hadLunch) {
				projectManager.isLunchTime();
				hadLunch = true;
			}
			
			if(currentTime >= 840 && !hadTwoMeeting) {
				projectManager.isTwoMeetingTime();
				hadTwoMeeting = true;
			}
			
			if(currentTime == 960 && !timeClock.hadFinalStandup()) {
				projectManager.isFinalStandupTime();
			}
			
			if(timeClock.isWaitingManagerQuestion()){
				projectManager.questionWaiting();
			}
			
			timeClock.checkTime();
			
		}
	}

}
