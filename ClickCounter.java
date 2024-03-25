
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClickCounter extends Application {

    private int counter;
    private Text textCounter;
    private Button clickButton;
    private Button resetButton;

    private class ResetButtonHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event){

            counter = 0;
            textCounter.setText("Clicks: "+counter);

        }
    }

    @Override
    public void start(Stage stage) {

        this.counter = 0;
        this.textCounter = new Text("Click: "+counter);

        this.clickButton = new Button("Click Me");
        this.clickButton.setOnAction((event) ->{//lambda event action
            this.counter++;
            this.textCounter.setText("Clicks: "+this.counter);
        });

        this.resetButton = new Button("Reset");
        this.resetButton.setOnAction(new ResetButtonHandler());

        FlowPane pane = new FlowPane(
            clickButton,
            this.textCounter,
            this.resetButton
        );
        pane.setAlignment(Pos.CENTER);// Setup the position in screen, setAligment method.
        pane.setHgap(20); // number of pixels gap between two field.

        Scene scene = new Scene(pane, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    // public void handleClickButton(ActionEvent event){

    //     this.counter++;
    //     this.textCounter.setText("Clicks: "+this.counter);

    //    // System.out.println("Button Clicked , Number of Count:-"+ this.counter);
    //    // this code used for showing the text in command terminal.
    // }

    // public void handleResetButton(ActionEvent event){

    //     this.counter = 0;
    //     this.textCounter.setText("Clicks: "+this.counter);

    // }


    public static void main(String[] args) {
        launch();
    }

}
