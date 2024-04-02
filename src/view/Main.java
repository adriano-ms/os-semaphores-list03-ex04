package view;

import controller.MatchController;

public class Main {

	public static void main(String[] args) {

		MatchController match = new MatchController(7, 2);

		match.start();

	}

}
