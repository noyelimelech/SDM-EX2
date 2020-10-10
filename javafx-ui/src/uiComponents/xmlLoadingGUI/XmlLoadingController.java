package uiComponents.xmlLoadingGUI;

import SDM.Exception.*;
import SDM.SDMEngine;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tasks.XmlLoadingTask;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.function.Consumer;

public class XmlLoadingController {

    @FXML private Button openXMLFileButton;
    @FXML private ProgressIndicator loadingProgressBar;
    @FXML private TextArea statusTextArea;
    @FXML private Label filePathLabel;

    private Stage stage;
    private SDMEngine sdmEngine;
    private Consumer<Boolean> updateWhenLoadingIsFinished;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public SDMEngine getSdmEngine() {
        return sdmEngine;
    }

    public void setSdmEngine(SDMEngine sdmEngine) {
        this.sdmEngine = sdmEngine;
    }

    public void setUpdateWhenLoadingIsFinishedConsumer(Consumer<Boolean> updateConsumer) {
        updateWhenLoadingIsFinished = updateConsumer;
    }

    public void bindTaskOfLoading(Task<Boolean> taskToBind) {
        loadingProgressBar.progressProperty().bind(taskToBind.progressProperty());
        taskToBind.messageProperty().addListener(((obs, oldMsg, newMsg) -> addNewLineToText(newMsg)));
    }

    private void addNewLineToText(String newTextLine) {
        statusTextArea.appendText("\n" + newTextLine);
    }

    @FXML
    private void openXMLFileButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select XML file to load");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if(selectedFile == null) {
            return;
        }
        XmlLoadingTask taskOfXMLLoading = new XmlLoadingTask();
        taskOfXMLLoading.setSdmEngine(sdmEngine);
        taskOfXMLLoading.setFileAbsolutePath(selectedFile.getAbsolutePath());
        taskOfXMLLoading.setUpdateWhenLoadingIsFinished(updateWhenLoadingIsFinished);
        bindTaskOfLoading(taskOfXMLLoading);
        loadingProgressBar.setStyle("-fx-progress-color:dodgerblue");

        taskOfXMLLoading.setOnFailed((observable -> {
            handleException(taskOfXMLLoading.getException());
            loadingProgressBar.setStyle("-fx-progress-color:red");
        }));

        filePathLabel.setText(selectedFile.getAbsolutePath());
        statusTextArea.clear();

        new Thread(taskOfXMLLoading).start();
    }

    private void handleException(Throwable exceptionThrown) {
        if (exceptionThrown instanceof FileNotFoundException) {
            addNewLineToText("\nERROR: The file does not exist in the path given, please try again.");
        } else if (exceptionThrown instanceof FileNotEndWithXMLException) {
            addNewLineToText("\nERROR: The file you given extension is" + ((FileNotEndWithXMLException) exceptionThrown).getFileType() + " and not an XML file, please make sure it ends with .xml and try again.");
        } else if (exceptionThrown instanceof LocationIsOutOfBorderException) {
            addNewLineToText("\nERROR: The object of type " + ((LocationIsOutOfBorderException) exceptionThrown).getLocatableType() +
                    " with id of: " + ((LocationIsOutOfBorderException) exceptionThrown).getId() + " is located out of allowed borders which are between "
                    + ((LocationIsOutOfBorderException) exceptionThrown).getMinBorder() + "to " + ((LocationIsOutOfBorderException) exceptionThrown).getMaxBorder() + ".Please fix this.");
        } else if (exceptionThrown instanceof DuplicateStoreItemException) {
            addNewLineToText("\nERROR: The store item with ID of " + ((DuplicateStoreItemException) exceptionThrown).getId() + " appears more than once in the XML file.");
        } else if (exceptionThrown instanceof DuplicateItemException) {
            addNewLineToText("\nERROR: The item with ID of " + ((DuplicateItemException) exceptionThrown).getId() + " appears more than once in the XML file.");
        } else if (exceptionThrown instanceof TryingToGiveDifferentPricesForSameStoreItemException) {
            addNewLineToText("\nERROR: The file has store with ID" + ((TryingToGiveDifferentPricesForSameStoreItemException) exceptionThrown).getStoreId() + "that try to give an item price multiple time. ");
        } else if (exceptionThrown instanceof TryingToGivePriceOfItemWhichIDNotExistException) {
            addNewLineToText("\nERROR: The file has store which trying to give a price of item which is ID " + ((TryingToGivePriceOfItemWhichIDNotExistException)exceptionThrown).getId() + " does not exist");
        } else if (exceptionThrown instanceof DuplicateStoreIDException) {
            addNewLineToText("\nERROR: The store with ID of " + ((DuplicateStoreIDException)exceptionThrown).getId() + " appears more than once in the XML file");
        } else if(exceptionThrown instanceof ItemNoOneSellException) {
            addNewLineToText("\nERROR: The item with ID " + ((ItemNoOneSellException)exceptionThrown).getId() + " doesnt sold by any store.");
        } else if(exceptionThrown instanceof StoreWithNoItemException) {
            addNewLineToText("\nERROR: The store with ID " + ((StoreWithNoItemException)exceptionThrown).getId() + " doesnt sell any items");
        }else {
            addNewLineToText("\nERROR: Unknown error has happen, the error message is: " + exceptionThrown.getMessage());
        }
    }
}
