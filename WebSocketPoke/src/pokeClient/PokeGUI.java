package pokeClient;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import javax.swing.*;
import javax.websocket.*;

import org.glassfish.tyrus.client.ClientManager;

import wsMessages.*;

/**
 * This class combines (a bit awkwardly) GUI setup code and WebSockets
 * client-endpoint code. Note that most of the user interaction is handled by
 * the MessagePanel class. Note how the onMessage() method decides what to do
 * depending on the type of message received.
 * 
 * @author sdexter72
 *
 */

@ClientEndpoint(decoders = { MessageDecoder.class }, encoders = {
		PokeMessageEncoder.class, ProdMessageEncoder.class })
public class PokeGUI {
	private static CountDownLatch latch;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private static MessagePanel messageArea;

	@OnOpen
	public void onOpen(Session session) {
		logger.info("Connected ... " + session.getId());
		try {
			session.getBasicRemote().sendText("start");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@OnMessage
	public void onMessage(Session session, Message message) {
		logger.info("Received ...." + message.toString());

		if (message instanceof PokeMessage) {
			messageArea.receivePoke((PokeMessage) message);
		} else if (message instanceof ProdMessage) {
			messageArea.receiveProd((ProdMessage) message);
		}
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s close because of %s",
				session.getId(), closeReason));
		latch.countDown();
	}

	public static void main(String[] args) {
		latch = new CountDownLatch(1);

		Session peer;
		ClientManager client = ClientManager.createClient();
		try {
			peer = client.connectToServer(PokeGUI.class, new URI(
					"ws://localhost:8025/websockets/poke"));
			createAndShowGUI(peer);
			latch.await();

		} catch (DeploymentException | URISyntaxException
				| InterruptedException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void createAndShowGUI(Session session) {
		JFrame frame = new JFrame("Poke");

		JLabel idLabel = new JLabel("Your id:");
		JTextField idField = new JTextField(String.valueOf(Math.round(Math
				.random() * 100000)), 10);
		idField.setEditable(true);

		JButton pokeButton = new JButton("Poke");
		JButton prodButton = new JButton("Prod");

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(idLabel);
		buttonPanel.add(idField);
		buttonPanel.add(pokeButton);
		buttonPanel.add(prodButton);

		frame.add(buttonPanel, BorderLayout.NORTH);

		messageArea = new MessagePanel(session, idField, pokeButton, prodButton);

		frame.add(messageArea, BorderLayout.WEST);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setVisible(true);
	}
}
