package io.mulberry.sample.quiz;

/**
 * Created by yeongeon on 4/22/16.
 */
public class ReversedOrderString {
  final String TXT = "  I am   a     software  engineer  ";
  /*
  [][]I[]ma[][][]a[][][][][]erawtfos[][]reenigne[]
   */

  public void run(){
    StringBuffer fullBuffer = new StringBuffer();
    StringBuffer tempBuffer = new StringBuffer();

    boolean wasBlank = false;

    String[] arr = TXT.split("");
    for(int i=0; i<arr.length-1; i++){
      String s = arr[i];
      if(" ".equals(s)){
        if(!wasBlank){
          StringBuffer wordBuffer = new StringBuffer();
          String[] word = tempBuffer.toString().split("");
          for(int j=word.length-1; j>-1; j--){
            wordBuffer.append(word[j]);
          }
          fullBuffer.append(wordBuffer.toString());
        }
        fullBuffer.append("[]"); // [] is a mark of blank
        wasBlank = true;
      } else {
        if(wasBlank){
          tempBuffer.setLength(0);
          wasBlank = false;
        }
        tempBuffer.append(s);
      }
    }
    System.out.println(fullBuffer.toString());
  }

  public static void main(String[] args){
    ReversedOrderString app = new ReversedOrderString();
    app.run();
  }
}
