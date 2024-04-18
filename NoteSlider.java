

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

public class NoteSlider extends Application {

    // GUI models
    private Slider noteDurationSlider;
    private Label noteDurationLabel;
    private Canvas noteTimingCanvas;
    private Slider noteDecaySlider;
    private Label noteDecayLabel;

    // data model

    private JobV3 job;

    @Override
    public void start (Stage stage){

        this.job = new JobV3();

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
                    System.out.println(duration);
                    job.setNoteDuration(duration);
                    noteDurationLabel.setText(
                        String.format(
                            "Duration: %d ms",
                            duration
                        )
                    );
                }
            }
        );

        //

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
                }
            }
        );

        int height = 30;
        this.noteTimingCanvas = new Canvas(450, height);
        GraphicsContext gc = this.noteTimingCanvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(
            0,
            0,
            this.noteTimingCanvas.getWidth(),
            this.noteTimingCanvas.getHeight()
        );

        gc.setFill(Color.BLUE);
        int durationWidth = this.job.getNoteDuration() / 6;
        gc.fillRect(0, 0, durationWidth, height);
        gc.strokeRect(0, 0, durationWidth, height);

        gc.setFill(Color.LIGHTGRAY);
        int decayWidth = this.job.getNoteDuration() / 8;
        gc.fillRect(durationWidth, 1, decayWidth, height);
        gc.strokeRect(durationWidth, 1, decayWidth, height);



        VBox noteDurationBox = new VBox(
            this.noteDurationLabel,
            this.noteDurationSlider
        );

        VBox noteDecayBox = new VBox(
            this.noteDecayLabel,
            this.noteDecaySlider
        );

        StackPane noteTimesPane = new StackPane(
            noteDurationBox,
            noteDecayBox,
            this.noteTimingCanvas
        );


        stage.setTitle("Note Sliders");
        Scene scene = new Scene(noteTimesPane, 500, 400);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
