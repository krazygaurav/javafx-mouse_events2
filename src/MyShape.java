
public interface MyShape {
	public void move(double dx, double dy);
	public void add(MyShape shape);
	public void remove(MyShape shape);
	public MyShape getChild(int i);
}
