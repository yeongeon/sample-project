package io.mulberry.sample.color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by yeongeon on 3/16/16.
 */
public class ImageAnalyzer{
  private int pixdata[][];
  private int rgbdata[][];
  private BufferedImage image;
  int background_color;
  int border_color;
  int imagebg_color;
  private void populateRGB(){
    rgbdata = new int[image.getWidth()][image.getHeight()];
    for(int i = 0; i < image.getWidth(); i++){
      for(int j = 0; j < image.getHeight(); j++){
        rgbdata[i][j] = image.getRGB(i, j);
      }
    }
    int howmanydone = 0;
    int prevcolor,newcolor;
    prevcolor = rgbdata[0][0];

        /*
        for(int i = 0; i < image.getWidth(); i++){
           for(int j = 0; j < image.getHeight(); j++){
              System.out.print(rgbdata[i][j]);
           }
           System.out.println("");
        }*/
    for(int i = 0; i < image.getWidth(); i++){
      for(int j = 0; j < image.getHeight(); j++){
        newcolor = rgbdata[i][j];
        if((howmanydone == 0) && (newcolor != prevcolor)){
          background_color = prevcolor;
          border_color = newcolor;
          prevcolor = newcolor;
          howmanydone = 1;
        }
        if((newcolor != prevcolor) && (howmanydone == 1)){
          imagebg_color = newcolor;
        }
      }
    }
  }
  public ImageAnalyzer(){ background_color = 0; border_color = 0; imagebg_color = 0;}
  public int background(){ return background_color; }
  public int border() { return border_color;}
  public int imagebg() {return imagebg_color;}
  public int analyze(String filename,String what) throws IOException {
    image = ImageIO.read(new File(filename));
    pixdata = new int[image.getHeight()][image.getWidth()];
    populateRGB();
    if(what.equals("background"))return background();
    if(what.equals("border"))return border();
    if(what.equals("image-background"))return imagebg();
    else return 0;
  }

  public static void main(String[] args){
    ImageAnalyzer an = new ImageAnalyzer();
    String imageName;

    Scanner scan = new Scanner(System.in);
    System.out.print("Enter image name:");
    imageName = scan.nextLine();
    try{
      int a = an.analyze(imageName,"border");//"border","image-background","background" will get you different colors
      System.out.printf("Color bg: %x",a);

    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }
}
