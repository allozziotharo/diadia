package it.uniroma3.diadia.giocatore;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Borsa {
	public final static int DEFAULT_PESO_MAX_BORSA = 10;
	private Attrezzo[] attrezzi;
	private int numeroAttrezzi;
	private int pesoMax;
	public Borsa() {
		this(DEFAULT_PESO_MAX_BORSA);
	}
	public Borsa(int pesoMax) {
		this.pesoMax = pesoMax;
		this.attrezzi = new Attrezzo[10]; // speriamo bastino...
		this.numeroAttrezzi = 0;
	}
	public boolean addAttrezzo(Attrezzo attrezzo) {
		if (this.getPeso() + attrezzo.getPeso() > this.getPesoMax()) {
			System.out.println("\nC'E' SPAZIO MA NON C'ENTRA\n");
			return false;
		}
		if (this.numeroAttrezzi==10) {
			System.out.println("\nBORSA PIENA\n");
			return false;
		}
		this.attrezzi[this.numeroAttrezzi] = attrezzo;
		this.numeroAttrezzi++;
		System.out.println("\nL'attrezzo è stato messo in borsa\n");
		return true;
	}
	public int getPesoMax() {
		return pesoMax;
	}
	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		Attrezzo attrezzo = null;
		for (int i= 0; i<this.numeroAttrezzi; i++)
			if (this.attrezzi[i].getNome().equals(nomeAttrezzo))
				attrezzo = attrezzi[i];
		return attrezzo;
	}
	public int getPeso() {
		int peso = 0;
		for (int i= 0; i<this.numeroAttrezzi; i++)
			peso += this.attrezzi[i].getPeso();
		return peso;
	}
	public boolean isEmpty() {
		return this.numeroAttrezzi == 0;
	}
	public boolean hasAttrezzo(String nomeAttrezzo) {
		return this.getAttrezzo(nomeAttrezzo)!=null;
	}
	
	/**
	 * Rimuove un'UNICA OCCORRENZA dell'attrezzo dalla borsa (ricerca in base al nome).
	 * COPIATA ALLA CLASSE STANZA, dato che anche qui serviva implementare una casistica se la borsa fosse stata vuota
	 * @param nomeAttrezzo
	 * @return true se l'attrezzo e' stato rimosso, false altrimenti
	 */
	public boolean removeAttrezzo(String nomeAttrezzo) {
	
		for(Attrezzo attrezzo: this.attrezzi) { 
			for(int i = 0; i < this.numeroAttrezzi; i++) {
	
				if(attrezzo.getNome().equals(nomeAttrezzo)) {
				this.attrezzi[i] = this.attrezzi[numeroAttrezzi-1]; //sostituisco con ULTIMO attrezzo dell'array, invece che scalarli tutti
				this.attrezzi[numeroAttrezzi-1] = null;
				this.numeroAttrezzi--;
				System.out.println("\nL'attrezzo è stato rimosso dalla borsa\n");
				return true;
				}
			}
		}
		System.out.println("\nRIMOZIONE FALLITA, sicuro che l'attrezzo sia presente?\n");
		return false;
	}
	public String toString() {
		StringBuilder s = new StringBuilder();
		if (!this.isEmpty()) {
			s.append("Contenuto borsa ("+this.getPeso()+"kg/"+this.getPesoMax()+"kg): ");
			for (int i= 0; i<this.numeroAttrezzi; i++)
				s.append(attrezzi[i].toString()+" ");
		}
		else
			s.append("Borsa vuota");
		return s.toString();
	}
}