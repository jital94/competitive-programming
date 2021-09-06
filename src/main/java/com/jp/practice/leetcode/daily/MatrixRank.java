package com.jp.practice.leetcode.daily;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class MatrixRank {
    public int[][] matrixRankTransform(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[] columnRanks = new int[n];
        int[] rowRanks = new int[m];

        PriorityQueue<Holder> queue = new PriorityQueue<Holder>(Comparator.comparingInt(c -> c.val));
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                queue.add(new Holder(matrix[i][j], i, j));
            }
        }
        int[][] result = new int[m][n];
        while (queue.size() > 0) {
            Holder e = queue.poll();
            List<Holder> sameElems = new ArrayList();
            sameElems.add(e);

            while (queue.size() > 0 && queue.peek().val == e.val) {
                sameElems.add(queue.poll());
            }

            // Create sets Disjoint set;


            // Optimise this
            // create sets
            // Create sets for this based on i and j
            Map<Integer, List<Holder>> map = createSets(sameElems);

            for (Integer k : map.keySet()) {
                int maxRank = -1;
                for (Holder elem : map.get(k)) {
                    maxRank = Math.max(Math.max(rowRanks[elem.i], columnRanks[elem.j]), maxRank);
                }

                int newRank = maxRank + 1;

                for (Holder elem : map.get(k)) {
                    result[elem.i][elem.j] = newRank;
                    rowRanks[elem.i] = newRank;
                    columnRanks[elem.j] = newRank;
                }

            }
        }
        return result;
    }

    public Map<Integer, List<Holder>> createSets(List<Holder> sameElems) {
        int[] parents = new int[sameElems.size()];
        for (int i = 0; i < sameElems.size(); i++) {
            parents[i] = -1;
        }
        if (sameElems.size() > 10) {
            System.out.println(sameElems.size());
        }
        int count = 0;
        for (int i = 0; i < sameElems.size(); i++) {
            for (int j = i + 1; j < sameElems.size(); j++) {
                if (sameElems.get(i).i == sameElems.get(j).i || sameElems.get(i).j == sameElems.get(j).j) {
                    int start = i;
                    while (parents[start] != -1) {
                        start = parents[start];
                    }
                    int end = j;
                    while (parents[end] != -1) {
                        end = parents[end];
                    }
                    if (end == start) {
                        continue;
                    }
                    parents[start] = j;
                    count++;
                    if (count >= sameElems.size() - 1) {
                        System.out.println("In break");
                        break;
                    }
                }
            }
            if (count >= sameElems.size() - 1) {
                break;
            }
        }
        Map<Integer, List<Holder>> map = new HashMap();

        for (int i = 0; i < sameElems.size(); i++) {
            int start = i;
            while (parents[start] != -1) {
                start = parents[start];
            }
            int groupCount = start;
            start = i;
            while (parents[start] != -1) {
                int oldStart = start;
                start = parents[start];
                parents[oldStart] = groupCount;
            }
            if (!map.containsKey(start)) {
                map.put(groupCount, new ArrayList());
            }
            map.get(groupCount).add(sameElems.get(i));
        }
        return map;
    }

    class Holder {
        int val;
        int i;
        int j;

        Holder(int val, int i, int j) {
            this.val = val;
            this.i = i;
            this.j = j;
        }
    }
}
