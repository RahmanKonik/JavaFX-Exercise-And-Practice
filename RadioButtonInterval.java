import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RadioButtonInterval extends Application{
    private Spinner<Integer> fromNoteSpinner;
    private Spinner<Integer> toNoteSpinner;
    private CheckBox useDefaultTimeCheckBox;
    private Button updateButton;
    private List<RadioButton> intervalButtons;
    private Job job;
    private boolean useDefaultTimes;

    @Override
    public void start(Stage stage){
        this.job = new Job();

        IntegerSpinnerValueFactory fromNoteValueFactory = new IntegerSpinnerValueFactory(0, 127, this.job.getFromNote());
        this.fromNoteSpinner = new Spinner<Integer>(fromNoteValueFactory);
        this.fromNoteSpinner.valueProperty().addListener((spinner, oldValue, newValue) -> {
            this.setUpdateButtonState();
        });

        IntegerSpinnerValueFactory toNoteValueFactory = new IntegerSpinnerValueFactory(0, 127, this.job.getToNote());
        this.toNoteSpinner = new Spinner<Integer>(toNoteValueFactory);
        this.toNoteSpinner.valueProperty().addListener((spinner, oldValue, newValue) -> {
            this.setUpdateButtonState();
        });

        // radio button

        List<String> intervalRadioButtonNames = new ArrayList<String>();
        intervalRadioButtonNames.add("One");
        intervalRadioButtonNames.add("Three");
        intervalRadioButtonNames.add("Six");
        intervalRadioButtonNames.add("Twelve");

        ToggleGroup intervalButtonGroup = new ToggleGroup();
        this.intervalButtons = new ArrayList<RadioButton>();

        Job.Interval[] intervalValues = Job.Interval.values();
        int buttonIndex = 0;
        for (Job.Interval value : intervalValues){
            RadioButton radioButton = new RadioButton(intervalRadioButtonNames.get(buttonIndex));
            radioButton.setToggleGroup(intervalButtonGroup);
            radioButton.setOnAction(this::intervalButtonActionHandler);
            this.intervalButtons.add(radioButton);
            buttonIndex++;
        }

        this.intervalButtons.get(0).setSelected(true);

        HBox intervalBox = new HBox(
            this.intervalButtons.get(0),
            this.intervalButtons.get(1),
            this.intervalButtons.get(2),
            this.intervalButtons.get(3)
        );
        intervalBox.setSpacing(15);

        TitledPane intervalPane = new TitledPane("Interval", intervalBox);

        // check box
        this.useDefaultTimeCheckBox = new CheckBox("Use default times");
        this.useDefaultTimeCheckBox.setOnAction(this::defaultTimeCheckBoxHandler);

        // update button
        this.updateButton = new Button("Update Job");
        this.updateButton.setOnAction(this::updateButtonHandler);
        boolean canUpdate = this.job.getFromNote() <= this.job.getToNote();
        this.updateButton.setDisable(!canUpdate);

        //Gridpane
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);

        pane.add(new Label("From Note: "), 0, 0);
        pane.add(this.fromNoteSpinner, 1, 0);
        pane.add(new Label("To Note: "), 0, 1);
        pane.add(this.toNoteSpinner, 1, 1);
        pane.add(intervalPane, 0, 2, 2, 1);
        pane.add(this.useDefaultTimeCheckBox, 0, 3, 2, 1);
        pane.add(this.updateButton, 0, 4, 2, 1);
        GridPane.setHalignment(this.updateButton, HPos.CENTER);

        stage.setTitle("Note Spinner");

        Scene scene = new Scene(pane, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    public void intervalButtonActionHandler(ActionEvent event) {

        if(this.intervalButtons.get(0).isSelected()) {
            this.job.setInterval(Job.Interval.ONE);
        }
        else if (this.intervalButtons.get(1).isSelected()){
            this.job.setInterval(Job.Interval.THREE);
        }
        else if (this.intervalButtons.get(2).isSelected()){
            this.job.setInterval(Job.Interval.SIX);
        }
        else if (this.intervalButtons.get(3).isSelected()){
            this.job.setInterval(Job.Interval.TWELVE);
        }
        System.out.println(this.job);
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

        this.job.setFromNote(fromNote);
        this.job.setToNote(toNote);

        System.out.println(job);
    }

    public static void main(String[] args) {
        launch();
    }
}
