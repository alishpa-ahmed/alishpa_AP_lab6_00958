import java.util.LinkedList;
 
public class linkedList {
 
  private LinkedList<String> linkL;
 private boolean done;  
 private int size;  
 
 public linkedList() {
	 linkL = new LinkedList<String>();
  done = false;
  size = 0;
 }
 
 public synchronized void add(String s) {
	 linkL.add(s);
  size++;
  notifyAll();
 }
 
 public synchronized String remove() {
  String s;
  while (!done && size == 0) {
   try {
    wait();
   } catch (Exception e) {};
  }
  if (size > 0) {
   s = linkL.remove();
   size--;
   notifyAll();
  } else
   s = null;
  return s;
 }
 
 public synchronized void finish() {
  done = true;
  notifyAll();
 }
}
