import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Canvas extends Application {

	Pane myPane;
	Scene scene;
	AnchorPane root;
	Point2D lastPosition;
	List<MyCircle> myCircles = new ArrayList<MyCircle>();
	List<MyRectangle> myRectangles = new ArrayList<MyRectangle>();
	boolean mouseDragged = false;
	int index = -1;
	MyCircle subject = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		root = new AnchorPane();
		scene = new Scene(root, 600, 600);
		scene.setFill(Color.WHITE);

		scene.setOnMouseClicked(mouseHandler);
		scene.setOnMouseDragged(mouseHandler);
		scene.setOnMousePressed(mouseHandler);
		scene.setOnMouseReleased(mouseHandler);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void createCircle(double x, double y) {
		Circle circle = new Circle(x, y, 20);
		circle.setFill(Color.BLACK);
		circle.setOnMouseDragged(mouseHandler);
		circle.setOnMousePressed(mouseHandler);
		MyCircle newCircle = new MyCircle();
		newCircle.setCircle(circle);
		root.getChildren().add(newCircle.getCircle());
		myCircles.add(newCircle);
	}

	public void createRectangle(double x, double y) {
		Rectangle rectangle = new Rectangle(x, y, 150, 100);
		rectangle.setFill(Color.WHITE);
		rectangle.setOnMouseDragged(mouseHandler);
		rectangle.setOnMousePressed(mouseHandler);
		Random random = new Random();
		rectangle.setStroke(new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1));
		MyRectangle newRectangle = new MyRectangle();
		newRectangle.setRectangle(rectangle);
		root.getChildren().add(newRectangle.getRectangle());
		myRectangles.add(newRectangle);
	}

	EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
		// @Override
		public void handle(MouseEvent mouseEvent) {
			Point2D clickPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
			String eventName = mouseEvent.getEventType().getName();
			System.out.println(eventName);
			
			switch (eventName) {
			case ("MOUSE_CLICKED"):
				System.out.println("Clicked - "+mouseDragged);
				if(mouseDragged == false){
					if (mouseEvent.getButton() == MouseButton.PRIMARY) {
						createCircle(clickPoint.getX(), clickPoint.getY());
					} else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
						createRectangle(clickPoint.getX(), clickPoint.getY());
					}
				}else{
					//If index == -1 that means Circle is inside i(th) rectangle
					if(index != -1){
						System.out.println("INSIDE");
						myRectangles.get(index).add(subject);
						index = -1;
						subject = null;
					}else{
						if(subject != null){
							subject.getCircle().setFill(Color.BLACK);
							for(MyRectangle myRectangle : myRectangles){
								if(myRectangle.childs.size() != 0 && myRectangle.childs.contains(subject))
									myRectangle.childs.remove(subject);
							}
						}
					}
				}
				break;
			case ("MOUSE_DRAGGED"):
				mouseDragged = true;
				System.out.println("Dragged - "+mouseDragged);
				if (lastPosition != null) {
					double delataX = clickPoint.getX() - lastPosition.getX();
					double delataY = clickPoint.getY() - lastPosition.getY();
					for (MyRectangle myRectangle : myRectangles) {
						if (mouseEvent.getSource() == myRectangle.getRectangle()) {
							myRectangle.getRectangle().toBack();
							myRectangle.move(delataX, delataY);
							break;
						}
					}
					for (MyCircle myCircle : myCircles) {
						if (mouseEvent.getSource() == myCircle.getCircle()) {
							myCircle.getCircle().toFront();
							myCircle.move(delataX, delataY);
							index = MyRectangle.containsPoint(myCircle, myRectangles);
							subject = myCircle;
							break;
						}
					}
				}
				break;
			case ("MOUSE_PRESSED"):
				mouseDragged = false;
				System.out.println("Pressed - "+mouseDragged);
				lastPosition = null;
				for (MyRectangle myRectangle : myRectangles) {
					if (mouseEvent.getSource() == myRectangle.getRectangle()) {
						System.out.println("Got Rectangle");
					}
				}
				for (MyCircle myCircle : myCircles) {
					if (mouseEvent.getSource() == myCircle.getCircle()) {
						System.out.println("Got Circle");
					}
				}
				break;
			}
			lastPosition = clickPoint;
		}
	};
	public static void main(String[] args) {
		launch(args);
	}
}
