package it.uniroma3.diadia;


import java.util.Scanner;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Borsa;


/**
 * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
 * Per giocare crea un'istanza di questa classe e invoca il letodo gioca
 *
 * Questa e' la classe principale crea e istanzia tutte le altre
 *
 * @author  docente di POO 
 *         (da un'idea di Michael Kolling and David J. Barnes) 
 *          
 * @version base
 */

public class DiaDia {
	
	static final private String MESSAGGIO_BENVENUTO = ""+
			"Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n" +
			"Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"+
			"I locali sono popolati da strani personaggi, " +
			"alcuni amici, altri... chissa!\n"+
			"Ci sono attrezzi che potrebbero servirti nell'impresa:\n"+
			"puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n" +
			"o regalarli se pensi che possano ingraziarti qualcuno.\n\n"+
			"Per conoscere le istruzioni usa il comando 'aiuto'.";
	
	static final private String[] elencoComandi = {"vai", "aiuto", "fine", "prendi", "posa"};

	private Partita partita;

	public DiaDia() {
		this.partita = new Partita();
	}

	public void gioca() {
		String istruzione; 

		System.out.println(MESSAGGIO_BENVENUTO);
		try (Scanner scannerDiLinee = new Scanner(System.in)) {		
		do		
			istruzione = scannerDiLinee.nextLine();
		while (!processaIstruzione(istruzione));
		}
	}   


	/**
	 * Processa una istruzione 
	 *
	 * @return true se l'istruzione e' eseguita e il gioco continua, false altrimenti
	 */
	private boolean processaIstruzione(String istruzione) {
		Comando comandoDaEseguire = new Comando(istruzione);
		
		if (comandoDaEseguire.getNome() == null) {
			System.out.println("non hai inserito niente");
			return true;  //se il prof vuole che con un input nullo venga terminato il gioco metto così
		} //se il compilatore entra e da questa stampa, non legge più input fino al prossimo run
		
		if (comandoDaEseguire.getNome().equals("fine")) {
			this.fine(); 
			return true;
		} else if (comandoDaEseguire.getNome().equals("vai"))
			this.vai(comandoDaEseguire.getParametro());
		else if (comandoDaEseguire.getNome().equals("aiuto"))
			this.aiuto();
		else if (comandoDaEseguire.getNome().equals("prendi"))
			this.prendi(comandoDaEseguire.getParametro());
		else if (comandoDaEseguire.getNome().equals("posa"))
			this.posa(comandoDaEseguire.getParametro());
		else
			System.out.println("Comando sconosciuto");
		if (this.partita.vinta()) {
			System.out.println("Hai vinto!");
			return true;
		} else
			return false;
	}   

	// implementazioni dei comandi dell'utente:

	/**
	 * Stampa informazioni di aiuto.
	 */
	private void aiuto() {
		for(int i=0; i< elencoComandi.length; i++) 
			System.out.print(elencoComandi[i]+" ");
		System.out.println();
	}

	/**
	 * Cerca di andare in una direzione. Se c'e' una stanza ci entra 
	 * e ne stampa il nome, altrimenti stampa un messaggio di errore
	 */
	private void vai(String direzione) {
		if(direzione==null)
			System.out.println("Dove vuoi andare ?");
		Stanza prossimaStanza = null;
		prossimaStanza = this.partita.getLabirinto().getStanzaCorrente().getStanzaAdiacente(direzione);
		if (prossimaStanza == null)
			System.out.println("Direzione inesistente");
		else {
			this.partita.getLabirinto().setStanzaCorrente(prossimaStanza);
			int cfu = this.partita.getGiocatore().getCfu(); //NON E' RIDONDANTE! NON ELIMINARE PERCHE SENNO NON SAI COME ACCEDERE AI CFU DELLA PARTITA PER DECREMENTARLI!
			this.partita.getGiocatore().setCfu(cfu--);
		}
		System.out.println(partita.getLabirinto().getStanzaCorrente().getDescrizione());
	}

	/**
	 * Comando "Fine".
	 */
	private void fine() {
		System.out.println("Grazie di aver giocato!");  // si desidera smettere
	}
	
	
	
/**
 * MAIN DEL GIOCO NELLA CLASSE DiaDia
 * 
 * 
 * 
 * 
 * 
 * 
 * */
	
	
	public static void main(String[] argc) {
		DiaDia gioco = new DiaDia();
		gioco.gioca();
	}

	
	
	
	
	
	
	/**
	 * Comando "Prendi".
	 */
	private void prendi (String nomeAttrezzo) {
		Stanza stanzaCorrente = this.partita.getLabirinto().getStanzaCorrente();
		if(stanzaCorrente.getAttrezzi() == null) {
			System.out.println("\nla stanza è vuota!\n"); }

		else if (stanzaCorrente.hasAttrezzo(nomeAttrezzo) == false) {
			System.out.println("\npurtroppo non c'è\n");
					}
			
		else {
			Attrezzo attrezzoDaAggiungere = stanzaCorrente.getAttrezzo(nomeAttrezzo);
			this.partita.getGiocatore().getBorsa().addAttrezzo(attrezzoDaAggiungere);
		}
	}

	private void posa(String nomeAttrezzo) {
		Borsa borsaPartita = this.partita.getGiocatore().getBorsa();
		if(borsaPartita.hasAttrezzo(nomeAttrezzo) == false) {
			System.out.println("\nL'ATTREZZO NON E' NELLA BORSA\n");
		}
		
		else {
			borsaPartita.removeAttrezzo(nomeAttrezzo);
			Attrezzo attrezzoDaAggiungere = borsaPartita.getAttrezzo(nomeAttrezzo);
			this.partita.getLabirinto().getStanzaCorrente().addAttrezzo(attrezzoDaAggiungere);
		}
	}
}