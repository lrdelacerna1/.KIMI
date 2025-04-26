/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IDE;

/**
 *
 * @author isabelalobitana
 */
import java.util.*;
import java.util.regex.*;
import javax.swing.JOptionPane;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;

//added the input string na part 
public class RecursiveDescentParser {

     //Define tokens
    private enum TokenType {
        WORD, DATATYPE, NUMBER, ADD, MULTIPLY, DIVIDE, SUBTRACT, INPUT, COMMA, COLON, ASSIGNMENT, LET, EOF, IF, ELSEIF, FOR, ELSE, OUTPUT, ASTERIS, OPENBRACKET, CLOSEBRACKET, CURLL, CURLR, EQ, NE,GE,LE,GT,LT
    }
    
    private static class Token {
        TokenType type;
        String value;

        Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }
        
//        @Override
//        public String toString() {
//        return "Token{" +
//                "type=" + type +
//                ", value='" + value + '\'' +
//                '}';
//        }
    }

    private static class Lexer {
        // Regex patterns for each TOKEN TYPE
        
        
        private static final String ADD = "\\bdugangi it\\b";
        private static final String MULTIPLY = "\\bigpilopilo it\\b";
        private static final String DIVIDE = "\\bbahini it\\b";
        private static final String SUBTRACT = "\\bibani it\\b";
        private static final String INPUT = "\\bpangaro hin\\b";
        private static final String OUTPUT = "\\bipakita\\b";
        private static final String COMMA = ",";
        private static final String COLON = ":";
        private static final String DATATYPE = "\\bhimua nga\\b";
        private static final String ASSIGNMENT = "\\bigbutang ha\\b";
        private static final String LET = "\\bit\\b";
        private static final String NUMBER = "\\b\\d+(\\.\\d+)?\\b";
        private static final String IF = "\\bkung\\b";
        private static final String ELSEIF = "\\bkung dire\\b";
        private static final String ELSE = "\\bdire\\b";
        private static final String FOR = "\\bhabang\\b";
        private static final String ASTERIS = "\\*";
        private static final String WORD =  "\\b[a-zA-Z_][a-zA-Z0-9_]*\\b";
        private static final String OPENBRACKET = "\\[";
        private static final String CLOSEBRACKET = "\\]";
        private static final String EQ = "\\==";
        private static final String NE = "\\!=";
        private static final String GE = "\\>=";
        private static final String LE = "\\<=";
        private static final String GT = "\\>";
        private static final String LT = "\\<";
        private static final String CURLL = "\\{";
        private static final String CURLR = "\\}";
        
        //<LET> <WORD/IDENTIFIER><,><DATATYPE> <NUMBER>
        //<OUTPUT><:> <WORD>  (prints in console)
        //<INPUT> <NUMBER><:> <ASSIGNMENT> <WORD/IDENTIFIER> 
        //<ADD>|<SUBTRACT>|<MULTIPLY>|<DIVIDE> <WORD><,> <WORD><,> <WORD><:> <ASSIGNMENT> <WORD> 
        

        // Combined regex pattern
        private static final Pattern TOKEN_PATTERNS = Pattern.compile(
            String.format(
                "((?<NUMBER>%s)|(?<ADD>%s)|(?<MULTIPLY>%s)|(?<DIVIDE>%s)|(?<SUBTRACT>%s)|(?<INPUT>%s)|(?<LET>%s)|(?<DATATYPE>%s)|(?<COMMA>%s)|(?<COLON>%s)|(?<OUTPUT>%s)|(?<ASSIGNMENT>%s)|(?<IF>%s)|(?<ELSEIF>%s)|(?<ELSE>%s)|(?<FOR>%s)|(?<ASTERIS>%s)|(?<WORD>%s)|(?<OPENBRACKET>%s)|(?<CLOSEBRACKET>%s)|(?<CURLL>%s)|(?<CURLR>%s)|(?<EQ>%s)|(?<NE>%s)|(?<GE>%s)|(?<LE>%s)|(?<GT>%s)|(?<LT>%s))",
                NUMBER, ADD, MULTIPLY, DIVIDE, SUBTRACT, INPUT, LET, DATATYPE, COMMA, COLON, OUTPUT, ASSIGNMENT, IF, ELSEIF, ELSE, FOR, ASTERIS, WORD, OPENBRACKET, CLOSEBRACKET, CURLL, CURLR, EQ, NE, GE, LE, GT, LT
            )
        );


        private final String input; 
        private final Matcher matcher;

        Lexer(String input) {
            this.input = input;
            this.matcher = TOKEN_PATTERNS.matcher(input);
        }

        List<Token> tokenize() { //creates a token with its type and value
            
            List<Token> tokens = new ArrayList<>();
            while (matcher.find()) {
                if (matcher.group("NUMBER") != null) {
                    Token token = new Token(TokenType.NUMBER, matcher.group("NUMBER"));
                    tokens.add(token);
                    System.out.println("NUMBER: " + token.value);
                } else if (matcher.group("ADD") != null) {
                    Token token = new Token(TokenType.ADD, matcher.group("ADD"));
                    tokens.add(token);
                    System.out.println("ADD: " + token.value);
                } else if (matcher.group("MULTIPLY") != null) {
                    Token token = new Token(TokenType.MULTIPLY, matcher.group("MULTIPLY"));
                    tokens.add(token);
                    System.out.println("MULTIPLY: " + token.value);
                } else if (matcher.group("DIVIDE") != null) {
                    Token token = new Token(TokenType.DIVIDE, matcher.group("DIVIDE"));
                    tokens.add(token);
                    System.out.println("DIVIDE: " + token.value);
                } else if (matcher.group("SUBTRACT") != null) {
                    Token token = new Token(TokenType.SUBTRACT, matcher.group("SUBTRACT"));
                    tokens.add(token);
                    System.out.println("SUBTRACT: " + token.value);
                } else if (matcher.group("DATATYPE") != null) {
                    Token token = new Token(TokenType.DATATYPE, matcher.group("DATATYPE"));
                    tokens.add(token);
                    System.out.println("DATATYPE: " + token.value);
                } else if (matcher.group("INPUT") != null) {
                    Token token = new Token(TokenType.INPUT, matcher.group("INPUT"));
                    tokens.add(token);
                    System.out.println("INPUT: " + token.value);
                } else if (matcher.group("OUTPUT") != null) {
                    Token token = new Token(TokenType.OUTPUT, matcher.group("OUTPUT"));
                    tokens.add(token);
                    System.out.println("OUTPUT: " + token.value);
                } else if (matcher.group("COMMA") != null) {
                    Token token = new Token(TokenType.COMMA, matcher.group("COMMA"));
                    tokens.add(token);
                    System.out.println("COMMA: " + token.value);
                } else if (matcher.group("COLON") != null) {
                    Token token = new Token(TokenType.COLON, matcher.group("COLON"));
                    tokens.add(token);
                    System.out.println("COLON: " + token.value);
                } else if (matcher.group("ASSIGNMENT") != null) {
                    Token token = new Token(TokenType.ASSIGNMENT, matcher.group("ASSIGNMENT"));
                    tokens.add(token);
                    System.out.println("ASSIGNMENT: " + token.value);
                } else if (matcher.group("LET") != null) {
                    Token token = new Token(TokenType.LET, matcher.group("LET"));
                    tokens.add(token);
                    System.out.println("LET: " + token.value);
                } else if (matcher.group("IF") != null) {
                    Token token = new Token(TokenType.IF, matcher.group("IF"));
                    tokens.add(token);
                    System.out.println("IF: " + token.value);
                } else if (matcher.group("ELSEIF") != null) {
                    Token token = new Token(TokenType.ELSEIF, matcher.group("ELSEIF"));
                    tokens.add(token);
                    System.out.println("ELSEIF: " + token.value);
                } else if (matcher.group("ELSE") != null) {
                    Token token = new Token(TokenType.LET, matcher.group("ELSE"));
                    tokens.add(token);
                    System.out.println("ELSE: " + token.value);
                } else if (matcher.group("FOR") != null) {
                    Token token = new Token(TokenType.FOR, matcher.group("FOR"));
                    tokens.add(token);
                    System.out.println("FOR: " + token.value);
                } else if (matcher.group("WORD") != null) {
                    Token token = new Token(TokenType.WORD, matcher.group("WORD"));
                    tokens.add(token);
                    System.out.println("WORD: " + token.value);
                } else if (matcher.group("ASTERIS") != null) {
                    Token token = new Token(TokenType.ASTERIS, matcher.group("ASTERIS"));
                    tokens.add(token);
                    System.out.println("WORD: " + token.value);
                } else if (matcher.group("OPENBRACKET") != null) {
                    Token token = new Token(TokenType.OPENBRACKET, matcher.group("OPENBRACKET"));
                    tokens.add(token);
                    System.out.println("WORD: " + token.value);
                } else if (matcher.group("CLOSEBRACKET") != null) {
                    Token token = new Token(TokenType.CLOSEBRACKET, matcher.group("CLOSEBRACKET"));
                    tokens.add(token);
                    System.out.println("WORD: " + token.value);
                } else if (matcher.group("EQ") != null) {
                    Token token = new Token(TokenType.EQ, matcher.group("EQ"));
                    tokens.add(token);
                    System.out.println("EQ: " + token.value);
                } else if (matcher.group("NE") != null) {
                    Token token = new Token(TokenType.NE, matcher.group("NE"));
                    tokens.add(token);
                    System.out.println("NE: " + token.value);
                } else if (matcher.group("GE") != null) {
                    Token token = new Token(TokenType.GE, matcher.group("GE"));
                    tokens.add(token);
                    System.out.println("GE: " + token.value);
                } else if (matcher.group("LE") != null) {
                    Token token = new Token(TokenType.LE, matcher.group("LE"));
                    tokens.add(token);
                    System.out.println("LE: " + token.value);
                } else if (matcher.group("GT") != null) {
                    Token token = new Token(TokenType.GT, matcher.group("GT"));
                    tokens.add(token);
                    System.out.println("GT: " + token.value);
                } else if (matcher.group("LT") != null) {
                    Token token = new Token(TokenType.LT, matcher.group("LT"));
                    tokens.add(token);
                    System.out.println("LT: " + token.value);
                } else if (matcher.group("CURLL") != null) {
                    Token token = new Token(TokenType.CURLL, matcher.group("CURLL"));
                    tokens.add(token);
                    System.out.println("CURLL: " + token.value);
                } else if (matcher.group("CURLR") != null) {
                    Token token = new Token(TokenType.CURLR, matcher.group("CURLR"));
                    tokens.add(token);
                    System.out.println("CURLR: " + token.value);
                } else {
                    throw new RuntimeException("Unexpected token: " + matcher.group());
                }
            }
            tokens.add(new Token(TokenType.EOF, "")); // End of input
            return tokens;
        }
     }
    
    public static class RegisterManager {

    // Counter for the temporary registers (for $t0, $t1, $t2)
    private int tempRegCounter;

    // Set to track used dynamic registers ($t3-$t9)
    private Set<String> usedDynamicRegs;

    // Constructor to initialize the structures
    public RegisterManager() {
        tempRegCounter = 0;  // Start at $t0
        usedDynamicRegs = new HashSet<>();
    }

    // Method to get a temporary register ($t0-$t2)
    public String getTempReg() {
        // Allocate $t0, $t1, $t2 in a round-robin fashion
        String reg = "$t" + tempRegCounter;
        tempRegCounter = (tempRegCounter + 1) % 3; // Cycle back to $t0 after $t2
        return reg;
    }

    // Method to get a dynamic register ($t3-$t9)
    public String getDynamicReg() {
        for (int i = 3; i <= 9; i++) {
            String reg = "$t" + i;
            if (!usedDynamicRegs.contains(reg)) {
                // Mark the register as used
                usedDynamicRegs.add(reg);
                return reg;
            }
        }
        // If no registers are available, return an error
        throw new RuntimeException("You've declared too much variables! Not enough registers");
    }

    // Method to free (release) a dynamic register
    public void freeDynamicReg(String reg) {
        if (reg.startsWith("$t") && Integer.parseInt(reg.substring(2)) >= 3 && Integer.parseInt(reg.substring(2)) <= 9) {
            // Remove from the used dynamic registers set
            usedDynamicRegs.remove(reg);
        }
    }

    // Method to print the status of registers (for debugging)
    public void printStatus() {
        System.out.println("Used dynamic registers: " + usedDynamicRegs);
    }

    // Example usage
    public static void main(String[] args) {
    }
}
    
    public static class Symbol {
    String name;
    String type; // Can be null initially
    String register; // Can be null initially
    String value;

    public Symbol(String name) {
        this.name = name;
        this.type = null; // Unknown initially
        this.register = null; // Unknown initially
    }

    public void setType(String type) {
        this.type = type;
        System.out.println("TYPE CHANGED!!!!!" + this.type);
    }

    public void setRegister(String reg) {
        this.register = reg;
    }

    public void setValue(String val){
        this.value = value;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Type: " + (type != null ? type : "Unknown") +
               ", Register: " + (register != null ? register : "None");
    }
    }

    public static class SymbolTable {
        //String is the name of the variable (unique), and the symbol is the object symbol
        private HashMap<String, Symbol> table;

        public SymbolTable() {
            table = new HashMap<>();
        }
        
        public boolean has(String name){
            if(table.containsKey(name)) {
                return true;
            }
            else
                return false;
        }

        // Add a new symbol (name only)
        public void addSymbol(String name) {
            if (!table.containsKey(name)) {
                table.put(name, new Symbol(name));
            }
        }

        public void removeSymbol(String name) {
            if (table.containsKey(name)) {
                table.remove(name);
                System.out.println("Symbol " + name + " removed.");
            } else {
                System.out.println("Symbol " + name + " not found.");
            }
        }

        // Update type
        public void setType(String name, String type) {
            if (table.containsKey(name)) {
                table.get(name).setType(type);
            }
        }
        
        public String getType(String name) {
            System.out.println("here type" + name);
            if (table.containsKey(name)) {
                return table.get(name).type;
            }
       
            return table.get(name).type;
        }
        
        // Update value 
        public void setValue(String name, String value) {
            if (table.containsKey(name)) {
                table.get(name).setValue(value);
            }
        }

        // Update register
        public void setRegister(String name, String register) {
            if (table.containsKey(name)) {
                table.get(name).setRegister(register);
            }
        }
        
        public String getRegister(String name) {
            if (table.containsKey(name)) {
                return table.get(name).register;
            }
            return table.get(name).register;
        }


        // Retrieve a symbol
        public Symbol getSymbol(String name) {
            return table.get(name);
        }
        
        public void printTable() {
            if (table.isEmpty()) {
                System.out.println("Symbol Table is empty.");
                return;
            }

            System.out.println("Symbol Table:");
            for (Map.Entry<String, Symbol> entry : table.entrySet()) {
                System.out.println(entry.getValue().toString());
            }
        }
    }
    
    private static class Parser {
    private final List<Token> tokens;
    private int pos = 0;
    //private int tempCounter = 0; // For generating MIPS temp variables
    private boolean done_data_print = false;
    private boolean print_text_main = false;
    //private boolean declared = false;
    private int counter = 0;
    RegisterManager regManager = new RegisterManager();
    SymbolTable symbolTable = new SymbolTable();

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }
    
    public boolean isAllNumbers(String str) {
        return str != null && str.matches("\\d+");
    }

    public String parse() {
        StringBuilder mipsCodeMain = new StringBuilder();
        StringBuilder mipsCodeSection = new StringBuilder();
        StringBuilder mipsCodeTextMain = new StringBuilder();
        appendDataSectionSetup(mipsCodeSection);
        appendMainTextSetup(mipsCodeTextMain);

        // Parse the input expression and generate MIPS code
        while (currentToken().type != TokenType.EOF) {
            parseStatement(mipsCodeMain, mipsCodeSection);
        }
        
        mipsCodeMain.append("li $v0, 10\n"); // syscall for exit
        mipsCodeMain.append("syscall\n");
        
        //added kale 
        mipsCodeSection = removeDuplicateDeclarations(mipsCodeSection);
        mipsCodeSection.append(mipsCodeTextMain);
        mipsCodeSection.append(mipsCodeMain);
        
        return mipsCodeSection.toString();
    }
    
    //added kale
    private StringBuilder removeDuplicateDeclarations(StringBuilder mipsCodeSection) {
        // Use a LinkedHashMap to preserve insertion order and handle duplicates efficiently
        Map<String, String> declarationMap = new LinkedHashMap<>();

        // Split the content by lines
        String[] lines = mipsCodeSection.toString().split("\n");

        boolean dataSectionAdded = false;  // Flag to track if ".data" is added

        // Process each line
        for (String line : lines) {
            String trimmedLine = line.trim();
            if (!trimmedLine.isEmpty()) {
                // Ensure ".data" is at the top
                if (trimmedLine.equals(".data")) {
                    if (!dataSectionAdded) {
                        declarationMap.put(".data", ".data");  // Only add it once
                        dataSectionAdded = true;
                    }
                } else {
                    // Extract the label (everything before the colon ':')
                    int colonIndex = trimmedLine.indexOf(':');
                    if (colonIndex != -1) {
                        String label = trimmedLine.substring(0, colonIndex).trim();
                        declarationMap.put(label, line);  // Overwrite if label already exists
                    }
                }
            }
        }
    //added kale
    // Reconstruct the StringBuilder with unique declarations
    StringBuilder cleanedMipsCodeSection = new StringBuilder();
        for (String declaration : declarationMap.values()) {
            cleanedMipsCodeSection.append(declaration).append("\n");
        }

        return cleanedMipsCodeSection;
    }

    private void appendMainTextSetup(StringBuilder mipsCodeMainText) {
        if (!print_text_main) {
            mipsCodeMainText.append("newline: .asciiz \"\\n\"\n");
            mipsCodeMainText.append("space: .asciiz \" \"\n");
            mipsCodeMainText.append(".text\n");
            mipsCodeMainText.append("main:\n");
            print_text_main = true;  // Ensure this section is added only once
        }
    }
    
    private void appendDataSectionSetup(StringBuilder mipsCodeSection) {
        if (!done_data_print) {
            mipsCodeSection.append(".data\n");
            done_data_print = true;  // Ensure the .data section is added only once
        }
    }
    
    private Token currentToken() {
        return tokens.get(pos);
    }

    private Token consume(TokenType expected) {
        //this condition doesnt really do anything because of condition in caller (parseOperator)
        if (currentToken().type == expected) {
            return tokens.get(pos++);
        } else {
            throw new RuntimeException("Expected " + expected + " but got " + currentToken().type);
        }
    }
    
    private boolean isOperationToken(TokenType type) {
            return type == TokenType.EQ || type == TokenType.NE || type == TokenType.GE ||
                   type == TokenType.LE || type == TokenType.GT || type == TokenType.LT;
    }
    
    private void generateIfConditionMipsCode(StringBuilder mipsCodeMain, StringBuilder mipsCodeSection, Token identifier1, Token operation, Token identifier2, boolean hasFalse) {
            String reg1;
            if (identifier1.type == TokenType.NUMBER) {
                reg1 = regManager.getTempReg();
                mipsCodeMain.append("li ").append(reg1).append(", ").append(Integer.parseInt(identifier1.value)).append("\n");
            } else {
                if (symbolTable.has(identifier1.value)) {
                    Symbol symbol = symbolTable.getSymbol(identifier1.value);
                    if (symbol.value != null) {
                        reg1 = regManager.getTempReg(); // Assign a temporary register
                        try {
                            int intValue = Integer.parseInt(symbol.value); // Convert the value to integer
                            mipsCodeMain.append("li ").append(reg1).append(", ").append(intValue).append("\n");
                        } catch (NumberFormatException e) {
                            throw new RuntimeException("Symbol " + identifier1.value + " does not have a valid integer value.");
                        }
                    } else {
                        throw new RuntimeException("Symbol " + identifier1.value + " has no assigned value.");
                    }
                } else {
                    throw new RuntimeException(identifier1.value + " is not declared.");
                }
            }

            String reg2;
            if (identifier2.type == TokenType.NUMBER) {
                reg2 = regManager.getTempReg();
                mipsCodeMain.append("li ").append(reg2).append(", ").append(Integer.parseInt(identifier2.value)).append("\n");
            } else {
                if (symbolTable.has(identifier2.value)) {
                    Symbol symbol = symbolTable.getSymbol(identifier2.value);
                    if (symbol.value != null) {
                        reg2 = regManager.getTempReg(); // Assign a temporary register
                        try {
                            int intValue = Integer.parseInt(symbol.value); // Convert the value to integer
                            mipsCodeMain.append("li ").append(reg2).append(", ").append(intValue).append("\n");
                        } catch (NumberFormatException e) {
                            throw new RuntimeException("Symbol " + identifier2.value + " does not have a valid integer value.");
                        }
                    } else {
                        throw new RuntimeException("Symbol " + identifier2.value + " has no assigned value.");
                    }
                } else {
                    throw new RuntimeException(identifier2.value + " is not declared.");
                }
            }

            // Generate the conditional branch
            String labelTrue = "LABEL_TRUE";
            String labelUntrue;
            
            if(hasFalse){
                labelUntrue = "LABEL_FALSE";
            } else{
                labelUntrue = "LABEL_END";
            }
               
            switch (operation.type) {
                case EQ:
                    mipsCodeMain.append("beq ").append(reg1).append(", ").append(reg2).append(", ").append(labelTrue).append("\n");
                    break;
                case NE:
                    mipsCodeMain.append("bne ").append(reg1).append(", ").append(reg2).append(", ").append(labelTrue).append("\n");
                    break;
                case GE:
                    mipsCodeMain.append("bge ").append(reg1).append(", ").append(reg2).append(", ").append(labelTrue).append("\n");
                    break;
                case LE:
                    mipsCodeMain.append("ble ").append(reg1).append(", ").append(reg2).append(", ").append(labelTrue).append("\n");
                    break;
                case GT:
                    mipsCodeMain.append("bgt ").append(reg1).append(", ").append(reg2).append(", ").append(labelTrue).append("\n");
                    break;
                case LT:
                    mipsCodeMain.append("blt ").append(reg1).append(", ").append(reg2).append(", ").append(labelTrue).append("\n");
                    break;
                default:
                    throw new RuntimeException("Unsupported operation: " + operation.type);
            }

            // Skip the true block if the condition is false
            mipsCodeMain.append("j ").append(labelUntrue).append("\n");
            // True block
            mipsCodeMain.append(labelTrue).append(":\n");
        }      
        
        private boolean checkForElse(){
            int pos = 0;
            System.out.println("-----------------CHECKING FOR ELSE");

            while (pos < tokens.size()) {
                Token token = tokens.get(pos);
                System.out.println("Token Type: " + token.type + ", Value: " + token.value);

                // Example: Check for a specific token type
                if (token.type == TokenType.ELSE) {
                    System.out.println("Found an 'ELSE' statement!");
                    return true;
                }

                pos++;
            }
            return false;
        }
        
         private void parseIfStatement(StringBuilder mipsCodeMain, StringBuilder mipsCodeData) {
            // Consume the IF token
            consume(TokenType.IF);
            boolean hasFalse = checkForElse(); 
            // Parse the first identifier (WORD or NUMBER)
            Token identifier1 = currentToken();
            if (identifier1.type != TokenType.WORD && identifier1.type != TokenType.NUMBER) {
                throw new RuntimeException("Undeclared identifier");
            }
            consume(identifier1.type);

            // Parse the operation
            Token operation = currentToken();
            if (!isOperationToken(operation.type)) {
                throw new RuntimeException("Expected a comparison operator, but found: " + operation.type);
            }
            consume(operation.type);

            // Parse the second identifier (WORD or NUMBER)
            Token identifier2 = currentToken();
            if (identifier2.type != TokenType.WORD && identifier2.type != TokenType.NUMBER) {
                throw new RuntimeException("Undeclared identifier");
            }
            consume(identifier2.type);

            consume(TokenType.COLON);
            consume(TokenType.CURLL);
            
            
            // Generate MIPS code for the if-statement
            generateIfConditionMipsCode(mipsCodeMain, mipsCodeData, identifier1, operation, identifier2, hasFalse);
            
            parseCodeBlock(mipsCodeMain,mipsCodeData);
            
            
            if(hasFalse){
                mipsCodeMain.append("j ").append("LABEL_END").append("\n");
                parseElseStatement(mipsCodeMain, mipsCodeData);
            }
            
            // End label
            mipsCodeMain.append("LABEL_END").append(":\n");    
        }
        
        private void parseElseStatement(StringBuilder mipsCodeMain, StringBuilder mipsCodeData) {
            consume(TokenType.ELSE);
            consume(TokenType.COLON);
            consume(TokenType.CURLL);
            
            mipsCodeMain.append("LABEL_FALSE").append(":\n");
            parseCodeBlock(mipsCodeMain,mipsCodeData);
        }
        
        private void parseCodeBlock(StringBuilder mipsCodeMain, StringBuilder mipsCodeData){
            while(currentToken().type != TokenType.CURLR){
                parseStatement(mipsCodeMain,mipsCodeData);   
            }
            mipsCodeMain.append("syscall             # Perform the syscall\n");
            consume(TokenType.CURLR);
        }
        
    private void parseStatement(StringBuilder mipsCodeMain, StringBuilder mipsCodeSection) {
        switch (currentToken().type) {
            case LET:
                parseDeclaration(mipsCodeMain, mipsCodeSection);
                break;
            case ADD:
            case SUBTRACT:
            case MULTIPLY:
            case DIVIDE:
                parseArithmeticStatement(mipsCodeMain, mipsCodeSection);
                break;
            case INPUT:
                parseInput(mipsCodeMain, mipsCodeSection);
                break;
            case OUTPUT:
                parseOutput(mipsCodeMain, mipsCodeSection);
                break;
            case IF:
                parseIfStatement(mipsCodeMain, mipsCodeSection);
                break;
            case ELSE:
                parseElseStatement(mipsCodeMain, mipsCodeSection);
                break;
            case EOF:
                break;
            default:
                throw new RuntimeException("Unexpected: " + currentToken());
        }
    }
    
    private void parseOutput(StringBuilder mipsCodeMain, StringBuilder mipsCodeSection ) {
        consume(TokenType.OUTPUT); // Consume OUTPUT
        consume(TokenType.COLON);  // Consume COLON
        
        System.out.println("inside output");
        if (currentToken().type == TokenType.WORD || currentToken().type == TokenType.ASTERIS) { //Handle output for variables/literal strings
            outputString(mipsCodeMain,mipsCodeSection);  
        } else if (currentToken().type == TokenType.NUMBER) {
            outputInt(mipsCodeMain, mipsCodeSection);     // Handle output for literal integers
        } else {
            throw new IllegalArgumentException("Unsupported output type");
        }
    }   
    
    private void outputString(StringBuilder mipsCodeMain, StringBuilder mipsCodeSection ) {
        if(currentToken().type == TokenType.ASTERIS){ //Handle printing value of a variable
            consume(TokenType.ASTERIS);
            if(symbolTable.has(currentToken().value)){ //to check if variable has been declared
                if(symbolTable.getType(currentToken().value).equals("number")){ //if variable is number type
                    String reg = symbolTable.getRegister(currentToken().value);
                    mipsCodeMain.append("# Output integer\n");
                    mipsCodeMain.append("li $v0, 1           # syscall CODE for printing integer\n");
                    mipsCodeMain.append("move $a0, ").append(reg).append("  # Load the variable's value into $a0\n");
                    mipsCodeMain.append("syscall            # Perform the integer output syscall\n");

//                    // Optionally print a newline for formatting
//                    mipsCodeMain.append("# Print newline\n");
//                    mipsCodeMain.append("li $v0, 4          # syscall code for printing string\n");
//                    mipsCodeMain.append("la $a0, newline    # Load newline address\n");
//                    mipsCodeMain.append("syscall            # Perform syscall to print newline\n");
                }
                else{ //if variable is string type
                    mipsCodeMain.append("li $v0, 4          # syscall code for printing string\n");
                    mipsCodeMain.append("la $a0, ").append(currentToken().value).append("  # Load address of the string\n");
                    mipsCodeMain.append("syscall            # Perform syscall to print the string\n");

                    mipsCodeMain.append("# Output space\n");
                    mipsCodeMain.append("li $v0, 4          # syscall code for printing space\n");
                    mipsCodeMain.append("la $a0, space      # Load address of the space\n");
                    mipsCodeMain.append("syscall            # Perform syscall to print the space\n");
                }
                consume(TokenType.WORD);
            }
            else{ //variable was not declared
                throw new RuntimeException(currentToken().value + " isn't declared yet!");
            }
        }
        else { //Handle printing of literal string
            
            // Loop to collect all words
            StringBuilder output = new StringBuilder();
            while (currentToken().type == TokenType.WORD) {
                String word = consume(TokenType.WORD).value;
                output.append(word).append(" "); // Append word with space
            }

            // Trim the concatenated output to remove extra spaces
            String finalOutput = output.toString().trim();
            String[] words = finalOutput.split(" ");  // Split into individual words


            for (String word : words) {
                // Define a label for each word in the .data section
                String label = "inline_string_" + counter;
                mipsCodeSection.append(label).append(": .asciiz \"").append(word).append("\"\n");

                // Generate MIPS code to print the string in the .text section
                mipsCodeMain.append("# Output string: ").append(word).append("\n");
                mipsCodeMain.append("li $v0, 4          # syscall code for printing string\n");
                mipsCodeMain.append("la $a0, ").append(label).append("  # Load address of the string\n");
                mipsCodeMain.append("syscall            # Perform syscall to print the string\n");

                mipsCodeMain.append("# Output space\n");
                mipsCodeMain.append("li $v0, 4          # syscall code for printing space\n");
                mipsCodeMain.append("la $a0, space      # Load address of the space\n");
                mipsCodeMain.append("syscall            # Perform syscall to print the space\n");


                // Increment the counter for the next label
                counter++;
            }   
        }

        // Optionally print a newline for formatting
        mipsCodeMain.append("# Print newline\n");
        mipsCodeMain.append("li $v0, 4          # syscall code for printing string\n");
        mipsCodeMain.append("la $a0, newline    # Load newline address\n");
        mipsCodeMain.append("syscall            # Perform syscall to print newline\n");
    }

    private void outputInt(StringBuilder mipsCodeMain, StringBuilder mipsCodeSection ) {
        
        // Consume the number from the input (assuming TokenType.NUMBER exists)
        String number = consume(TokenType.NUMBER).value;

        // Generate MIPS code to output the integer directly
        mipsCodeMain.append("# Output integer\n");
        mipsCodeMain.append("li $v0, 1          # syscall CODE for printing integer\n");
        mipsCodeMain.append("li $a0, ").append(number).append("  # Load the immediate value into $a0\n");
        mipsCodeMain.append("syscall            # Perform the integer output syscall\n");

        // Optionally print a newline for formatting
        mipsCodeMain.append("# Print newline\n");
        mipsCodeMain.append("li $v0, 4          # syscall code for printing string\n");
        mipsCodeMain.append("la $a0, newline    # Load newline address\n");
        mipsCodeMain.append("syscall            # Perform syscall to print newline\n");
    }

    //changed kale
    private void parseArithmeticStatement(StringBuilder mipsCodeMain, StringBuilder mipsCodeSection) {
        // Parse the operator (ADD, SUBTRACT, MULTIPLY, DIVIDE)
        System.out.println("in parse arithmetic");
        String operator = parseOperator();
        String result = "";
        String next = "";
        
        symbolTable.printTable();
        
        if(currentToken().type == TokenType.NUMBER)
            result = parseNum(mipsCodeMain);
        else
            result = parseWord(mipsCodeMain);
        
      
        // Process additional arguments and apply the operator to them
        while (currentToken().type == TokenType.COMMA) {
            consume(TokenType.COMMA); // consume comma

            if(currentToken().type == TokenType.NUMBER)
                next = parseNum(mipsCodeMain);
            else
                next = parseWord(mipsCodeMain);

            // Apply the operator to the operands (number-type only)
            String temp = regManager.getTempReg();
            mipsCodeMain.append(operatorToMips(operator, result, next, temp));
            result = temp;
            System.out.println("in number parsing");
        }

        // Consume the COLON and ASSIGNMENT 
        consume(TokenType.COLON);
        consume(TokenType.ASSIGNMENT);  // Assume that ASSIGNMENT is a keyword like "igbutang ha"

        // Parse the variable (word) where the result will be stored
        String variableName = currentToken().value;

        // Check if the variable has been declared
        if (symbolTable.has(variableName)){
            // Check if the variable type is a string (we cannot assign numbers to strings)
            System.out.println(variableName + "'s type: "+ symbolTable.getType(variableName));
            if(symbolTable.getType(variableName).equals("string")) {
                throw new RuntimeException("Can't assign number to string!");
            }
            else {
                String varReg = symbolTable.getRegister(variableName); // Get register from symbol table
                mipsCodeMain.append("move ").append(varReg).append(", ").append(result);
                mipsCodeMain.append("\n"); // Add a newline after each expression
                // Update the symbol table (the value of the variable)
                symbolTable.setValue(variableName, result);
                consume(TokenType.WORD);  // Consume the variable name token
            }
        }
        else {
            throw new RuntimeException("Variable " + variableName + " is not yet declared!");
        }
        
    }

    //changed kale
    private String parseWord(StringBuilder mipsCodeMain) {
        if (currentToken().type == TokenType.WORD) {
            System.out.println("in parse word");
            
            //!!check if it is in symbol table
            if(symbolTable.has(currentToken().value)){
                if (symbolTable.getType(currentToken().value).equals("string")) {
                    throw new RuntimeException("Cannot perform arithmetic operations on strings!");
                }
                String temp = symbolTable.getRegister(currentToken().value);
                consume(TokenType.WORD);
                return temp;
            }
            else{
                throw new RuntimeException("Variable " + currentToken().value + " is not declared yet");
            }
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken().type);
        }
    }

    private String parseOperator() {
        Token operatorToken = currentToken();
        if (operatorToken.type == TokenType.ADD) {
            System.out.println("in parse operator");
            consume(TokenType.ADD);
            return "dugangi it";
        } else if (operatorToken.type == TokenType.SUBTRACT) {
            consume(TokenType.SUBTRACT);
            return "ibani it";
        } else if (operatorToken.type == TokenType.MULTIPLY) {
            consume(TokenType.MULTIPLY);
            return "igpilopilo it";
        } else if (operatorToken.type == TokenType.DIVIDE) {
            consume(TokenType.DIVIDE);
            return "bahini it";
        } else {
            throw new RuntimeException("Unexpected operator: " + operatorToken.type);
        }
    }

    private String operatorToMips(String operator, String left, String right, String temp) {
        switch (operator) {
            case "dugangi it":  // Assume it means addition
                return "add " + temp + ", " + left + ", " + right + "\n";
            case "ibani it":  // Assume it means subtraction
                return "sub " + temp + ", " + left + ", " + right + "\n";
            case "igpilopilo it":  // Assume it means multiplication
                return "mul " + temp + ", " + left + ", " + right + "\n";
            case "bahini it":  // Assume it means division
                return "div " + temp + ", " + left + ", " + right + "\n";
            default:
                throw new RuntimeException("Unknown operator: " + operator);
        }
    }
    
    //changed kale added check if int = int in input 
    private void parseInput(StringBuilder mipsCodeMain, StringBuilder mipsCodeSection) {
       //sample: pangaro hin: x
        // Consume the INPUT token
        consume(TokenType.INPUT);  // Consume the INPUT token

        // Consume the COLON token
        consume(TokenType.COLON);   // Consume the COLON token

        // Get the variable name to store the input value
        String variableName = currentToken().value;  // e.g., "x"
        consume(TokenType.WORD);  // Consume the variable name token

        // Construct the prompt message (optional, for context in the UI)

        // Display input dialog to get the user input
        System.out.println("BEFORE JOB");

        String userInputStr = JOptionPane.showInputDialog(null, "Input:", "Input Required", JOptionPane.QUESTION_MESSAGE);
        
        System.out.println("AFTER JOB");
        // Validate the user input
        if (userInputStr == null || userInputStr.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty for variable: " + variableName);
        }

        // Convert the input to an integer
        //int userInput = Integer.parseInt(userInputStr);
        
        String declaredType = symbolTable.getType(variableName);

        // Check if the input type matches the declared type
        if (declaredType.equals("number")) {
            if (!isAllNumbers(userInputStr)) {
                throw new RuntimeException("Input type does not match declared type for variable: " + variableName);
            }

            // Input is a number, store it in the symbol table and generate MIPS code
            String reg = regManager.getDynamicReg();
            mipsCodeMain.append("li ").append(reg).append(", ").append(userInputStr).append("\n");
            symbolTable.setRegister(variableName, reg);
            symbolTable.setType(variableName, "number");

        }
        else {  // Input is a string
            // Store the string in the data section using .asciiz
            symbolTable.addSymbol(variableName);
            symbolTable.setType(variableName, "string");
            mipsCodeSection.append(variableName)
                .append(": \t")  // Align the label
                .append(".asciiz \"").append(userInputStr).append("\"\n");
        }
    }

    //changed kale
    private void parseDeclaration(StringBuilder mipsCodeMain, StringBuilder mipsCodeSection) {
        // Ensure data section is printed only once
        //declared = true;
        
        consume(TokenType.LET); // Consume the LET token (in your case "it")

        String variableName = currentToken().value;
        if(!symbolTable.has(variableName)){
            symbolTable.addSymbol(variableName); //store symbol in symboltable
        }
        else{
            throw new RuntimeException("Variable " + currentToken().value + " has already been declared!");
        }
        consume(TokenType.WORD); // Consume variable name token
            

        consume(TokenType.COMMA); // Consume comma (in your case ",")

        consume(TokenType.DATATYPE); // Consume datatype token (in your case "himua nga")
        
        // Parse the value (can be number or string)
        //store it in a register, available to u: variableName and value
        String value = currentToken().value;
        String reg = regManager.getDynamicReg();
        System.out.println(value);
        
        if(currentToken().type == TokenType.NUMBER){
            System.out.println("inside number type for dest reg");
            consume(TokenType.NUMBER); // Consume the number input value 
            mipsCodeMain.append("li ").append(reg).append(", ").append(value).append("\n");
            String numtype = "number";
            symbolTable.setType(variableName, numtype);
            System.out.println(variableName + " " + symbolTable.getType(variableName)+"\n");
            symbolTable.setValue(variableName,value);
            symbolTable.setRegister(variableName,reg);
        }
        else{ //declare the string in the data section
            consume(TokenType.OPENBRACKET);
            // Loop to collect all words inside the bracket
            StringBuilder strInput = new StringBuilder();
            while (currentToken().type != TokenType.CLOSEBRACKET) {
                String stringBit = "";
                if(currentToken().type == TokenType.WORD){
                    stringBit = consume(TokenType.WORD).value;
                }
                else if(currentToken().type == TokenType.NUMBER){
                    stringBit = consume(TokenType.NUMBER).value;
                }
                strInput.append(stringBit).append(" "); // Append word with space
            }
            consume(TokenType.CLOSEBRACKET);

            // Trim the concatenated output to remove extra spaces
            String finalInputStr = strInput.toString().trim();
            //String[] words = finalOutput.split(" ");  // Split into individual words

            //consume(TokenType.WORD); // Consume the string input value 
            symbolTable.setType(variableName,"string");
            mipsCodeSection.append(variableName)
            .append(": \t")  // Add spaces to align the variable labels
            .append(" .asciiz \"").append(finalInputStr).append("\"\n");
        }
    }

    private String parseNum(StringBuilder mipsCodeMain) {
        if (currentToken().type == TokenType.NUMBER) {
            String value = consume(TokenType.NUMBER).value;
            String temp = regManager.getTempReg();
            mipsCodeMain.append("li ").append(temp).append(", ").append(value).append("\n");
            return temp;
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken().type);
        }
    }

    }
    
    public static String main(String[] args) {
    //public static void main(String input) {
        String input = args[0];
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        Parser parser = new Parser(tokens);
        String mipsCode = parser.parse();
        System.out.println(mipsCode);
        //String mipsCode = "check it girl";
        return mipsCode;
    }
}