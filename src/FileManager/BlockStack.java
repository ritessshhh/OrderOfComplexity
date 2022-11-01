package OrderOfComplexityCalculator;
/**
 * Ritesh Sunil Chavan
 */

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * This class stores the block of code in the stack
 */
public class BlockStack {
    private Stack<CodeBlock> stack =  new Stack<>();

    /**
     * This method pushes the block of code into the stack
     * @param block
     */
    public void push(CodeBlock block) {
        if(stack == null)
            throw new EmptyStackException();
        else
            stack.push(block);
    }

    /**
     * This method pops the block of code from the stack
     * @return
     */
    public CodeBlock pop() {
        if(isEmpty())
            throw new EmptyStackException();
        return stack.pop();
    }

    /**
     * This methods returns the topmost code block in the stack
     * @return topmost code block
     */
    public CodeBlock peek() {
        if(isEmpty())
            throw new EmptyStackException();
        return stack.peek();
    }

    /**
     * This method returns the size of the stack
     * @return size of the stack
     */
    public int size() {
        return stack.size();
    }

    /**
     * This method checks whether a stack is empty or not
     * @return boolean value of empty stackgg
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
