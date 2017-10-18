package model;

/**
 * @author Conor James Giles
 *
 */
public class ItemList<T extends CSV>{
	private ItemList<T> next = null;
	private T object;
	public ItemList(T object) {
		this.object = object;
	}
	public ItemList(T object, ItemList<T> list) {
		this.object = object;
		this.next = list;
	}
	public ItemList() {
		this.object = null;
		this.next = null;
	}
	public ItemList<T> next(){
		return next;
	}
	public T retrieve(){
		return object;
	}
	public int length() {
		if(next == null)
			return 1;
		return next.length() + 1;
	}
	public void prepend(T object) {
		ItemList<T> temp = new ItemList<T>(object, this);
		this.next = temp.next;
		this.object = temp.object;
	}
	public void append(T object) {
		if(this.next == null) {
			this.next = new ItemList<T>(object);
		}else {
			this.next.append(object);
		}
	}
	public void insert(T object, int index) {
		if(index == 0) {
			this.next = new ItemList<T>(object, this.next);
		}else if(this.next == null){
			this.next = new ItemList<T>(object);
		}else {
			this.next.insert(object, --index);
		}
	}
	public T get(int index) {
		if (index == 0)
			return this.retrieve();
		else
			return this.next().get(--index);
	}
	public int getIndexOf(T object) {
		return getIndexOf(object, 0);
	}
	private int getIndexOf(T object, int index) {
		if(this.object.equals(object))
			return index;
		else if(this.next == null)
			return -1;
		else
			return this.next.getIndexOf(object, ++index);
	}
	public void set(int index, T object) {
		if(index == 0)
			this.object = object;
		else
			this.set(--index, object);
	}
	public void remove(int index) {
		if(index == 0) {
			if(this.next!=null) {
				this.object = this.next.object;
				this.next = this.next.next;
			}
		}
		else {
			if(this.next != null)
				if(this.next.next != null)
					this.next.remove(--index);
				else
					this.next = null;
		}
	}
	public String toCSV(String divider) {
		String str = this.retrieve().toCSV();
		str += this.next() == null?"":(divider + this.next().toCSV(divider));
		return str;
	}
	public String toString() {
		return this.retrieve() + "\n" + (this.next==null?"":this.next);
	}
}
