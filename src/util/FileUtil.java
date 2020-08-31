package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utilities for file copy.
 * 
 * @author Yichen.Hua on 2018/05/08.
 */
public final class FileUtil {

	/**
	 * Copy specific file to specified destination.
	 * 
	 * @param file
	 *            the file to be transferred.
	 * 
	 * @param destination
	 *            the path of the destination.
	 * 
	 * @throws java.io.IOException
	 */
	public static void copyFile(File file, String destination) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(file);
			os = new FileOutputStream(destination);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
		}
	}

}

