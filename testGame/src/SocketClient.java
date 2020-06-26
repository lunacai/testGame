
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.net.URLEncoder;
//import java.nio.ByteBuffer;
//import java.nio.channels.SocketChannel;
//import javax.script.Invocable;
//import javax.script.ScriptEngine;
//import javax.script.ScriptEngineManager;
//import javax.script.ScriptException;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class SocketClient extends AbstractJavaSamplerClient {
	private String ip;
	private int port;
	// private String username;
	// private String pwd;
	// private String data;

	// 这个方法是用来自定义java方法入参的。
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("ip", "");
		params.addArgument("port", "");
		// params.addArgument("username", "");
		// params.addArgument("pwd", "");
		// params.addArgument("data", "");
		return params;
	}

	// 每个线程测试前执行一次，做一些初始化工作；
	public void setupTest() {
	}

	// 开始测试，从arg0参数可以获得参数值；
	public SampleResult runTest(JavaSamplerContext arg0) {
		SampleResult sr = new SampleResult();
		ip = arg0.getParameter("ip");
		port = arg0.getIntParameter("port");
		sendMsg(ip, port);
		// username = arg0.getParameter("username", "");
		// pwd = arg0.getParameter("pwd", "");
		// data = arg0.getParameter("data", "");
		// try {
		// String encode = URLEncoder.encode(data, "utf-8");
		// sr.sampleStart();
		// sendMsg(ip, port, username, pwd, encode);
		// sr.setSuccessful(true);
		// } catch (Throwable e) {
		// sr.setSuccessful(false);
		// } finally {
		// sr.sampleEnd();
		// }
		return sr;
	}

	// 测试结束时调用；
	public void teardownTest() {
	}

	private void sendMsg(String ip, int port) {
		System.out.print(ip + ":::::");

	}

	// public static void main(String[] args) {// 120.78.190.245 boomgo.vchushou.com
	// try {
	// } catch (Exception e) {
	//
	// // TODO Auto-generated catch block
	// System.out.println("false");
	// e.printStackTrace();
	// }
	// }
}