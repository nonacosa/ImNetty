package nettyIO;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

/**
 * @author wenda.zhuang
 * @Date 2019/8/19 18:52
 * @Description Netty 服务端
 * @E-mail sis.nonacosa@gmail.com
 */
public class NettyServer {

	public static void server() {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();

		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<NioSocketChannel>() {
					protected void initChannel(NioSocketChannel ch) {
						System.out.println("服务端启动中");
						System.out.println(ch.attr(AttributeKey.newInstance("serverName")));
					}
				})
				/**
				 * attr()方法可以给服务端的 channel，也就是NioServerSocketChannel指定一些自定义属性，然后我们可以通过channel.attr()取出这个属性
				 */
				.attr(AttributeKey.newInstance("serverName"), "nettyServer");

		/**
		 * 抽离 bind
		 */
//		serverBootstrap.bind(8000).addListener(future -> {
//			if(future.isSuccess()) {
//				System.out.println("端口绑定成功");
//			} else {
//				System.out.println("端口绑定失败");
//			}
//		});
		bind(serverBootstrap, 1);
	}

	/**
	 * 绑定端口，若被占用 + 1  继续尝试绑定
	 */
	private static void bind(final ServerBootstrap serverBootstrap, final int port) {
		serverBootstrap.bind(port)
				.addListener(future -> {
					if (future.isSuccess()) {
						System.out.println("端口【" + port + "】绑定成功 ！");
					} else {
						System.out.println("端口【" + port + "】绑定失败 ！");
						bind(serverBootstrap, port + 1);
					}
				});
	}

	public static void main(String[] args) {
		server();
	}

}
