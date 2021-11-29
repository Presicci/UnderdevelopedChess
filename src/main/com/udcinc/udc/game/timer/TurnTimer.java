package main.com.udcinc.udc.game.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer object that stores and handles a player timer
 * Timer starts disabled and wont tick down till enabled
 * Disabled at end of turn, enabled at start of turn
 * 
 * @author Thomas Presicci
 */
public class TurnTimer {
	// Seconds left for the players turns
	private int seconds;
	
	// Timer object that will handle the decrementing of time
	private Timer timer;
	
	// If the timer should tick down
	private boolean running = false;
	
	public TurnTimer(int seconds) {
		this.seconds = seconds;
		
		timer = new Timer();
		
		//	Timer task that ticks every second
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (running) {
					decrementTimer();
				}
			}
		}, 1000, 1000);	// 1 second
	}
	
	public void stop() {
		timer.cancel();
		timer.purge();
	}
	
	public int getSeconds() {
		return seconds;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public int decrementTimer() {
		return --seconds;
	}
}
