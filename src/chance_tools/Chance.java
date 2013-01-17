package chance_tools;

public class Chance {

	public static boolean timeGamble(int currentTime, int maxTime, int interval) {
		boolean success = false;
		double remainingTime = maxTime - currentTime;
		double chanceBoost = 0;
		if(remainingTime == 0) {
			chanceBoost = 2;
		} else if(remainingTime < interval) {
			chanceBoost = 1 + ((100 / remainingTime) / 100);
		}
		double chance = (interval * Math.random()) * chanceBoost;
		if(chance > interval) {
			success = true;
		}
		
		return success;
	}
	
	public static boolean timeGamble(int currentTime, int maxTime, int waitInterval, int interval) {
		boolean success = false;
		double remainingTime = maxTime - currentTime;
		double chanceBoost = 0;
		if (remainingTime > waitInterval) {
			chanceBoost = 0;
		} else if(remainingTime == 0) {
			chanceBoost = 2;
		} else if(remainingTime < interval) {
			chanceBoost = 1 + ((100 / remainingTime) / 100);
		}
		
		double chance = (interval * Math.random()) * chanceBoost;
		if(chance > interval) {
			success = true;
		}
		
		return success;
	}
	
	public static boolean percentGamble(int chanceSuccess) {
		boolean success = false;
		double chanceFail = 100 - chanceSuccess;
		double chance = (100 * Math.random());
		
		if(chance > chanceFail) {
			success = true;
		}

		return success;
	}
}
