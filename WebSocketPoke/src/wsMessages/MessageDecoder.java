package wsMessages;

import java.io.StringReader;

import javax.json.*;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {

	@Override
	public Message decode(String msg) throws DecodeException {
		JsonObject jsonObject = Json.createReader(new StringReader(msg))
				.readObject();

		if (jsonObject.getString("type").equals("poke")) {
			PokeMessage message = new PokeMessage(jsonObject.getString("ID"));
			return message;
		} else if (jsonObject.getString("type").equals("prod")) {
			ProdMessage message = new ProdMessage(jsonObject.getString("ID"));
			return message;
		} else throw new DecodeException(msg,"Neither poke nor prod.");

	}

	/**
	 * Check to see if incoming message is valid JSON
	 */

	@Override
	public boolean willDecode(String msg) {
		try {
			Json.createReader(new StringReader(msg)).readObject();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

}
