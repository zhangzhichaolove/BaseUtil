package com.chao.baselib.cache;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.chao.baselib.util.PathUtil;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片加载工具类
 */
public class ImgLoaderUtil {

    private String path = PathUtil.getPackagePath() + "imageCache" + File.separator;
    private ImageManagerUtils manager = new ImageManagerUtils(path);
    public ExecutorService threadPool = Executors.newFixedThreadPool(5);
    private static volatile ImgLoaderUtil imgLoader = null;
    private Handler handler = new Handler();

    private ImgLoaderUtil() {
    }

    public static ImgLoaderUtil getLoader() {
        if (imgLoader == null) {
            synchronized (ImgLoaderUtil.class) {
                if (imgLoader == null) {
                    imgLoader = new ImgLoaderUtil();
                }
            }
        }
        return imgLoader;
    }

    public void loadImgBitmap(final String url,
                              final ImageView imageView) {
        // 先从缓存中读取图片资源
        try {
            final Bitmap bitmap = manager.getImgFromCache(url);
            if (null == bitmap) {//无缓存
                // 开启线程从网络上下载
                threadPool.submit(new Runnable() {
                    // submit方法确保下载是从线程池中的线程执行
                    @Override
                    public void run() {
                        final Bitmap bitmapFromUrl = manager.getBitMapFromUrl(url);//从网络下载
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (bitmapFromUrl != null) {
                                    imageView.setImageBitmap(bitmapFromUrl);
                                }
                            }
                        });
                    }
                });
            } else {//有缓存
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadImgBitmap(final String url,
                              final ImageView imageView, @DrawableRes final int loadImage) {
        // 先从缓存中读取图片资源
        try {
            final Bitmap bitmap = manager.getImgFromCache(url);
            if (null == bitmap) {//无缓存
                // 开启线程从网络上下载
                threadPool.submit(new Runnable() {
                    // submit方法确保下载是从线程池中的线程执行
                    @Override
                    public void run() {
                        final Bitmap bitmapFromUrl = manager.getBitMapFromUrl(url);//从网络下载
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (bitmapFromUrl != null) {
                                    imageView.setImageBitmap(bitmapFromUrl);
                                } else {
                                    imageView.setImageResource(loadImage);
                                }
                            }
                        });
                    }
                });
            } else {//有缓存
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public synchronized void loadImg(final String url,
                                     final ImgCallback callback) {
        // 先从缓存中读取图片资源
        try {
            final Bitmap bitmap = manager.getImgFromCache(url);
            if (null == bitmap) {//无缓存
                // 开启线程从网络上下载
                threadPool.submit(new Runnable() {
                    // submit方法确保下载是从线程池中的线程执行
                    @Override
                    public void run() {
                        final Bitmap bitmapFromUrl = manager.getBitMapFromUrl(url);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null && bitmapFromUrl != null) {
                                    callback.refresh(bitmapFromUrl);
                                }
                            }
                        });
                    }
                });
            } else {//有缓存
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.refresh(bitmap);
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface ImgCallback {
        void refresh(Bitmap bitmap);
    }

// public void removeImgFromCatche(String url) {
//  if (imgCache.containsKey(base64String(url))) {
//   Bitmap imageBit = imgCache.get(
//     Base64.encodeToString(url.getBytes(), Base64.DEFAULT))
//     .get();
//   if (imageBit != null && !imageBit.isRecycled()) {
//    imageBit.recycle();
//    imgCache.remove(base64String(url));
//    imageBit = null;
//   }
//  }
// }


}