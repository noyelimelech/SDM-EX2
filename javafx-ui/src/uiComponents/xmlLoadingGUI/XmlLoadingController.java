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

import java.io.File;
import java.io.FileNotFoundException;

public class XmlLoadingController {

    @FXML private Button openXMLFileButton;
    @FXML private ProgressIndicator loadingProgressBar;
    @FXML private TextArea statusTextArea;
    @FXML private Label filePathLabel;

    private Stage stage;
    private SDMEngine sdmEngine;

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
        File selectedFile = fileChooser.showOpenDialog(stage);
        boolean isLoadingSucceeded = false;

        filePathLabel.setText(selectedFile.getAbsolutePath());
        statusTextArea.clear();

        try {
                sdmEngine.updateAllStoresAndAllItemsAndAllCustomers(selectedFile.getAbsolutePath());
                isLoadingSucceeded = true;
        }
        catch (FileNotFoundException ex) {
            statusTextArea.appendText("\nERROR: The file does not exist in the path given, please try again.");
        }
        catch(FileNotEndWithXMLException ex) {
            statusTextArea.appendText("\nERROR: The file you given extension is" + ex.getFileType() + " and not an XML file, please make sure it ends with .xml and try again.");
        }
        catch(LocationIsOutOfBorderException ex) {
            statusTextArea.appendText("\nERROR: The object of type " +  ex.getLocatableType()+
                    " with id of: " + ex.getId() + " is located out of allowed borders which are between "
                    + ex.getMinBorder() + "to " + ex.getMaxBorder() + ".Please fix this.");
        }
        catch(DuplicateStoreItemException ex) {
            statusTextArea.appendText("\nERROR: The store item with ID of " + ex.getId() + " appears more than once in the XML file.");
        }
        catch(DuplicateItemException ex) {
            statusTextArea.appendText("\nERROR: The item with ID of " + ex.getId() + " appears more than once in the XML file.");
        }
        catch (TryingToGiveDifferentPricesForSameStoreItemException e) {
            statusTextArea.appendText("\nERROR: The file has store with ID" + e.getStoreId() + "that try to give an item price multiple time. ");
        }
        catch (TryingToGivePriceOfItemWhichIDNotExistException ex) {
            statusTextArea.appendText("\nERROR: The file has store which trying to give a price of item which is ID "+ex.getId()+" does not exist");
        }
        catch(DuplicateStoreIDException ex) {
            statusTextArea.appendText("\nERROR: The store with ID of " + ex.getId() + " appears more than once in the XML file");
        }
        catch (ItemNoOneSellException ex) {
            statusTextArea.appendText("\nERROR: The item with ID " + ex.getId() + " doesnt sold by any store.");
        }
        catch (StoreWithNoItemException ex) {
            statusTextArea.appendText("\nERROR: The store with ID " + ex.getId() + " doesnt sell any items");
        }
        catch(Exception ex) {
            statusTextArea.appendText("\nERROR: Unknown error has happen, the error message is: " + ex.getMessage());
        }
        finally {
            if(isLoadingSucceeded) {
                statusTextArea.appendText("\nLoading is done. All the data loaded from the XML file is updated in the system.");
            }
            else{
                statusTextArea.appendText("\nLoading has been stopped. Please fixed the above issues and try again.");
            }
        }

    }

}
