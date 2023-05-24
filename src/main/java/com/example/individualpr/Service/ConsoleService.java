package com.example.individualpr.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ConsoleService {
//использования команд в для дампа и скачивания в бд
    @Deprecated
    public static void exec(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec("cmd");
        OutputStream stdin = process.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
        writer.write(command);
        writer.newLine();
        writer.flush();
        writer.close();
        process.waitFor();
    }

}