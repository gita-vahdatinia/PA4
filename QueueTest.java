//---------------------------------------------------------------
// Guita Vahdatinia
// gvahdati
// 12B
// 5/13/16
// Test out the Queue ADT 
// QueueTest.java
// ----------------------------------------------------------------
public class QueueTest{
    public static void main(String[] args){
    Queue A = new Queue();
    System.out.println(A.isEmpty());
    A.enqueue(4);
    System.out.println(A);
    A.enqueue(1);
    A.enqueue(2);
    A.enqueue(3);
    System.out.println(A.length());
    System.out.println(A.isEmpty());
    A.dequeue();
    System.out.println(A.length());
    System.out.println(A);
    A.dequeueAll();
    System.out.println(A.length());
    System.out.println(A); 
    }
}
