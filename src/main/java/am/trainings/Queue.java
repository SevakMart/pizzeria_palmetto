package am.trainings;

public class Queue<T> {

    private final Object [] data = new Object[10];
    private int head = 0;
    private int tail = 0;


    public void push(T value){
        data[head++] = value;
    }

    public T pop(){
        return (T)data[tail ++];
    }
}
