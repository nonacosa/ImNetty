package nettyIO;



import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author wenda.zhuang
 * @Date 2019/8/19 18:52
 * @Description Netty 服务端
 * @E-mail   sis.nonacosa@gmail.com
 */
public class NettyServer {

	public void server() {
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		NioEventLoopGroup boss = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();

		serverBootstrap.group(boss,worker).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<NioSocketChannel>() {

			@Override
			protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
				nioSocketChannel.pipeline().addLast(new StringDecoder());
				nioSocketChannel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
					@Override
					protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
						System.out.println(s);
					}
				});
			}
		}).bind(8000);
	}
}
