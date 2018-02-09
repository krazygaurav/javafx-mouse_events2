import javafx.scene.shape.Circle;

public class MyCircle implements MyShape{
	Circle circle;
	
	public Circle getCircle() {
		return circle;
	}
	public void setCircle(Circle circle) {
		this.circle = circle;
	}
	@Override
	public void move(double dx, double dy) {
		circle.setCenterY(circle.getCenterY() + dy);
		circle.setCenterX(circle.getCenterX() + dx);
	}
	@Override
	public void add(MyShape shape) {
		// TODO Auto-generated method stub
	}
	@Override
	public void remove(MyShape shape) {
		// TODO Auto-generated method stub
	}
	@Override
	public MyShape getChild(int i) {
		// TODO Auto-generated method stub
		return null;
	}
}
