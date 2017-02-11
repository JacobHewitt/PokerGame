package CircularLinkedList;

public class Node<T> {
	
	T player;
	int seat;
	Node<T> next;
	Node<T> prev;
	
	public Node(T data, int seatNumber, Node<T> prev, Node<T> next){
		this.player = data;
		this.seat = seatNumber;
		this.prev = prev;
		this.next = next;
		
	}

	public T getPlayer() {
		return player;
	}

	public void setPlayer(T player) {
		this.player = player;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

	public Node<T> getPrev() {
		return prev;
	}

	public void setPrev(Node<T> prev) {
		this.prev = prev;
	}
	
	public int getSeat(){
		return seat;
	}
	
	public void setSeat(int seat){
		this.seat = seat;
	}

}
