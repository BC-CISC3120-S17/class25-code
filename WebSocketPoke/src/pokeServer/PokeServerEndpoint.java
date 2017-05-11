package pokeServer;
import java.io.IOException;
import java.util.logging.*;

import javax.websocket.*;

import wsMessages.*;

import javax.websocket.server.ServerEndpoint;

/**
 * What does this server do when it receives a message from a client?
 * 
 * @author sdexter72
 *
 */
 
@ServerEndpoint(value = "/poke",decoders = { MessageDecoder.class }, encoders = {
		PokeMessageEncoder.class, ProdMessageEncoder.class })
public class PokeServerEndpoint {
 
    private Logger logger = Logger.getLogger(this.getClass().getName());
 
    @OnOpen
    public void onOpen(Session peer) {
        logger.info("Connected ... " + peer.getId());
    }
 
    @OnMessage
    public void onMessage(Session peer, Message msg) throws EncodeException {
        logger.log(Level.FINE, "Message {0} from {1}", new Object[]{msg, peer.getId()});

        for (Session other : peer.getOpenSessions()) {
            try {
                other.getBasicRemote().sendObject(msg);
            } catch (IOException ex) {
                Logger.getLogger(PokeServerEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
 
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }
    
 
}
