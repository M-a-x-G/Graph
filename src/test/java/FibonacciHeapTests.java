import junit.framework.Test;
import junit.framework.TestSuite;

public class FibonacciHeapTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for fibonacci heap");
		//$JUnit-BEGIN$
		suite.addTestSuite(FibonacciHeapCorrectnessTest.class);
		suite.addTestSuite(FibonacciHeapPerformanceTest.class);
		//$JUnit-END$
		return suite;
	}
	//vm args -Xms128m -Xmx512m
}