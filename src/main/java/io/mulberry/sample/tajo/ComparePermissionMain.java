package io.mulberry.sample.tajo;

import io.mulberry.sample.file.Files;
import org.apache.hadoop.security.AccessControlException;
import org.apache.tajo.conf.TajoConf;

import java.io.IOException;

/**
 * Created by yeongeon on 12/9/15.
 */
public class ComparePermissionMain {

    public static void main(String[] args) throws IOException {
        TajoConf conf = new TajoConf();
        Files files = new Files(conf);

        String[] users = {"yeongeon", "jenkins"};
        for (int i = 0; i < users.length; i++) {
            final String src = String.format("file:/tmp/%s-tajo/table1/data.csv", users[i]);
            try {
                files.getPermit(src);
            } catch(AccessControlException e) {
                throw new AccessControlException("ERROR: " + src + " permission denied");
            }
            System.out.println("> Yes Exist : "+ src);
        }
    }

}
