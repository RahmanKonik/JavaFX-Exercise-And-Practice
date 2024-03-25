import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class JobTest extends Application {

    // private field variable
    private TextField nameTextField;
    private Job job;
    private Button updateButton;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Job name Interface"); // showing it in my window.
        Label jobNamelabel = new Label("Name of the Job:  ");
        this.nameTextField = new TextField();

        this.updateButton = new Button("Update"); // update button and passed it into nameBox
        this.nameTextField.setOnAction(this::nameProcesshandler); // event hanlder method

        // update button event action, same event as nameTextField.
        this.updateButton.setOnAction(this::nameProcesshandler);
        HBox nameBox = new HBox(
            jobNamelabel,
            this.nameTextField,
            this.updateButton
        );
        // nameBox is layout container. so it will be horizontally center.
        nameBox.setAlignment(Pos.CENTER);

        // FlowPane pane = new FlowPane(nameBox);
        // pane.setAlignment(Pos.CENTER);

        // pane.getchildren- list of child added to the stackpane.
        //add(namebox)- adds the nameBow as a child of the stackpane.
        // the nameBox will be added to the StackPane as a child.
        // same result as like flowPane.
        StackPane pane = new StackPane();
        pane.getChildren().add(nameBox);

        Scene scene = new Scene(pane, 640, 480);
        stage.setScene(scene);
        stage.show();
    }
    public void nameProcesshandler(ActionEvent event){
        String nameText = this.nameTextField.getText();
        if ( !nameText.isEmpty() && nameText.length() <= 20){ // checking textfield is not empty and length is less than 20 character.
            this.job = new Job(nameText);
            System.out.println(job);
            // clear the text field after clicking the update button.
            this.nameTextField.clear();
        } else{
            System.out.println("Text must not be empty! or not too long!.");
        }
    }
    public static void main(String[] args) {
        launch();
    }
}
