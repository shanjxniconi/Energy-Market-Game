package controller;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import DAO.UserDAO;
import model.User;
import util.StringUtil;
import view.Template;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;

/**
 * The controller of the login view - Template.fxml. This controller includes
 * basic loading functions of the login as well as welcoming user interface and
 * calls the functions from userDAO to alter the user table.
 * 
 * @author Jiaxiang Shan on 07/09/2020.
 */
public final class TemplateController implements Initializable {

	@FXML
	private TextField username_field;

	@FXML
	private TextField password_field;

	@FXML
	private Label error_message;

	@FXML
	private Button login_button;

	@FXML
	private Button register_button;

	/**
	 * Overrides the initialize functions in the interface Initializable. Do the
	 * major thing when the view is loaded.
	 * 
	 * @param location
	 *            the location of the new view.
	 * @param resources
	 *            resource for the view.
	 * 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//
		Template.getPrimaryStage().setTitle("Energy Market Game");

		// Template.getPrimaryStage().getIcons().add(new
		// Image("/de/fhluebeck/group3/resources/system/cookbook.jpg"));

		/*
		Template.getPrimaryStage().getIcons().add(
				new Image(new File(MainFrameController.SYSTEM_IMAGE_DEFAULT_PATH + "cookbook.jpg").toURI().toString()));

		this.password_field.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					attemptLogin();
				}
			}

		});
		*/

		// set the trigger to the login_button;
		this.login_button.setOnAction(event -> {
			attemptLogin();
		});

		this.register_button.setOnAction(event -> {
			User newUser = showRegisterationDialog();

			if (newUser != null && newUser.getUsername() != null && newUser.getPassword() != null) { // Maybe this
																										// condition
																										// is
				// useless.
				if (UserDAO.addUser(newUser)) { // successfully insert
					Alert alert = new Alert(AlertType.INFORMATION,
							"Registeration has been succeed, please enjoy you first trip to our Digital Cookbook",
							ButtonType.YES);
					alert.setTitle("Congurations");
					alert.setHeaderText("Registeration is successful");
					alert.showAndWait();
				} else {
					Alert alert = new Alert(AlertType.ERROR, "Sorry, due to some reasons,registeration has failed",
							ButtonType.YES);
					alert.setTitle("Error");
					alert.setHeaderText("Registeration is failed");
					alert.showAndWait();
				}
			}
		});

	}

	/******************************* Private Zone ******************************/

	/**
	 * Prompt the error message when user inputs the invalid information.
	 */
	private void setErrorInformation() {
		this.error_message.setVisible(true);
	}

	/**
	 * Hide the error message when user inputs the valid information.
	 */
	private void hideErrorInformation() {
		this.error_message.setVisible(false);
	}

	/**
	 * Called when the user clicks the login button or presses the "Enter" key in
	 * the keyboard. Attempt to login, shift to MainRecipeFrame.fxml is succeed or
	 * prompt error message when failed.
	 */
	protected void attemptLogin() {
		String username = username_field.getText();
		String password = password_field.getText();
		User user = UserDAO.validatePassword(username, password);
		if (user != null) { // validation succeed
			this.hideErrorInformation();
			Template.setCurrentUser(user);

			// shift the stage to the main Scene.
			try {
				Template.replaceSceneContent("./MainRecipeFrame.fxml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else { // validation fails
			this.setErrorInformation(); // set error information to notice user.
		}
	}

	/**
	 * When the user clicks the Register Button, show the interface for pre-designed
	 * registration.
	 * 
	 * @return newUser the user account the new User has created.
	 */
	private User showRegisterationDialog() {
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Registration Dialog");
		dialog.setHeaderText(
				"Please fill the following form to finish the registration procedure: We strongly recommend you to use your name "
						+ "as Username such as 'yichen.Hua'");

		// Set the icon (must be included in the project).
		// dialog.setGraphic(
		// new
		// ImageView(this.getClass().getResource("/de/fhluebeck/group3/resources/cookbook.jpg").toString()));

		// Set the button types.
		ButtonType registerButtonType = new ButtonType("Register", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(registerButtonType, ButtonType.CANCEL);

		// Create the user name and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		// create all the elements presented in the dialog.
		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");
		PasswordField reEnterPassword = new PasswordField();
		reEnterPassword.setPromptText("Re-Enter your Password");

		Label error_Label = new Label("re-enter password not matched");
		error_Label.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		error_Label.setTextFill(Color.RED);
		error_Label.setVisible(false);

		Label invalid_username_label = new Label(
				"Username can only include digits and letters and less than 20 elements");
		invalid_username_label.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		invalid_username_label.setTextFill(Color.RED);
		invalid_username_label.setVisible(false);

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);
		grid.add(new Label("re-Enter Password:"), 0, 2);
		grid.add(reEnterPassword, 1, 2);
		grid.add(error_Label, 2, 2);
		grid.add(invalid_username_label, 2, 0);

		// Enable/Disable login button depending on whether a userName was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(registerButtonType);
		loginButton.setDisable(true);

		// Do some validation on username text field.
		username.textProperty().addListener((observable, oldValue, newValue) -> {
			if (StringUtil.isLetterOrDigit(newValue) && newValue.length() <= 20) {
				invalid_username_label.setVisible(false);
				loginButton.setDisable(false);
			} else {
				invalid_username_label.setVisible(true);
				loginButton.setDisable(true);
			}
			loginButton.setDisable(newValue.trim().isEmpty() || password.getText().trim().isEmpty());
		});

		// Do some validation on re-enter password text field.
		password.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty() || !newValue.equals(reEnterPassword.getText()));
			error_Label.setVisible(newValue.trim().isEmpty() || !newValue.equals(reEnterPassword.getText()));
		});

		// Do some validation on re-enter password text field.
		reEnterPassword.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty() || !newValue.equals(password.getText()));
			error_Label.setVisible(newValue.trim().isEmpty() || !newValue.equals(password.getText()));
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the user name field by default.
		Platform.runLater(() -> username.requestFocus());

		// Convert the result to a user name-password-pair when the register button is
		// clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == registerButtonType) {
				return new Pair<>(username.getText(), password.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		// Here we must make the User final since it will be called in the anonymous
		final User newUser = new User();

		result.ifPresent(usernamePassword -> {
			// System.out.println("Username=" + usernamePassword.getKey() + ", Password=" +
			// usernamePassword.getValue());
			newUser.setUsername(usernamePassword.getKey());
			newUser.setPassword(usernamePassword.getValue());
			newUser.setStatus(1);
		});

		return newUser;
	}

}
