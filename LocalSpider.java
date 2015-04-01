import java.util.*;
import java.io.*;
import java.util.Scanner;

public class LocalSpider {
 
  private linkedList linkL;
  static int i = 0;
 
 private class Worker implements Runnable {
 
  private linkedList queue;
 
  public Worker(linkedList q) {
   queue = q;
  }
 
 
  public void run() {
   String name;
   while ((name = queue.remove()) != null) {
    File file = new File(name);
    String entries[] = file.list();
    if (entries == null)
     continue;
    for (String entry : entries) {
     if (entry.compareTo(".") == 0)
      continue;
     if (entry.compareTo("..") == 0)
      continue;
     String fn = name + "\\" + entry;
     System.out.println(fn);
    }
   }
  }
 }
 
 public LocalSpider() {
	 linkL = new linkedList();
 }
 
 public Worker createWorker() {
  return new Worker(linkL);
 }
 
 
// need try ... catch below in case the directory is not legal
 
 public void processDirectory(String dir) {
   try{
   File file = new File(dir);
   if (file.isDirectory()) {
    String entries[] = file.list();
    if (entries != null)
    	linkL.add(dir);
 
    for (String entry : entries) {
     String subdir;
     if (entry.compareTo(".") == 0)
      continue;
     if (entry.compareTo("..") == 0)
      continue;
     if (dir.endsWith("\\"))
      subdir = dir+entry;
     else
      subdir = dir+"\\"+entry;
     processDirectory(subdir);
    }
   }}catch(Exception e){}
 }
 
 public static void main(String Args[]) {
	 String name;
	 System.out.println("enter the word to begin search ");
	 Scanner in = new Scanner(System.in);
	 name = in.nextLine();
	 System.out.println("search results for :"+name);
	 LocalSpider fc = new LocalSpider();
 
//  now start all of the worker threads
 
  int N = 5;
  ArrayList<Thread> thread = new ArrayList<Thread>(N);
  for (int i = 0; i < N; i++) {
   Thread t = new Thread(fc.createWorker());
   thread.add(t);
   t.start();
  }
 
//  now place each directory into the linkL
 
  fc.processDirectory("D:");
  
 
//  indicate that there are no more directories to add
 
  fc.linkL.finish();
 
  for (int i = 0; i < N; i++){
   try {
    thread.get(i).join();
   } catch (Exception e) {};
  }
 }
}
