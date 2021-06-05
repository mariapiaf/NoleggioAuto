package polito.it.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;

import polito.it.noleggio.model.Event.EventType;

public class Simulator {
	
	// EVENTI 
	private PriorityQueue<Event> queue;
	
	// PARAMAETRI DI SIMULAZIONE
	private int NC; // number of cars
	private Duration T_IN; // intervallo di tempo tra clienti
	private LocalTime oraApertura = LocalTime.of(8, 0);
	private LocalTime oraChiusura = LocalTime.of(20, 0);
	
	// STATO DEL SISTEMA (DEL MONDO)
	private int nAuto; // auto attualmente presenti
	
	// MISURA IN USCITA 
	private int nClienti;
	private int nClientiInsoddisfatti;
	
	// IMPOSTAZIONE DEI PARAMETRI INIZIALI
	
	public void setNumCars(int NC) {
		this.NC = NC;
	}
	
	public void setClientFrequency(Duration d) {
		this.T_IN = d;
	}
	
	// SIMULAZIONE
	
	public void run() {
		// deve inizializzare la coda degli eventi e lo stato del sistema
		this.queue = new PriorityQueue<Event>();
		
		// Stato iniziale
		this.nAuto = NC; // all'inizio della simulazione
		this.nClienti = 0;
		this.nClientiInsoddisfatti = 0;
		
		// Eventi iniziali
		LocalTime ora = this.oraApertura;
		while(ora.isBefore(oraChiusura)) {
			this.queue.add(new Event(ora, EventType.NUOVO_CLIENTE));
			ora= ora.plus(this.T_IN);
		}
		// parto dalle 8 di mattino devo aggiungere degli eventi di tipo nuovo cliente
		
		// Ciclo di simulazione vero e proprio
		// esistono eventi da simulare? allora simuliamoli
		
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
			
		}
		
		
	}
	
	
	private void processEvent(Event e) {
		// c'è un evento, calcolane le conseguenze
		// si chiede che tipo di evento è
		switch(e.getType()) {
		case NUOVO_CLIENTE: // devo controllare se posso dare un'auto o no
			this.nClienti++;
			
			if(this.nAuto>0) {
				// Noleggia
				this.nAuto--;
				double num = Math.random()*3;
				if(num<1.0) {
					this.queue.add(new Event(e.getTime().plus(Duration.of(1,  ChronoUnit.HOURS)), EventType.RITORNO_AUTO));
				}
				else if(num<2.0) {
					this.queue.add(new Event(e.getTime().plus(Duration.of(2,  ChronoUnit.HOURS)), EventType.RITORNO_AUTO));
				}
				else {
					this.queue.add(new Event(e.getTime().plus(Duration.of(3,  ChronoUnit.HOURS)), EventType.RITORNO_AUTO));
				}
			} else {
				// insoddisfatto
				this.nClientiInsoddisfatti++;
			}
			break;
		case RITORNO_AUTO:
			this.nAuto++; // l'auto è tornata in garage
			break;
		}
	}
	
	public int getTotClients() {
		return this.nClienti;
	}
	
	public int getDissatisfied() {
		return this.nClientiInsoddisfatti;
	}
	
}
