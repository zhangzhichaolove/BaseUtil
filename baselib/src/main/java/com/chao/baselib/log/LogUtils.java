package com.chao.baselib.log;

import android.util.Log;

import com.chao.baselib.config.CustomConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * 创建日期：2017/4/9 on 12:07
 * 描述:Log打印工具类
 * 作者:张智超 Chao
 */
public class LogUtils {
    private static final String TAG = "CLog";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String TOP_BORDER = "╔═══════════════════════════════════════════════════════════════════════════════════════════════════" + LINE_SEPARATOR;
    private static final String LEFT_BORDER = "║ ";
    private static final String BOTTOM_BORDER = "╚═══════════════════════════════════════════════════════════════════════════════════════════════════";

    public static void println(Object object) {
        if (CustomConfig.deBug) {
            System.out.println(callMethodAndLine(P, TAG, object));
        }
    }

    public static void showTagD(String tag, Object object) {
        if (CustomConfig.deBug) {
            callMethodAndLine(D, tag, object);// 蓝
        }
    }

    public static void showTagD(Object object) {
        if (CustomConfig.deBug) {
            callMethodAndLine(D, TAG, object);// 蓝
        }
    }


    public static void showTagI(String tag, Object object) {
        if (CustomConfig.deBug) {
            callMethodAndLine(I, tag, object);// 绿
        }
    }

    public static void showTagI(Object object) {
        if (CustomConfig.deBug) {
            // 1、Log.v
            // 的调试颜色为黑色的，任何消息都会输出，这里的v代表verbose啰嗦的意思，平时使用就是Log.v("","");
            // Log.v(TAG, object.toString());// 黑

            // 2、Log.d的输出颜色是蓝色的，仅输出debug调试的意思，但他会输出上层的信息，过滤起来可以通过DDMS的Logcat标签来选择.
            // Log.d(TAG, object.toString());// 蓝

            // 3、Log.i的输出为绿色，一般提示性的消息information，它不会输出Log.v和Log.d的信息，但会显示i、w和e的信息
            callMethodAndLine(I, TAG, object);// 绿

            // 4、Log.w的意思为橙色，可以看作为warning警告，一般需要我们注意优化Android代码，同时选择它后还会输出Log.e的信息。
            // Log.w(TAG, object.toString());// 黄

            // 5、Log.e为红色，可以想到error错误，这里仅显示红色的错误信息，这些错误就需要我们认真的分析，查看栈的信息了。
            // Log.e(TAG, object.toString());// 红

            // 6 Log.a为4.0新增加的。Assert。
        }
    }

    public static void showTagW(String tag, Object object) {
        if (CustomConfig.deBug) {
            callMethodAndLine(W, tag, object);// 黄
        }
    }

    public static void showTagW(Object object) {
        if (CustomConfig.deBug) {
            callMethodAndLine(W, TAG, object);// 黄
        }
    }

    public static void showTagE(String tag, Object object) {
        if (CustomConfig.deBug) {
            callMethodAndLine(E, tag, object);
        }
    }

    public static void showTagE(Object object) {
        if (CustomConfig.deBug) {
            callMethodAndLine(E, TAG, object);
        }
    }

    private static String callMethodAndLine(int type, String tag, Object result) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement thisMethodStack = Thread.currentThread()
                .getStackTrace()[4];
        sb.append("Thread：" + Thread.currentThread().getName() + "，").append(thisMethodStack.getMethodName() + "\t")
                .append("(").append(thisMethodStack.getFileName())
                .append(":").append(thisMethodStack.getLineNumber())
                .append(")" + LINE_SEPARATOR);
//        sb.append(TOP_BORDER).append(LEFT_BORDER)
//                .append("Thread：" + Thread.currentThread().getName() + "，").append(thisMethodStack.getMethodName() + "\t")
//                .append("(").append(thisMethodStack.getFileName())
//                .append(":").append(thisMethodStack.getLineNumber())
//                .append(")" + LINE_SEPARATOR);
        sb.append(TOP_BORDER);
        String body = formatJson(result.toString());
        StringBuilder stb = new StringBuilder();
        stb.append(sb.toString());
        if (body != null) {
            String[] lines = body.split(LINE_SEPARATOR);
            for (String line : lines) {
                stb.append(LEFT_BORDER).append(line).append(LINE_SEPARATOR);
            }
            stb.append(BOTTOM_BORDER);
            printLog(type, tag, stb.toString());
            return sb.toString() + stb.toString();
        }
        String[] lines = result.toString().split(LINE_SEPARATOR);
        for (String line : lines) {
            stb.append(LEFT_BORDER).append(line).append(LINE_SEPARATOR);
        }
        stb.append(BOTTOM_BORDER);
//        result = stb.toString();
//        int length = 150;
//        try {
//            for (int i = 0; result.toString().length() > 0; i++) {
//                if (result.toString().length() > (i + 1) * length) {
//                    sb.append(LEFT_BORDER).append(result.toString().substring(i * length, (i + 1) * length)).append(LINE_SEPARATOR);
//                } else {
//                    sb.append(LEFT_BORDER).append(result.toString().substring(i * length)).append(LINE_SEPARATOR).append(BOTTOM_BORDER);
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //body = formatXml(body);
        printLog(type, tag, stb.toString());
        return sb.toString();
    }

    private static final int MAX_LEN = 3500;

    private static void printLog(int type, String tag, String msg) {

        int len = msg.length();
//        int maxCount = len / MAX_LEN;
        if (len > MAX_LEN) {
            String sub;
            int lastLine = -1;
            int start = 0;
            for (int i = 0, maxCount = 1; i < maxCount; i++) {
                sub = LEFT_BORDER + msg.substring(start, start + MAX_LEN);
                lastLine = sub.lastIndexOf(LINE_SEPARATOR);
                lastLine = lastLine == -1 ? start + MAX_LEN : lastLine;
                sub = (i == 0 ? "" : LEFT_BORDER) + msg.substring(i == 0 ? start : start + 1, start + lastLine - 1);
                if (len - (start + lastLine - 2) > MAX_LEN) {
                    maxCount++;
                }
                start += lastLine;
                print(type, tag, sub);
            }
            print(type, tag, LEFT_BORDER + msg.substring(start + 1, len));
        } else {
            print(type, tag, msg);
        }
    }

    public static int indexOf(String srcText, String findText) {
        int count = 0;
        int index = 0;
        while ((index = srcText.indexOf(findText, index)) != -1) {
            index = index + findText.length();
            count++;
        }
        //Log.e("TAG", count + "");
        return count;
    }

    private static final int V = 0x01;
    private static final int D = 0x02;
    private static final int I = 0x04;
    private static final int W = 0x08;
    private static final int E = 0x10;
    private static final int A = 0x20;
    private static final int P = 0x30;

    private static void print(final int type, final String tag, String msg) {
        switch (type) {
            case V:
                Log.v(tag, msg);
                break;
            case D:
                Log.d(tag, msg);
                break;
            case I:
                Log.i(tag, msg);
                break;
            case W:
                Log.w(tag, msg);
                break;
            case E:
                Log.e(tag, msg);
                break;
            case A:
                Log.wtf(tag, msg);
                break;
            case P://打印
                break;
        }
    }

    private static String formatJson(String json) {
        try {
            if (json.startsWith("{")) {
                json = new JSONObject(json).toString(4);
            } else if (json.startsWith("[")) {
                json = new JSONArray(json).toString(4);
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    private static String formatXml(String xml) {
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(xmlInput, xmlOutput);
            xml = xmlOutput.getWriter().toString().replaceFirst(">", ">" + LINE_SEPARATOR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }

}
