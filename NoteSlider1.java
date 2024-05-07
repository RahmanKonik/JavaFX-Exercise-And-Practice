

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

public class NoteSlider1 extends Application {

    // GUI models
    private Slider noteDurationSlider;
    private Label noteDurationLabel;
    private Canvas noteTimingCanvas;
    private Slider noteDecaySlider;
    private Label noteDecayLabel;
    private Slider noteGapSlider;
    private Label noteGapLabel;
    private Button updateButton;

    // data model

    private Job job;

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



        // update button.

        this.updateButton = new Button("Update Job and Print");
        this.updateButton.setOnAction((ActionEvent event) -> {
            System.out.println(job);
        });

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
            noteGapBox,
            this.updateButton
        );

        StackPane noteTimesPane = new StackPane(
            this.noteTimingCanvas,
            controlsBox
        );

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
