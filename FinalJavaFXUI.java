import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TitledPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FinalJavaFXUI extends Application {

    private ListView<String> jobListView;
    private Spinner<Integer> fromNoteSpinner;
    private Spinner<Integer> toNoteSpinner;
    private TextField jobNameField;

    private Canvas noteTimingCanvas;

    private Slider noteDurationSlider;
    private Label noteDurationLabel;

    private Slider noteDecaySlider;
    private Label noteDecayLabel;

    private Slider noteGapSlider;
    private Label noteGapLabel;

    private TextField decapField;
    private TextField gapField;

    private List<RadioButton> intervalButtons;

    private TableView<Note> noteTableView;

    private Job job;

    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage stage) {

        this.job = new Job();

        // Job list

        String[] jobs = {
            "My Job 1",
            "My Job 2",
            "My Job 3"
        };

        ObservableList<String> jobList = FXCollections.observableArrayList();
        jobList.addAll(jobs);

        // Create ListView for jobs and configure selection listener.

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

        // Radio Button Interval

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


        // gridpane

        GridPane pane = new GridPane();
        //pane.setAlignment(Pos.CENTER);
        pane.setHgap(5);
        pane.setVgap(5);

        pane.add(new Label("Job Name:"), 0, 0);
        jobNameField = new TextField();
        pane.add(jobNameField, 1, 0);

        pane.add(new Label("From Note: "), 0, 1);
        pane.add(this.fromNoteSpinner, 1, 1);

        pane.add(new Label("To Note: "), 0, 2);
        pane.add(this.toNoteSpinner, 1, 2);

        pane.add(intervalPane, 0, 3, 3, 1);

         // Duration slider and label

        noteDurationLabel = new Label(String.format("Duration: %d ms", this.job.getNoteDuration()));
        pane.add(noteDurationLabel, 0, 5);

        noteDurationSlider = new Slider(100, 5000, this.job.getNoteDuration());
        noteDurationSlider.setShowTickMarks(true);
        noteDurationSlider.setMajorTickUnit(100);
        noteDurationSlider.setBlockIncrement(100);
        noteDurationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int duration = newValue.intValue();
            job.setNoteDuration(duration);
            updateCanvas();
        });
        pane.add(noteDurationSlider, 0, 6, 6, 1);

        // Decap slider and label

        noteDecayLabel = new Label(String.format("Decay: %d ms", this.job.getNoteDecay()));
        pane.add(noteDecayLabel, 0, 7);

        noteDecaySlider = new Slider(100, 5000, this.job.getNoteDecay());
        noteDecaySlider.setShowTickMarks(true);
        noteDecaySlider.setMajorTickUnit(100);
        noteDecaySlider.setBlockIncrement(100);
        noteDecaySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int decay = newValue.intValue();
            job.setNoteDecay(decay);
            updateCanvas();
        });
        pane.add(noteDecaySlider, 0, 8, 8, 1);

        // Gap Slider and Label

        noteGapLabel = new Label(String.format("Gap: %d ms", this.job.getNoteGap()));
        pane.add(noteGapLabel, 0, 9);

        noteGapSlider = new Slider(100, 5000, this.job.getNoteGap());
        noteGapSlider.setShowTickMarks(true);
        noteGapSlider.setMajorTickUnit(100);
        noteGapSlider.setBlockIncrement(100);
        noteGapSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int gap = newValue.intValue();
            job.setNoteDuration(gap);
            updateCanvas();
        });
        pane.add(noteGapSlider, 0, 10, 10, 1);


        int height = 30;
        this.noteTimingCanvas = new Canvas(450, height);
        updateCanvas();

        VBox noteDurationBox = new VBox(
            this.noteDurationLabel,
            this.noteDurationSlider
        );

        VBox noteDecayBox = new VBox(
            this.noteDecayLabel,
            this.noteDecaySlider
        );

        VBox noteGapBox = new VBox(
            this.noteGapLabel,
            this.noteGapSlider
        );

        VBox controlsBox = new VBox(
            noteDurationBox,
            noteDecayBox,
            noteGapBox
        );

        pane.add(controlsBox, 0, 11, 11, 1);

        pane.add(this.noteTimingCanvas, 0, 12, 12, 1);

        // Note Table

        List<Integer> velocities = new ArrayList<Integer>();

        velocities.add(60);
        velocities.add(80);
        velocities.add(100);
        this.job.setSpecificVelocities(velocities);
        System.out.println(this.job);

        List<Note> notes = new ArrayList<Note>();

        int noteStartTime = 0;
        int noteEndTime = 0;

        for ( int note : this.job.getNotes()) {

            for ( int velocity : this.job.getVelocities()){

                noteEndTime = noteStartTime + this.job.getNoteDuration();
                Note n = new Note(note, velocity, noteStartTime, noteEndTime);
                notes.add(n);

                noteStartTime += this.job.getNoteDuration() + this.job.getNoteDecay() + this.job.getNoteGap();
            }

        }

        System.out.println("Total number of notes = "+ notes.size());

        ObservableList<Note> noteList = FXCollections.observableArrayList();
        noteList.addAll(notes);

        this.noteTableView = new TableView<Note>();

        TableColumn<Note, Integer> noteColumn = new TableColumn<>("Note");
        TableColumn<Note, Integer> velocityColumn = new TableColumn<>("Velocity");
        TableColumn<Note, Integer> noteStartColumn = new TableColumn<>("Start (ms)");
        TableColumn<Note, Integer> noteEndColumn = new TableColumn<>("End (ms)");

        noteColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        velocityColumn.setCellValueFactory(new PropertyValueFactory<>("velocity"));
        noteStartColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        noteEndColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        this.noteTableView.getColumns().addAll(
            noteColumn,
            velocityColumn,
            noteStartColumn,
            noteEndColumn
        );

        this.noteTableView.setItems(noteList);

        pane.add(this.noteTableView, 0, 13, 13, 1);


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
    }

    public void setUpdateButtonState() {
        int fromNote = this.fromNoteSpinner.getValue();
        int toNote = this.toNoteSpinner.getValue();
    }

    private void processJobSelection(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        // For demonstration purposes, updating fields with dummy values
        jobNameField.setText(newValue);
        if ("My Job 1".equals(newValue)) {
            noteDurationSlider.setValue(1000);
            noteDecaySlider.setValue(500);
            noteGapSlider.setValue(100);
        } else if ("My Job 2".equals(newValue)) {
            noteDurationSlider.setValue(2000);
            noteDecaySlider.setValue(600);
            noteGapSlider.setValue(150);
        } else if ("My Job 3".equals(newValue)) {
            noteDurationSlider.setValue(1500);
            noteDecaySlider.setValue(550);
            noteGapSlider.setValue(120);
        }
        // Update the labels accordingly
        noteDurationLabel.setText(String.format("Duration: %d ms", (int) noteDurationSlider.getValue()));
        noteDecayLabel.setText(String.format("Decay: %d ms", (int) noteDecaySlider.getValue()));
        noteGapLabel.setText(String.format("Gap: %d ms", (int) noteGapSlider.getValue()));
        updateCanvas();
    }

    private void updateCanvas() {
        GraphicsContext gc = this.noteTimingCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, this.noteTimingCanvas.getWidth(), this.noteTimingCanvas.getHeight());
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, this.noteTimingCanvas.getWidth(), this.noteTimingCanvas.getHeight());

        // Duration color.

        gc.setFill(Color.GREEN);
        int durationWidth = this.job.getNoteDuration() / 6;
        gc.fillRect(0, 0, durationWidth, this.noteTimingCanvas.getHeight());
        gc.strokeRect(0, 0, durationWidth, this.noteTimingCanvas.getHeight());

        // Decap Color.

        gc.setFill(Color.LIGHTBLUE);
        int decayWidth = this.job.getNoteDecay() / 8;
        gc.fillRect(durationWidth, 1, decayWidth, this.noteTimingCanvas.getHeight());
        gc.strokeRect(durationWidth, 1, decayWidth, this.noteTimingCanvas.getHeight());

        // Gap Color.

        gc.setFill(Color.YELLOW);
        int gapWidth = this.job.getNoteGap() / 10;
        gc.fillRect(decayWidth, 2, gapWidth, this.noteTimingCanvas.getHeight());
        gc.strokeRect(decayWidth, 2, gapWidth, this.noteTimingCanvas.getHeight());

        // Gap Color.
    }

    public static void main(String[] args) {
        launch();
    }
}
