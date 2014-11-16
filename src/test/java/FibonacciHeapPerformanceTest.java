import heap.fibonacci.FibonacciHeap;

import java.io.IOException;
import java.util.PriorityQueue;

import junit.framework.TestCase;

public class FibonacciHeapPerformanceTest extends TestCase {
	private final int TESTS = 1000000;
	private final int EXTRACT_FREQUENCY = 5;

	public void testFibAdd() throws IOException{
		FibonacciHeap<Integer> fib = new FibonacciHeap<Integer>();
		long s,e;
		s = System.currentTimeMillis();
		for (int i = 0 ; i < TESTS ; i++) {
			fib.insert(TESTS-i,TESTS-i);
			if(i % EXTRACT_FREQUENCY == 0){
				fib.extractMin();
			}
		}
		e = System.currentTimeMillis();
		System.out.println("fibonacci: " + String.valueOf(e - s));
	}
	
	public void testBinAdd() throws IOException{
		PriorityQueue<Integer> b = new PriorityQueue<Integer>();
		long s,e;
		s = System.currentTimeMillis();
		for (int i = 0 ; i < TESTS ; i++) {
			b.offer(TESTS-i);
			if(i % EXTRACT_FREQUENCY == 0){
				b.poll();
			}
		}
		e = System.currentTimeMillis();
		System.out.println("binary: " + String.valueOf(e - s));
	}
}
