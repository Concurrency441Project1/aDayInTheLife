package employees;

import chance_tools.Chance;
import time_tools.TimeClock;
import time_tools.TimeConverter;

public class TeamLead extends Employee {
	
	private boolean questionForLead = false;
		
	public TeamLead(int team, int id, TimeClock timeClock) {
		this.team = team;
		this.id = id;
		this.timeClock = timeClock;
	}
	
	public synchronized boolean askTeamLeadQuestion() {
		while(questionForLead) {
			try {
				wait();
			} catch (InterruptedException e) {

			}
		}
		questionForLead = true;
		boolean answerForQuestion = Chance.percentGamble(100);
		if(answerForQuestion) {
			System.out.println(TimeConverter.convertTime(timeClock.currentTime())
					+ " The Dev Lead had an answer.");
		}
		questionForLead = false;
		notify();
		return answerForQuestion;
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
			
			if(waitingQuestion) {
				//askProjectManagerQuestion();
			}
			
			if(timeToLeave()) {
				leave();
			}
		}
	}
	
}
