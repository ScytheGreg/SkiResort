package containers;

public class emptyContainerException extends Exception{
    public emptyContainerException(){
        super("Attempt of access to an empty container");
    }
}
