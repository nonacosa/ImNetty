package badIO;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * @author wenda.zhuang
 * @Date 2019/8/19 18:34
 * @Description ... E-mail   sis.nonacosa@gmail.com
 */
public class IOClient {
	public static void main(String[] args) {
		new Thread(() -> {
			try {
				Socket socket = new Socket("127.0.0.1", 8000);
				while (true) {
					socket.getOutputStream()
							.write((new Date() + ": hello world").getBytes());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}).start();
	}
}
