package view;

import model.User;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Template is regarded as the whole application and be called by the Entrance
 * class.
 * 
 * @author Eason.Hua on 2018/05/30.
 */
public class Template extends Application {

	private static Stage primaryStage;

	private static FXMLLoader iFxmlLoader;

	// private static GridPane rootLayout;

	// private static AnchorPane layOutAP;

	/**
	 * Current login user, when the user login, we load all the relevant
	 * informations into the user.
	 */
	private static User currentUser;

	/**
	 * Start function to be automatically called by the system.
	 * 
	 * @param stage
	 *            new instance of JavaFX Stage.
	 * 
	 */
	@Override
	public void start(Stage stage) throws Exception {

		Template.primaryStage = stage;

		initLayout();
	}

	/**
	 * Initializes the root layout.
	 */
	private void initLayout() {
		try {

			replaceSceneContent("./Template.fxml");
			Template.primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static User getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(User currentUser) {
		Template.currentUser = currentUser;
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void clearCurrentUser() {
		currentUser = null;
	}

	public static FXMLLoader getiFxmlLoader() {
		return iFxmlLoader;
	}

	public static void setiFxmlLoader(FXMLLoader iFxmlLoader) {
		Template.iFxmlLoader = iFxmlLoader;
	}

	/**
	 * The common function for scene change in the root stage. This is quite useful
	 * and efficient when we want to change the scene.
	 * 
	 * @param fxml
	 *            the name of the interface, like Template.fxml
	 * 
	 * @throws Exception
	 * 
	 */
	public static void replaceSceneContent(String fxml) throws Exception {

		FXMLLoader loader = new FXMLLoader(Template.class.getResource(fxml), null, new JavaFXBuilderFactory());
		Template.setiFxmlLoader(loader);

		// Parent page = (Parent) FXMLLoader.load(Template.class.getResource(fxml),
		// null, new JavaFXBuilderFactory());
		Parent page = (Parent) loader.load();

		Scene scene = primaryStage.getScene();
		if (scene == null) { // at the first time, create a new scene.
			scene = new Scene(page);
			// scene.getStylesheets().add(Template.class.getResource("demo.css").toExternalForm());
			primaryStage.setScene(scene);
		} else {
			primaryStage.getScene().setRoot(page);
		}
		primaryStage.sizeToScene();
		// return loader;
	}

}
