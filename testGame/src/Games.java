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

	// ��������������Զ���java������εġ�
	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		params.addArgument("ip", "");
		params.addArgument("port", "");
		return params;
	}

	// ÿ���̲߳���ǰִ��һ�Σ���һЩ��ʼ��������
	public void setupTest() {
		start = System.currentTimeMillis();
	}

	// ��ʼ���ԣ���arg0�������Ի�ò���ֵ��
	public SampleResult runTest(JavaSamplerContext arg0) {
		SampleResult sr = new SampleResult();
		ip = arg0.getParameter("ip");
		port = arg0.getIntParameter("port");
		sr.setSamplerData("�������ip��" + ip);
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

	// ���Խ���ʱ���ã�
	public void teardownTest() {
		end = System.currentTimeMillis();
		System.err.println("cost time:" + (end - start) + "����");
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
