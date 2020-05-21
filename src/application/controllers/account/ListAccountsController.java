package application.controllers.account;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import application.controllers.client.ClientInterface;
import application.controllers.counter.AccountInterface;
import entities.Account;
import entities.Address;
import entities.Client;
import entities.Counter;
import entities.CurrentAccount;
import entities.Person;
import entities_enums.CivilState;
import entities_enums.Continent;
import entities_enums.Sex;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListAccountsController implements Initializable{
	@FXML
    private Label label;
    @FXML private TextField filterField;
    @FXML private TableView<AccountInterface> tableview;
  
    @FXML private TableColumn<AccountInterface, String> type;
    @FXML private TableColumn<AccountInterface, String> money;
    @FXML private TableColumn<AccountInterface, String> threshold;
    @FXML private TableColumn<AccountInterface, String> isAvailable;
    
    @FXML private TableColumn<AccountInterface, String> openingDate;
    @FXML private TableColumn<AccountInterface, String> closingDate;
    @FXML private TableColumn<AccountInterface, String> owner;
    @FXML private TableColumn<AccountInterface, String> createdBy;
            
    //observalble list to store data
    private final ObservableList<AccountInterface> dataList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {      
        //we must create a ClientInterface filled with client entities class with attributes like this  this.EmpID = new SimpleIntegerProperty(id);           
       // EmpID.setCellValueFactory(new PropertyValueFactory<>("EmpID"));       
    	type.setCellValueFactory(new PropertyValueFactory<>("type"));        
    	money.setCellValueFactory(new PropertyValueFactory<>("money"));        
    	threshold.setCellValueFactory(new PropertyValueFactory<>("threshold"));        
    	isAvailable.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));
        //tel.setText(arg0);
 
    	openingDate.setCellValueFactory(new PropertyValueFactory<>("openingDate"));
    	closingDate.setCellValueFactory(new PropertyValueFactory<>("closingDate"));
    	owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
    	createdBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
    	
    	Account c1 = new CurrentAccount();
    	Person cli1 = new Client();cli1.setFirstname("cli_fn1");cli1.setLastname("cli_ln1");
    	Person count1 = new Counter();count1.setFirstname("counter_fn1");count1.setLastname("counter_ln1");
    	c1.setCreatedBy(count1);c1.setOwner(cli1);
    	c1.setMoney(1200);c1.setThreshold(500);c1.setAvailable(true);
    	c1.setOpeningDate(new Date());
    	c1.setClosingDate(new Date());
    	
    	AccountInterface ai1 = new AccountInterface(c1);
    	
     	Account c2 = new CurrentAccount();
    	Person cli2 = new Client();cli2.setFirstname("cli_fn2");cli2.setLastname("cli_ln2");
    	Person count2 = new Counter();count2.setFirstname("counter_fn2");count2.setLastname("counter_ln2");
    	c2.setCreatedBy(count2);c2.setOwner(cli2);
    	c2.setMoney(1200);c2.setThreshold(500);c2.setAvailable(true);
    	c2.setOpeningDate(new Date());
    	c2.setClosingDate(new Date());
    	
    	AccountInterface ai2 = new AccountInterface(c2);
    	
     	Account c3 = new CurrentAccount();
    	Person cli3 = new Client();cli3.setFirstname("cli_fn3");cli3.setLastname("cli_ln3");
    	Person count3 = new Counter();count3.setFirstname("counter_fn3");count3.setLastname("counter_ln3");
    	c3.setCreatedBy(count3);c3.setOwner(cli3);
    	c3.setMoney(1200);c3.setThreshold(500);c3.setAvailable(true);
    	c3.setOpeningDate(new Date());
    	c3.setClosingDate(new Date());
    	
    	AccountInterface ai3 = new AccountInterface(c3);
    	
    	
     	Account c4 = new CurrentAccount();
    	Person cli4 = new Client();cli4.setFirstname("cli_fn4");cli4.setLastname("cli_ln4");
    	Person count4 = new Counter();count4.setFirstname("counter_fn4");count4.setLastname("counter_ln4");
    	c4.setCreatedBy(count4);c4.setOwner(cli4);
    	c4.setMoney(1200);c4.setThreshold(500);c4.setAvailable(true);
    	c4.setOpeningDate(new Date());
    	c4.setClosingDate(new Date());
    	
    	AccountInterface ai4 = new AccountInterface(c4);
       
        dataList.addAll(ai1,ai2, ai3, ai4);
        
        //real world scenario ==> oussama
       // dataList.addAll(new LinkedList<ClientInterface>());
        
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<AccountInterface> filteredData = new FilteredList<>(dataList, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(account -> {
				// If filter text is empty, display all persons.		
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (account.getType().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (account.getOwner().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				else if (account.getIsAvailable().indexOf(lowerCaseFilter)!=-1) {
				     return true;
				}else  {
				     return false; // Does not match.
				}
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<AccountInterface> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tableview.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tableview.setItems(sortedData);
    } 
}
