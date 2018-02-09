import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MyRectangle implements MyShape{
	Rectangle rectangle;
	List<MyShape> childs = new ArrayList<MyShape>();

	public Rectangle getRectangle() {
		return rectangle;
	}
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	@Override
	public void move(double dx, double dy) {
		for(MyShape myShape : this.childs){
			myShape.move(dx, dy);
		}
		rectangle.setX(rectangle.getX() + dx);
		rectangle.setY(rectangle.getY() + dy);
	}
	@Override
	public void add(MyShape shape) {
		if(!childs.contains(shape))
			this.childs.add(shape);
	}
	@Override
	public void remove(MyShape shape) {
		this.childs.remove(shape);
	}
	@Override
	public MyShape getChild(int i) {
		return this.childs.get(i);
	}
	public static int containsPoint(MyShape shape, List<MyRectangle> myRectangles){
		MyCircle circle = (MyCircle)shape;
		boolean found = false;
		int index = -1;
		for(int i=0;i<myRectangles.size();i++){
			if(myRectangles.get(i).getRectangle().contains(circle.getCircle().getCenterX(), circle.getCircle().getCenterY())){
				//Getting Boundry color of Rectangle and Setting that color as fill of Circle
				circle.getCircle().setFill(myRectangles.get(i).getRectangle().getStroke());
				found = true;
				index = i;
			}
		}
		if(!found){
			circle.getCircle().setFill(Color.BLACK);
		}
		return index;
	}
}
