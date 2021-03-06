package utils.VFS;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/*
 * Created by elvira on 12.04.14.
 */
public class VFS implements VFSInterface{
    private String root;

    public VFS(String root){
        this.root = root;
    }

    public void changeRoot(String newRoot) {this.root = this.root + newRoot; }

    public class FileIterator implements Iterator<String>{

        private Queue<File> files = new LinkedList<>();

        public FileIterator(String path){
            files.add(new File(path));
        }

        @Override
        public boolean hasNext() {
            return !files.isEmpty();
        }

        @Override
        public String next(){
            File file = files.peek();
            if(file.isDirectory()){
                File[] listFiles = file.listFiles();
                if (listFiles != null){
                    Collections.addAll(files, listFiles);
                }
            }
            return files.poll().getPath();//.getAbsolutePath();
        }

        @Override
        public void remove() {

        }
    }

    @Override
    public boolean isExist(String path) {
        return new File(path).exists();
    }

    @Override
    public boolean isDirectory(String path) {
        return new File(root+path).isDirectory();
    }

    @Override
    public String getAbsolutePath(String file) {
        return new File(root+file).getAbsolutePath();
    }

    @Override
    public byte[] getBytes(String file) throws IOException{
        return IOHelper.readBytesFromFile(file);
    }

    @Override
    public String getUFT8Text(String file) throws IOException{
        return IOHelper.readTextFromFile(file);
    }

    @Override
    public Iterator<String> getIterator(String startDir) {
        return new FileIterator(this.root + startDir);
    }
}
