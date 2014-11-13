package de.fhb.graph.utility;

/**
 * Reference to node in Fibonacci heap
 * @param <T>
 */
public interface INode<T> {
	public int key();
	public T value();
}
