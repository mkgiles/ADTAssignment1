package model;

/**
 * @author Conor James Giles
 *
 */
public class ItemList<T>{
	private ItemList<T> next = null;
	private T object;
	ItemList(T object) {
		this.object = object;
	}
	ItemList(T object, ItemList<T> list) {
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
	public static <S> void append(ItemList<S> list, S object) {
		ItemList<S> head = list;
		if(head==null)
			list = new ItemList<S>(object);
		while(head.next()!=null) {
			head=head.next();
		}
		head.next = new ItemList<S>(object);
	}
	public static <S> void prepend(ItemList<S> list, S object) {
		list = new ItemList<S>(object, list);
	}
	public static <S> void insert(ItemList<S> list, S object, int index) {
		if(index == 0) {
			prepend(list, object);
		}
		else {
			insert(list.next(),object,--index);
		}
	}
}
