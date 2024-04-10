import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TimeCheckBox extends Application{

    private Spinner<Integer> fromNoteSpinner;
    private Spinner<Integer> toNoteSpinner;
    private CheckBox useDefaultTimeCheckBox;
    private Button updateButton;

    private JobV2 jobV2;
    private boolean useDefaultTimes;

    @Override
    public void start(Stage stage){
        this.jobV2 = new JobV2();

        IntegerSpinnerValueFactory fromNoteValueFactory = new IntegerSpinnerValueFactory(0, 127, this.jobV2.getFromNote());

        this.fromNoteSpinner = new Spinner<Integer>(fromNoteValueFactory);
        //this.fromNoteSpinner.setEditable(true);

        this.fromNoteSpinner.valueProperty().addListener((spinner, oldValue, newValue) -> {
            //System.out.println("from Note: old "+oldValue+ " new "+newValue);
            this.setUpdateButtonState();
        });

        // HBox fromNoteBox = new HBox(new Label("From Note: "),this.fromNoteSpinner);
        // fromNoteBox.setSpacing(15);


        IntegerSpinnerValueFactory toNoteValueFactory = new IntegerSpinnerValueFactory(0, 127, this.jobV2.getToNote());

        this.toNoteSpinner = new Spinner<Integer>(toNoteValueFactory);
        //this.toNoteSpinner.setEditable(true);

        this.toNoteSpinner.valueProperty().addListener((spinner, oldValue, newValue) -> {
            //System.out.println("to Note: old "+oldValue+ " new "+newValue);
            this.setUpdateButtonState();
        });

        // HBox toNoteBox = new HBox(new Label("To Note: "),this.toNoteSpinner);
        // toNoteBox.setSpacing(28);

        // check box

        this.useDefaultTimeCheckBox = new CheckBox("Use default times");
        this.useDefaultTimeCheckBox.setOnAction(this::defaultTimeCheckBoxHandler);

        // update button

        this.updateButton = new Button("Update Job");
        this.updateButton.setOnAction(this::updateButtonHandler);
        boolean canUpdate = this.jobV2.getFromNote() <= this.jobV2.getToNote();
        this.updateButton.setDisable(!canUpdate);

        // VBox box = new VBox(fromNoteBox, toNoteBox, this.updateButton);

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);

        pane.add(new Label("From Note: "), 0, 0);
        pane.add(this.fromNoteSpinner, 1, 0);

        pane.add(new Label("To Note: "), 0, 1);
        pane.add(this.toNoteSpinner, 1, 1);

        pane.add(this.useDefaultTimeCheckBox, 0, 2, 2, 1);

        pane.add(this.updateButton, 0, 3, 2, 1);
        GridPane.setHalignment(this.updateButton, HPos.CENTER);

        stage.setTitle("Note Spinner");

        Scene scene = new Scene(pane, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    public void defaultTimeCheckBoxHandler(ActionEvent event){

        this.useDefaultTimes = this.useDefaultTimeCheckBox.isSelected();
    }

    private void setUpdateButtonState(){
        int fromNote = this.fromNoteSpinner.getValue();
        int toNote = this.toNoteSpinner.getValue();
        this.updateButton.setDisable(fromNote > toNote);

    }

    public void updateButtonHandler(ActionEvent event){

        int fromNote = this.fromNoteSpinner.getValue();
        int toNote = this.toNoteSpinner.getValue();

        this.jobV2.setFromNote(fromNote);
        this.jobV2.setToNote(toNote);

        System.out.println(jobV2);
    }

    public static void main(String[] args) {
        launch();
    }

}
