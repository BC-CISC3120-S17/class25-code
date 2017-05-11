package pokeClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import wsMessages.PokeMessage;
import wsMessages.ProdMessage;


public class MessagePanel extends JPanel implements ActionListener {
	
	private JTextField id;
	private JButton poke;
	private JButton prod;
	Session session;
	
	
	JTextArea messageArea;
	private String myId;
	
	public MessagePanel(Session session, JTextField id, JButton poke, JButton prod) {
		this.session = session;
		this.id = id;
		this.poke = poke;
		this.prod = prod;
		myId = id.getText();	
		
		id.addActionListener(this);
		poke.addActionListener(this);
		prod.addActionListener(this);

		messageArea = new JTextArea(10, 40);
		JScrollPane scroller = new JScrollPane(messageArea);
		this.add(scroller);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == id)
			myId = id.getText();
		else if (e.getSource() == poke) {
			doPoke();
		}
		else if (e.getSource() == prod) {
			doProd();
		}

	}

	private void doPoke() {
		PokeMessage pokeMsg = new PokeMessage(myId);
		try {
			session.getBasicRemote().sendObject(pokeMsg);
		} catch (IOException | EncodeException e) {
			System.err.println("Problem with sending a poke-message.");
		}
		
	}


	private void doProd() {
		ProdMessage prodMsg = new ProdMessage(myId);
		try {
			session.getBasicRemote().sendObject(prodMsg);
		} catch (IOException | EncodeException e) {
			System.err.println("Problem with sending a poke-message.");
		}
		
		
	}

	public void receivePoke(PokeMessage pokeMsg) {
		messageArea.append(pokeMsg.getID() + " poked.\n");
		
	}
	
	public void receiveProd(ProdMessage prodMsg) {
		messageArea.append(prodMsg.getID() + " prodded.\n");
		
	}

}
