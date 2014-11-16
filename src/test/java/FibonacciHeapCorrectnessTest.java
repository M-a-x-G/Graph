
import java.util.PriorityQueue;
import java.util.Random;

import junit.framework.TestCase;

import de.fhb.graph.utility.FibonacciHeap;
import de.fhb.graph.utility.INode;

public class FibonacciHeapCorrectnessTest extends TestCase {
	
	public void test1(){
		FibonacciHeap<Integer> f = new FibonacciHeap<>();
		assertEquals(true, f.isEmpty());
		f.insert(1,1);
		assertEquals(false, f.isEmpty());
		f.extractMin();
		assertEquals(true, f.isEmpty());
	}
	
	public void test2(){
		FibonacciHeap<Integer> f = new FibonacciHeap<>();
		f.insert(1,1);
		INode<Integer> i = f.extractMin();
		assertEquals(1, i.value().intValue());
		f.insert(2,2);
		f.insert(-1,-1);
		i = f.extractMin();
		assertEquals(-1, i.value().intValue());
		assertEquals(false, f.isEmpty());
		i = f.extractMin();
		assertEquals(2, i.value().intValue());
		assertEquals(true, f.isEmpty());
	}
	
	public void test3(){
		FibonacciHeap<Integer> f = new FibonacciHeap<>();
		for (int i = 0; i < 100; i++) {
			f.insert(i,i);
			assertEquals(i, f.extractMin().value().intValue());
		}
	}
	
	public void test4(){
		FibonacciHeap<Integer> f = new FibonacciHeap<>();
		PriorityQueue<Integer> b = new PriorityQueue<>();
		int tests = 500;
		Random r = new Random();
		int num ;
		for (int i = 0; i < tests; i++) {
			num = r.nextInt(100);
			f.insert(num,num);
			b.add(num);
		}
		boolean equals;
		for (int i = 0; i < tests; i++) {
			equals = f.extractMin().value().equals(b.poll());
			assertEquals(true, equals);
		}
		assertEquals(true, f.isEmpty());
		assertEquals(true, b.isEmpty());
		assertEquals(null, f.extractMin());
	}
}
