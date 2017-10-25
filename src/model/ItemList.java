package model;

/**
 * The Class ItemList.
 *
 * @author Conor James Giles
 * @param <T>
 *            the generic type
 */
public class ItemList<T extends CSV> {

	/** The next item in the list. (cdr)*/
	private ItemList<T> next = null;

	/** The object containing in the head of the list. (car) */
	private T object;

	/**
	 * Instantiates a new item list.
	 *
	 * @param object
	 *            the object
	 */
	public ItemList(T object) {
		this.object = object;
	}

	/**
	 * Instantiates a new item list.
	 *
	 * @param object
	 *            the object
	 * @param list
	 *            the list
	 */
	public ItemList(T object, ItemList<T> list) {
		this.object = object;
		this.next = list;
	}

	/**
	 * Instantiates a new item list.
	 */
	public ItemList() {
		this.object = null;
		this.next = null;
	}

	/**
	 * Move to the next node in the list.
	 *
	 * @return the item list
	 */
	public ItemList<T> next() {
		return next;
	}

	/**
	 * Retrieve the item at the head of the list..
	 *
	 * @return the t
	 */
	public T retrieve() {
		return object;
	}

	/**
	 * Returns the length of the list.
	 *
	 * @return the int
	 */
	public int length() {
		if (next == null)
			return 1;
		return next.length() + 1;
	}

	/**
	 * Prepend.
	 *
	 * @param object
	 *            the object
	 */
	public void prepend(T object) {
		ItemList<T> temp = new ItemList<T>(object, this);
		this.next = temp.next;
		this.object = temp.object;
	}

	/**
	 * Append.
	 *
	 * @param object
	 *            the object
	 */
	public void append(T object) {
		if (this.next == null) {
			this.next = new ItemList<T>(object);
		} else {
			this.next.append(object);
		}
	}

	/**
	 * Insert.
	 *
	 * @param object
	 *            the object
	 * @param index
	 *            the index
	 */
	public void insert(T object, int index) {
		if (index == 0) {
			this.next = new ItemList<T>(object, this.next);
		} else if (this.next == null) {
			this.next = new ItemList<T>(object);
		} else {
			this.next.insert(object, --index);
		}
	}

	/**
	 * Gets the object at a given index.
	 *
	 * @param index
	 *            the index
	 * @return the t
	 */
	public T get(int index) {
		if (index == 0)
			return this.retrieve();
		if(this.next() == null)
			return null;
		else
			return this.next().get(--index);
	}

	/**
	 * Gets the index of an object, returns minus one if not found.
	 *
	 * @param object
	 *            the object
	 * @return the index of
	 */
	public int getIndexOf(T object) {
		return getIndexOf(object, 0);
	}

	/**
	 * Internal iter-recursive method for getIndexOf.
	 *
	 * @param object
	 *            the object
	 * @param index
	 *            the index
	 * @return the index of
	 */
	private int getIndexOf(T object, int index) {
		if (this.object.equals(object))
			return index;
		else if (this.next == null)
			return -1;
		else
			return this.next.getIndexOf(object, ++index);
	}

	/**
	 * Sets the element at a given index to the given object.
	 *
	 * @param index
	 *            the index
	 * @param object
	 *            the object
	 */
	public void set(int index, T object) {
		if (index == 0)
			this.object = object;
		else
			this.set(--index, object);
	}

	/**
	 * Removes the node at a given index.
	 *
	 * @param index
	 *            the index
	 */
	public void remove(int index) {
		if (index == 0) {
			if (this.next != null) {
				this.object = this.next.object;
				this.next = this.next.next;
			}
		} else {
			if (this.next != null)
				if (this.next.next != null)
					this.next.remove(--index);
				else
					this.next = null;
		}
	}

	/**
	 * To CSV method for creating a CSV out of a list of CSV interface extending entities.
	 *
	 * @param divider
	 *            the divider
	 * @return the string
	 */
	public String toCSV(String divider) {
		String str = this.retrieve().toCSV();
		str += this.next() == null ? "" : (divider + this.next().toCSV(divider));
		return str;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.retrieve() + "\n" + (this.next == null ? "" : this.next);
	}
}
