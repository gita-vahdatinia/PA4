//---------------------------------------------------------------
// Guita Vahdatinia
// gvahdati
// 12B
// 5/13/16
// Queue ADT that is a linked list 
// Queue.java
// ----------------------------------------------------------------
import java.util.*;


public class Queue implements QueueInterface{
   private int size;      // number of items in this IntegerQueue
   private Node front, last;

    //node class 
    private class Node{
    Object item;
    Node next;

    //node constructor
    public Node(Object newItem){
        item = newItem;
        next = null;
    }


    public Node(Object newItem, Node nextNode){
        item = newItem;
        next = nextNode;
    }

    //set an item
    public void setItem(Object newItem){
        item = newItem;
    }

    //return an item;
    public Object getItem(){
        return item;
    }

    //set next Node
    public void setNext(Node nextNode){
        next = nextNode;
    }

    public Node getNext(){
        return next;
    }
}
//Queue constructor    
    public Queue(){
        front = null;
        last = null;
        size = 0;
    }
//returns true if queue is empty
    public boolean isEmpty(){
        return(size==0);
    }
//return length of queue
    public int length(){
        return size;
    }
//adds in a new object queue
    public void enqueue(Object newItem){
        if(isEmpty()){
            front = new Node(newItem);
        }
        else {
            Node N = front;
            while(N.next != null){
                N = N.next;
            }
            N.next = new Node(newItem);
            last = N.next;
           // newNode.setNext(last.getNext());
           // last.setNext(newNode);
        }
       // last = newNode;
        size++;
     }
//deletes all of nodes
    public void dequeueAll(){
        front=null;
        last = null;
        size = 0;
    }

//deletes first node
    public Object dequeue() throws QueueEmptyException {
        if(!isEmpty()) {
            Object val = front.item;
            Node N = front;
            front = front.next;
            N.next = null;
            size--;
            return val;
        }
        //    Node firstNode = last.getNext();
        //    if(firstNode == last){
        //        last = null;
        //    }
        //    else {
        //        last.setNext(firstNode.getNext());
        //    }
        //    size--;
        //    return firstNode.getItem();
        //    
        // }
        else {
            throw new QueueEmptyException("QueueEmptyException on dequeue: ");
        }
        
    }
//view first node
    public Object peek() throws QueueEmptyException{
        if(!isEmpty()){
           //Node firstNode = last.getNext();
           // return firstNode.getItem();
           return front.getItem();
        }
        else {
            throw new QueueEmptyException("Exception on peek");
        }
    }
//prints out nodes
    public String toString(){
        String s = "";
        Node loop = front;
        if(loop==null){
           return s; 
        }
        while(loop!=null){
            s+=loop.item+ " ";
            loop = loop.next;
        }
        return s;        
    }
}

