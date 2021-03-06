package model;

import java.util.Calendar;

import database.Datenbank;

public class User {
	private Kalender kalender;
	private String Username;
	private static User loggedIn = null;
	private int MonthViewed;
	private int YearViewed;
	private int DayViewed = 0;
	private static final String[] MonatsName = {"Januar", "Februar", "Maerz", "April", "Mai", "Juni", "Juli", "August", "Sepetember",
			"Oktober", "November", "Dezember"};
	private static final String[] WochenTage = {"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Sammstag", "Sonntag"};
	
	/**
	 * User wird bei erfolgreichem login bei der DB von der DB erstellt
	 * @param Username
	 * @param kalender
	 */
	private User(String Username, Kalender kalender) {
		this.Username = Username;
		this.kalender = kalender;
		MonthViewed = Calendar.getInstance().get(Calendar.MONTH);
		YearViewed = Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public static void regUser(String Username, Kalender kalender) {
		loggedIn = new User(Username, kalender);
	}
	
	/**
	 * Es gibt immer nur eine Instanz 
	 * @return User instanz
	 */
	public static User getInstanz() {
		if(loggedIn == null) {
			return null;
		}
		else return loggedIn;
	}
	
	
	public void addTermin(Termin t) {
		kalender.addTermine(t);
	}
	
	
	public String toString() {
		return Username + "\n" + kalender.toString();
	}
	
	public String getUsername() {
		return Username;
	}
	
	public Kalender getKalender() {
		return kalender;
	}
	
	public int getYearViewed() {
		return YearViewed;
	}
	
	public int getMonthViewed() {
		return MonthViewed;
	}
	
//	public void setView(int Month, int Year) {
//		if(Month >= 1 && Month <= 12 ) {
//			MonthViewed = Month;
//			YearViewed = Year;
//			Datenbank.getInstanz().syncMonth(MonthViewed, YearViewed);
//		}
//	}
	
	/**
	 * Laed Termine des naechsten Monats aus DB und aktuallisiert MonthViewd und YearViewed
	 */
	public void viewNextMonth() {
		if(MonthViewed == 11) {
			MonthViewed = 0;
			YearViewed++;
		} else {
			MonthViewed++;
		}
		Datenbank.getInstanz().syncMonth(MonthViewed, YearViewed);
	}
	
	/**
	 * Laed Termine des vorherigen Monats aus DB und aktuallisiert MonthViewd und YearViewed
	 */
	public void viewPrevMonth() {
		if(MonthViewed == 0) {
			MonthViewed = 11;
			YearViewed--;
		} else {
			MonthViewed--;
		}
		System.err.println("prev" + MonthViewed + " " + YearViewed );
		Datenbank.getInstanz().syncMonth(MonthViewed, YearViewed);
	}
	
	public int getDayViewed() {
		return DayViewed;
	}
	
	public void setDayViewed(int day) {
		DayViewed = day;
	}
	
	public static String getMonthName(int month) {
		return MonatsName[month];
	}
	public static String getDayName(int day) {
		day--;
		return WochenTage[day];
	}
	
}
//