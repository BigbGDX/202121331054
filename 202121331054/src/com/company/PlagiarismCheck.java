package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PlagiarismCheck {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("请输入正确的命令行参数：原文文件路径、抄袭版论文文件路径、输出文件路径");
            return;
        }

        String originalFilePath = args[0];
        String copiedFilePath = args[1];
        String outputFilePath = args[2];

        try {
            String originalText = readTextFromFile(originalFilePath);
            String copiedText = readTextFromFile(copiedFilePath);

            double similarity = calculateSimilarity(originalText, copiedText);

            writeResultToFile(outputFilePath, similarity);
        } catch (IOException e) {
            System.out.println("发生错误：" + e.getMessage());
        }
    }

    private static String readTextFromFile(String filePath) throws IOException {
        StringBuilder text = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;
        while ((line = reader.readLine()) != null) {
            text.append(line);
        }

        reader.close();

        return text.toString();
    }

    private static double calculateSimilarity(String originalText, String copiedText) {
        String[] originalWords = originalText.split("\\s+");
        String[] copiedWords = copiedText.split("\\s+");

        int commonWords = 0;
        for (String word : copiedWords) {
            for (String originalWord : originalWords) {
                if (word.equals(originalWord)) {
                    commonWords++;
                    break;
                }
            }
        }

        return (double) commonWords / originalWords.length * 100;
    }

    private static void writeResultToFile(String filePath, double similarity) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.write(String.format("重复率：%.2f%%", similarity));
        writer.close();
    }
}
