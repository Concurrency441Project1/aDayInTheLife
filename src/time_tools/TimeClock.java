package time_tools;

/**
 * TimeClock.java
 * 
 * The clock for our universe, where 1 minute = 10ms.
 * (Therefore 8 hours = 4800ms / 4.8s)
 *
 */
public class TimeClock {
    
    private volatile int time = 480;
    
    private int leadsAvailable = 0;
    private int employeesWorking = 0;
    
    boolean leadMeetingInProgress = false;
    boolean hadLeadMeeting = false;
    boolean conferenceRoomAvailable = true;
    
	/*
	 * tick() - the clock 'ticks' for 1 minute of the appropriate time.
	 */
	public synchronized void tick(){
		time++;
		notifyAll();
	}
	
	public synchronized void checkTime() {
		try {
			wait();
		} catch (InterruptedException e) {
			
		}
	}
	
	public boolean leadMeetingActive() {
		return leadMeetingInProgress;
	}
	
	public boolean leadMeetingComplete() {
		return hadLeadMeeting;
	}
	
	public synchronized void leadMeeting(int waitTime) {
		leadMeetingInProgress = true;
		meeting(waitTime);
		leadMeetingInProgress = false;
		hadLeadMeeting = true;
	}
	
	public synchronized void meeting(int waitTime) {
		for(int i = 0; i < waitTime; i++) {
			try {
				wait();
			} catch (InterruptedException e){
				
			}
		}
	}
	
	public synchronized boolean morningTeamMeeting(int id) {
		boolean hadMeeting = false;
		while(!hadMeeting) {
			if(conferenceRoomAvailable) {
				conferenceRoomAvailable = false;
				System.out.println(TimeConverter.convertTime(time) 
						+ " Team " + id + " is having their morning meeting.");
				for(int i = 0; i < 15; i++) {
					try {
						wait();
					} catch (InterruptedException e) {
						
					}
				}
				hadMeeting = true;
			} else {
				try {
					wait();
				} catch (InterruptedException e) {

				}
			}
		}
		System.out.println(TimeConverter.convertTime(time) 
				+ " Team " + id + " has ended their morning meeting.");
		return hadMeeting;
	}
	
	public synchronized void meetingWithEnd(int endTime) {
		while(time < endTime) {
			try {
				wait();
			} catch (InterruptedException e) {
				
			}
		}
	}
    
    public int currentTime() {
        return time;
    }
    
    public void leadArrives() {
    	leadsAvailable++;
    	employeeArrives();
    }
    
    public void leadLeaves() {
    	leadsAvailable--;
    	employeesLeaves();
    }
    
    public int currentLeads() {
    	return leadsAvailable;
    }
    
    public void employeeArrives() {
    	employeesWorking++;
    }
    
    public void employeesLeaves() {
    	employeesWorking--;
    }
    
    public int currentEmployees() {
    	return employeesWorking;
    }
}