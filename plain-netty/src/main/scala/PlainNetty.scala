import io.Codec
import java.net.InetSocketAddress
import java.util.concurrent.Executors
import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.channel._
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory
import org.jboss.netty.handler.codec.http._
import org.jboss.netty.handler.codec.http.HttpHeaders.{Values, Names}
import org.jboss.netty.handler.stream.ChunkedWriteHandler

object PlainNetty extends App {

  val server = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()))
  server.setOption("soLinger", 0)
  server.setOption("reuseAddress", true)
  server.setOption("child.tcpNoDelay", true)
//  server.setOption("child.keepAlive", true)

  server.setPipelineFactory(new ChannelPipelineFactory {
    def getPipeline: ChannelPipeline = {
      val pipe = Channels.pipeline
      pipe.addLast("decoder", new HttpRequestDecoder)
//      pipe.addLast("aggregator", new HttpChunkAggregator(65536))
      pipe.addLast("encoder", new HttpResponseEncoder)
//      pipe.addLast("chunkedWriter", new ChunkedWriteHandler)
      pipe.addLast("helloworld", new SimpleChannelUpstreamHandler {
        override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) {
          e.getMessage match {
            case r: HttpRequest => {
              val keepAlive = HttpHeaders.isKeepAlive(r)
              val resp = new DefaultHttpResponse(r.getProtocolVersion, HttpResponseStatus.OK)
              val content = ChannelBuffers.copiedBuffer("Hello, world!", Codec.UTF8)
              resp.setHeader("Content-Length", content.readableBytes())
              resp.setHeader("Content-Type", "text/plain;charset=utf-8")
              if (keepAlive && r.getProtocolVersion == HttpVersion.HTTP_1_0)
                resp.setHeader(Names.CONNECTION, Values.KEEP_ALIVE)
              resp.setContent(content)

              val fut = ctx.getChannel.write(resp)
              if (!keepAlive) fut.addListener(ChannelFutureListener.CLOSE)
            }
            case _ => ctx.sendUpstream(e)
          }
        }

        override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent) {
          e.getCause.printStackTrace()
        }
      })
      pipe
    }
  })

  sys.addShutdownHook {
    server.releaseExternalResources()
    println("Server stopped")
  }
  server.bind(new InetSocketAddress(8084))
  println("Server started on 8084")
}
