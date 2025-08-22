package com.ias.dpat;

import com.github.mauricioaniche.ck.CK;
import com.github.mauricioaniche.ck.CKClassResult;
import com.github.mauricioaniche.ck.CKNotifier;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DebugMetricsAnalyzer {
    
    public static void main(String[] args) {
        System.out.println("=== DEBUG METRICS ANALYZER ===");
        
        // Configuration
        String projectPath = "src/main/java";
        boolean useJars = false;
        int maxAtOnce = 0;
        boolean variablesAndFields = false;
        
        // Debug: Check if directory exists and list Java files
        System.out.println("1. Checking project directory...");
        File projectDir = new File(projectPath);
        System.out.println("   Project path: " + projectDir.getAbsolutePath());
        System.out.println("   Directory exists: " + projectDir.exists());
        System.out.println("   Is directory: " + projectDir.isDirectory());
        
        if (projectDir.exists() && projectDir.isDirectory()) {
            System.out.println("   Directory contents:");
            listJavaFiles(projectDir, 0);
        }
        
        // Count Java files
        System.out.println("\n2. Counting Java files...");
        try {
            Path startPath = Paths.get(projectPath);
            if (Files.exists(startPath)) {
                long javaFileCount = Files.walk(startPath)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .peek(path -> System.out.println("   Found: " + path))
                    .count();
                System.out.println("   Total Java files found: " + javaFileCount);
            } else {
                System.out.println("   Path does not exist!");
            }
        } catch (Exception e) {
            System.out.println("   Error walking directory: " + e.getMessage());
        }
        
        // Try alternative paths
        System.out.println("\n3. Trying alternative paths...");
        String[] alternativePaths = {
            "src/main/java",
            "./src/main/java",
            "src",
            "."
        };
        
        for (String altPath : alternativePaths) {
            File altDir = new File(altPath);
            System.out.println("   " + altPath + " -> exists: " + altDir.exists() + 
                             ", is directory: " + altDir.isDirectory());
        }
        
        // Now try CK analysis
        System.out.println("\n4. Running CK analysis...");
        CK ck = new CK(useJars, maxAtOnce, variablesAndFields);
        
        // Counter for results
        final int[] resultCount = {0};
        
        CKNotifier notifier = new CKNotifier() {
            @Override
            public void notify(CKClassResult result) {
                resultCount[0]++;
                System.out.println("   [" + resultCount[0] + "] Analyzed: " + result.getClassName());
                System.out.println("       File: " + result.getFile());
                System.out.println("       LOC: " + result.getLoc());
                System.out.println("       WMC: " + result.getWmc());
                System.out.println("       CBO: " + result.getCbo());
                System.out.println();
            }
            
            @Override
            public void notifyError(String sourceFilePath, Exception e) {
                System.err.println("   ERROR processing: " + sourceFilePath);
                System.err.println("   Exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                e.printStackTrace();
            }
        };
        
        System.out.println("   Starting CK calculation on: " + projectPath);
        try {
            ck.calculate(projectPath, notifier);
            System.out.println("   CK calculation completed.");
            System.out.println("   Total results processed: " + resultCount[0]);
        } catch (Exception e) {
            System.err.println("   Exception during CK calculation:");
            e.printStackTrace();
        }
        
        System.out.println("\n=== DEBUG COMPLETE ===");
    }
    
    private static void listJavaFiles(File dir, int depth) {
        if (depth > 3) return; // Limit recursion depth
        
        String indent = "   " + "  ".repeat(depth);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println(indent + "[DIR] " + file.getName() + "/");
                    listJavaFiles(file, depth + 1);
                } else if (file.getName().endsWith(".java")) {
                    System.out.println(indent + "[JAVA] " + file.getName());
                }
            }
        }
    }
}