package lol;
//Therese Julianne C. Villaro - ITCC 11.1 B

import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;



class FileNode {
    String name;
    boolean isDirectory;
    List<FileNode> children;

    FileNode(String name, boolean isDirectory) {
        this.name = name;
        this.isDirectory = isDirectory;
        this.children = new ArrayList<>();
    }

    void addChild(FileNode child) {
        if (this.isDirectory) {
            this.children.add(child);
        }
    }
}


interface FileFoundListener {
    void onFileFound(String filePath, BufferedWriter writer) throws IOException;
}


class FileSearcher {
    private FileFoundListener listener;

    FileSearcher(FileFoundListener listener) {
        this.listener = listener;
    }

    void searchFiles(FileNode node, String extension, String path, BufferedWriter writer) throws IOException {
        if (node.isDirectory) {
            for (FileNode child : node.children) {
                searchFiles(child, extension, path + "\\" + node.name, writer);
            }
        } else if (node.name.endsWith(extension)) {
            listener.onFileFound(path + "\\" + node.name, writer);
        }
    }
}

//Simulates a mock file system
class MockFileSystem {
    public static FileNode createMockFileSystem() {

        FileNode root = new FileNode("Documents", true);
        FileNode subfolder = new FileNode("subfolder", true);
        FileNode file1 = new FileNode("notes.txt", false);
        FileNode file2 = new FileNode("homework.docx", false);
        FileNode file3 = new FileNode("todo.txt", false);

 
        root.addChild(file1);
        root.addChild(file2);
        subfolder.addChild(file3);
        root.addChild(subfolder);

        return root;
    }
}


public class Villaro_LabActivity2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //for user input
        System.out.print("Enter the directory path: ");
        String dirPath = scanner.nextLine();
        System.out.print("Enter file extension to search for: ");
        String extension = scanner.nextLine();

        FileNode root = MockFileSystem.createMockFileSystem();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("search_results.txt"))) {
            FileSearcher searcher = new FileSearcher((filePath, fileWriter) -> {
                System.out.println("File found: " + filePath);
                fileWriter.write("File found: " + filePath + "\n");
            });

            System.out.println("\nSearching...");
            searcher.searchFiles(root, extension, dirPath, writer);
            System.out.println("\nSearch completed. Results saved to search_results.txt.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}