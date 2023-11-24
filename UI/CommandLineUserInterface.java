package edu.upenn.cit594.ui;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeMap;

import edu.upenn.cit594.processor.Processor;

public class CommandLineUserInterface {
	
	protected Processor processor;
	
	protected Scanner in;
	
	public CommandLineUserInterface(Processor processor) {
		this.processor = processor;
		
		this.in = new Scanner(System.in);
	}
	
	
	/**
	 * Function to execute the programme
	 */
	public void start() {
		
		while (true) {
		System.out.println("Enter 0 to exit the program\n"
				+ "1. Show the available actions\n"
				+ "2. Show the total population of all zip-codes\n"
				+ "3. Show the total vaccinations per capita for each ZIP Code for the specified date\n"
				+ "4. Show the average market value for properties in a specified ZIP Code\n"
				+ "5. Show the average total livable area for properties in a specified ZIP Code\n"
				+ "6. Show the total market value of properties, per capita, for a specified ZIP Code\n"
				+ "7. Custom feature");
		
		int choice = -1;
		while (true) {
			try {
				System.out.print("> ");
				choice = this.in.nextInt();
				if (choice < 0 || choice > 7) {continue;}
				else {break;}
				} catch (InputMismatchException e) {
					System.out.println("Invalid input, try again");
					this.in.nextLine();
					continue;
				}
			}

		
		
			
		switch (choice) {
		case 0:
			System.out.println("Bye");
			return;
		case 1:
			System.out.println("BEGIN OUTPUT");
			System.out.println("0");
			System.out.println("1");
			if (this.processor.checkPopData()) {
				System.out.println("2");
			}
			if (this.processor.checkPopData() && this.processor.checkCovData()) {
				System.out.println("3");
			}
			if (this.processor.checkPropData()) {
				System.out.println("4");
				System.out.println("5");
			}
			if (this.processor.checkPropData() && this.processor.checkPopData()) {
				System.out.println("6");
			}
			
			if (this.processor.checkCovData() && this.processor.checkPopData() && this.processor.checkPropData()) {
				System.out.println("7");
			}
			System.out.println("END OUTPUT");
			break;
		case 2:
			this.choice2();
			break;
		case 3:
			this.choice3();
			break;
		case 4:
			this.choice4();
			break;
		case 5:
			this.choice5();
			break;
		case 6:
			this.choice6();
			break;
		case 7:
			this.choice7();
		}
		}
	}
	
	protected void choice2() {
		int popCount = this.processor.totalPopulationCount();
		System.out.println("BEGIN OUTPUT");
		System.out.println(popCount);
		System.out.println("END OUTPUT");
	}
	
	protected void choice3() {
		System.out.println("What type of status, partial or full");
		System.out.print(">");
		String reply  = this.in.next();
		
		if (!reply.equals("partial") && !reply.equals("full")) {System.out.println("Invalid input"); return;}
		
		System.out.println("Please enter date");
		System.out.print(">");
		String ans  = this.in.next();
		
		//SimpleDateFormat timestampFormat =  new SimpleDateFormat("YYYY-MM-DD");
		//LocalDate timestampformat;
		try {
			//Date dCheck = timestampFormat.parse(ans);
			LocalDate dCheck = LocalDate.parse(ans);
			} catch (java.time.format.DateTimeParseException  e){
				System.out.println("Invalid input");
				this.in.nextLine();
				return;
			}
		
		TreeMap<Long, Double> vacTree = this.processor.vacPerCapital(reply, ans);
		System.out.println("BEGIN OUTPUT");
		if (!vacTree.isEmpty()) {
		vacTree.forEach((z,v) -> {
			System.out.printf(z + " "  + "%.4f\n", v);
		});}
		else {
			System.out.println(0);
		}
		System.out.println("END OUTPUT");
	}
	
	protected void choice4() {
		System.out.println("Enter a 5 digit zipcode");
		System.out.print(">");
		long reply;
		try {
			reply = this.in.nextLong();
			if (reply < 10000 || reply > 99999 ) {return;}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input");
			return;
		}
		
		long ans = this.processor.getAvgMV(reply);
		System.out.println("BEGIN OUTPUT");
		System.out.println(ans);
		System.out.println("END OUTPUT");
		
	}
	protected void choice5() {
		System.out.println("Enter a 5 digit zipcode");
		long reply;
		try {
			reply = this.in.nextLong();
			if (reply < 10000 || reply > 99999 ) {return;}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input");
			return;
		}
		
		long ans = this.processor.getAvgLV(reply);
		System.out.println("BEGIN OUTPUT");
		System.out.println(ans);
		System.out.println("END OUTPUT");
		
	}
	
	protected void choice6() {
		System.out.println("Enter a 5 digit zipcode");
		System.out.print(">");
		long reply;
		try {
			reply = this.in.nextLong();
			if (reply < 10000 || reply > 99999 ) {return;}
		} catch (InputMismatchException e) {
			System.out.println("Invalid input");
			return;
		}
		
		long ans = this.processor.mvPerCapita(reply);
		System.out.println("BEGIN OUTPUT");
		System.out.println(ans);
		System.out.println("END OUTPUT");
		
	}
	
	protected void choice7() {
		return;
	}
	
}
