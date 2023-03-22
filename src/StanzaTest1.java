
public class StanzaTest1 {
	
	public static void main() {
		Stanza bar = new Stanza("Bar");
		Stanza mensa = new Stanza("Mensa");
		
		bar.impostaStanzaAdiacente("Nord", mensa);
		mensa.impostaStanzaAdiacente("Sud", bar);
		
		stanzaAdiacenteBar = bar.getStanzaAdiacente("Nord");
		stanzaAdiacenteBar.getDescrizione();
		
		
	}
	
	
	
}
