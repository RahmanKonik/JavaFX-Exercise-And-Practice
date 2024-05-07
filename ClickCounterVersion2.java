import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClickCounterVersion2 extends Application {

    private int counter;
    private Text countText;
    private Button startButton;
    private Button resetButton;

    @Override
    public void start(Stage stage){

        this.counter = 0;
        this.countText = new Text("Clicked: "+counter);

        // click button and handling.
        this.startButton = new Button("Click Me.");
        this.startButton.setOnAction(this::clickButtonHandling); // passing the event handling method

        // reset button and handling.
        this.resetButton = new Button("Reset Me.");
        this.resetButton.setOnAction(this::resetButtonHandling);

        // using the flowPane , as same as stackPane result.
        FlowPane pane = new FlowPane(
            startButton,
            this.countText,
            resetButton
            );

        pane.setAlignment(Pos.CENTER);
        pane.setHgap(20);
        Scene scene = new Scene(pane, 720, 600);
        stage.setScene(scene);
        stage.show();
    }

    // click handling event.
    public void clickButtonHandling (ActionEvent event){

        this.counter++;
        this.countText.setText("Clicked: "+this.counter);
    }

    // reset handling event.
    public void resetButtonHandling (ActionEvent event){

        this.counter = 0;
        this.countText.setText("Clicked: "+this.counter);
    }

    public static void main(String[] args) {
        launch();
    }
}
