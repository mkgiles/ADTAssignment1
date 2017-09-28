package model;

/**
 * @author Conor James Giles
 *
 */
public class ItemList{
	private ItemList next = null;
	private Object object;
	ItemList(Object object) {
		this.object = object;
	}
	ItemList(Object object, ItemList list) {
		this.object = object;
		this.next = list;
	}
	public ItemList next(){
		return next;
	}
	public Object retrieve(){
		return object;
	}
	public int length() {
		if(next == null)
			return 1;
		return next.length() + 1;
	}
	public static void append(ItemList list, Object object) {
		ItemList head = list;
		if(head==null)
			list = new ItemList(object);
		while(head.next()!=null) {
			head=head.next();
		}
		head.next = new ItemList(object);
	}
	public static void prepend(ItemList list, Object object) {
		list = new ItemList(object, list);
	}
	public static void insert(ItemList list, Object object, int index) {
		if(index == 0) {
			prepend(list, object);
		}
		else {
			insert(list.next(),object,--index);
		}
	}
}
