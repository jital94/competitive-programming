package com.jp.practice.leetcode.daily;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

class MatrixRankTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.Test
    void matrixRankTransform() throws IOException {
        InputStream in = this.getClass().getResourceAsStream("/test-matrix-rank.txt");
        Path tempFile =
                Files.createTempDirectory("").resolve(UUID.randomUUID().toString() + ".tmp");
        Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
        String result = new String(Files.readAllBytes(tempFile));
        result = result.replace("[", "");//replacing all [ to ""
        result = result.substring(0, result.length() - 2);//ignoring last two ]]
        String s1[] = result.split("],");//separating all by "],"
        int[][] mat = new int[s1.length][0];

        for (int i = 0; i < s1.length; i++) {
            s1[i] = s1[i].trim();//ignoring all extra space if the string s1[i] has
            String single_int[] = s1[i].split(",");//separating integers by ", "
            mat[i] = new int[single_int.length];

            for (int j = 0; j < single_int.length; j++) {
                mat[i][j] = Integer.parseInt(single_int[j]);//adding single values
            }
        }
        System.out.println(mat.length + "|" + mat[0].length);
        MatrixRank matrixRank = new MatrixRank();
        matrixRank.matrixRankTransform(mat);
    }
}