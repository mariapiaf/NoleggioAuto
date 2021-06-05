package polito.it.noleggio.model;

import java.time.LocalTime;

public class Event implements Comparable<Event>{ // rappresenta l'evento del mio simulatore

	public enum EventType{
		NUOVO_CLIENTE,
		RITORNO_AUTO
	} // è una classe che definisce automaticamente delle costanti 
	
	private LocalTime time; // istante di tempo in cui l'evento accade
	private EventType type;
	// type può assumere valore NUOVO_CLIENTE o RITORNA_AUTO, sono deu costanti
	
	@Override
	public int compareTo(Event other) {
		// facciamo il confronto sui tempi
		return this.time.compareTo(other.time);
	} // gli eventi sono ordinati per tempo crescente
	// il primo valore è quello che ha il tempo più basso
	
	public Event(LocalTime time, EventType type) {
		super();
		this.time = time;
		this.type = type;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + "]";
	}
	
	
	
}
