
import java.util.List;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NoteSlider2 extends Application {

    // GUI models
    private Slider noteDurationSlider;
    private Label noteDurationLabel;
    private Canvas noteTimingCanvas;
    private Slider noteDecaySlider;
    private Label noteDecayLabel;
    private Slider noteGapSlider;
    private Label noteGapLabel;
    private TableView<Note> noteTableView;

    // data model

    private Job job;

    @SuppressWarnings("unchecked")
    @Override
    public void start (Stage stage){

        this.job = new Job();

        // duration

        this.noteDurationLabel = new Label(
            String.format(
                "Duration is: %d ms(millisecond)",
                this.job.getNoteDuration()
            )
        );

        this.noteDurationSlider = new Slider(
            100,
            5000,
            this.job.getNoteDuration()
        );

        this.noteDurationSlider.setShowTickMarks(true);
        this.noteDurationSlider.setMajorTickUnit(100);
        this.noteDurationSlider.setBlockIncrement(100);

        this.noteDurationSlider.valueProperty().addListener(
            new ChangeListener<Number>(){
                public void changed(
                    ObservableValue <? extends Number > ov,
                    Number oldValue,
                    Number newValue) {
                    int duration = newValue.intValue();
                    //System.out.println(duration);
                    job.setNoteDuration(duration);
                    noteDurationLabel.setText(
                        String.format(
                            "Duration: %d ms",
                            duration
                        )
                    );
                    updateCanvas();
                }
            }
        );

        // decay

        this.noteDecayLabel = new Label(
            String.format(
                "Decay is: %d ms(millisecond)",
                this.job.getNoteDecay()
            )
        );

        this.noteDecaySlider = new Slider(
            100,
            4500,
            this.job.getNoteDecay()
        );

        this.noteDecaySlider.setShowTickMarks(true);
        this.noteDecaySlider.setMajorTickUnit(100);
        this.noteDecaySlider.setBlockIncrement(100);

        this.noteDecaySlider.valueProperty().addListener(
            new ChangeListener<Number>(){
                public void changed(
                    ObservableValue <? extends Number > ov,
                    Number oldValue,
                    Number newValue) {
                    int decay = newValue.intValue();
                    job.setNoteDecay(decay);
                    noteDecayLabel.setText(
                        String.format(
                            "Decay: %d ms",
                            decay
                        )
                    );
                    updateCanvas();
                }
            }
        );

        // gap

        this.noteGapLabel = new Label(
            String.format(
                "Gap is: %d ms(millisecond)",
                this.job.getNoteGap()
            )
        );

        this.noteGapSlider = new Slider(
            100,
            500,
            this.job.getNoteGap()
        );

        this.noteGapSlider.setShowTickMarks(true);
        this.noteGapSlider.setMajorTickUnit(100);
        this.noteGapSlider.setBlockIncrement(100);

        this.noteGapSlider.valueProperty().addListener(
            new ChangeListener<Number>(){
                public void changed(
                    ObservableValue <? extends Number > ov,
                    Number oldValue,
                    Number newValue) {
                    int gap = newValue.intValue();
                    job.setNoteGap(gap);
                    noteGapLabel.setText(
                        String.format(
                            "Gap: %d ms",
                            gap
                        )
                    );
                    updateCanvas();
                }
            }
        );

        // note table view

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
            noteGapBox,
            this.noteTableView
        );

        int height = 350;//30
        this.noteTimingCanvas = new Canvas(500, height);
        updateCanvas();

        StackPane noteTimesPane = new StackPane(
            this.noteTimingCanvas,
            controlsBox
        );

        StackPane.setAlignment(controlsBox, Pos.BOTTOM_CENTER);


        stage.setTitle("Note Sliders");
        Scene scene = new Scene(noteTimesPane, 500, 400);
        stage.setScene(scene);
        stage.show();

    }

    private void updateCanvas() {
        GraphicsContext gc = this.noteTimingCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, this.noteTimingCanvas.getWidth(), this.noteTimingCanvas.getHeight());
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, this.noteTimingCanvas.getWidth(), this.noteTimingCanvas.getHeight());
        gc.setFill(Color.LIGHTBLUE);


        int durationWidth = this.job.getNoteDuration() / 6;
        gc.fillRect(0, 0, durationWidth, this.noteTimingCanvas.getHeight());
        gc.strokeRect(0, 0, durationWidth, this.noteTimingCanvas.getHeight());

        gc.setFill(Color.LIGHTBLUE);
        int decayWidth = this.job.getNoteDecay() / 8;
        gc.fillRect(durationWidth, 1, decayWidth, this.noteTimingCanvas.getHeight());
        gc.strokeRect(durationWidth, 1, decayWidth, this.noteTimingCanvas.getHeight());

        gc.setFill(Color.LIGHTBLUE);
        int gapWidth = this.job.getNoteGap() / 10;
        gc.fillRect(decayWidth, 2, gapWidth, this.noteTimingCanvas.getHeight());
        gc.strokeRect(decayWidth, 2, gapWidth, this.noteTimingCanvas.getHeight());
    }

    public static void main(String[] args) {
        launch();
    }
}
