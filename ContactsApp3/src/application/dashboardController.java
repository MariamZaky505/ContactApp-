package application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;




public class dashboardController implements Initializable {
	
	

@FXML
private AnchorPane main_form;
	
@FXML
private Button addContact_addBtn;

@FXML
private Button addContact_btn;

@FXML
private Button addContact_clearBtn;

@FXML
private TableView<contactData> addContact_tableView;

@FXML
private TableColumn<contactData, String> addContact_col_contactID;

@FXML
private TableColumn<contactData, String> addContact_col_dateAdded;

@FXML
private TableColumn<contactData, String> addContact_col_email;

@FXML
private TableColumn<contactData, String> addContact_col_firstName;

@FXML
private TableColumn<contactData, String> addContact_col_gender;

@FXML
private TableColumn<contactData, String> addContact_col_lastName;

@FXML
private TableColumn<contactData, String> addContact_col_phone;

@FXML
private TextField addContact_contactID;

@FXML
private Button addContact_deleteBtn;

@FXML
private TextField addContact_email;

@FXML
private TextField addContact_firstName;

@FXML
private AnchorPane addContact_form;

@FXML
private ComboBox<?> addContact_gender;

@FXML
private AnchorPane addContact_image;

@FXML
private Button addContact_importBtn;



@FXML
private TextField addContact_lastName;

@FXML
private TextField addContact_phone;

@FXML
private TextField addContact_search;

@FXML
private Button addContact_updateBtn;

@FXML
private Button close;

@FXML
private AnchorPane home_allContacts;

@FXML
private Button home_btn;

@FXML
private AnchorPane home_favourites;

@FXML
private AnchorPane home_form;

@FXML
private AnchorPane home_recentAdded;

@FXML
private Button logout;

@FXML
private Button minimize;

@FXML
private Button update_btn;

@FXML
private Button update_clearBtn;

@FXML
private TableColumn<?, ?> update_col_contactID;

@FXML
private TableColumn<?, ?> update_col_email;

@FXML
private TableColumn<?, ?> update_col_firstName;

@FXML
private TableColumn<?, ?> update_col_lastName;

@FXML
private TableColumn<?, ?> update_col_phone;

@FXML
private TextField update_contactID;

@FXML
private TextField update_email;

@FXML
private TextField update_firstName;

@FXML
private AnchorPane update_form;

@FXML
private TextField update_lastName;

@FXML
private TextField update_phone;

@FXML
private TableView<?> update_tableView;

@FXML
private Button update_updateBtn;

@FXML
private Label username;

private Connection connect;
private PreparedStatement prepare;
private Statement statement;
private ResultSet result;
private Image image;


public void addContactAdd() {
	
	
	Date date = new Date(0);
	java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	
String sql = "INSERT INTO contact "
	+ "(contact_id,firstName,lastName,gender,phone,email,image,date)"
	 + "VALUES(?,?,?,?,?,?,?,?)";


	connect = database.connectDb();

	try {
		Alert alert;
        if (addContact_contactID.getText().isEmpty()
                || addContact_firstName.getText().isEmpty()
                || addContact_lastName.getText().isEmpty()
                || addContact_gender.getSelectionModel().getSelectedItem() == null
                || addContact_phone.getText().isEmpty()
                || addContact_phone.getText().isEmpty()
                || getData.path == null || getData.path == "") {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {

            String check = "SELECT contact_id FROM contacts WHERE contact_id = '"
                    + addContact_contactID.getText() + "'";

            statement = connect.createStatement();
            result = statement.executeQuery(check);

            if (result.next()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Contact ID: " + addContact_contactID.getText() + " was already exist!");
                alert.showAndWait();
            }else {

		prepare= connect.prepareStatement(sql);
		prepare.setString(1, addContact_contactID.getText());
		prepare.setString(2, addContact_firstName.getText());
        prepare.setString(3, addContact_lastName.getText());
        prepare.setString(4, (String) addContact_gender.getSelectionModel().getSelectedItem());
        prepare.setString(5,addContact_phone.getText());
        prepare.setString(6, addContact_email.getText());
		
        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");

        prepare.setString(7, uri);
        prepare.setString(8, String.valueOf(sqlDate));
        prepare.executeUpdate();
        
        String insertInfo = "INSERT INTO contact_info "
                + "(contact_id,firstName,lastName,email,date) "
                + "VALUES(?,?,?,?,?)";
        
        prepare = connect.prepareStatement(insertInfo);
        prepare.setString(1, addContact_contactID.getText());
        prepare.setString(2, addContact_firstName.getText());
        prepare.setString(3, addContact_lastName.getText());
        prepare.setString(4, addContact_email.getText());
        prepare.setString(5, "0.0");
        prepare.setString(6, String.valueOf(sqlDate));
        prepare.executeUpdate();

	    alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText("Successfuly Added!");
        alert.showAndWait();
        
        addContactShowListData();
        addContactReset();
            
            } }}
	
            catch (Exception e) {e.printStackTrace();
	
	

	}}
public void addContactReset() {
    addContact_contactID.setText("");
    addContact_firstName.setText("");
    addContact_lastName.setText("");
    addContact_gender.getSelectionModel().clearSelection();
    addContact_email.setText("");
    addContact_phone.setText("");
    addContact_image.ImageView(null);
    getData.path = "";
}
public ObservableList<contactData> addContactListData() { //CLASS
	 ObservableList<contactData> listData = FXCollections.observableArrayList();
String sql = 	"SELECT * FROM CONTACT";

connect = database.connectDb(); 
try {
    prepare = connect.prepareStatement(sql);
    result = prepare.executeQuery();
    contactData contactD;

    while (result.next()) {
        contactD = new contactData(result.getInt("contact_id"),
                result.getString("firstName"),
                result.getString("lastName"),
                result.getString("gender"),
                result.getString("phone"),
                result.getString("email"),
                sql, result.getDate("date"));
                result.getString("image");
        listData.add(contactD);

    }

} catch (Exception e) {
    e.printStackTrace();
}
return listData;
}

private ObservableList<contactData> addContactList;

public void addContactShowListData() {
    addContactList = addContactListData();

    addContact_col_contactID.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    addContact_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    addContact_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    addContact_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    addContact_col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    addContact_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
    addContact_col_dateAdded.setCellValueFactory(new PropertyValueFactory<>("date"));

    addContact_tableView.setItems(addContactList);

}

public void addContactSelect() {
    contactData contactD = addContact_tableView.getSelectionModel().getSelectedItem();
    int num = addContact_tableView.getSelectionModel().getSelectedIndex();

    if ((num - 1) < -1) {
        return;
    }

    addContact_contactID.setText(String.valueOf(contactD.getContactId()));
    addContact_firstName.setText(contactD.getFirstName());
    addContact_lastName.setText(contactD.getLastName());
    addContact_phone.setText(contactD.getPhone());
    addContact_email.setText(contactD.getEmail());

    
    getData.path = contactD.getImage();

    String uri = "file:" + contactD.getImage();

    image = new Image(uri, 101, 127, false, true);
    addContact_image.ImageView(image);
}


public void defaultNav() {
    home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right,#000000,#003333, #330019,#6B0000);");
}


public void displayUsername(){
	username.setText(getData.username);
}



public void switchForm(ActionEvent event) {

if (event.getSource() == home_btn) {
    home_form.setVisible(true);
    addContact_form.setVisible(false);
    update_form.setVisible(false);

    
    home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right,#000000,#003333, #330019,#6B0000 );");
    addContact_btn.setStyle("-fx-background-color:transparent");
    update_btn.setStyle("-fx-background-color:transparent");

    
} else if (event.getSource() == addContact_btn) {
    home_form.setVisible(false);
    addContact_form.setVisible(true);
    update_form.setVisible(false);
    
    addContact_btn.setStyle("-fx-background-color:linear-gradient(to bottom right,#000000,#003333, #330019,#6B0000 );");
    home_btn.setStyle("-fx-background-color:transparent");
    update_btn.setStyle("-fx-background-color:transparent");
    
} else if (event.getSource() == update_btn) {
        home_form.setVisible(false);
        addContact_form.setVisible(false);
        update_form.setVisible(true);

        update_btn.setStyle("-fx-background-color:linear-gradient(to bottom right,#000000,#003333, #330019,#6B0000 );");
        addContact_btn.setStyle("-fx-background-color:transparent");
        home_btn.setStyle("-fx-background-color:transparent");

}

    
}


private double x = 0;
private double y = 0;

public void logout() {

    Alert alert = new Alert(AlertType.CONFIRMATION); //used alert for dialogue 
    alert.setTitle("Confirmation Message");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to logout?");
    Optional<ButtonType> option = alert.showAndWait();
    try {
        if (option.get().equals(ButtonType.OK)) {

            logout.getScene().getWindow().hide(); //Closes the current window
            Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml")); // to the login scene
            Stage stage = new Stage();
            Scene scene = new Scene(root);  // creating new stage

            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);

                stage.setOpacity(.8);
            });

            root.setOnMouseReleased((MouseEvent event) -> {
                stage.setOpacity(1);
            });

            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);
            stage.show();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

}

public void close() {
	System.exit(0);
	
}

public void minimize() {
	Stage stage = (Stage)main_form.getScene().getWindow();
	stage.setIconified(true);
	
}
public void addContactInsertImage() {

    FileChooser open = new FileChooser();
    File file = open.showOpenDialog(main_form.getScene().getWindow());

    if (file != null) {
        getData.path = file.getAbsolutePath();

        image = new Image(file.toURI().toString(), 101, 127, false, true);
        addContact_image.ImageView(image);
    }
}


@Override
public void initialize(URL location, ResourceBundle resources) {
	displayUsername();
	addContactShowListData();
	
	
}
	
}

