package nettyIO;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author wenda.zhuang
 * @Date 2019/8/20 12:55
 * @Description netty 客户端
 * @E-mail sis.nonacosa@gmail.com
 */
public class NettyClient {


	private static final int MAX_RETRY = 5;

	public static void client() throws InterruptedException {
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();

		Bootstrap bootstrap = new Bootstrap();
		bootstrap
				// 1.指定线程模型
				.group(workerGroup)
				// 2.指定 IO 类型为 NIO
				.channel(NioSocketChannel.class)
				// 3.IO 处理逻辑
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) {
					}
				});
		// 4.建立连接
		connect(bootstrap, "juejin.im", 80, MAX_RETRY);
//		bootstrap.connect("juejin.im", 80)
//				.addListener(future -> {
//					if (future.isSuccess()) {
//						System.out.println("连接成功!");
//					} else {
//						System.err.println("连接失败!");
//					}
//
//				});
	}

	/**
	 * 失败重连
	 */
	private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
		bootstrap.connect(host, port)
				.addListener(future -> {
					if (future.isSuccess()) {
						System.out.println("连接成功!");
					} else if (retry == 0) {
						System.out.println("重连次数用完，放弃链接");
					} else {
						// 第几次重连
						int order = (MAX_RETRY - retry) + 1;
						// 本次重连的间隔 「幂等实现」
						int delay = 1 << order;
						System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
						System.err.println("连接失败，开始重连");
						bootstrap.config()
								.group()
								.schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
					}
				});
	}


	public static void main(String[] args) throws InterruptedException {
		client();
	}
}
