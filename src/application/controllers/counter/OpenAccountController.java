package application.controllers.counter;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import entities.Account;
import entities.CurrentAccount;
import entities.SavingAccount;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class OpenAccountController implements Initializable{
	@FXML
	private TextField somme;
	
	@FXML 
	private Text sommeErr;
	
	@FXML
	private Button submitBtn;
	
	@FXML
	private RadioButton compteCourant,compteEpargne;
	
	@FXML 
	private ToggleGroup accountType;
	
	private Long counterId,clientId;
	
	public void setCounterId(Long id) {
		System.out.println("Setting counter id");
		this.counterId = id;
	}
	
	public void setClientId(Long id) {
		System.out.println("Setting client id");
		this.clientId = id;
	}
	
	
	public void onSubmit(MouseEvent event) {
		 Double sommeVal;
		  try {
			  sommeVal = Double.parseDouble(somme.getText());
			   if(sommeVal <= 0) {
				  sommeErr.setVisible(true);
				  sommeErr.setText("* valeur négative non alloué !");
			  }else {
			   Calendar cal = Calendar.getInstance();
		   	   cal.setTime(new Date());
			   Account c;
			   RadioButton rb = (RadioButton)accountType.getSelectedToggle();
			    if(rb==compteCourant) {
				   c = new CurrentAccount();
				   c.setThreshold(Account.THRESHOLD_CURRENT_ACCOUNT);
			    }else{
			      c = new SavingAccount();
			      c.setThreshold(Account.THRESHOLD_SAVING_ACCOUNT);
			    }
			    c.setMoney(sommeVal);
		    	c.setAvailable(true);
		    	c.setClosingDate(cal.getTime());
		    	
			  }
		  }catch(NumberFormatException e) {
			  e.printStackTrace();
		  }
	}
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
