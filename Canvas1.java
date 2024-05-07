import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Canvas1 extends Application {

    // GUI models
    private Slider noteDurationSlider;
    private Label noteDurationLabel;
    private Canvas noteTimingCanvas;

    // data model
    private Job job;

    @Override
    public void start(Stage stage) {
        this.job = new Job();

        // duration
        this.noteDurationLabel = new Label(
            String.format(
                "Duration is: %d ms(millisecond)",
                this.job.getNoteDuration()
            )
        );

        this.noteDurationSlider = new Slider(100, 5000, this.job.getNoteDuration());
        this.noteDurationSlider.setShowTickMarks(true);
        this.noteDurationSlider.setMajorTickUnit(100);
        this.noteDurationSlider.setBlockIncrement(100);

        this.noteDurationSlider.valueProperty().addListener(
            new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                    int duration = newValue.intValue();
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

        int height = 30;
        this.noteTimingCanvas = new Canvas(450, height);
        updateCanvas();

        VBox noteDurationBox = new VBox(
            this.noteDurationLabel,
            this.noteDurationSlider
        );

        StackPane noteTimesPane = new StackPane(
            this.noteTimingCanvas,
            noteDurationBox
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

        gc.setFill(Color.GREEN);
        int durationWidth = this.job.getNoteDuration() / 6;
        gc.fillRect(0, 0, durationWidth, this.noteTimingCanvas.getHeight());
        gc.strokeRect(0, 0, durationWidth, this.noteTimingCanvas.getHeight());
    }

    public static void main(String[] args) {
        launch();
    }
}
