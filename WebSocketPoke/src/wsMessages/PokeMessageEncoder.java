package wsMessages;

import javax.json.*;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class PokeMessageEncoder implements Encoder.Text<PokeMessage> {

	@Override
	public String encode(PokeMessage msg) throws EncodeException {
		JsonObject jsonPokeMessage = Json.createObjectBuilder()
				.add("type","poke")
                .add("ID", msg.getID())
                .build();

        return jsonPokeMessage.toString();
    }	

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

}
