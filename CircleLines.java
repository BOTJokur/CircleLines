
import javafx.application.Application; //All the things I imported
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

public class Assignment2 extends Application { //Lines 22 = 40 are for declaring all my variables
	private double M = 2.0;		//M is the multiplier, made it a double as I am going to add an increment to it. 2 is the default value.
	private int N = 10;			//N is 10. N is the amount of dots. N is an Int as it will never be a have a decimal. 10 is the default value
	private int N_end = 100;	//N_end is the number of dots the program will end at. 100 is the default value.1
	private int add_dot;		//add_dot is to give the program the ability to add a dot or not.
	private double M_end = 20.0;	//M_end is the M value the program will end at. 20.0 is the default value.
	private double M_increment = 0.1;	//M_increment is the value at which M will increase by. 0.1 is the default value.
	private final int radius = 250;		//radius of the circle. This value will never change.
	private final int radius_dot = 3;	//radius of the dots. This value will never change.
	private long oldTime = 0;			
	private String N_Input, N_end_input, TM_Input, TM_Increment_Input, TM_end_Input; //The the strings of what the user inputs into the text fields
	private Group shapes;		//the group with the lines, dots and circle
	private Scene scene;		
	private HBox root;			//The HBox with all the buttons and the shapes
	private VBox buttonLayout;	//The layout of which the GUI will be in
	private Button Draw_N, Draw_N_end, Draw_M_Next, Draw_Changing_M;	//The buttons that when pressed will do their specifc command based on the values in the text field
	private Label LabelN, LabelN_end, LabelCurrent_N, LabelM, LabelDraw_Next, LabelM_end, LabelCurrent_M;	//All the labels in my GUI
	private TextField TDraw_N, TDraw_N_end, TM, TM_Increment, TM_end;	//All the text fields in my GUI
	private Separator s1, s2, s3;	//All the separators in my GUI
	private final Border border = new Border
			(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, null, null)); //The border to my GUI
	
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception { 
		root = new HBox(); //the top layer HBox for my GUI and shapes
		shapes = new Group();	//A group to put my lines, dots, and circle
		buttonLayout = new VBox();	//VBox for my GUI


		//settings for my VBox 
		buttonLayout.setSpacing(5); //spacing in between objects on my GUI is 5 pixels
		buttonLayout.setPrefWidth(150); //width of my VBox is 150 pixels
		buttonLayout.setBorder(border);	//setting the border of the VBox to the one 1 I created.

		//buttons
		Draw_N = new Button("Draw N");				
		Draw_N_end = new Button("Draw N to End");
		Draw_M_Next = new Button("Draw to Next M");
		Draw_Changing_M = new Button("Draw Changing M");

		//setting the button's width
		Draw_N.setPrefWidth(150); //all the has width of 150 pixels as thats the width of the VBox
		Draw_N_end.setPrefWidth(150);
		Draw_M_Next.setPrefWidth(150);
		Draw_Changing_M.setPrefWidth(150);

		//labels
		LabelN = new Label("N:");
		LabelN_end = new Label("End N:");
		LabelCurrent_N = new Label("Current N = " + N); //The label changes when N changes.
		LabelM = new Label("M:");
		LabelDraw_Next = new Label("M Increment:");
		LabelM_end = new Label("End M:");
		LabelCurrent_M = new Label("Current M = " + M);	//The label changes when M changes.

		//text fields
		TDraw_N = new TextField("10");	//all the predefined inputs are the default values
		TDraw_N_end = new TextField("100");
		TM = new TextField("2");
		TM_Increment = new TextField("0.1");
		TM_end = new TextField("20");

		//setting the textfield's width
		TDraw_N.setMaxWidth(150); //all the widths are 150 as that is the max.
		TDraw_N_end.setMaxWidth(150);
		TM.setMaxWidth(150);
		TM_Increment.setMaxWidth(150);
		TM_end.setMaxWidth(150);

		//separators + their widths
		s1 = new Separator();	//all the widths are 150 as that is the max.
		s1.setPrefWidth(150);
		s2 = new Separator();
		s2.setPrefWidth(150);
		s3 = new Separator();
		s3.setPrefWidth(150);


		AnimationTimer timer = new AnimationTimer() {
			public void handle(long time) {
				if (time - oldTime >= 1e8) { //updates the scene every 1*10^8 nanoseconds (1/10 seconds)
					upDate();
					oldTime = time;
				}
			}
		};
		
		//does this whenever the Draw_N button is pressed.
		Draw_N.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				timer.stop(); //stops the timer thereby stopping any animation.
				getData();	//gets data from the text fields.
				Draw_Noanime(); //draws the image.
			}
		});
		
		//does this whenever the Draw_N_end button is pressed.
		Draw_N_end.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				timer.start(); //starts the timer thereby starting the animation.
				getData();		//gets data from the text fields.
				M_increment = 0;	//prevents the M from changing.
				add_dot = 1;		//allows N to change.
				upDate();			//draws the image (animation).
			}
		});
		
		//Does this whenever the Draw_M_Next button is pressed.
		Draw_M_Next.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				add_dot = 0;	//Doesn't allow the N to change.
				getData();		//gets data from the text fields.
				M_end = M+1;	//Sets M_end to the M+1.
				timer.start();	//starts the animation.
				upDate();		//draws the image (animation).
			}
		});
		
		//Does this whenever the Draw_Changing_M button is pressed
		Draw_Changing_M.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				timer.start();		//starts the timer
				add_dot = 0;		//Doesn't allow the N to change.
				getData();			//gets Data from the text fields.
				upDate();			//draws the image (animation)
			}
		});
		

		circle();		//draws the circle even when no buttons are pressed
		
		buttonLayout.getChildren().addAll(LabelN, TDraw_N, Draw_N, s1, LabelN_end, TDraw_N_end, 
				LabelCurrent_N, Draw_N_end, s2, LabelM, TM, LabelDraw_Next, TM_Increment, Draw_M_Next,
				s3, LabelM_end, TM_end, Draw_Changing_M, LabelCurrent_M);
		 		//Adds all these objects to the GUI
		
		root.getChildren().addAll(buttonLayout, shapes);	//adds all the objects to the HBox
		
		scene = new Scene(root, 800, 800, Color.BLANCHEDALMOND);	
		//Creates a 800x800 window with a background colour blanched Almond
		
		primaryStage.setTitle("CIRCLE PATTERNS ASSIGNMENT (ICS ASSIGNMENT 2)");		
		//Sets title as CIRCLE PATTERNS ASSIGNMENT (ICS ASSIGNMENT 2).
		
		primaryStage.setScene(scene);	
		primaryStage.show();
	}


	private void upDate() {		//does this whenever the timer starts and the method is called
	
		if (M < M_end && N < N_end) {		//only updates the image if M is less than the 
			shapes.getChildren().clear();	//clears the circle, dots and lines
			buttonLayout.getChildren().clear();	//clears the GUI
			M+=M_increment;		//adds M by the increment
			N+=add_dot;			//adds N by 1
			circle();			//draws the circle
			dots();				//draws the dots
			line();				//draws the lines
			LabelCurrent_N.setText("Current N = " + N);		//changes the current N indicator on the GUI
			LabelCurrent_M.setText("Current M = " + M);		//changes the current M indicator on the GUI
			buttonLayout.getChildren().addAll(LabelN, TDraw_N, Draw_N, s1, LabelN_end, TDraw_N_end, 
					LabelCurrent_N, Draw_N_end, s2, LabelM, TM, LabelDraw_Next, TM_Increment, Draw_M_Next,
					s3, LabelM_end, TM_end, Draw_Changing_M, LabelCurrent_M); 
			/*Adds all these objects to the GUI
			I must add all the objects to update the values of LabelCurrent_N and LabelCurrent_M*/			
			
		}
	}	


	private double[] points_x(int N, int radius) {	//finds the x coordinates of the dots.
		double theta = Math.toRadians(360.0/N);	/*divides the 360 degrees of the circle by the amount 
															of dots. Then turns the value into radians*/
		double[] points_x = new double[N+1];	//makes a list for the x coordinates.
		double temp = 0;	//temporary variable to move each coordinate into the list.
		
		for (int i = 0; i < points_x.length-1; i++) {	//loop to find each x coordinate
			temp = radius*Math.cos(i*theta-Math.PI)+400;	/*finds the coordinate by multiplying the radius 
						by the cosine of i*theta - pi. I also add 400 to put the dots on the circle itself*/
			points_x[i] = temp; 	//adds the coordinate to the list of x coordinates
		}
		return points_x;
	}
	
	private double[] points_y(int N, int radius) {	//finds the y coordinates of the dots
		double theta = Math.toRadians(360.0/N);	/*divides the 360 degrees of the circle by 
												the amount of dots. Then turns the value into radians*/
		double[] points_y = new double[N+1];	//makes a list of y coordinates.
		double temp = 0;		//temporary variable to move each coordinate into the list. 
		
		for (int i = 0; i < points_y.length-1; i++) {
			temp = radius*Math.sin(i*theta-Math.PI)+400;	/*finds the coordinate by multiplying the radius
						by the sine of i*theta - pi. I also add 400 to put the dots on the circle itself*/
			points_y[i] = temp; 			//adds the coordinate to the list of y coordinates
		}
		return points_y;

	}


	private void line() {			//draws the lines
		double[] x = new double[N+1];	//creates a list for the x coordinate points of the dots
		double[] y = new double[N+1];	//creates a list for the y coordinate points of the dots
		y = points_y(N, radius);		//calls the method to generate the y coordinates
		x = points_x(N, radius);		//calls the method to generate the x coordinates
		
		for (int i = 0; i < N; i++) {		//for loop to run through all the points0
			int end_dot = (int) (i*M)%N;	/*finds the end dot of the line by using the formula 
											(i(dot number) * Multiplier(M))% amount of dots (N)*/
			Line line = new Line();			//creates a new line object
			line.setStartX(x[i]);			//sets the starting x coordinate
			line.setStartY(y[i]);			//sets the starting y coordinate
			line.setEndX(x[end_dot]);		//sets the final x coordinate
			line.setEndY(y[end_dot]);		//sets the final y coordinate
			shapes.getChildren().add(line);	//adds the line to the shapes group
		}
	}


	private void dots() {	//draws the dots
		double[] x = new double[N+1];	//creates a list for the x coordinate points of the dots
		double[] y = new double[N+1];	//creates a list for the y coordinate points of the dots
		y = points_y(N, radius);		//calls the method to generate the y coordinates
		x = points_x(N, radius);		//calls the method to generate the x coordinates
		
		for (int i = 0; i < N; i++) {
			Circle dots = new Circle(x[i], y[i], radius_dot); /*creates dots based on the x and y 
																coordinates. sets the radius to 3 pixels*/			
			dots.setFill(Color.RED);			//sets the colour of the dots to red
			shapes.getChildren().add(dots);		// adds the dots to the shapes group
		}
	}


	private void circle() {		//creates the main circle
		Circle c1 = new Circle(400,400,radius);	//circle at coordinates 400x400 with a radius of 250 pixels
		c1.setStroke(Color.BLACK);	//sets the colour of the edge to black
		c1.setFill(Color.WHITE);	//sets the colour of the inside to white
		shapes.getChildren().add(c1); //adds the circle to the shapes group

	}

	private void Draw_Noanime() {		//used in the button Draw_N to draw the image
		shapes.getChildren().clear();	//clears all the entire shapes group
		circle();	//draws the main circle
		dots();		//draws the dots with the given N
		line();		//draws the lines with the dots and the given N
	}

	private void getData() {//gets data from the various texts fields
		//I would take the numbers in the text fields then store them into a string
		//After that I would turn the strings into a integer and set it to their specific variable
		
		N_Input =  TDraw_N.getText();
		N = Integer.parseInt(N_Input);					

		N_end_input = TDraw_N_end.getText();
		N_end = Integer.parseInt(N_end_input);			

		TM_Input = TM.getText();
		M = Integer.parseInt(TM_Input);

		TM_Increment_Input = TM_Increment.getText();
		M_increment = Double.parseDouble(TM_Increment_Input);

		TM_end_Input = TM_end.getText();
		M_end = Double.parseDouble(TM_end_Input);

	}
}
