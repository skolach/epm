package web_jstl;

import java.util.HashSet;
import java.util.Iterator;

public class MySet extends HashSet {

	private static final long serialVersionUID = 4014465989789112768L;

	private Iterator it;

	public MySet() {
		this.add("Field #1");
		this.add("Field #2");
		this.add("Field #3");
	}
	public String getSize() {
		it = this.iterator();
		return Integer.toString(this.size());
	}
	public String getElement() {
		return it.next().toString();
	}
}
