package cn.asn.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import org.json.JSONObject;

public class HealthServerHandler extends ChannelInboundHandlerAdapter {

    private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");
    private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
    private static final AsciiString CONNECTION = new AsciiString("Connection");
    private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof FullHttpRequest) {
            FullHttpRequest req = (FullHttpRequest) msg; // 客户端的请求对象
            JSONObject respJson = new JSONObject(); // 新建一个返回消息的Json对象

            if (req.method() == HttpMethod.POST) {
                // 把客户端的请求数据格式化为Json对象
                JSONObject reqJson = null;

                try {
                    reqJson = new JSONObject(parseJsonRequest(req));
                } catch (Exception e) {
                    sendResponseJson(ctx, req, new String("error json"));
                    return;
                }

                // 根据请求的不同URI进行处理
                if (req.uri().equals("/bmi")) {
                    // 计算体重质量指数
                    double height = 0.01 * reqJson.getDouble("height");
                    double weight = reqJson.getDouble("weight");
                    double bmi = weight / (height * height);
                    bmi = ((int) (bmi * 100)) / 100.0;
                    respJson.put("bmi", bmi + "");
                } else if (req.uri().equals("/bmr")) {
                    // 计算基础代谢率
                    boolean isBoy = reqJson.getBoolean("isBoy");
                    double height = reqJson.getDouble("height");
                    double weight = reqJson.getDouble("weight");
                    int age = reqJson.getInt("age");
                    double bmr = 0;
                    if (isBoy) {
                        // 66 + (13.7 * 体重kg) + (5 * 身高cm) - (6.8 * 年龄years)
                        bmr = 66 + (13.7 * weight) + (5 * height) - (6.8 * age);
                    } else {
                        bmr = 655 + (9.6 * weight) + 1.8 * height - 4.7 * age;
                    }
                    bmr = ((int) (bmr * 100)) / 100.0;
                    respJson.put("bmr", bmr + "");

                } else {
                    // 错误处理
                    respJson.put("error", "404 Not Find");
                }
            } else {
                // 错误处理
                respJson.put("error", "The request method " + req.method() + " is not supported!");
            }

            // 向客户端发送结果
            sendResponseJson(ctx, req, respJson.toString());
        }
    }

    /**
     * 获取请求的内容
     * @param request
     * @return
     */
    private String parseJsonRequest(FullHttpRequest request) {
        ByteBuf jsonBuf = request.content();
        String jsonStr = jsonBuf.toString(CharsetUtil.UTF_8);
        return jsonStr;
    }

    /**
     * 响应 HTTP 的请求
     * @param ctx
     * @param req
     * @param jsonStr
     */
    private void sendResponseJson(ChannelHandlerContext ctx, FullHttpRequest req, String jsonStr) {
        boolean keepAlive = HttpUtil.isKeepAlive(req);
        byte[] jsonByte = jsonStr.getBytes();

        FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(jsonByte));
        resp.headers().set(CONTENT_TYPE, "text/json");
        resp.headers().setInt(CONTENT_LENGTH, resp.content().readableBytes());

        if (!keepAlive) {
            ctx.write(resp).addListener(ChannelFutureListener.CLOSE);
        } else {
            resp.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.write(resp);
        }
    }

}
