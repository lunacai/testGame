import java.util.Locale;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.JMeterEngine;
import org.apache.jmeter.engine.JMeterEngineException;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.testelement.property.BooleanProperty;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jmeter.testelement.property.TestElementProperty;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

public class wocketGame {
	private static final Logger log = LoggingManager.getLoggerForClass();

	public static void main(String[] args) {
        
        JMeterEngine engine = new StandardJMeterEngine();
        HashTree config = new ListedHashTree();
        TestPlan testPlan = new TestPlan("websocket test");
        testPlan.setFunctionalMode(false);
        testPlan.setSerialized(false);
        testPlan.setProperty(new BooleanProperty(TestElement.ENABLED, true));
        testPlan.setUserDefinedVariables(new Arguments());

        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setNumThreads(300);
        threadGroup.setRampUp(20);
        threadGroup.setDelay(0);
        threadGroup.setDuration(0);
        threadGroup.setProperty(new StringProperty(ThreadGroup.ON_SAMPLE_ERROR, "continue"));
        threadGroup.setScheduler(false);
        threadGroup.setName("Group1");
        threadGroup.setProperty(new BooleanProperty(TestElement.ENABLED, true));

        LoopController controller = new LoopController();
        controller.setLoops(10);
        controller.setContinueForever(false);
        controller.setProperty(new BooleanProperty(TestElement.ENABLED, true));
        threadGroup.setProperty(new TestElementProperty(ThreadGroup.MAIN_CONTROLLER, controller));


        WebSocketSampler sampler = new WebSocketSampler();
        sampler.setName("WebSocket Test");
        sampler.setProperty(new BooleanProperty(TestElement.ENABLED, true));
        sampler.setContentEncoding("UTF-8");
        sampler.setProtocol("wss");
        sampler.setDomain("boomgo.vchushou.com");
        sampler.setPort(20013);
        sampler.setPath("/", "UTF-8");
        sampler.setSendMessage("${__RandomString(50,ABCDEFGHIJKLMNOPQRSTUVWXYZ)}");
        sampler.setRecvMessage("\"name\":\"${USER_NAME}\"");


        Summariser summariser = new Summariser();

        HashTree tpConfig = config.add(testPlan);
        HashTree tgConfig = tpConfig.add(threadGroup);

        HashTree samplerConfig = tgConfig.add(sampler);
        samplerConfig.add(summariser);

        engine.configure(config);
        try {
			engine.runTest();
		} catch (JMeterEngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
