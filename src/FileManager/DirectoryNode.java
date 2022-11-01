package FileManager;
/**
 * Name: Ritesh Sunil Chavan
 */
public class DirectoryNode {
    /**
     * Member Variables
     */
    private int counter;
    private String name;
    private DirectoryNode parent;
    private boolean isFile;
    private static String spaces = "";
    private static boolean Found = true;
    private DirectoryNode[] references;

    /**
     * Constructor for creating a new directory
     * @param name
     */
    public DirectoryNode(String name){
        this.counter = 0;
        this.name = name;
        this.references = new DirectoryNode[10];
        this.parent = null;
        this.isFile = false;
    }

    /**
     * Getter method for directory name
     * @return name of the directory
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for directory name
     * @param name name of the directory
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for the reference of next directory
     * @param index index value of the directory
     * @return directory at that particular index
     */
    public DirectoryNode getReference(int index){
        return references[index];
    }

    /**
     * Setter method for references
     * @param index index value of the directory
     * @param newReference new directory reference
     */
    public void setReferences(int index, DirectoryNode newReference){
        references[index] = newReference;
    }

    /**
     * Getter method for isFile
     * @return boolean value of whether it's a file or not
     */
    public boolean getIsFile(){
        return isFile;
    }

    /**
     * Setter method for isFile
     * @param value boolean value
     */
    public void setIsFile(boolean value){
        this.isFile = value;
    }

    /**
     * Setter method for spaces
     * @param spaces indent
     */
    public void setSpaces(String spaces) {
        DirectoryNode.spaces = spaces;
    }

    /**
     * Getter method for parent of a directory
     * @return reference of the parent directory
     */
    public DirectoryNode getParent() {
        return parent;
    }

    /**
     * Setter method for the parent directory
     * @param parent new parent reference
     */
    public void setParent(DirectoryNode parent) {
        this.parent = parent;
    }

    /**
     * Setter method for found
     * @param Found boolean expression of whether its found before
     */
    public void setFound(boolean Found) {
        this.Found = Found;
    }

    /**
     * Getter method for found
     * @return boolean value
     */
    public boolean Found() {
        return this.Found;
    }

    /**
     * Getter method for the counter
     * @return counter
     */
    public int getCounter(){
        return this.counter;
    }

    /**
     * Setter mthod for counter
     * @param counter new counter value
     */
    public void setCounter(int counter){
        this.counter = counter;
    }

    /**
     * Adds child to a directory
     * @param node new directory node
     * @throws FullDirectoryException if the directory is full
     * @throws NotADirectoryException if the directory is a file
     */
    public void addChild(DirectoryNode node) throws FullDirectoryException, NotADirectoryException {
        if(counter != 10) {
            if (!this.isFile) {
                references[counter] = node;
                counter++;

            }
            else {
                throw new NotADirectoryException();
            }
        }
        else {
            throw new FullDirectoryException();
        }
    }

    /**
     * Gets the path to a certain file
     * @param name name of the file
     */
    public void getPath(String name) {
        if (getName().equals(name)) {
            this.setFound(false);
            System.out.println(goBack(this));
        }
        for(int i = 0; i<counter; i++) {
            if (references[i] != null) {
                references[i].getPath(name);
            }
        }
    }

    /**
     * Go back and print from directory to root
     * @param node node from which we have to go back
     * @return path of the file/directory
     */
    public String goBack(DirectoryNode node){
        String path = "";
        while (node != null){
            path += node.getName() + "/";
            node = node.getParent();
        }
        String[] pathArr;
        pathArr = path.split("/");
        path = "";
        for(int i = pathArr.length - 1; i >= 0; i--){
            path += pathArr[i];
            if(i != 0){
                path += "/";
            }
        }
        return path;
    }

    /**
     * Prints the whole file system in a systematically formatted manner
     */
    public void printTree(){
        if(!this.isFile)
            System.out.println(spaces + "|- " + name);
        else
            System.out.println(spaces + "- " + name);
        spaces += "    ";
        for(int i = 0; i<counter; i++){
            if(references[i] != null){
                references[i].printTree();
            }
        }
        spaces = spaces.substring(0, spaces.length()-4);
    }

    /**
     * Shifts the elements of the array to left
     * @param position position from which elements must be shifted
     */
    public void shiftReferences(int position){
        System.arraycopy(references, position + 1, references, position, (counter - position)-1);
        references[counter - 1] = null;
        counter--;
    }
}
