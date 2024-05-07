
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SplitView extends Application {

    private ListView<String> colourListView;
    private Label labelView;



    @Override
    public void start(Stage stage){

        String[] colors = {
            "My Job 1: from 60 to 120 by 6 semitons, duration 1000 ms, decap 500 ms, gap 100 ms",
            "My Job 2: from 60 to 120 by 6 semitons, duration 1000 ms, decap 500 ms, gap 100 ms",
            "My Job 3: from 60 to 120 by 6 semitons, duration 1000 ms, decap 500 ms, gap 100 ms"
        };

        // Creating Manu

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu();

        for (String color : colors) {
            MenuItem menuItem = new MenuItem(color);
            menuItem.setOnAction(event -> labelView.setText(color));
            menu.getItems().add(menuItem);
        }
        menuBar.getMenus().add(menu);

        ObservableList<String> colorList = FXCollections.observableArrayList();

        colorList.addAll(colors);

        this.colourListView = new ListView<>(colorList);

        this.colourListView.getSelectionModel().select(0); // default selection by index zero with text.

        this.colourListView.getSelectionModel().selectedItemProperty().addListener(this:: processColorListSelection);

        this.labelView = new Label();

        StackPane colorPane = new StackPane(labelView);

        SplitPane rootPane = new SplitPane();

        rootPane.setDividerPositions(0.25); // set the position in window screen.

        rootPane.getItems().addAll(this.colourListView, colorPane, menuBar);

        Scene scene = new Scene(rootPane, 500, 400);
        stage.setTitle("Split List View");
        stage.setScene(scene);
        stage.show();

    }

    public void processColorListSelection(ObservableValue<? extends String> val, String oldValue, String newValue) {
        this.labelView.setText(newValue);

    }


    public static void main(String[] args) {
        launch();
    }

}
