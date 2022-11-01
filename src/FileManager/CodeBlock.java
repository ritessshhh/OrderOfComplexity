package OrderOfComplexityCalculator;
/**
 * Ritesh Sunil Chavan
 */

/**
 * This class stores the information of a certain block of code
 */
public class CodeBlock {

    /**
     * This is an array of block types, it contains the keywords for the name of the block
     */
    public static String[] BLOCK_TYPES={"def","for","while","if","else","elif"};

    /**
     * These variable store the index at which they are stored in the block type array
     */
    public static final int DEF=0;
    public static final int FOR=1;
    public static final int WHILE=2;
    public static final int IF=3;
    public static final int ELIF=4;
    public static final int ELSE=5;

    private String name;
    private Complexity blockComplexity;
    private Complexity highestSubComplexity;
    private String loopVariable;
    private String type;

    /**
     *
     * @param name name type of the code block
     * @param blockComplexity block complexity of the code block
     * @param highestSubComplexity nested block complexity of the code block
     * @param loopVariable variable name in while loop
     * @param type type of the code block
     */
    public CodeBlock(String name, Complexity blockComplexity, Complexity highestSubComplexity, String loopVariable, String type) {
        this.name = name;
        this.blockComplexity = blockComplexity;
        this.highestSubComplexity = highestSubComplexity;
        this.loopVariable = loopVariable;
        this.type = type;
    }

    /**
     *
     * @param name name type of the code block
     * @param blockComplexity block complexity of the code block
     * @param highestSubComplexity nested block complexity of the code block
     * @param type type of the code block
     */
    public CodeBlock(String name, Complexity blockComplexity, Complexity highestSubComplexity, String type) {
        this.name = name;
        this.blockComplexity = blockComplexity;
        this.highestSubComplexity = highestSubComplexity;
        this.type = type;
    }

    /**
     * Getter method for block name
     * @return name of block
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for name
     * @param name name of the block
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for block complexity
     * @return block complexity of the block
     */
    public Complexity getBlockComplexity() {
        return blockComplexity;
    }

    /**
     * Setter method for block complexity
     * @param blockComplexity block complexity of the block
     */
    public void setBlockComplexity(Complexity blockComplexity) {
        this.blockComplexity = blockComplexity;
    }

    /**
     * Getter method for the highest complexity of the block
     * @return highest complexity of the block
     */
    public Complexity getHighestSubComplexity() {
        return highestSubComplexity;
    }

    /**
     * Setter method for the highest complexity of the block
     * @param highestSubComplexity highest complexity of the block
     */
    public void setHighestSubComplexity(Complexity highestSubComplexity) {
        this.highestSubComplexity = highestSubComplexity;
    }

    /**
     * Getter method for the while loop variable
     * @return loop variable of the while loop
     */
    public String getLoopVariable() {
        return loopVariable;
    }

    /**
     * Setter method for the while loop variable
     * @param loopVariable loop variable of the while loop
     */
    public void setLoopVariable(String loopVariable) {
        this.loopVariable = loopVariable;
    }

    /**
     * Getter method for the type of the block
     * @return type of block
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for the type of the block
     * @param type type of block
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * toString method for the code block
     * @return Neatly formatted block and highest complexity
     */
    public String toString() {
        String a = "    BLOCK "+name+":";
        String b = "block complexity = "+blockComplexity.toString();
        String c = "highest sub-complexity = "+highestSubComplexity.toString();
        return String.format("%-19s%-34s%s",a,b,c);

    }
}
