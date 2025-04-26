package IDE;

import javafx.fxml.FXML;
import javafx.scene.control.Button; // Import Button
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button; // Import Button
import javafx.scene.control.TextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import javafx.util.Duration;
import java.util.Optional;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ScrollEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class MainFXMLController {
    
    @FXML
    private TextArea codeEditor;  // TextArea where the code is written
    @FXML
    private Button btnSaveClicked; // Reference to the Save button
    private File currentFile;      // To keep track of the currently opened file
    private boolean isModified = false; // Flag to track if the code has been modified
    private boolean isNew = false;
    private boolean isSaved = false;
    @FXML
    private Button btnOpenFileClicked;
    @FXML
    private Button btnNewFileClicked;
    @FXML
    private Button btnSaveAsClicked;
    @FXML
    private Button btnCloseFileClicked;
    @FXML
    private Button btnCopyClicked;
    @FXML
    private Button btnPasteClicked;
    @FXML
    private Button btnUndoClicked;
    @FXML
    private Button btnRedoClicked;
    @FXML
    private Button btnCompileClicked;
    @FXML
    private Button btnExecuteClicked;
    @FXML
    private Tab tab1;
    @FXML
    private TextArea nums;
    @FXML
    private TextArea terminal;
     @FXML
    private TreeView<File> fileTreeView; // Changed to TreeView<File>

    // Path to the currently open file in the IDE
    private File currentlyOpenFile;
    @FXML
    private ToolBar toolbar;
    @FXML
    private Label output;


    public void initialize() {
        // Set tooltips with hover delays
      
        setTooltip(btnSaveClicked, "Save", 0.2);
        setTooltip(btnOpenFileClicked, "Open File", 0.2);
        setTooltip(btnNewFileClicked, "New File", 0.2);
        setTooltip(btnSaveAsClicked, "Save As", 0.2);
        setTooltip(btnCloseFileClicked, "Close ", 0.2);
        setTooltip(btnCopyClicked, "Copy", 0.2);
        setTooltip(btnPasteClicked, "Paste", 0.2);
        setTooltip(btnUndoClicked, "Undo", 0.2);
        setTooltip(btnRedoClicked, "Redo", 0.2);
        setTooltip(btnCompileClicked, "Compile", 0.2);
        setTooltip(btnExecuteClicked, "Execute", 0.2);
        codeEditor.textProperty().addListener((obs, oldText, newText) -> updateLineNumbers());
        setupScrollSynchronization(codeEditor, nums);
        setupScrollSynchronization(nums, codeEditor);
        
        //tab1.setVisible(false);
        codeEditor.setVisible(false);
        disableAllButtonsExceptNewAndOpen();
        
        // Set up the root directory (could be your project workspace folder)
        String projectDirectory = System.getProperty("user.dir");  // Set this to your IDE's project root
        File rootDir = new File(projectDirectory);
        TreeItem<File> rootItem = new TreeItem<>(rootDir);
        fileTreeView.setRoot(rootItem);

        // Populate the TreeView with files and directories from the project folder
        createTree(rootDir, rootItem);

        // Set the custom cell factory
        fileTreeView.setCellFactory(new Callback<TreeView<File>, TreeCell<File>>() {
            @Override
            public TreeCell<File> call(TreeView<File> param) {
                return new TreeCell<File>() {
                    @Override
                    protected void updateItem(File item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                            setStyle(""); // Clear any styles
                        } else {
                            setText(item.getName());
                            // Apply style if this item represents the currently open file
                            if (item.equals(currentlyOpenFile)) {
                                setStyle("-fx-font-weight: bold; -fx-background-color: lightblue;");
                            } else {
                                setStyle("");
                            }
                        }
                    }
                };
            }
        });
         
    }
    
    // Recursive method to create the TreeView structure
    private void createTree(File dir, TreeItem<File> parent) {
        if (dir.listFiles() != null) {
            for (File file : dir.listFiles()) {
               TreeItem<File> item = new TreeItem<>(file);
               parent.getChildren().add(item);
                if (file.isDirectory()) {
                    createTree(file, item);  // Recursively add subdirectories and files
                }
            }
        }
    }
    
    

    // Set the currently open file (this could be called from your IDE logic when a file is opened)
    public void setCurrentlyOpenFile(File openFile) {
        this.currentlyOpenFile = openFile;
        fileTreeView.refresh(); // Refresh the TreeView to update styles
    }
    
        // Helper method to set tooltip with delay
    private void setTooltip(Button button, String tooltipText, double seconds) {
        Tooltip tooltip = new Tooltip(tooltipText);
        tooltip.setShowDelay(new javafx.util.Duration(seconds * 1000)); // Set delay in milliseconds
        Tooltip.install(button, tooltip); // Install the tooltip to the button
    }
    
     private void disableAllButtonsExceptNewAndOpen() {
        btnSaveClicked.setDisable(true);
        btnSaveAsClicked.setDisable(true);
        btnCloseFileClicked.setDisable(true);
        btnCopyClicked.setDisable(true);
        btnPasteClicked.setDisable(true);
        btnUndoClicked.setDisable(true);
        btnRedoClicked.setDisable(true);
        btnCompileClicked.setDisable(true);
        btnExecuteClicked.setDisable(true);
    }
    // Method to open a new file (clears the TextArea)
    @FXML
    private void btnNewFileClicked(ActionEvent event) {
        if (isModified) {
            // Create an alert to confirm saving changes
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Unsaved Changes");
            alert.setHeaderText("You have unsaved changes.");
            alert.setContentText("Do you want to save your changes before closing?");

            // Add buttons for Yes, No, and Cancel
            ButtonType saveButton = new ButtonType("Save");
            ButtonType discardButton = new ButtonType("Don't Save");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(saveButton, discardButton, cancelButton);

            // Show the alert and wait for the user's response
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == saveButton) {
                    // Call the save method to save changes
                    btnSaveClicked(null); // Pass null since ActionEvent is not needed
                    clearEditor();
                } else if (result.get() == discardButton) {
                    // Discard changes, just close the file
                    clearEditor();
                } // If cancel, do nothing and return from the method
            }
        }
        codeEditor.clear();
        codeEditor.setVisible(true);
        terminal.clear();
        terminal.setVisible(true);
        currentFile = null; // Reset current file
        isModified = false; // Reset modified flag
        isNew = true;
        updateAllButtonStateAfterNew();
        updateClose();
        tab1.setText("Untitled"); 
        showAlert("Ready to start a new file!");
        
    }

    // Method to open an existing file
    @FXML
    private void btnOpenFileClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Kimi File");

        // Add filter for your custom .kimi files only
        FileChooser.ExtensionFilter kimiFilter = new FileChooser.ExtensionFilter("Kimi Files (*.kimi)", "*.kimi");
        fileChooser.getExtensionFilters().add(kimiFilter);

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                // Read the content of the selected .kimi file
                String content = new String(Files.readAllBytes(file.toPath()));
                codeEditor.clear();
                codeEditor.setText(content);
                codeEditor.setVisible(true);
                terminal.clear();
                terminal.setVisible(true);
                currentFile = file; // Update current file
                //isModified = false; // Reset modified flag after loading a file
                btnCompileClicked.setDisable(false); // Enable Compile if content is modified
                btnExecuteClicked.setDisable(false);
                String fileName = file.getName();
                tab1.setText(fileName); 
                updateAllButtonStateAfterSave();
                updateClose();
            } catch (IOException e) {
                showAlert("File not found or could not be read.");
            }
        }
    }


    // Method to save the file
    @FXML
    private void btnSaveClicked(ActionEvent event) {
        if (currentFile == null) { // If no file is currently saved, show a file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Kimi File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Kimi Files (*.kimi)", "*.kimi"));
            currentFile = fileChooser.showSaveDialog(null);

            // If a file is chosen, ensure it has a .kimi extension
            if (currentFile != null && !currentFile.getName().endsWith(".kimi")) {
                currentFile = new File(currentFile.getAbsolutePath() + ".kimi");
            }
        }

        // Save the contents of the TextArea to the file
        if (currentFile != null) {
            try {
                Files.write(currentFile.toPath(), codeEditor.getText().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                 // Reset modified flag after saving
                tab1.setText(currentFile.getName()); 
                isModified = false;
                updateAllButtonStateAfterSave();
             
                showAlert("File saved successfully!");
                isNew = false;
                updateClose();
                
            } catch (IOException e) {
                showAlert("Error saving the file: " + e.getMessage());
            }
        }
    }

    private void updateLineNumbers() {
        String[] lines = codeEditor.getText().split("\n");

        // Create a StringBuilder to hold line numbers
        StringBuilder lineNumbers = new StringBuilder();

        // Populate the StringBuilder with line numbers
        for (int i = 1; i <= lines.length; i++) {
            lineNumbers.append(i).append("\n");  // Adding line number with a newline
        }

        // Set the text in the TextArea
        nums.setText(lineNumbers.toString());
    }

    private void setupScrollSynchronization(TextArea source, TextArea target) {
        source.scrollTopProperty().addListener((observable, oldValue, newValue) -> {
        // Adjust the scroll position of the target TextArea based on the source's scroll position
        target.setScrollTop(newValue.doubleValue());
    });

    // You can do the same for horizontal scrolling if needed
    source.scrollLeftProperty().addListener((observable, oldValue, newValue) -> {
        target.setScrollLeft(newValue.doubleValue());
    });
    }
    
    // Method to handle code modifications
    @FXML
    private void handleCodeModification() {
        isModified = true; // Set modified flag
        if (isNew){
            updateAllButtonStateAfterNew();
        }
        else {
            updateAllButtonStateAfterSave();
        }
        updateAllButtonState();
        
    }
    
    private void updateClose() {
        btnCloseFileClicked.setDisable(false);
    }
    
    private void updateAllButtonState() {
        btnCloseFileClicked.setDisable(false); // Enable Close if there's an open file
        btnCompileClicked.setDisable(!isModified); // Enable Compile if content is modified
        btnExecuteClicked.setDisable(!isModified); // Enable Execute if content is modified
        btnCopyClicked.setDisable(codeEditor.getText().isEmpty()); // Enable Copy only if there's text
        btnPasteClicked.setDisable(!isModified); // Always enable Paste (assuming content is in clipboard)
        btnUndoClicked.setDisable(!isModified); // Enable Undo if content is modified
        btnRedoClicked.setDisable(!isModified); // Enable Redo if content is modified
    
    }
    // Method to update the state of the after a new file, no save as yet
    private void updateAllButtonStateAfterNew() {
        boolean hasContent = !codeEditor.getText().trim().isEmpty();
    
        btnSaveClicked.setDisable(!isModified || !hasContent); // Enable Save if modified and there is content
        btnSaveAsClicked.setDisable(true); // Enable Save As only if a file has been saved
    }
    
     // Method to update the state of the after a new file, no save as yet
    private void updateAllButtonStateAfterSave() {
        boolean hasContent = !codeEditor.getText().trim().isEmpty();
    
        btnSaveClicked.setDisable(!isModified || !hasContent); // Enable Save if modified and there is content
        btnSaveAsClicked.setDisable(!hasContent); // Enable Save As only if the editor has content
        
    }
    
    private void updateSaveButtonState() {
        btnSaveClicked.setDisable(!isModified);
    }

    // Helper method to show alerts
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    private void btnSaveAsClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Kimi Files (*.kimi)", "*.kimi"));

        // Show the save dialog and get the selected file
        File newFile = fileChooser.showSaveDialog(null);

        // If a file is chosen, ensure it has a .kimi extension
        if (newFile != null && !newFile.getName().endsWith(".kimi")) {
            newFile = new File(newFile.getAbsolutePath() + ".kimi");
        }

        // Save the contents of the TextArea to the new file
        if (newFile != null) {
            try {
                Files.write(newFile.toPath(), codeEditor.getText().getBytes(), 
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                currentFile = newFile; // Update the current file to the new file
                isModified = false; // Reset modified flag after saving
                updateAllButtonStateAfterSave();
                tab1.setText(currentFile.getName()); 
                showAlert("File saved as successfully!");
                updateClose();
            } catch (IOException e) {
                showAlert("Error saving the file: " + e.getMessage());
            }
        }
    }


    @FXML
    private void btnCloseFileClicked(ActionEvent event) {
        // Check if there are unsaved changes
        if (isModified) {
            // Create an alert to confirm saving changes
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Unsaved Changes");
            alert.setHeaderText("You have unsaved changes.");
            alert.setContentText("Do you want to save your changes before closing?");

            // Add buttons for Yes, No, and Cancel
            ButtonType saveButton = new ButtonType("Save");
            ButtonType discardButton = new ButtonType("Don't Save");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(saveButton, discardButton, cancelButton);

            // Show the alert and wait for the user's response
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == saveButton) {
                    // Call the save method to save changes
                    btnSaveClicked(null); // Pass null since ActionEvent is not needed
                    clearEditor();
                } else if (result.get() == discardButton) {
                    // Discard changes, just close the file
                    clearEditor();
                } // If cancel, do nothing and return from the method
            }
        } else {
            // No unsaved changes, just close the file
            clearEditor();
            //closeApplicationWindow();
            
        }
        disableAllButtonsExceptNewAndOpen();
        isNew = true;
        updateAllButtonStateAfterNew();
        tab1.setText(null); 
        codeEditor.setVisible(false);
        terminal.setVisible(false);
        nums.clear();
        currentFile = null; // Reset current file
        isModified = false; // Reset modified flag
    }
    
    private void closeApplicationWindow() {
    // Get the current window (stage) and close it
    Stage stage = (Stage) btnCloseFileClicked.getScene().getWindow();
    stage.close(); // Close the entire window
    }
    

    // Helper method to clear the TextArea and reset variables
    private void clearEditor() {
        codeEditor.clear();
    }


    // Method to handle Copy action
    @FXML
    private void btnCopyClicked(ActionEvent event) {
        codeEditor.copy(); // Copy selected text to clipboard
    }

    // Method to handle Cut action
    private void btnCutClicked(ActionEvent event) {
        codeEditor.cut(); // Cut selected text to clipboard
    }

    // Method to handle Paste action
    @FXML
    private void btnPasteClicked(ActionEvent event) {
        codeEditor.paste(); // Paste text from clipboard
    }

    // Method to handle Undo action
    @FXML
    private void btnUndoClicked(ActionEvent event) {
        codeEditor.undo(); // Undo the last action
    }

    // Method to handle Redo action
    @FXML
    private void btnRedoClicked(ActionEvent event) {
        codeEditor.redo(); // Redo the last undone action
    }

    @FXML
    private void btnCompileClicked(ActionEvent event) {
        // If the code is modified, save it before compiling
        if (isModified) {
            btnSaveClicked(null); // Save the code before compiling
        }

    if (currentFile != null) {
        try {
            // Assuming you have a custom compiler command
            compileSourceFile(currentFile.getPath()); // Compile the source code
            showAlert("Compilation successful!");
        } catch (Exception e) {
            showAlert("Error during compilation: " + e.getMessage());
        }
    } else {
        showAlert("No file to compile. Please save the code first.");
    }
}

    @FXML
    private void btnExecuteClicked(ActionEvent event) {
        // If the code is modified, save it before compiling
        if (isModified) {
            btnSaveClicked(null); // Save the code before compiling
        }

        if (currentFile != null) {
            try {
                // Compile the source code first
                compileSourceFile(currentFile.getPath());

                // Then execute the compiled code
                executeProgram(currentFile.getPath());
                showAlert("Execution finished!");
            } catch (Exception e) {
                showAlert("Error during execution: " + e.getMessage());
            }
        } else {
            showAlert("No file to execute. Please save the code first.");
        }
    }

    // changed kay nadouble ang pag call sa recursive parser, both sa terminal and mips
    private void compileSourceFile(String sourceFilePath) throws IOException, InterruptedException {
        // Assuming you have a custom compiler (use ProcessBuilder to run external commands)
        String[] toPass = {codeEditor.getText()};

        // Run the parser once and store the result
        String parsedCode = RecursiveDescentParser.main(toPass);

        // Set the parsed code to the terminal
        terminal.setText(parsedCode);

        // Pass the parsed code to the MIPS simulator
        MipsSimulator.main(parsedCode);
    }

// Helper method to execute the compiled program
    private void executeProgram(String compiledFilePath) throws IOException, InterruptedException {
        // Assuming you have an execution command (use ProcessBuilder to run the compiled file)
        ProcessBuilder pb = new ProcessBuilder("your-execution-command", compiledFilePath);
        Process process = pb.start();
        int exitCode = process.waitFor(); // Wait until the process finishes
        if (exitCode != 0) {
            throw new IOException("Execution failed with exit code: " + exitCode);
        }
    }

    @FXML
    private void handleTerminalModification(KeyEvent event) {
    }

}
