package FileManager;
/**
 * Name: Ritesh Sunil Chavan
 */
import java.util.Scanner;

public class BashTerminal {

    public static void main(String[] args) {
        DirectoryTree bashShell = new DirectoryTree();
        terminal(bashShell);
    }

    /**
     * Terminal of the file system
     * @param bashShell Tree root
     */
    public static void terminal(DirectoryTree bashShell){
        boolean condition = true;
        Scanner sc = new Scanner(System.in);
        System.out.println("Starting bash terminal.");
        String command;
        String[] commandArr;
        while(condition){
            System.out.print("[rchavan@Kali]: $ ");
            command = sc.nextLine();
            commandArr = command.split(" ");
            try{
                condition = checkSwitch(commandArr, bashShell);
            }
            catch (FullDirectoryException e){
                System.out.println("ERROR: Present directory is full.");
            }
            catch (NotADirectoryException e){
                System.out.println("ERROR: Cannot change directory into a file.");
            }
            catch (DirectoryNotFound e){
                System.out.println("ERROR: No such directory named '"+ commandArr[1] + "'.");
            }
        }
    }

    /**
     * Condition check
     * @param commandArr command
     * @param bashShell Tree root
     * @return boolean value to terminate program
     * @throws FullDirectoryException if the directory is full
     * @throws DirectoryNotFound if the directory is not found
     * @throws NotADirectoryException if some node is file and not directory
     */
    public static boolean checkSwitch(String[] commandArr, DirectoryTree bashShell) throws FullDirectoryException, DirectoryNotFound, NotADirectoryException {
        switch (commandArr[0]) {
            case "pwd":
                System.out.println(bashShell.presentWorkingDirectory());
                break;
            case "ls":
                if (commandArr.length == 2) {
                    if (commandArr[1].equals("-R"))
                        bashShell.printDirectoryTree();
                    break;
                } else {
                    System.out.println(bashShell.listDirectory());
                    break;
                }
            case "cd":
                if (commandArr[1].equals("/")) {
                    bashShell.resetCursor();
                    break;
                } else if (commandArr[1].equals("..")) {
                    bashShell.changeParent();
                    break;
                } else {
                    String[] commandArrArr = commandArr[1].split("/");
                    if (commandArrArr.length > 1)
                        bashShell.changePath(commandArr[1]);
                    else
                        bashShell.changeDirectory(commandArr[1]);
                    break;
                }
            case "mkdir":
                bashShell.makeDirectory(commandArr[1]);
                break;
            case "touch":
                bashShell.makeFile(commandArr[1]);
                break;
            case "find":
                if (commandArr.length == 2)
                    bashShell.findDirectory(commandArr[1]);
                else
                    System.out.println("ERROR: Invalid Command.");
                break;
            case "mv":
                if (commandArr.length == 3) {
                    if (commandArr[1].equals("root"))
                        System.out.println("ERROR: Cannot move root");
                    else
                        bashShell.moveFiles(commandArr[1], commandArr[2]);
                } else
                    System.out.println("ERROR: Invalid Command.");
                break;
            case "exit":
                System.out.println("Program terminating normally");
                return false;

        }
        return true;
    }
}
