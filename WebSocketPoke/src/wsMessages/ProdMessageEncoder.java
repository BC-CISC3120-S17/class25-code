package wsMessages;

import javax.json.*;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ProdMessageEncoder implements Encoder.Text<ProdMessage> {

	@Override
	public String encode(ProdMessage msg) throws EncodeException {
		JsonObject jsonProdMessage = Json.createObjectBuilder()
				.add("type","prod")
                .add("ID", msg.getID())
                .build();

        return jsonProdMessage.toString();
    }	

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

}
