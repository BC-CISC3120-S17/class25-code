package wsMessages;

public class PokeMessage extends Message {

	String id; 
	
	public PokeMessage(String id) {
		this.id = id;
	}
	
	public String getID() { return id; }
}
