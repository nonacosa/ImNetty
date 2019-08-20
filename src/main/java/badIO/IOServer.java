package badIO;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wenda.zhuang
 * @Date 2019/8/19 18:23
 * @Description ...
 * @E-mail sis.nonacosa@gmail.com
 */
public class IOServer {
	public static void run() throws IOException {
		ServerSocket serverSocket = new ServerSocket(8000);

		new Thread(() -> {
			while (true) {
				try {
					// 阻塞方法 「获取新的链接」
					Socket socket = serverSocket.accept();

					new Thread(() -> {
						int len;
						byte[] data = new byte[1024];
						try {
							InputStream inputStream = socket.getInputStream();
							//按照字节流的方式读取
							while ((len = inputStream.read(data)) != -1) {
								System.out.println(new String(data, 0, len));
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public static void main(String[] args) throws IOException {
		run();
	}
}
