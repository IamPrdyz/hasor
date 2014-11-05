/*
 * Copyright 2008-2009 the original 赵永春(zyc@hasor.net).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.hasor.rsf.server.handler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.hasor.rsf.general.ProtocolStatus;
import net.hasor.rsf.general.RSFConstants;
import net.hasor.rsf.protocol.socket.RequestSocketMessage;
import net.hasor.rsf.protocol.socket.ResponseSocketMessage;
/**
 * 
 * @version : 2014年11月4日
 * @author 赵永春(zyc@hasor.net)
 */
public class ServiceHandler extends ChannelInboundHandlerAdapter {
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof RequestSocketMessage) {
            //1.发送ACK包
            RequestSocketMessage reqMSG = (RequestSocketMessage) msg;
            ResponseSocketMessage ack = new ResponseSocketMessage();
            ack.setVersion(RSFConstants.RSF_V_1_0_Res);
            ack.setRequestID(reqMSG.getRequestID());
            ack.setStatus(ProtocolStatus.Accepted.shortValue());
            //2.当ACK，发送成功之后继续传递msg
            ctx.writeAndFlush(ack).addListener(new FireChannel(ctx, msg));
            //
        } else if (msg instanceof RequestSocketMessage) {
            //
            //
        }
    }
}