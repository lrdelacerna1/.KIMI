package IDE;
import java.io.*;
import java.nio.file.*;

public class MipsSimulator {
    public static void main(String args) {
        try {
            
            //FROM US ANG STRING
            String mipsCode = args;
            // Sample MIPS code as a string
            //String mipsCode = ".data\n" +
            //                  ".text\n" +
            //                  "main:\n" +
            //                  "  li $a0, 42\n" +     // Load 42 into $a0
            //                  "  li $v0, 1\n" +      // Syscall for print integer
            //                  "  syscall\n" +        // Execute syscall
            //                  "  li $v0, 10\n" +     // Exit syscall
            //                  "  syscall\n";
            
            // Create a temporary file to store the MIPS code
            File tempFile = File.createTempFile("mipsCode", ".asm");
            tempFile.deleteOnExit();  // Ensure the file is deleted when the program exits
            
            // Write the MIPS code to the temporary file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(mipsCode);
            }

            // Absolute path to the QtSPIM executable
            //String spimCommand = "/Applications/QtSPIM.app/Contents/MacOS/qtspim";
            String spimCommand = "C:\\\\Program Files (x86)\\\\QtSpim\\\\QtSpim.exe";

            // Command to run the MIPS code with QtSPIM
            ProcessBuilder pb = new ProcessBuilder(spimCommand, "-file", tempFile.getAbsolutePath());
            
            // Start the process
            Process process = pb.start();
            
            // Capture the output of the QtSPIM simulation
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // Output the MIPS simulation result to Java console
            }
            
            // Capture any error output (if any)
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println(errorLine);  // Output any error messages
            }
            
            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println("QtSPIM exited with code: " + exitCode);
            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
