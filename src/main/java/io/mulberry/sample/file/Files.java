package io.mulberry.sample.file;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.AclStatus;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.tajo.conf.TajoConf;

import java.io.IOException;

/**
 * Created by yeongeon on 12/9/15.
 */
public class Files {

    protected TajoConf conf;

    public Files(TajoConf tajoConf){
        this.conf = new TajoConf(tajoConf);
    }

    public boolean getPermit(String src) throws IOException {
        Path path = new Path(src);
        FileSystem fs = path.getFileSystem(conf);
        FileStatus status = fs.getFileStatus(path);
        FsPermission fsPermission = status.getPermission();
        FsAction fsAction = fsPermission.getUserAction();
        boolean permit = fsAction.implies(FsAction.READ_WRITE);
        return permit;
    }
}
