import javax.websocket.*;

@ClientEndpoint()
public class client {
	Session session;

    @OnOpen
    public void open(Session session){
        this.session = session;
    }
    
    @OnMessage
    public void onMessage(String message) {
        System.out.println("Client onMessage: " + message);
    }
    @OnClose
    public void onClose() {}
}