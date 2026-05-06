package containers;

public class emptyContainerException extends RuntimeException{
    public emptyContainerException(){
        super("Attempt of access to an empty container");
    }
}
