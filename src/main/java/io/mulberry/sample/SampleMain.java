package io.mulberry.sample;

import io.mulberry.sample.file.Files;
import org.apache.tajo.conf.TajoConf;

import java.io.IOException;

/**
 * Created by yeongeon on 12/9/15.
 */
public class SampleMain {

    public static void main(String[] args) throws IOException {
        TajoConf conf = new TajoConf();
        Files files = new Files(conf);

        String[] users = {"yeongeon", "jenkins"};
        for (int i = 0; i < users.length; i++) {
            final String src = String.format("file:/tmp/%s-tajo/table1/data.csv", users[i]);
            if(files.getPermit(src)){
                System.out.println("> Yes Exist : "+ src);
            } else {
                System.out.println("> No Exist : "+ src);
            }
        }
    }

}
