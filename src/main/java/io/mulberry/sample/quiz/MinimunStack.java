package io.mulberry.sample.quiz;

import java.util.*;

/**
 * Created by yeongeon on 4/22/16.
 */
public class MinimunStack {
  final String TXT =  "10 6 7 5 3 4";
  final String TXT2 = "10 6 7 5 3 4 2 1 9 8 14";
  final String TXT3 = "4 5 6 10 7 3 2 17 15 16 1";
  /*
  "10 6 7 5 3 4"
                      10
                     /  \
                    6
                   /
                  5
                 /
                3(min)

  "10 6 7 5 3 4 2 1 9 8 14"
                      10
                     /  \
                    6
                   /
                  5
                 /
                3
               /
              2
             /
            1(min)

  "4 5 6 10 7 3 2 17 15 16 1"
                      4
                     /  \
                    3
                   /
                  2
                 /
                1(min)
   */

  public class MinTree {
    private Node root;
    private Node minNode;

    public void insert(Integer v) throws NullPointerException, CloneNotSupportedException {
      if(this.root==null){
        this.root = new Node(v);
      }
      if(this.minNode==null || this.minNode.value==null){
        this.minNode = this.root;
      } else {
        Node newNode = new Node(v);
        Node savedMinNode = this.minNode.clone();
        if(v < savedMinNode.value){
          savedMinNode.left = newNode;
          newNode.parent = savedMinNode;
          this.minNode = newNode;
        } else if(v > this.minNode.value){
          savedMinNode.right = newNode;
          newNode.parent = savedMinNode;
        }
      }
    }

    public void remove(Integer v) {
      if(v == this.minNode.value){
        this.minNode.value = null;
        this.minNode.parent.left = null;
      } else {
        Node parentNode = this.minNode.parent;
        while(parentNode != null){
          if(v == parentNode.value){
            if(parentNode.value == this.root.value){
              this.root = null;
            }
            parentNode.value = null;
            parentNode.left = null;
            this.minNode = parentNode;
          }
          parentNode = parentNode.parent;
        }
      }

    }

    public Integer getMinimun(){
      return this.minNode.value;
    }

    class Node implements Cloneable {
      Node left;
      Node right;
      Node parent;
      Integer value;

      public Node(Integer v){
        this.value = v;
      }

      @Override
      protected Node clone() throws CloneNotSupportedException {
        Node newNode = new Node(this.value);
        newNode.left = this.left;
        newNode.right = this.right;
        newNode.parent = this.parent;
        return newNode;
      }
    }
  }

  private List<Integer> stack = new LinkedList<Integer>();
  private int ptr = -1;

  private MinTree minTree = new MinTree();

  public void push(int v) throws CloneNotSupportedException {
    this.stack.add(v);
    this.minTree.insert(v);
    this.ptr += 1;
  }

  public int pop(){
    int v = this.stack.get(this.ptr);
    this.stack.remove(this.ptr);
    this.minTree.remove(v);
    this.ptr -= 1;
    return v;
  }

  public int minimum(){
    Integer mn = null;
    Iterator<Integer> iterator = this.stack.iterator();
    while(iterator.hasNext()){
      int v = iterator.next();
      if(mn == null || mn > v){
        mn = v;
      }
    }
    return mn;
  }

  @Override
  public String toString() {
    StringBuffer stb = new StringBuffer();
    Iterator<Integer> iterator = this.stack.iterator();
    while(iterator.hasNext()){
      if(stb.length() >0){
        stb.append(" ");
      }
      stb.append(iterator.next());
    }
    return stb.toString();
  }

  public void run() throws CloneNotSupportedException {
    StringTokenizer tokenizer = new StringTokenizer(this.TXT);
    while(tokenizer.hasMoreElements()){
      int v = Integer.parseInt((String)tokenizer.nextElement());
      push(v);
    }
    System.out.println("min(stack): "+ minimum() +", min(tree): "+ this.minTree.getMinimun());
    System.out.println("pop: "+ pop()); // 4
    System.out.println("pop: "+ pop()); // 3
    System.out.println("pop: "+ pop()); // 5
    System.out.println("pop: "+ pop()); // 7
    System.out.println("pop: "+ pop()); // 6
    System.out.println("pop: "+ pop()); // 10
    System.out.println(toString());


    tokenizer = new StringTokenizer(this.TXT2);
    while(tokenizer.hasMoreElements()){
      int v = Integer.parseInt((String)tokenizer.nextElement());
      push(v);
    }
    System.out.println("min(stack): "+ minimum() +", min(tree): "+ this.minTree.getMinimun());
    System.out.println("pop: "+ pop()); // 2
    System.out.println("pop: "+ pop()); // 1
    System.out.println("pop: "+ pop()); // 9
    System.out.println("pop: "+ pop()); // 8
    System.out.println("pop: "+ pop()); // 14
    System.out.println("pop: "+ pop()); // 4
    System.out.println("pop: "+ pop()); // 3
    System.out.println("pop: "+ pop()); // 5
    System.out.println("pop: "+ pop()); // 7
    System.out.println("pop: "+ pop()); // 6
    System.out.println("pop: "+ pop()); // 10
    System.out.println(toString());


    tokenizer = new StringTokenizer(this.TXT3);
    while(tokenizer.hasMoreElements()){
      int v = Integer.parseInt((String)tokenizer.nextElement());
      push(v);
    }
    System.out.println("min(stack): "+ minimum() +", min(tree): "+ this.minTree.getMinimun());
    System.out.println("pop: "+ pop()); // 1
    System.out.println("pop: "+ pop()); // 16
    System.out.println("pop: "+ pop()); // 15
    System.out.println("pop: "+ pop()); // 17
    System.out.println("pop: "+ pop()); // 2
    System.out.println("pop: "+ pop()); // 3
    System.out.println("pop: "+ pop()); // 7
    System.out.println("pop: "+ pop()); // 10
    System.out.println("pop: "+ pop()); // 6
    System.out.println("pop: "+ pop()); // 5
    System.out.println("pop: "+ pop()); // 4
    System.out.println(toString());
  }

  public static void main(String[] args) throws CloneNotSupportedException {
    MinimunStack app = new MinimunStack();
    app.run();
  }
}
