package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import DAO.GameResultDAO;
import model.GameResult;
import util.FileUtil;
import util.StringUtil;
import view.Template;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * The controller of the rank view - Rank.fxml. This
 * controller includes basic view loading functions and logical functions for
 * basic operations on the view. This controller calls the functions from
 * GameResultDAO. The users can read ranking on view Rank.fxml.
 * 
 * @author Jiaxiang Shan on 02/09/2020.
 */

public final class RankController implements Initializable {

	@FXML
	private Button backButton;

	@FXML
	private TableView<GameResult> gameResult;
	
	@FXML
	private TableColumn<GameResult, String> userNameColumn;
	
	@FXML
	private TableColumn<GameResult, Integer> scoreColumn;
	
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
		
		this.initialSetAllElementProperities();

	}
	
	private void initialSetAllElementProperities() {

		// Fill the table column with object data.
		ingredientNameColumn.setCellValueFactory(cellData -> cellData.getValue().getStringProperityIngredientName());
		ingredientQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().getDoubleProperityQuantity());
		ingredientUnitColumn.setCellValueFactory(cellData -> cellData.getValue().getStringProperityUnit());
		ingredientCommentColumn.setCellValueFactory(cellData -> cellData.getValue().getStringProperityComment());

		stepOrderColumn.setCellValueFactory(cellData -> cellData.getValue().getIntegerProperityStepOrder());
		stepContentColumn.setCellValueFactory(cellData -> cellData.getValue().getStringProperityStepContent());

		// Actions when the cancel button is clicked.
		this.cancel.setOnAction((event) -> {
			Alert alert = new Alert(AlertType.WARNING, "Do you want to quit editting?", ButtonType.YES,
					ButtonType.CANCEL);

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.YES) { // User confirms the deletion.
				this.editStage.close();
			}

		});

		// Set edit events in ingredients table.
		ingredients.setEditable(true);

		// Set actions when editing ingredientNameColumn.
		ingredientNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		ingredientNameColumn.setOnEditCommit((event) -> {
			event.getTableView().getItems().get(event.getTablePosition().getRow())
					.setIngredientName(event.getNewValue());
		});

		// Set actions when editing ingredientQuantityColumn.
		ingredientQuantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() {

			@Override
			public Double fromString(String string) {
				return Double.valueOf(string);
			}

			@Override
			public String toString(Double object) {
				return String.valueOf(object);
			}
		}));

		// Set actions when editing ingredientQuantityColumn.
		ingredientQuantityColumn.setOnEditCommit((event) -> {
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setQuantity(event.getNewValue());
		});

		// Set actions when editing ingredientUnitColumn.
		ingredientUnitColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		ingredientUnitColumn.setOnEditCommit((event) -> {
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setUnit(event.getNewValue());
		});

		// Set actions when editing ingredientCommentColumn.
		ingredientCommentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		ingredientCommentColumn.setOnEditCommit((event) -> {
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setComment(event.getNewValue());
		});

		// Actions when the add row button is clicked.
		this.addIngredient.setOnAction((event) -> {
			Ingredient ingredient;
			ingredient = new Ingredient("", 0.0d, 0, Ingredient.GRAM, "");
			int row = ingredients.getSelectionModel().getSelectedIndex();
			if (row == -1) { // if no row is selected
				row = ingredients.getItems().size() - 1;
				this.ingredients.getItems().add(ingredient);
				ingredients.scrollTo(ingredient);

			} else {

				this.ingredients.getItems().add(row + 1, ingredient);

			}

			ingredients.getSelectionModel().select(row + 1);
		});

		// Actions when the remove row button is clicked.
		this.removeIngredient.setOnAction((event) -> {

			int row = ingredients.getSelectionModel().getSelectedIndex();

			if (row < 0) {

				new Alert(AlertType.ERROR, "Please select one row ! ", ButtonType.CLOSE).showAndWait();

			} else {

				Ingredient ingredient = ingredients.getItems().get(row);

				if (ingredient.getIngredientID() != null) { // means the old ingredients.

					this.deletedIngredients.add(ingredient);

				}
				//
				// Alert alert = new Alert(AlertType.WARNING,"Do you want to delete ingredient "
				// +ingredient.getIngredientName() +"?",ButtonType.YES,ButtonType.NO);
				//
				// Optional<ButtonType> result = alert.showAndWait();
				//
				// if(result.get() == ButtonType.YES) {
				//
				// if(IngredientDAO.deleteIngredientById(ingredient.getIngredientID())) {
				//
				// new Alert(AlertType.CONFIRMATION,"Ingredient has been deleted",);
				//
				// }else {
				//
				// }
				//
				//
				// }
				//
				// }

				this.ingredients.getItems().remove(row);
				if (row < this.ingredients.getItems().size()) {
					// If selected within the range of table, cursor stay
					this.ingredients.getSelectionModel().select(row);
				} else {
					// If selected the last row of the table, cursor goes upward.
					this.ingredients.getSelectionModel().select(row - 1);
				}

			}

		});

		// Set edit events in steps table.
		steps.setEditable(true);

		// Set actions when editing stepContentColumn.
		stepContentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		stepContentColumn.setOnEditCommit((event) -> {
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setContent(event.getNewValue());
		});

		// Set actions when editing stepOrderColumn.
		stepOrderColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {

			@Override
			public Integer fromString(String string) {
				return Integer.valueOf(string);
			}

			@Override
			public String toString(Integer object) {
				return String.valueOf(object);
			}
		}));

		// Actions when the addStep button is clicked.
		this.addStep.setOnAction((event) -> {

			Step step = new Step("", 1, 0);
			// int row = ingredients.getItems().size() - 1;
			int row = steps.getSelectionModel().getSelectedIndex();
			if (row == -1 && steps.getItems().size() == 0) { // if the table is empty.

				this.steps.getItems().add(step);
				steps.scrollTo(step);

			} else if (row == -1 && steps.getItems().size() > 0) { // if no row is selected.

				step.setStepOrder(steps.getItems().size() + 1);
				this.steps.getItems().add(step);
				steps.scrollTo(step);

			} else {

				step.setStepOrder(row + 1);
				this.steps.getItems().add(row + 1, step);

				for (int i = row + 1; i < steps.getItems().size(); i += 1) {
					steps.getItems().get(i).setStepOrder(i + 1);
				}

			}

			steps.getSelectionModel().select(row + 1);

		});

		// Actions when the removeStep button is clicked.
		this.removeStep.setOnAction((event) -> {

			int row = steps.getSelectionModel().getSelectedIndex();
			Step step;

			if (row < 0) {
				new Alert(AlertType.ERROR, "Please select one row ! ", ButtonType.CLOSE).showAndWait();
			} else {

				step = steps.getItems().get(row);

				if (step.getStepID() != null) {

					this.deletedSteps.add(step);

				}

				// Set the step order to their proper form.
				for (int i = row + 1; i < steps.getItems().size(); i += 1) {
					steps.getItems().get(i).setStepOrder(i);
				}

				this.steps.getItems().remove(row);
				if (row < this.steps.getItems().size()) {
					// If selected within the range of table, cursor stay
					this.steps.getSelectionModel().select(row);
				} else {
					// If selected the last row of the table, cursor up
					this.steps.getSelectionModel().select(row - 1);
				}

			}

		});

		// Actions when the choosePicture button is clicked.
		choosePicture.setOnAction((event) -> {
			File selectedFile;
			this.fileChooser = new FileChooser();
			fileChooser.setTitle("Pick the image for the recipe");
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
					new FileChooser.ExtensionFilter("JPEG", "*.jpeg"), new FileChooser.ExtensionFilter("JPG", "*.jpg"),
					new FileChooser.ExtensionFilter("PNG", "*.png"));
			selectedFile = fileChooser.showOpenDialog(this.parentStage);
			if (selectedFile != null) {
				// resolve the different notation for system path for Windows and MacOS
				String systemPath = System.getProperty("user.dir") + "/" + MainFrameController.RECIPE_IMAGE_DEFAULT_PATH
						+ selectedFile.getName();
				try {
					this.imagePath = selectedFile.getName();
					FileUtil.copyFile(selectedFile, systemPath);
					this.newRecipeImage.setImage(new Image(selectedFile.toURI().toString(), 100, 100, false, false));
					this.defaultImageView.setText("");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});

		// Actions when the amountOfPeople textArea is changed.
		amountOfPeople.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (StringUtil.isNumeric(newValue)) {
					restoreNumberPromptWarning();

				} else {
					setNumberPromptWarning();
				}

			}

		});

		// Actions when the preparingTime textArea is changed.
		preparingTime.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (StringUtil.isNumeric(newValue)) {
					restoreNumberPromptWarning();

				} else {
					setNumberPromptWarning();
				}

			}

		});

		// Actions when the preparingTime textArea is changed.
		cookingTime.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (StringUtil.isNumeric(newValue)) {
					restoreNumberPromptWarning();

				} else {
					setNumberPromptWarning();
				}

			}

		});

		// Actions when the saveRecipe button is clicked.
		saveRecipe.setOnAction((event) -> {

			if (this.formValidationCheck()) {

				if (this.isAddingRecipe) { // add new recipe add form validation.

					boolean flag = false;

					Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to save this Recipe?",
							ButtonType.APPLY, ButtonType.CANCEL);

					Optional<ButtonType> result = alert.showAndWait();

					if (result.get() == ButtonType.APPLY) {

						flag = this.insertRecipeIntoDB();

					}

					if (flag) {

						new Alert(AlertType.INFORMATION, "Recipe Successfully Inserted !", ButtonType.OK).showAndWait();

						Object controllerObject = Template.getiFxmlLoader().getController();

						if (controllerObject instanceof MainFrameController) {
							MainFrameController controller = (MainFrameController) controllerObject;

							controller.refreshWholeInterface();
						}

						this.editStage.close();

					}

				} else { // Edit recipe

					boolean flag = false;

					Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to update this Recipe?",
							ButtonType.APPLY, ButtonType.CANCEL);

					Optional<ButtonType> result = alert.showAndWait();

					if (result.get() == ButtonType.APPLY) {

						flag = this.updateRecipeIntoDB();

					}

					if (flag) {

						new Alert(AlertType.INFORMATION, "Recipe Successfully Updated !", ButtonType.OK).showAndWait();

						Object controllerObject = Template.getiFxmlLoader().getController();

						if (controllerObject instanceof MainFrameController) {
							MainFrameController controller = (MainFrameController) controllerObject;

							controller.refreshWholeInterface();
						}

						this.editStage.close();

					}

				}

			}

		});

	}
	
	
}
