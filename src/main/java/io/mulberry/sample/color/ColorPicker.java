package io.mulberry.sample.color;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yeongeon on 3/15/16.
 */
public class ColorPicker {
  private static Logger LOG = LoggerFactory.getLogger(ColorPicker.class);

  static final Class<ColorPicker> SELF_CLASS = ColorPicker.class;

  class RGB {
    List<Integer> r = new LinkedList<Integer>();
    List<Integer> g = new LinkedList<Integer>();
    List<Integer> b = new LinkedList<Integer>();

    int rr = 0;
    int gg = 0;
    int bb = 0;

    public void cal(){
      double r2 = 0;
      double g2 = 0;
      double b2 = 0;

      for (int i = 0; i < r.size(); i++) {
        r2 += r.get(i);
      }
      for (int i = 0; i < g.size(); i++) {
        g2 += g.get(i);
      }
      for (int i = 0; i < b.size(); i++) {
        b2 += b.get(i);
      }

      this.rr = (int)(r2/r.size());
      this.gg = (int)(g2/g.size());
      this.bb = (int)(b2/b.size());
    }

    public int getRed(){
      return this.rr;
    }

    public int getGreen(){
      return this.gg;
    }

    public int getBlue(){
      return this.bb;
    }

    public void addRed(int rd){
      r.add(rd);
    }
    public void addGreen(int gr){
      g.add(gr);
    }
    public void addBlue(int bl){
      b.add(bl);
    }

    @Override
    public String toString() {
      cal();

      System.out.printf(">>>>> AA rgb (int):%d, %d, %d\n", this.rr, this.gg, this.bb);

      String hex = String.format("#%02x%02x%02x", this.rr, this.gg, this.bb);
      return hex;
    }
  }

  RGB rgb = new RGB();

  public void printPixelARGB(int pixel) {
    int alpha = (pixel >> 24) & 0xff;
    int red = (pixel >> 16) & 0xff;
    int green = (pixel >> 8) & 0xff;
    int blue = (pixel) & 0xff;
    //System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
    rgb.addRed(red);
    rgb.addGreen(green);
    rgb.addBlue(blue);
  }

  private void marchThroughImage(BufferedImage image) {
    int w = image.getWidth();
    int h = image.getHeight();
    System.out.println("width, height: " + w + ", " + h);
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        //System.out.println("x,y: " + j + ", " + i);
        int pixel = image.getRGB(j, i);
        if(i%10==0 && j%10==0){
          printPixelARGB(pixel);
        }
        //System.out.println("");
      }
    }
  }

  public ColorPicker() {
    try {
      // get the BufferedImage, using the ImageIO class
      String targetFile = SELF_CLASS.getResource("/images/sample7.jpg").getPath();
      System.out.println("targetFile: "+ targetFile);
      BufferedImage image = ImageIO.read(new File(targetFile));
      marchThroughImage(image);
      System.out.println(">>>>> AVG: "+ rgb.toString());


      int x = 0, y = 0;
      int rgb = image.getRGB(x, y);
      float hsb[] = new float[3];

      //hsb[0] = hue;         //색
      //hsb[1] = saturation;  //채도
      //hsb[2] = brightness;  //밝기

      int r = (rgb >> 16) & 0xFF;
      int g = (rgb >>  8) & 0xFF;
      int b = (rgb      ) & 0xFF;

      System.out.printf(">>>>> BB rgb (int):%d, %d, %d\n", r, g, b);

      Color.RGBtoHSB(r, g, b, hsb);


      //float[] hsb = Color.RGBtoHSB(red, green, blue, null);
      //float hue = hsb[0];

      System.out.printf(">>>>> HSB:%f, %f, %f\n", hsb[0], hsb[1], hsb[2]);

      if      (hsb[1] < 0.1 && hsb[2] > 0.9){
        System.out.println("NearlyWhite");
      }
      else if (hsb[2] < 0.2){
        System.out.println("NearlyBlack");
      }
      else {
        float deg = hsb[0]*360;
        System.out.printf(">>>>> deg:%f\n", deg);
        if      (deg >=   0 && deg <  30) {
          System.out.println("RED");
        }
        else if (deg >=  30 && deg <  90) {
          System.out.println("YELLOW");
        }
        else if (deg >=  90 && deg < 150) {
          System.out.println("GREEN");
        }
        else if (deg >= 150 && deg < 210) {
          System.out.println("CYAN");
        }
        else if (deg >= 210 && deg < 270) {
          System.out.println("BLUE");
        }
        else if (deg >= 270 && deg < 330) {
          System.out.println("MAGENTA");
        }
        else {
          System.out.println("RED");
        }
      }



    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    /*
    sample1.jpg
    #A5A8A2 (165,168,162)

    sample2.jpg
    #21201F
    (33,32,31)
    */
  }

  public static void main(String[] args){
    new ColorPicker();
  }

}
