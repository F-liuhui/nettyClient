package com.cnpc.neyytclient;

import com.cnpc.handler.SimpleClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * netty 客户端练习
 *
 */
public class NettyClient {
    public static void main(String[] args) {
        client();
    }
    public static void client(){
        //io线程组
        EventLoopGroup workGroup=new NioEventLoopGroup();
        //netty客户端管理类
        Bootstrap bootstrap=new Bootstrap();
        //绑定线程组
        bootstrap.group(workGroup);
        //绑定客户端通道
        bootstrap.channel(NioSocketChannel.class);
        //为通道绑定handler
        bootstrap.handler(new SimpleClientInitializer());
        try {
            Channel ch =bootstrap.connect("127.0.0.1",8008).sync().channel();
            // 控制台输入
            Scanner in = new Scanner(System.in);
            String line = "";
            while (line!=null){
                line=in.nextLine();
                if (line.equals("lisi")) {
                    break;
                }
                /**
                 * 向服务端发送在控制台输入的文本 并用"\r\n"结尾
                 * 之所以用\r\n结尾 是因为我们在handler中添加了 DelimiterBasedFrameDecoder 帧解码。
                 * 这个解码器是一个根据\n符号位分隔符的解码器。所以每条消息的最后必须加上\n否则无法识别和解码
                 *
                 */
                ch.writeAndFlush(line + "\r\n");
            }
            ch.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            workGroup.shutdownGracefully();
        }
    }
}
