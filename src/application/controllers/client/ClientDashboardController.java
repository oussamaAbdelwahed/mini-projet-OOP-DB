package application.controllers.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.controllers.DashboardInterface;
import application.helpers.FxmlLoader;
import entities.Client;
import entities.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientDashboardController implements Initializable,DashboardInterface {
	    @FXML
	    HBox ouvrirCompte,listerComptes,fermerCompte;
	    
	    @FXML
	    HBox profile,deconnexion;
	    
	    private FxmlLoader loader ;
	    
	    @FXML
	    ScrollPane displayTarget;
	    
	    @FXML
	    Label diplayTargetHeaderTitle;
	    
	    @FXML 
	    private TextField chercherCompte; 
	    
	    @FXML
	    private Text usernameText;
	    //private Long userId;
	    
	    private Client client;
	    
	    public void setLoggedOnUser(Person p) {
	    	usernameText.setText(p.getFirstname()+" "+p.getLastname());
	    	this.client = (Client)p;
	    	
	    }
	    
	    public void onOuvrirCompteClick(MouseEvent event) throws IOException {
	    	FXMLLoader l= loader.getPage("account/openAccount");
			Pane p = l.load();
	    	
	    	displayTarget.setContent(p);
	    	diplayTargetHeaderTitle.setText("Ouvrir un nouveau compte");
	    }
	    
	    public void onListerComptesClick(MouseEvent event) throws IOException {
	    	FXMLLoader l= loader.getPage("account/listAccounts");
			Pane p = l.load();
	    	
	    	displayTarget.setContent(p);
	    	diplayTargetHeaderTitle.setText("Lister les comptes");
	    }
	    
	    public void onFermerCompteClick(MouseEvent event) throws IOException {
	    	FXMLLoader l= loader.getPage("account/closeAccount");
			Pane p = l.load();
	  
	    	displayTarget.setContent(p);
	    	diplayTargetHeaderTitle.setText("Fermer un compte");
	    }
	    
	
	    public void onChercherCompteKeyPress(KeyEvent event) throws IOException {
	    	if (event.getCode() == KeyCode.ENTER) {
	    		FXMLLoader l= loader.getPage("account/showAccount");
	   		    Pane p = l.load();
	    	  
	        	displayTarget.setContent(p);
	        	diplayTargetHeaderTitle.setText("Informations du compte");
	    	}
	    }
	    
	    
	    public void onDeposerArgentClick(MouseEvent event) throws IOException {
	       System.out.println("depo");
	    }
	    
	    public void onRetirerArgentClick(MouseEvent event) throws IOException {
	       System.out.println("retrait");
	    }
	    
	    public void onVirementClick(MouseEvent event) throws IOException {
	       System.out.println("virement");
	    }
	    
	    
	    public void onProfileClick(MouseEvent event) throws IOException {
	    	FXMLLoader l= loader.getPage("editProfile");
			Pane p = l.load();
	    
	    	displayTarget.setContent(p);
	    	diplayTargetHeaderTitle.setText("Modifier le profile");
	    }
	    
	   
	    
	    public void onDeconnexionClick(MouseEvent event) {
	    	 ((Node) (event.getSource())).getScene().getWindow().hide();
	    	 Parent root;
			try {
				
				FXMLLoader loader = new FxmlLoader().getPage("login");
				root = loader.load();
				
				Stage stage = new Stage();
				Scene scene = new Scene(root);
				
				stage.setScene(scene);
				stage.setTitle("interface de connexion");
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
	    }
		 
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			loader = new FxmlLoader();
			
		}

}
