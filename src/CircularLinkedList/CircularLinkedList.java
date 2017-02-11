package CircularLinkedList;

import java.util.Iterator;

public class CircularLinkedList<T> implements Iterable{
	
	private Node<T> sentinel;
	private int size;
	
	public CircularLinkedList(int numberOfSeats){
		size = 0;
		sentinel = new Node<T>(null, 0, null, null);
		
		
		for(int x = 0; x < numberOfSeats; x++){
			
			appendEmptyNode(x);
			
		}
		
	}
	
	public int getSize(){
		return size;
	}
	
	private void appendEmptyNode(int seatNumber){
		
		Node<T> toAdd = new Node<T>(null, seatNumber, null, null);
		if(size == 0){
			sentinel.setNext(toAdd);
			toAdd.setNext(sentinel);
			toAdd.setPrev(sentinel);
		}else{
			toAdd.setNext(sentinel);
			toAdd.setPrev(sentinel.getPrev());
			sentinel.getPrev().setNext(toAdd);
			sentinel.setPrev(toAdd);
		}
		size++;
	}
	
	public void addPlayer(T player, int seatNumber){
		Node<T> temp = sentinel;
		for(int x = 0; x < seatNumber; x++){
			temp = temp.getNext();
			
		}
		if(temp.getPlayer() != null){
			temp.setPlayer(player);
		}
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return new It();
	}
	
	private class It implements Iterator{
		
		Node<T> current;
		int count;
		
		public It(){
			current = sentinel.getNext();
			count = 0;
		}
		
		
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return count<=size;
		}

		@Override
		public T next() {
			// TODO Auto-generated method stub
			T toReturn;
			while(current.getPlayer() == null){
				current = current.getNext();
				count++;
				if(hasNext() == false){
					return null;
				}
			}
			toReturn = current.getPlayer();
			
			
			return toReturn;
		}
		
	}
	

}
