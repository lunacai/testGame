import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.Arrays;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.apache.log4j.Logger;

@SuppressWarnings("restriction")
public class WebSocket extends AbstractJavaSamplerClient {
	private String ip;
	private String port;
	private String username;
	private String pwd;
	private String data;
	private static Logger log;
	
	// ��������������Զ���java������εġ�
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("ip", "");
		params.addArgument("port", "");
		params.addArgument("username", "");
		params.addArgument("pwd", "");
		params.addArgument("data", "");
		return params;
	}

	// ÿ���̲߳���ǰִ��һ�Σ���һЩ��ʼ��������
	public void setupTest() {
	}

	// ��ʼ���ԣ���arg0�������Ի�ò���ֵ��
	public SampleResult runTest(JavaSamplerContext arg0) {
		SampleResult sr = new SampleResult();
		ip = arg0.getParameter("ip");
		port = arg0.getParameter("port");
		username = arg0.getParameter("username", "");
		pwd = arg0.getParameter("pwd", "");
		data = arg0.getParameter("data", "");
		try {
			String encode = URLEncoder.encode(data, "utf-8");
			sr.sampleStart();
			System.out.println("start");
			sendMsg(ip, port, username, pwd, encode);
			System.out.println("end");
			sr.setSuccessful(true);
		} catch (Throwable e) {
			sr.setSuccessful(false);
		} finally {
			sr.sampleEnd();
		}
		return sr;
	}

	// ���Խ���ʱ���ã�
	public void teardownTest() {
	}

	public static byte[] analyJss(String username, String pwd, String data, String msg, String names) {
		// File directory = new File("");
		byte[] bytes = null;
		FileReader reader;
		try {
			Object cx = null;
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
			engine.eval("function hello() {var buffer = new ArrayBuffer(5);var view1 = new DataView(buffer);\n"
					+ "view1.setInt8(0, -12); // put 42 in slot 12\n" + "view1.setInt8(1, 73); // put 42 in slot 12\n"
					+ "view1.setInt8(2, 13); // put 42 in slot 12\n" + "\n"
					+ "var int8Bytes = new Int8Array( buffer );\n" + "var originalBytes = [];\n"
					+ "for (var i = 0; i < int8Bytes.length; i++) {\n" + "  originalBytes[i] = int8Bytes[i];\n"
					+ "}; return originalBytes;};");
			// directory.getAbsolutePath() +
			String jsFileName = "E:\\canace\\tools\\apache-jmeter-2.13\\bin\\boomJSAPI2.js"; // ��ȡjs�ļ�
			reader = new FileReader(jsFileName);
			// ִ��ָ���ű�
			engine.eval(reader);
			if (names.equals("getInitData")) {
				if (engine instanceof Invocable) {
					Invocable invoke = (Invocable) engine; // ����merge��������������������
					cx = invoke.invokeFunction("getInitData", msg);
				}
			} else if (names.equals("getImportData")) {
				if (engine instanceof Invocable) {
					Invocable invoke = (Invocable) engine; // ����merge��������������������
					cx = invoke.invokeFunction("getImportData");
				}
			} else if (names.equals("getLoginData")) {
				if (engine instanceof Invocable) {
					Invocable invoke = (Invocable) engine; // ����merge���������������
					cx = invoke.invokeFunction("getLoginData", username, pwd, data);
				}
			} else if (names.equals("getLoginBaseData")) {
				if (engine instanceof Invocable) {
					Invocable invoke = (Invocable) engine; // ����merge���������������
					cx = invoke.invokeFunction("getLoginBaseData", username, pwd);
				}
			} else if (names.equals("getBaseImportData")) {
				if (engine instanceof Invocable) {
					Invocable invoke = (Invocable) engine;
					cx = invoke.invokeFunction("getBaseImportData", "");
				}
			}
			System.out.println(cx);
			ScriptObjectMirror result = (ScriptObjectMirror) cx;
			bytes = new byte[result.values().size()];
			int i = 0;
			for (Object value : result.values()) {
				bytes[i++] = (byte) Integer.parseInt(String.valueOf(value));
			}
			System.out.println(names + "����byte:::" + Arrays.toString(bytes));
//			log.info(names + "����byte:::" + Arrays.toString(bytes));
			reader.close();
		} catch (NoSuchMethodException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}

	private static void sendMsg(String ip, String port, String username, String pwd, String data)
			throws Exception {
		System.out.println(ip);
		WebSocketContainer conmtainer = ContainerProvider.getWebSocketContainer();
		client client = new client();
		String url = "wss://" + ip + ":" + port + "/websocket/chat?query=Picasso";
		conmtainer.connectToServer(client, new URI(url));
		System.out.println("223");
//		log.info("url:"+url);
		try {

			// ��ͨ��getImportData�����Ϸ��ʼ���ݣ����ͷ�����
			byte[] bytess = analyJss("", "", "", "", "getImportData");
			ByteBuffer byteBuffer = ByteBuffer.wrap(bytess);
			client.session.getBasicRemote().sendBinary(byteBuffer);
			while (client.session.getBasicRemote() == null) {
				Thread.sleep(1000);
			}
			System.out.println("1:" + client.session.getBasicRemote());

			// ��һ��ͨ��getInitData("loginapp")
			byte[] bytes3 = analyJss("", "", "", "loginapp", "getInitData");
			ByteBuffer byteBuffer3 = ByteBuffer.wrap(bytes3);
			client.session.getBasicRemote().sendBinary(byteBuffer3);
			while (client.session.getBasicRemote() == null) {
				Thread.sleep(1000);
			}
			System.out.println("2:" + client.session.getBasicRemote());

			// ��һ��ͨ��getBaseImportData("")
			byte[] bytes2 = analyJss("", "", "", "", "getBaseImportData");
			ByteBuffer byteBuffer2 = ByteBuffer.wrap(bytes2);
			client.session.getBasicRemote().sendBinary(byteBuffer2);
			while (client.session.getBasicRemote() == null) {
				Thread.sleep(1000);
			}
			System.out.println("3:" + client.session.getBasicRemote());

			// ͨ��getLoginData(_userName, _userPass, _data)��õ������ݷ��͵�������
			byte[] bytes1 = analyJss(username, pwd, data, "", "getLoginData");
			ByteBuffer byteBuffer1 = ByteBuffer.wrap(bytes1);
			client.session.getBasicRemote().sendBinary(byteBuffer1);
			Thread.sleep(1000);
			System.out.println("4:" + client.session.getBasicRemote());

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("1");
		}

	}

	public static void main(String[] args) {
		String ip = "boomgo.vchushou.com";
		String port = "20013";
		String data = "{\"platform\":\"web\",\"os\":\"web\",\"ip\":\"183.129.155.243\",\"accessToken\":\"\"}";
		try {
			WebSocket.sendMsg(ip, port,  "usernae", "123456", data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
