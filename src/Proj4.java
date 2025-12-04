/*********************************************************************************
        * @file: Proj4.java
        * @description: This program reads a dataset from a CSV file, generates sorted, shuffled, and reversed
 *                      versions of the data, and measures the running time for insert, search, and delete operations
 *                      in a SeparateChainingHashTable.
        * @author: Aidan Broadhead
        * @date: December 4, 2025
 *********************************************************************************/

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj4 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        // FINISH ME

        // array list for the names of each song
        ArrayList<String> data = new ArrayList<>();

        int count = 0;

        // read and split each line by commas and then add the song name into the array list
        while (inputFileNameScanner.hasNextLine() && count < numLines) {
            String line = inputFileNameScanner.nextLine();

            String[] parts = line.split(",", -1);
            if (parts.length > 1) {
                String name = parts[1].trim();
                data.add(name);
                count++;
            }
        }

        // create sorted, shuffled, and reversed versions of the data
        ArrayList<String> sorted = new ArrayList<>(data);
        Collections.sort(sorted, String.CASE_INSENSITIVE_ORDER);

        ArrayList<String> shuffled = new ArrayList<>(data);
        Collections.shuffle(shuffled);

        ArrayList<String> reversed = new ArrayList<>(data);
        Collections.sort(reversed, String.CASE_INSENSITIVE_ORDER.reversed());

        // create doubles for timing results of each operation and input
        double sortedInsert, sortedSearch, sortedDelete;
        double shuffledInsert, shuffledSearch, shuffledDelete;
        double reversedInsert, reversedSearch, reversedDelete;

        // time the insert, search, and delete operations for the sorted data
        SeparateChainingHashTable<String> table = new SeparateChainingHashTable<>();

        long t1 = System.nanoTime();
        for (String s : sorted) table.insert(s);
        long t2 = System.nanoTime();
        sortedInsert = (t2 - t1) / 1000000000.0;

        t1 = System.nanoTime();
        for (String s : sorted) table.contains(s);
        t2 = System.nanoTime();
        sortedSearch = (t2 - t1) / 1000000000.0;

        t1 = System.nanoTime();
        for (String s : sorted) table.remove(s);
        t2 = System.nanoTime();
        sortedDelete = (t2 - t1) / 1000000000.0;

        // time the insert, search, and delete operations for the shuffled data
        table = new SeparateChainingHashTable<>();

        t1 = System.nanoTime();
        for (String s : shuffled) table.insert(s);
        t2 = System.nanoTime();
        shuffledInsert = (t2 - t1) / 1000000000.0;

        t1 = System.nanoTime();
        for (String s : shuffled) table.contains(s);
        t2 = System.nanoTime();
        shuffledSearch = (t2 - t1) / 1000000000.0;

        t1 = System.nanoTime();
        for (String s : shuffled) table.remove(s);
        t2 = System.nanoTime();
        shuffledDelete = (t2 - t1) / 1000000000.0;

        // time the insert, search, and delete operations for the reversed data
        table = new SeparateChainingHashTable<>();

        t1 = System.nanoTime();
        for (String s : reversed) table.insert(s);
        t2 = System.nanoTime();
        reversedInsert = (t2 - t1) / 1000000000.0;

        t1 = System.nanoTime();
        for (String s : reversed) table.contains(s);
        t2 = System.nanoTime();
        reversedSearch = (t2 - t1) / 1000000000.0;

        t1 = System.nanoTime();
        for (String s : reversed) table.remove(s);
        t2 = System.nanoTime();
        reversedDelete = (t2 - t1) / 1000000000.0;

        // print timing results for each dataset
        System.out.println("\nN = " + numLines + "\n");

        System.out.println("Already Sorted:");
        System.out.printf("  Insert: %.9f s%n", sortedInsert);
        System.out.printf("  Search: %.9f s%n", sortedSearch);
        System.out.printf("  Delete: %.9f s%n%n", sortedDelete);

        System.out.println("Shuffled:");
        System.out.printf("  Insert: %.9f s%n", shuffledInsert);
        System.out.printf("  Search: %.9f s%n", shuffledSearch);
        System.out.printf("  Delete: %.9f s%n%n", shuffledDelete);

        System.out.println("Reversed:");
        System.out.printf("  Insert: %.9f s%n", reversedInsert);
        System.out.printf("  Search: %.9f s%n", reversedSearch);
        System.out.printf("  Delete: %.9f s%n%n", reversedDelete);

        // record timing results to the analysis.txt file
        BufferedWriter bw = new BufferedWriter(new FileWriter("analysis.txt", true));
        bw.write(numLines + ",sorted," + sortedInsert + "," + sortedSearch + "," + sortedDelete);
        bw.newLine();
        bw.write(numLines + ",shuffled," + shuffledInsert + "," + shuffledSearch + "," + shuffledDelete);
        bw.newLine();
        bw.write(numLines + ",reversed," + reversedInsert + "," + reversedSearch + "," + reversedDelete);
        bw.newLine();
        bw.close();

    }
}
