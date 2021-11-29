package application.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Event{
	protected String day, description, time, notes;
	protected List<Event> events = new ArrayList<Event>();
	public Event(List<Event> events) {
		this.events = events;
	}
	public Event(String day, String description, String time, String notes) {
		super();
		this.day = day;
		this.description = description;
		this.time = time;
		this.notes = notes;
	}
	public void loadEvents(String user) throws FileNotFoundException {
		String fLine;
		String []data;
		File f1 = new File("data/events/"+user+".txt");
		Scanner IN = new Scanner(f1);
		Event event;
		while(IN.hasNextLine()) {
			fLine= IN.nextLine();
			data= fLine.split(",");
			day= data[0];
			time= data[1];
			description= data[2];
			if(data.length> 3) {
				notes = data[3];
			}else {
				notes= " There are no notes for event.";
			}
			event = new Event(day, description, time, notes);
			events.add(event);
		}
		IN.close();
	}
	@Override
	public String toString() {
		return day+", "+time + ", description: " + description+ ", notes:" + notes+" \n";
	}
	public static Comparator<Event> compare= new Comparator<Event>() {
		@Override
		public int compare(Event o1, Event o2) {
			try{
				return new SimpleDateFormat("hh:mma").parse(o1.getTime()).compareTo(new SimpleDateFormat("hh:mma").parse(o2.getTime()));
			}catch(ParseException e) {
				return 0;
			}
		}
	};
	public static Comparator<Event> compareDates = new Comparator<Event>() {
		@Override
		public int compare(Event o1, Event o2) {
			return o1.getDay().compareTo(o2.getDay());
		}
	};
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
