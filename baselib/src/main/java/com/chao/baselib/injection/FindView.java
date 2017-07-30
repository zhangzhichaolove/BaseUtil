package com.chao.baselib.injection;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 注解解析工具类
 *
 * @author Chao
 * @datetime: 2017年1月21日 下午5:52:12 Chao
 */
public class FindView {

    public static int OnClick = 0;
    public static int OnLongClick = 1;
    /**
     * 类的标识
     */
    private static final String TAG = "FindView";

    /**
     * 工具类，禁止new实例。
     */
    private FindView() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 对activity中的成员变量进行注入
     */
    public static void bind(Object context) {
        Activity at = null;
        if (context instanceof Activity) {
            at = (Activity) context;
        } else if (context instanceof Fragment) {
            Fragment fr = (Fragment) context;
            at = fr.getActivity();
        }
        // 获取这个activity中的所有成员变量
        Field[] fields = at.getClass().getDeclaredFields();

        for (Field field : fields) {
            // 获取该变量上面有没有打这个注解
            Id mId = field.getAnnotation(Id.class);
            if (mId != null) {// 有此注解
                int id = mId.value();// 获取注解值
                if (id != 0) {
                    //Log.e("TAG", "找到了该Id ：" + id);
                    // 反射执行
                    field.setAccessible(true);
                    Object view = null;
                    try {
                        view = at.findViewById(id);// 根据注解ID在Activity布局查找控件
                        // 设置字段的属性
                        field.set(at, view);// 在at中将field变量设置值view
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // 获取这个activity中的所有方法
        Method[] methods = at.getClass().getDeclaredMethods();
        for (Method method : methods) {
            // 获取该方法上面有没有打这个注解
            Click mClick = method.getAnnotation(Click.class);
            if (mClick != null) {// 有此注解
                int id = mClick.value();// 获取默认注解值ID
                int type = mClick.type();
                if (id != 0) {
                    //Log.e("TAG", "找到了该ID ：" + id);
                    // 反射执行
                    View view = at.findViewById(id);// 找到这个设置监听的View
                    if (type == OnClick) {// 选择了默认点击事件。
                        view.setOnClickListener(new ClickListener(at, method
                                .getName()));
                    } else if (type == OnLongClick) {// 长按事件。
                        view.setOnLongClickListener(new ClickListener(at,
                                method.getName()));
                    }
                }
            }
        }
    }

    /**
     * 对Fragment中的字段进行注入
     *
     * @param f
     * @param contentView
     */
    public static void bindView(Fragment f, View contentView) {
        // 获取这个activity中的所有成员变量
        Field[] fields = f.getClass().getDeclaredFields();

        for (Field field : fields) {
            // 获取该变量上面有没有打这个注解
            Id mId = field.getAnnotation(Id.class);
            if (mId != null) {// 有此注解
                int id = mId.value();// 获取注解值
                if (id != 0) {
                    //Log.e("TAG", "找到了该Id ：" + id);
                    // 反射执行
                    field.setAccessible(true);
                    Object view = null;
                    try {
                        view = contentView.findViewById(id);// 根据注解ID在Activity布局查找控件
                        // 设置字段的属性
                        field.set(f, view);// 在at中将field变量设置值view
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // 获取这个activity中的所有方法
        Method[] methods = f.getClass().getDeclaredMethods();
        for (Method method : methods) {
            // 获取该方法上面有没有打这个注解
            Click mClick = method.getAnnotation(Click.class);
            if (mClick != null) {// 有此注解
                int id = mClick.value();// 获取默认注解值ID
                int type = mClick.type();
                if (id != 0) {
                    //Log.e("TAG", "找到了该ID ：" + id);
                    // 反射执行
                    View view = contentView.findViewById(id);// 找到这个设置监听的View
                    if (type == OnClick) {// 选择了默认点击事件。
                        view.setOnClickListener(new ClickListener(f, method
                                .getName()));
                    } else if (type == OnLongClick) {// 长按事件。
                        view.setOnLongClickListener(new ClickListener(f, method
                                .getName()));
                    }
                }
            }
        }
    }

    public static void unBind() {

    }
}
