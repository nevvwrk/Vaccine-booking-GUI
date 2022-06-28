package Booking;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class testPie extends Application {
	
	@Override
	public void start(Stage primaryStage) {
	    Pane root = new Pane();
	    ObservableList<PieChart.Data> valueList = FXCollections.observableArrayList(
	            new PieChart.Data("Android", 55),
	            new PieChart.Data("IOS", 33),
	            new PieChart.Data("Windows", 12));
	    // create a pieChart with valueList data.
	    PieChart pieChart = new PieChart(valueList);
	    pieChart.setTitle("Popularity of Mobile OS");
	    //adding pieChart to the root.
	    root.getChildren().addAll(pieChart);
	    Scene scene = new Scene(root, 450, 450);

	    primaryStage.setTitle("Pie Chart Demo");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}

//	public static void main(String[] args) {
//	        launch(args);
//	    }
}
