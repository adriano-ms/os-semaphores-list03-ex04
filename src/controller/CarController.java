package controller;

import java.util.concurrent.Semaphore;

public class CarController extends Thread {

	private int id;
	private Semaphore teamSemaphore;
	private int bestTime;
	private MatchController match;

	public CarController() {
		super();
	}
	
	public CarController(int id, Semaphore teamSemaphore, MatchController match) {
		this.id = id;
		this.teamSemaphore = teamSemaphore;
		this.match = match;
		this.bestTime = 0;
	}

	public int getBestTime() {
		return bestTime;
	}

	public int getCarId() {
		return id;
	}

	private void enterSpeedway() throws InterruptedException {
		teamSemaphore.acquire();
		match.getSemaphore().acquire();
		for (int i = 1; i <= 3; i++) {
			int time = (int) ((Math.random() * 501) + 10);
			if (i == 1)
				bestTime = time;
			
			else if (bestTime > time) 
				bestTime = time;
			
			sleep(time);
			System.out.println(this + ": finish the " + i + "º round in " + time + "s");
		}
		teamSemaphore.release();
		match.getSemaphore().release();

	}

	@Override
	public void run() {
		try {
			enterSpeedway();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Car " + id;
	}
}
