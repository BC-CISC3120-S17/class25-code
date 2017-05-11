package wsMessages;

public class ProdMessage extends Message {

	private String id;

	public ProdMessage(String id) {
		this.id = id;
	}
	
	public String getID() { return id; }

}
