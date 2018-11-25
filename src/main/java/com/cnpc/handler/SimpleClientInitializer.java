package com.cnpc.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class SimpleClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {

        //从通道中得到管道。
        ChannelPipeline pipeline= channel.pipeline();
        /**
         * 这个地方的 必须和服务端对应上。否则无法正常解码和编码
         *
         * 解码和编码。
         *
         */
         //流解码器 Delimiters.lineDelimiter()表示以\n结尾表示一个完整的消息，8192表示流长度。
         pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
         pipeline.addLast("decoder", new StringDecoder());
         pipeline.addLast("encoder", new StringEncoder());
        // 客户端的逻辑
         pipeline.addLast("handler", new SimpleClientHandler());
    }
}
