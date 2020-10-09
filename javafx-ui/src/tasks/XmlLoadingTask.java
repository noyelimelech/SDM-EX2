package tasks;

import SDM.Exception.*;
import SDM.SDMEngine;
import javafx.concurrent.Task;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.function.Consumer;

public class XmlLoadingTask extends Task<Boolean> {

    private Consumer<Boolean> updateWhenLoadingIsFinished;
    private String fileAbsolutePath;
    private SDMEngine sdmEngine;


    public Consumer<Boolean> getUpdateWhenLoadingIsFinished() {
        return updateWhenLoadingIsFinished;
    }

    public void setUpdateWhenLoadingIsFinished(Consumer<Boolean> updateWhenLoadingIsFinished) {
        this.updateWhenLoadingIsFinished = updateWhenLoadingIsFinished;
    }

    public String getFileAbsolutePath() {
        return fileAbsolutePath;
    }

    public void setFileAbsolutePath(String fileAbsolutePath) {
        this.fileAbsolutePath = fileAbsolutePath;
    }

    public SDMEngine getSdmEngine() {
        return sdmEngine;
    }

    public void setSdmEngine(SDMEngine sdmEngine) {
        this.sdmEngine = sdmEngine;
    }

    @Override
    protected Boolean call() throws Exception {
        boolean isLoadingSucceeded = false;

        try {
            sdmEngine.updateAllStoresAndAllItemsAndAllCustomers(
                    fileAbsolutePath,
                    (progressMessage) -> this.updateMessage(progressMessage),
                    (progressPercent) -> this.updateProgress(progressPercent, 1));
            isLoadingSucceeded = true;
        } finally {
            if (isLoadingSucceeded) {
                updateProgress(1.0,1.0);
                updateMessage("\nLoading is done. All the data loaded from the XML file is updated in the system.");
            } else {
                updateMessage("\nLoading has been stopped. Please fixed the beloew issues and try again.");
                updateProgress(0.0,1.0);
            }
            if(updateWhenLoadingIsFinished != null) {
                updateWhenLoadingIsFinished.accept(isLoadingSucceeded);
            }
        }

        return isLoadingSucceeded;
    }
}
