package controller;

import java.util.concurrent.Semaphore;

public class MatchController extends Thread {

	private CarController[] cars;
	private Semaphore semaphore;
	
	public MatchController() {
		super();
	}
	
	public Semaphore getSemaphore() {
		return semaphore;
	}
	
	public MatchController(int teamsAmount, int carsPerTeam) {
		int carsAmount = teamsAmount*carsPerTeam;
		this.cars = new CarController[carsAmount];
		this.semaphore = new Semaphore(5);
		Semaphore[] teamSemaphores = new Semaphore[teamsAmount];
		for (int i = 0; i < teamsAmount; i++)
			teamSemaphores[i] = new Semaphore(carsPerTeam);
		
		for (int i = 0; i < carsAmount; i++) 
			cars[i] = new CarController(i,teamSemaphores[i / teamsAmount], this);
	}
	
	private boolean verifyCars() {
		for(CarController car : cars)
			if(car.isAlive())
				return false;
		
		return true;
	}
	
	private void sortCars() {
		int carsAmount = cars.length;
		for(int i = 0; i < carsAmount; i++) {
			for(int j = 0; j < carsAmount - i - 1; j++) {
				if(cars[j].getBestTime() > cars[j + 1].getBestTime()) {
					CarController aux = cars[j];
					cars[j] = cars[j + 1];
					cars[j + 1] = aux;
				}
			}
		}
			
	}
	
	@Override
	public void run() {
		try {
			for(CarController car : cars)
				car.start();
			
			while(!verifyCars()) 
				sleep(5);

			sortCars();
			
			System.out.println("Final Ranking");
			int carsAmount = cars.length;
			for(int i = 0; i < carsAmount; i++) {
				System.out.println((i+1) + "º: " + cars[i] + "(best time = " + cars[i].getBestTime() + "s)");
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
