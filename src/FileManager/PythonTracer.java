package OrderOfComplexityCalculator;
/**
 * Ritesh Sunil Chavan
 */

import java.io.File;
import java.util.Scanner;

public class PythonTracer {
    /**
     * Final value of number of spaces in a indent
     */
    public static final int SPACE_COUNT=4;

    public static void main(String[] args) {
        boolean condition = true;
        while(condition) {
            condition = takeInput();
        }
    }

    /**
     * This methods traces the python file and returns its order of complexity
     * @param file path of the file
     * @return total order of complexity of the python file
     */
        public static Complexity traceFile(String file)
        {
            BlockStack stack = new BlockStack();
            try
            {
                File readFile=new File(file);
                Scanner sc = new Scanner(readFile);
                String lastName = "";
                while (sc.hasNextLine())
                {
                    String line=sc.nextLine();

                    if (!line.isEmpty()&&!line.startsWith("#"))
                    {
                        int noOfSpace=line.indexOf(line.trim());
                        int indents=noOfSpace/SPACE_COUNT;
                        while (indents<stack.size())
                        {
                            if (indents==0)
                            {
                                sc.close();

                                return stack.peek().getHighestSubComplexity();
                            }
                            else
                            {
                                CodeBlock oldTop = stack.pop();
                                lastName=oldTop.getName();
                                Complexity oldTopComplexity = addComplexity(oldTop.getBlockComplexity(),oldTop.getHighestSubComplexity());
                                if (compareComplexity(oldTopComplexity,stack.peek().getHighestSubComplexity())>0) {
                                    stack.peek().setHighestSubComplexity(oldTopComplexity);
                                    System.out.println("\nLeaving block " +oldTop.getName()+ ", updating block "+stack.peek().getName()+":");
                                }
                                else
                                    System.out.println("\nLeaving block " +oldTop.getName()+ ", nothing to update.");
                                System.out.println(stack.peek());
                            }
                        }

                        String keyword = findKeyword(line);
                        if (!keyword.isEmpty())
                        {
                            if (keyword.equalsIgnoreCase("for"))
                            {
                                Complexity complexity = forLoopComplexity(line);
                                CodeBlock previous = stack.peek();
                                String name = nameOfCodeBlock( lastName,indents);
                                Complexity highestSubComplexity=new Complexity();
                                CodeBlock codeBlock=new CodeBlock(name,complexity,highestSubComplexity,findForLoopVariable(line), "for");
                                System.out.println("\nEntering block "+name+" 'for':");
                                System.out.println(codeBlock);
                                lastName=name;
                                stack.push(codeBlock);
                            }
                            else if (keyword.equalsIgnoreCase("while"))
                            {

                                CodeBlock previous = stack.peek();
                                String name=nameOfCodeBlock(lastName,indents);
                                Complexity complexity=new Complexity();
                                Complexity highestSubComplexity=new Complexity();
                                CodeBlock codeBlock=new CodeBlock(name,complexity,highestSubComplexity,findWhileLoopVariable(line),"while");
                                System.out.println("\nEntering block "+name+" 'while':");
                                System.out.println(codeBlock);
                                lastName=name;
                                stack.push(codeBlock);
                            }
                            else
                            {
                                Complexity complexity=new Complexity();
                                CodeBlock codeBlock=new CodeBlock("1",complexity,complexity,"def");
                                System.out.println("\nEntering block 1 'def':");
                                System.out.println(codeBlock);
                                lastName="1";
                                stack.push(codeBlock);
                            }

                        }

                        else if (stack.peek()!=null&&stack.peek().getType().equalsIgnoreCase("while"))
                        {
                            CodeBlock block = stack.peek();
                            updateWhileComplexity(block,line,indents);
                        }
                    }
                }

                while (stack.size()>1)
                {
                    CodeBlock oldTop = stack.pop();
                    Complexity oldTopComplexity = addComplexity(oldTop.getBlockComplexity(),oldTop.getHighestSubComplexity());
                    if (compareComplexity(oldTopComplexity,stack.peek().getHighestSubComplexity())>0)
                        stack.peek().setHighestSubComplexity(oldTopComplexity);
                }
                System.out.println("\nLeaving block " + stack.peek().getName() + ".");
                return stack.pop().getHighestSubComplexity();
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            return null;
        }

    /**
     * Custom method to add the complexity
     * @param first takes the first complexity
     * @param second takes the second complexity
     * @return complexity
     */

        private static Complexity addComplexity(Complexity first,Complexity second) {
            Complexity complexity = new Complexity();
            complexity.setN_power(first.getN_power()+second.getN_power());
            complexity.setLog_power(first.getLog_power()+ second.getLog_power());
            return complexity;
        }

    /**
     * update the while complexity
     * @param block takes the block of the CodeBlock
     * @param currLine current string of the line
     * @param indents indents of the loop
     */
        private static void updateWhileComplexity(CodeBlock block, String currLine, int indents)
        {
            if ((indents+1)>block.getName().split("\\.").length)
            {
                if (currLine.trim().contains(block.getLoopVariable()) && (currLine.trim().contains("/=")||currLine.trim().contains("-=")))
                {
                    if (currLine.contains("/="))
                    {
                        Complexity complexity = new Complexity();
                        complexity.setLog_power(1);
                        block.setBlockComplexity(complexity);
                        System.out.println("\nFound Update statement, updating block " + block.getName() + ":\n" + block.toString());
                    }
                    else if(currLine.contains("-="))
                    {
                        Complexity complexity=new Complexity();
                        complexity.setN_power(1);
                        block.setBlockComplexity(complexity);
                        System.out.println("\nFound Update statement, updating block " + block.getName() + ":\n" + block.toString());
                    }
                }
            }
        }

    /**
     * Find the for loop variable in the line
     * @param line String which finds the word
     * @return the string
     */

    private static String findForLoopVariable(String line){
            String[] s = line.trim().split("in");
            String first=s[0].trim();
            String[] s1 = first.split(" ");
            return s1[1];
        }

    /**
     * Find the while loop variable in the line
     * @param line String which finds the word
     * @return the string
     */

    private static String findWhileLoopVariable(String line){
            String[] s = line.trim().split(">");
            String first=s[0].trim();
            String[] s1 = first.split(" ");
            return s1[1];
        }

    /**
     * finds the name of the codeBlock
     * @param lastname String
     * @param currentIndent int
     * @return last name
     */
        private static String nameOfCodeBlock(String lastname,int currentIndent){
            if (currentIndent==0)
                return "1";
            String[] split = lastname.split("\\.");
            if (split.length==(currentIndent+1)){
                int last = Integer.parseInt(split[split.length - 1]);
                split[split.length-1]=Integer.toString(last+1);
                String finalResult="";
                for (int i = 0; i < split.length; i++) {
                    finalResult+=split[i];
                    if (i< split.length-1)
                        finalResult+=".";
                }
                return finalResult;
            } else {
                return lastname+".1";
            }
        }

    /**
     * finds the keyword of the line
     * @param line string
     * @return the string
     */

    private static String findKeyword(String line){
            for (String blockType : CodeBlock.BLOCK_TYPES) {

                if (line.contains(blockType+" "))
                    return blockType;
            }
            return "";
        }

    /**
     * find the for loop complexity
     * @param line String
     * @return the complexity
     */

    private static Complexity forLoopComplexity(String line){
            Complexity complexity=new Complexity();
            String[] s = line.split(" ");
            if (s[s.length-1].startsWith("N"))
            {
                complexity.setN_power(1);
                return complexity;
            }
            complexity.setLog_power(1);
            return complexity;
        }

    /**
     * compare the complexity of the first and second
     * @param first complexity
     * @param second complexity
     * @return the complexity
     */

    private static int compareComplexity(Complexity first, Complexity second){
//        System.out.println("First: "+first+" second: "+second);
            if (first.getN_power()==second.getN_power()&&first.getLog_power()==second.getLog_power())
                return 0;
            if (first.getN_power()>second.getN_power())
                return 1;
            else if (first.getN_power()<second.getN_power())
                return -1;
            else
            {
//            System.out.println("Testing");
                if (first.getLog_power()>second.getLog_power())
                    return 1;
                else
                    return -1;
            }
        }

    /**
     * checks to quit
     * @param input String
     * @return boolean variable
     */

    public static boolean checkQuit(String input) {
            if (input.toLowerCase().equals("quit")) {
                System.out.println("Program terminating successfully...");
                return false;
            }
            else
                return true;
        }

    /**
     * checks the validity while taking the input
     * @return boolean value
     */

    public static boolean takeInput(){
            Scanner sc = new Scanner(System.in);
            System.out.print("Please enter a file name (or 'quit' to quit): ");
            String path = sc.nextLine();
            if(!path.toLowerCase().equals("quit")) {
                Complexity complexity = traceFile(path);
                if(complexity!=null)
                    System.out.println("\nOverall complexity of " + path + ": " + complexity + "\n");
            }
            return checkQuit(path);
        }
    }
