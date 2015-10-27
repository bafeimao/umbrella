package net.bafeimao.umbrella.support.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {
	public static String copyToString(InputStream is) {
		if (is != null) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int i = -1;
				while ((i = is.read()) != -1) {
					baos.write(i);
				}
				return baos.toString();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException ignore) {
				}
			}

		}
		return null;
	}
}
