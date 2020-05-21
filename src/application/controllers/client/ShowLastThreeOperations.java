package application.controllers.client;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import entities.Operation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ShowLastThreeOperations  implements Initializable {
	@FXML
	private Text title1,title2,title3;
	
	@FXML
	private GridPane gridPane1,gridPane2,gridPane3;
	
	private Long accountId;
	
	public void setAccountId(Long id) {
		this.accountId = id;
		System.out.println("From Last 3 ops account id = "+id);
		//here we must do a dao call to get last three operations and depending on the length of result we decide
		//what grid panes and titles to show (because last ops they can be 0 , 1 , 2  or 3 operations
		List<Operation> res = new LinkedList<>();
		
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		
	}

}
