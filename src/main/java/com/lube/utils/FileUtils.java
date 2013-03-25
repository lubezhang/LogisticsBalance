package com.lube.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
    public static void mkdirs(String dir) {
        File dirFile = new File(dir);

        if (!dirFile.exists() || !dirFile.isDirectory()) {
            dirFile.mkdirs();
        }
    }

    public static void copy(String src, String dist) throws IOException {
        File fileSrc = new File(src);
        if (!fileSrc.exists()) {
            return;
        }
        if (fileSrc.isDirectory()) {
            File fileDist = new File(dist);
            if (!fileDist.exists() || !fileDist.isDirectory()) {
                fileDist.mkdirs();
            }
            String[] subs = fileSrc.list();
            if (subs != null) {
                for (int i = 0; i < subs.length; i++) {
                    copy(src + File.separator + subs[i], dist + File.separator + subs[i]);
                }
            }
        } else {
            byte[] buffer = new byte[(int) (fileSrc).length()];
            InputStream in = new BufferedInputStream(new FileInputStream(src));
            OutputStream out = new BufferedOutputStream(new FileOutputStream(dist));
            in.read(buffer);
            out.write(buffer);
            in.close();
            out.close();
        }
    }

    /**
     * 作用：配合传输多个文件
     * 添加方法：copyModify
     * 参数：src,dir
     * 时间：20080604 LiuQA
     */
    public static void copyModify(String src, String dir) throws IOException {
        File fileSrc = new File(src);
        if (!fileSrc.exists()) {
            return;
        }

        if (fileSrc.isDirectory()) {
            File fileDist = new File(dir);
            if (!fileDist.exists() || !fileDist.isDirectory()) {
                fileDist.mkdirs();
            }

            String[] subs = fileSrc.list();
            if (subs != null) {
                for (int i = 0; i < subs.length; i++) {
                    copyModify(src + File.separator + subs[i], dir + File.separator + subs[i]);
                }
            }
        } else {
            byte[] buffer = new byte[(int) (fileSrc).length()];
            InputStream in = new BufferedInputStream(new FileInputStream(src));
            OutputStream out = new BufferedOutputStream(new FileOutputStream(dir));
            in.read(buffer);
            out.write(buffer);
            in.close();
            out.close();
        }
    }

    public static void remove(String filename) {
        remove(new File(filename));
    }

    public static void remove(File file) {
        if (!file.exists())
            return;
        File[] subs = file.listFiles();
        if (subs != null) {
            for (int i = 0; i < subs.length; i++) {
                remove(subs[i]);
            }
        }

        file.delete();
    }

    /**
     * 将文件从源路径移动到目的路径中
     * @param src 源路径
     * @param dist 目的路径
     * @return
     */
    public static boolean move(String src, String dist) {
        File srcFile = new File(src);
        File distFile = new File(dist);

        if (distFile.exists()) {
            distFile.delete();
        }

        File parent = distFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            return false;
        }

        return srcFile.renameTo(distFile);
    }

    public static String parseExtension(String fileName) {
        int lastIndexOfSeparator = fileName.lastIndexOf(".");
        return fileName.substring(lastIndexOfSeparator + 1);
    }

    public static void main(String[] args) throws Exception {
        File f = new File("c:\\tmp\\abc\\a.txt");
        System.out.println(f.getParentFile().getAbsolutePath());
    }
}
