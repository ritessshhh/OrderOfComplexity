package FileManager;

/**
 * Name: Ritesh Sunil Chavan
 */
public class DirectoryTree {
    private DirectoryNode root;
    private DirectoryNode cursor;

    /**
     * Constructor for directory tree
     */
    public DirectoryTree(){
        root = new DirectoryNode("root");
        cursor = root;
    }

    /**
     * Resets the cursor to root
     */
    public void resetCursor(){
        cursor = root;
    }

    /**
     * Changes directory based on given name
     * @param name name of the directory
     * @throws NotADirectoryException if node is a file
     * @throws DirectoryNotFound if directory is not founf
     */
    public void changeDirectory(String name) throws NotADirectoryException, DirectoryNotFound {
        for(int i = 0; i < cursor.getCounter(); i++) {
            if (cursor.getReference(i) != null && cursor.getReference(i).getName().equals(name)) {
                if (!cursor.getReference(i).getIsFile()) {
                    cursor = cursor.getReference(i);
                    return;
                }
                else
                    throw new NotADirectoryException();
            }
        }
        throw new DirectoryNotFound();
    }

    /**
     * Changes directory based on given name
     * @param name name of the directory
     * @throws DirectoryNotFound if directory is not found
     */
    public void changeDirectory2(String name) throws DirectoryNotFound{
        for(int i = 0; i < cursor.getCounter(); i++) {
            if (cursor.getReference(i) != null && cursor.getReference(i).getName().equals(name)) {
                cursor = cursor.getReference(i);
                return;
            }
        }
        throw new DirectoryNotFound();
    }

    /**
     * Prints path to the current directory
     * @return
     */
    public String presentWorkingDirectory(){
        DirectoryNode temp = cursor;
        String path = cursor.goBack(cursor);
        cursor = temp;
        return path;
    }

    /**
     * Lists all the directories in the parent directory
     * @return all directories in the parent directory
     */
    public String listDirectory(){
        String directories = "";
        for(int i = 0; i < cursor.getCounter(); i++) {
            if (cursor.getReference(i) != null) {
                directories += cursor.getReference(i).getName() + " ";
            }
        }
        return directories;
    }

    /**
     * Prints the whole file system in a systematically formatted manner
     */
    public void printDirectoryTree(){
        cursor.printTree();
        cursor.setSpaces("");
    }

    /**
     * Makes directories with given name
     * @param name name of the directory
     * @throws IllegalArgumentException if the command is wrong
     * @throws FullDirectoryException if the parent directory is full
     * @throws NotADirectoryException if node is file
     */
    public void makeDirectory(String name) throws IllegalArgumentException, FullDirectoryException, NotADirectoryException {
        for(int i = 0; i<name.length(); i++){
            if(name.charAt(i) == ' ' || name.charAt(i) == '/'){
                throw new IllegalArgumentException();
            }
        }
        if(!cursor.getIsFile()) {
            DirectoryNode newDir = new DirectoryNode(name);
            newDir.setParent(cursor);
            cursor.addChild(newDir);
        }
    }

    /**
     * Makes files with given name
     * @param name name of the file
     * @throws IllegalArgumentException if the command is wrong
     * @throws FullDirectoryException if the parent directory is full
     * @throws NotADirectoryException if node is a file
     */
    public void makeFile(String name) throws IllegalArgumentException, FullDirectoryException, NotADirectoryException {
        for(int i = 0; i < name.length(); i++){
            if(name.charAt(i) == ' ' || name.charAt(i) == '/'){
                throw new IllegalArgumentException();
            }
        }
        DirectoryNode newDir = new DirectoryNode(name);
        newDir.setParent(cursor);
        newDir.setIsFile(true);
        cursor.addChild(newDir);
    }

    /**
     * Finds directory or file with specified name
     * @param name name of the directory or file
     */
    public void findDirectory(String name){
        cursor.getPath(name);
        if(cursor.Found()){
            System.out.println("ERROR: No such file exits.");
        }
        cursor.setFound(true);
    }

    /**
     * change cursor back to parent
     */
    public void changeParent(){
        if(cursor.getParent() != null)
            cursor = cursor.getParent();
        else
            System.out.println("ERROR: Already at root directory.");
    }

    /**
     * Change multiple paths
     * @param name String path
     */
    public void changePath(String name){
        DirectoryNode temp = cursor;
        try {
            goTo(name);
        }
        catch(NotADirectoryException e){
            cursor = temp;
            System.out.println("ERROR: Path not found!");
        }
        catch (DirectoryNotFound e){
            System.out.println("ERROR: Path not found!");
        }
    }

    /**
     * Move file in file system
     * @param source source of the file
     * @param destination destination of the file
     */
    public void moveFiles(String source, String destination){
        try {
            String[] src = source.split("/");
            if(!destination.contains(src[src.length-1])) {
                DirectoryNode temp = cursor;
                checkPath(source, destination);
                goTo2(source);
                DirectoryNode temp2 = cursor;
                cursor = cursor.getParent();
                for (int i = 0; i < cursor.getCounter(); i++) {
                    if (cursor.getReference(i) != null && cursor.getReference(i).getName().equals(source.split("/")[source.split("/").length - 1])) {
                        cursor.shiftReferences(i);
                    }
                }
                goTo(destination);
                cursor.addChild(temp2);
                cursor = temp;
            }
            else{
                System.out.println("ERROR: Cannot move directory into same directory");
            }
        }

        catch(NotADirectoryException e){
            System.out.println("ERROR: Not a directory.");
        }
        catch (FullDirectoryException e){
            System.out.println("ERROR: Full Directory.");
        }
        catch (DirectoryNotFound e){
            System.out.println("ERROR: Path not found.");
        }
    }

    /**
     * Go to a certain path
     * @param name String path
     * @throws NotADirectoryException if node is a file
     * @throws DirectoryNotFound if path is not found
     */
    public void goTo(String name) throws NotADirectoryException, DirectoryNotFound{
        String[] commandArr = name.split("/");
        if(commandArr[0].equals("root")) {
            resetCursor();
            commandArr[0] = null;
        }
        for (int i = 0; i < commandArr.length; i++) {
            if(commandArr[i] != null)
                changeDirectory(commandArr[i]);
        }
    }

    /**
     * Go to a certain path
     * @param name String path
     * @throws DirectoryNotFound if path is not found
     */
    public void goTo2(String name) throws DirectoryNotFound{
        String[] commandArr = name.split("/");
        if(commandArr[0].equals("root")) {
            resetCursor();
            commandArr[0] = null;
        }
        for (int i = 0; i < commandArr.length; i++) {
            if(commandArr[i] != null)
                changeDirectory2(commandArr[i]);
        }
    }

    /**
     * Checks if a given path is valid
     * @param source String source path
     * @param destination String destination path
     * @throws DirectoryNotFound if path is not found
     * @throws NotADirectoryException if node is a file
     */
    public void checkPath(String source, String destination) throws DirectoryNotFound, NotADirectoryException {
        goTo2(source);
        goTo2(destination);
    }
}

