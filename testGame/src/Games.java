import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class Games extends AbstractJavaSamplerClient {
	private String ip;
	private int port;
	private long start;
	private long end;

	// 这个方法是用来自定义java方法入参的。
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("ip", "");
		params.addArgument("port", "");
		return params;
	}

	// 每个线程测试前执行一次，做一些初始化工作；
	public void setupTest() {
		start = System.currentTimeMillis();
	}

	// 开始测试，从arg0参数可以获得参数值；
	public SampleResult runTest(JavaSamplerContext arg0) {
		SampleResult sr = new SampleResult();
		ip = arg0.getParameter("ip");
		port = arg0.getIntParameter("port");
		sr.setSamplerData("请求参数ip：" + ip);
		try {
			sr.sampleStart();
			sendMsg(ip, port);
			sr.setSuccessful(true);

		} catch (Throwable e) {
			sr.setSuccessful(false);
		} finally {
			sr.sampleEnd();
		}
		return sr;
	}

	// 测试结束时调用；
	public void teardownTest() {
		end = System.currentTimeMillis();
		System.err.println("cost time:" + (end - start) + "毫秒");
	}

	private static void sendMsg(String ip, int port) throws Exception {
		InetSocketAddress remote = new InetSocketAddress(ip, port);
		SocketChannel socketChannel = SocketChannel.open(remote);
		System.out.println(socketChannel);
		System.out.println("success");
	}

	// public static void main(String[] args) {
	// try {
	// Games.sendMsg("120.55.34.22", 80);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// System.out.println("false");
	// e.printStackTrace();
	// }
	// }
}
