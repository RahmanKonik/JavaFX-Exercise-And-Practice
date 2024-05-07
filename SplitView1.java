import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SplitView1 extends Application {

    private ListView<String> jobListView;
    private Spinner<Integer> fromNoteSpinner;
    private Spinner<Integer> toNoteSpinner;
    private TextField jobNameField;
    private TextField durationField;
    private TextField decapField;
    private TextField gapField;
    private Button updateButton;
    private List<RadioButton> intervalButtons;

    private Job job;

    @Override
    public void start(Stage stage) {

        this.job = new Job();

        String[] jobs = {
            "My Job 1",
            "My Job 2",
            "My Job 3"
        };

        ObservableList<String> jobList = FXCollections.observableArrayList();
        jobList.addAll(jobs);

        this.jobListView = new ListView<>(jobList);
        this.jobListView.getSelectionModel().selectedItemProperty().addListener(this::processJobSelection);

        // from note

        IntegerSpinnerValueFactory fromNoteValueFactory = new IntegerSpinnerValueFactory(0, 127, this.job.getFromNote());

        this.fromNoteSpinner = new Spinner<Integer>(fromNoteValueFactory);

        this.fromNoteSpinner.valueProperty().addListener((spinner, oldValue, newValue) -> {

            this.setUpdateButtonState();
        });


        // to note

        IntegerSpinnerValueFactory toNoteValueFactory = new IntegerSpinnerValueFactory(0, 127, this.job.getToNote());

        this.toNoteSpinner = new Spinner<Integer>(toNoteValueFactory);

        this.toNoteSpinner.valueProperty().addListener((spinner, oldValue, newValue) -> {

            this.setUpdateButtonState();
        });


        // Interval radio button

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

        // update button

        this.updateButton = new Button("Update Job");
        this.updateButton.setOnAction(this::updateButtonHandler);
        boolean canUpdate = this.job.getFromNote() <= this.job.getToNote();
        this.updateButton.setDisable(!canUpdate);


        // gridpane

        GridPane pane = new GridPane();
        //pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);

        pane.add(new Label("Job Name:"), 0, 0);
        jobNameField = new TextField();
        pane.add(jobNameField, 1, 0);

        pane.add(new Label("From Note: "), 0, 1);
        pane.add(this.fromNoteSpinner, 1, 1);

        pane.add(new Label("To Note: "), 0, 2);
        pane.add(this.toNoteSpinner, 1, 2);

        pane.add(intervalPane, 0, 3, 3, 1);


        pane.add(new Label("Duration:"), 0, 4);
        durationField = new TextField();
        pane.add(durationField, 1, 4);

        pane.add(new Label("Decap:"), 0, 5);
        decapField = new TextField();
        pane.add(decapField, 1, 5);

        pane.add(new Label("Gap:"), 0, 6);
        gapField = new TextField();
        pane.add(gapField, 1, 6);

        pane.add(this.updateButton, 0, 7, 2, 1);
        GridPane.setHalignment(this.updateButton, HPos.CENTER);


        VBox leftPane = new VBox(jobListView);

        SplitPane splitPane = new SplitPane(leftPane, pane);

        leftPane.setStyle("-fx-padding: 0 10 0 0;"); // Add padding to the right of leftPane
        pane.setStyle("-fx-padding: 0 0 0 10;");

        splitPane.setDividerPositions(0.25); // set the position in window screen.

        Scene scene = new Scene(splitPane, 800, 600);
        stage.setTitle("Split List View");
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
        //System.out.println(this.job);
    }

    public void setUpdateButtonState() {
        int fromNote = this.fromNoteSpinner.getValue();
        int toNote = this.toNoteSpinner.getValue();
        this.updateButton.setDisable(fromNote > toNote);
    }

    public void updateButtonHandler(ActionEvent event) {
        int fromNote = this.fromNoteSpinner.getValue();
        int toNote = this.toNoteSpinner.getValue();

        this.job.setFromNote(fromNote);
        this.job.setToNote(toNote);

        System.out.println(job);
    }

    private void processJobSelection(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        // For demonstration purposes, updating fields with dummy values
        jobNameField.setText(newValue);
        if ("My Job 1".equals(newValue)) {
            durationField.setText("1000 ms");
            decapField.setText("500 ms");
            gapField.setText("100 ms");
        } else if ("My Job 2".equals(newValue)) {
            durationField.setText("2000 ms");
            decapField.setText("600 ms");
            gapField.setText("150 ms");
        } else if ("My Job 3".equals(newValue)) {
            durationField.setText("1500 ms");
            decapField.setText("550 ms");
            gapField.setText("120 ms");
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
