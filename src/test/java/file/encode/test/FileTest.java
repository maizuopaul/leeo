package file.encode.test;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

public class FileTest {
	public static void main(String[] args) throws FileNotFoundException, IOException {
//		test("d:/");
		System.out.println(System.currentTimeMillis());
	}
	
	public static void test(String dir) throws FileNotFoundException, IOException{
		File f = new File(dir);
		if(f.isDirectory()){
			File[] listFiles = f.listFiles(new FileFilter() {
				public boolean accept(File f) {
					return f.isFile() && !f.isHidden() && f.canRead();
				}
			});
			if(null != listFiles && listFiles.length > 0){
				FileOutputStream out = null;
				for (File file : listFiles) {
					out = new FileOutputStream(new File(dir+File.separator+"-stream-"+file.getName()));
					out.write("fileName:".getBytes());
					out.write(file.getName().getBytes());
					out.write("\n".getBytes());
					out.write("fileDataBytesBase64:\t".getBytes());
					byte[] byteArray = IOUtils.toByteArray(new FileInputStream(file));
					out.write(Base64.encodeBase64String(byteArray).getBytes());
					out.flush();
					out.close();
				}
			}
		}
	}
}
