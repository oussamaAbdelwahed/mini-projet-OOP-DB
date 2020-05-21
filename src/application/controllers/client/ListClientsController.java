package application.controllers.client;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.ResourceBundle;

import entities.Address;
import entities.Client;
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
public class ListClientsController implements Initializable {
	
    @FXML
    private Label label;
    @FXML private TextField filterField;
    @FXML private TableView<ClientInterface> tableview;
  
    @FXML private TableColumn<ClientInterface, String> empName;
    @FXML private TableColumn<ClientInterface, String> email;
    @FXML private TableColumn<ClientInterface, String> cin;
    @FXML private TableColumn<ClientInterface, String> tel;
    
    @FXML private TableColumn<ClientInterface, String> dateNaiss;
    @FXML private TableColumn<ClientInterface, String> sex;
    @FXML private TableColumn<ClientInterface, String> etatCivile;
    @FXML private TableColumn<ClientInterface, String> adresse;
    
   
                  
    //observalble list to store data
    private final ObservableList<ClientInterface> dataList = FXCollections.observableArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {      
        //we must create a ClientInterface filled with client entities class with attributes like this  this.EmpID = new SimpleIntegerProperty(id);           
       // EmpID.setCellValueFactory(new PropertyValueFactory<>("EmpID"));       
        empName.setCellValueFactory(new PropertyValueFactory<>("nom"));        
        email.setCellValueFactory(new PropertyValueFactory<>("email"));        
        cin.setCellValueFactory(new PropertyValueFactory<>("cin"));        
        tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        //tel.setText(arg0);
 
        dateNaiss.setCellValueFactory(new PropertyValueFactory<>("dateNaiss"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        etatCivile.setCellValueFactory(new PropertyValueFactory<>("etatCivile"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        
        Client emp1 = new Client();
        Address adr = new Address();
        adr.setContinent(Continent.AFR);
        adr.setCountry("TUnisia");
        adr.setState("Bizerte");
        adr.setCity("eL alIA");
        adr.setStreet("revolution");
        adr.setZipCode(7016);
        
        emp1.setId(1);emp1.setFirstname("fn1");emp1.setLastname("ln1");emp1.setEmail("email1");emp1.setCin(11399688);emp1.setTel("tel1");
        emp1.setBirthdate(new Date());emp1.setSex(Sex.MAL);emp1.setCivilState(CivilState.MAR);emp1.setAddress(adr);
        
        //setting up his values 
        Client emp2 = new Client();
        
        emp2.setId(1);emp2.setFirstname("fn2");emp2.setLastname("ln2");emp2.setEmail("email2");emp2.setCin(11399688);emp2.setTel("tel2");
        emp2.setBirthdate(new Date());emp2.setSex(Sex.MAL);emp2.setCivilState(CivilState.MAR);emp2.setAddress(adr);
        Client emp3 = new Client( );
        
        emp3.setId(1);emp3.setFirstname("fn3");emp3.setLastname("ln3");emp3.setEmail("email3");emp3.setCin(11399688);emp3.setTel("tel3");
        emp3.setBirthdate(new Date());emp3.setSex(Sex.MAL);emp3.setCivilState(CivilState.MAR);emp3.setAddress(adr);
        

        Client emp4 = new Client();                   
        emp4.setId(1);emp4.setFirstname("fn4");emp4.setLastname("ln4");emp4.setEmail("email4");emp4.setCin(11399688);emp4.setTel("tel4");
        emp4.setBirthdate(new Date());emp4.setSex(Sex.MAL);emp4.setCivilState(CivilState.MAR);emp4.setAddress(adr);
        
        ClientInterface ci1 = new ClientInterface(emp1);ClientInterface ci2 = new ClientInterface(emp2);
        ClientInterface ci3 = new ClientInterface(emp3);ClientInterface ci4 = new ClientInterface(emp4);
        dataList.addAll(ci1,ci2, ci3, ci4);
        
        //real world scenario ==> oussama
       // dataList.addAll(new LinkedList<ClientInterface>());
        
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<ClientInterface> filteredData = new FilteredList<>(dataList, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(client -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (client.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (client.getCin().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}else if (client.getSex().indexOf(lowerCaseFilter)!=-1) {
				     return true;
				}else if (client.getEtatCivile().indexOf(lowerCaseFilter)!=-1) {
				     return true;
				}else{
				     return false; // Does not match.
				}
			});
		});
		
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<ClientInterface> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tableview.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tableview.setItems(sortedData);
    } 
}

    
