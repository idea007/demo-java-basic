package com.dafay.demo.java.basic;

import com.dafay.demo.lib.base.utils.LogExtKt;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Des
 * @Author lipengfei
 * @Date 2024/4/7
 */
public class IOTest {


    @Test
    public void test_input() {
        String inputFile = "input.txt";
        try {
            // 创建输入流
            FileInputStream fileInputStream = new FileInputStream(inputFile);
            // 创建带有输入缓冲区的输入流,FilterInputStream 的子类，提高读取文件的性能，特别是在频繁读取小量数据时
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            // 读取输入流的内容并打印
            int byteData;
            while ((byteData = bufferedInputStream.read()) != -1) {
                System.out.println("bufferedInputStream read:" + (char) byteData);
            }
            // 关闭流
            bufferedInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test_output() {
        String inputFile = "output.txt";
        String content = "Hello,This is a paragraph of text";
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            // 创建文件输出流
            fileOutputStream = new FileOutputStream(inputFile);
            // 带有实现缓冲输出流。内部维护一个缓冲区，将数据先写入缓冲区，然后批量写入到输出流，减少对底层资源的频繁访问
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(content.getBytes());
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test_reader() {
        String inputFile = "input.txt";
        try {
            // 使用默认缓冲区大小从字符文件读取文本。从字节解码为字符使用指定的字符集或平台的默认字符集。 FileReader 用于读取字符流。要读取原始字节流，请考虑使用 FileInputStream
            FileReader fileReader = new FileReader(inputFile);
            // 从字符输入流读取文本，缓冲字符，以便高效读取字符、数组和行
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_data_output() {
        String fileName = "data_output.txt";
        try (DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            // 写入整数
            dataOutputStream.writeInt(123);
            // 写入浮点数
            dataOutputStream.writeDouble(3.14);
            // 写入布尔值
            dataOutputStream.writeBoolean(true);
            // 写入字符串
            dataOutputStream.writeUTF("Hello, DataOutputStream!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
