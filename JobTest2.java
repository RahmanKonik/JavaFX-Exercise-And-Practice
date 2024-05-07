import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
//import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JobTest2 extends Application {

    // private field variable
    private TextField nameTextField;
    private Job job;
    private Button updateButton;

    private Spinner<Integer> noteStartSpinner;
    private Spinner<Integer> noteEndSpinner;

    @Override
    public void start(Stage stage) {

        this.job = new Job();
        stage.setTitle("Job name Interface"); // showing it in my window.

        // three labels
        Label jobNamelabel = new Label("Name of the Job:-  ");
        Label noteStartlabel = new Label("Label of Start Note:- ");
        Label noteEndlabel = new Label("Label of End Note:- ");

        this.nameTextField = new TextField();
        noteStartSpinner = new Spinner<>(0, 127, this.job.getFromNote()); // Default range from 0 to 100, initial value 0
        noteEndSpinner = new Spinner<>(0, 127, this.job.getToNote());

        this.updateButton = new Button("Update"); // update button and passed it into nameBox
        this.nameTextField.setOnAction(this::nameProcesshandler); // event hanlder method

        // update button event action, same event as nameTextField.
        this.updateButton.setOnAction(this::nameProcesshandler);

        // HBox nameBox = new HBox(
        //     jobNamelabel,
        //     this.nameTextField,
        //     this.updateButton,
        //     noteStartlabel,
        //     noteEndlabel
        // );

        // nameBox is layout container. so it will be horizontally center.
        //nameBox.setAlignment(Pos.CENTER);

        // FlowPane pane = new FlowPane(nameBox);
        // pane.setAlignment(Pos.CENTER);

        // pane.getchildren- list of child added to the stackpane.
        //add(namebox)- adds the nameBow as a child of the stackpane.
        // the nameBox will be added to the StackPane as a child.
        // same result as like flowPane.
        //StackPane pane = new StackPane();
        //pane.getChildren().add(nameBox);


        // gridPane container layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);// set the alignment to the center.
        gridPane.setHgap(10);// horizontal gap between column and set the space between 10 pixel.
        gridPane.setHgap(10);// vertical gap between row and set the space between 10 pixel.
        /**
         * Set the padding of the gridPane.
         * Padding is the space inside the gridpane between text and border.
         *
         * Insets- how much padding for each side(top, right, bottom, left) and 15 pixel for all side.
         */
        gridPane.setPadding(new Insets(20));


        // labels and input textfield to the gridpane.

        //girdpane( container layout).add(labelname, column index, row index, ...).
        gridPane.add(jobNamelabel, 0, 0);
        gridPane.add(this.nameTextField, 1, 0);
        gridPane.add(noteStartlabel, 0, 1);
        gridPane.add(noteStartSpinner, 1, 1);
        gridPane.add(noteEndlabel, 0, 2);
        gridPane.add(noteEndSpinner, 1, 2);
        gridPane.add(this.updateButton, 1, 3, 2, 1);


        Scene scene = new Scene(gridPane, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public void nameProcesshandler(ActionEvent event){
        String nameText = this.nameTextField.getText();
        int startNote = noteStartSpinner.getValue();
        int endNote = noteEndSpinner.getValue();

        this.job.setFromNote(startNote);
        this.job.setToNote(endNote);
        System.out.println(job);

        // if ( !nameText.isEmpty() && nameText.length() <= 20){ // checking textfield is not empty and length is less than 20 character.
        //     //this.job = new Job(nameText);
        //     System.out.println(job);
        //     // clear the text field after clicking the update button.
        //     this.nameTextField.clear();
        // } else{
        //     System.out.println("Text must not be empty! or not too long!.");
        // }
    }
    public static void main(String[] args) {
        launch();
    }
}
