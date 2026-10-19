
public class LinkedList<T>  {

	public Node<T> head;
	public Node<T> current;

	public LinkedList() {
		head = current = null;
	}

	public boolean empty() {
		return head == null;
	}

	public boolean full() {
		return false;
	}

	public boolean last() {
		return current.next == null;
	}

	public void findFirst() {

		current = head;
	}

	public void findNext() {
		current = current.next;
	}

	public T retrieve() {
		return current.data;
	}

	public void update(T var) {
		current.data = var;
	}

	public void insert(T var) {
		Node<T> tmp;

		if (empty()) {
			current = head = new Node<T>(var);
		}

		else {

			tmp = current;
			current.next = new Node<T>(var);
			current = current.next;
			tmp.next = current;

		}

	}

	public void remove() {
		if (head == current)
			head = head.next;

		else {
			Node<T> tmp;
			tmp = head;
			while (tmp.next != current)
				tmp = tmp.next;
			tmp.next = current.next;

		}
		if (current.next == null)
			current = head;
		else
			current = current.next;
	}

	public boolean serach(T e) {

		findFirst();
		if (!(empty())) {

			while (!last()) {
				if (retrieve().equals(e)) {
					return true;
				}
				findNext();

			}
		}
		return false;

	}

}
