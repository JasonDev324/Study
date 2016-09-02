package io.tanjundang.study.common.model;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/8/5
 * BaseModel用于缓存数据 - 当应用被卸载的时候清除数据
 * 数据保存在data/data/package/cache/FOLDER/
 */
public abstract class BaseModel {
    private static final String FOLDER = "BUFFER";

    static class DataCache<T> {

        /**
         * 写数据要提供文件的路径、文件名、数据
         *
         * @param context  上下文
         * @param folder   目录 需要判断是否为空
         * @param filename 文件名
         * @param data     数据
         */
        private void save(Context context, String folder, String filename, Object data) {
            if (context == null) {
                return;
            }
            if (folder == null || folder.equals("")) {
                folder = FOLDER;
            }

            File filedir = new File(context.getCacheDir().getPath(), folder);
            if (!filedir.exists() || !filedir.isDirectory()) {
                filedir.mkdirs();
            }
            File file = new File(filedir, filename);

            if (file.exists()) { //如果文件存在,则删除原有文件
                file.delete();
            }

            //如果文件不存在,输出流会自动创建文件,所以不需要createNewFile
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(data);
                oos.flush();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        /**
         * 保存对象,默认存在data/data/package/BUFFER/目录下
         *
         * @param context
         * @param filename
         * @param data
         */
        public void saveObject(Context context, String filename, Object data) {
            save(context, FOLDER, filename, data);
        }

        /**
         * 保存集合,默认存在data/data/package/BUFFER/目录下
         *
         * @param context
         * @param filename
         * @param data
         */
        public void saveList(Context context, String filename, ArrayList<T> data) {
            save(context, FOLDER, filename, data);
        }

        /**
         * @param context  用于获取路径
         * @param filename 获取文件名
         * @return
         */
        public T loadObject(Context context, String filename) {
            T data = null;
            //先找到目录,默认是data/data/package/buffer/
            File filedir = new File(context.getCacheDir().getPath(), FOLDER);
            if (!filedir.exists() || !filedir.isDirectory()) {
                filedir.mkdirs();
            }

            File file = new File(filedir, filename);
            if (file.exists() && !file.isDirectory()) { //需要判断文件是否存在
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                    data = (T) ois.readObject();
                    ois.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return data;
        }

        /**
         * @param context  用于获取路径
         * @param filename 获取文件名
         * @return
         */
        public ArrayList<T> loadList(Context context, String filename) {
            ArrayList<T> data = null;

            //找到目录
            File filedir = new File(context.getCacheDir().getPath(), FOLDER);
            if (!filedir.exists() || !filedir.isDirectory()) {
                filedir.mkdirs();
            }

            File file = new File(filedir, filename);

            if (file.exists()) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                    data = (ArrayList<T>) ois.readObject();
                    ois.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (data == null) { //防止奔溃
                data = new ArrayList<>();
            }

            return data;

        }
    }
}
