package model;

/**
 * @author Conor James Giles
 *
 */
public class ItemList<T>{
	private ItemList<T> next = null;
	private T object;
	public ItemList(T object) {
		this.object = object;
	}
	public ItemList(T object, ItemList<T> list) {
		this.object = object;
		this.next = list;
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
}
