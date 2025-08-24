package com.ias.dpat;

import com.github.mauricioaniche.ck.CK;
import com.github.mauricioaniche.ck.CKClassResult;
import com.github.mauricioaniche.ck.CKNotifier;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MetricsAnalyzer {
    
    private static List<CKClassResult> results = new ArrayList<>();
    
    public static void main(String[] args) {
        // Configuration
        String projectPath = "src/main/java";
        boolean useJars = false;
        int maxAtOnce = 0;
        boolean variablesAndFields = false;
        
        // Create CK instance
        CK ck = new CK(useJars, maxAtOnce, variablesAndFields);
        
        // Simple notifier that collects results
        CKNotifier notifier = new CKNotifier() {
            @Override
            public void notify(CKClassResult result) {
                results.add(result);
                System.out.println("Analyzed: " + result.getClassName());
            }
            
            @Override
            public void notifyError(String sourceFilePath, Exception e) {
                System.err.println("Error processing: " + sourceFilePath + " - " + e.getMessage());
            }
        };
        
        System.out.println("Starting analysis...");
        ck.calculate(projectPath, notifier);
        
        // Print summary
        System.out.println("\n=== ANALYSIS SUMMARY ===");
        System.out.println("Total classes analyzed: " + results.size());
        
        // Print detailed results
        System.out.println("\n=== DETAILED RESULTS ===");
        for (CKClassResult result : results) {
            System.out.println("\nClass: " + result.getClassName());
            System.out.println("  LOC: " + result.getLoc());
            System.out.println("  WMC: " + result.getWmc());
            System.out.println("  CBO: " + result.getCbo());
            System.out.println("  RFC: " + result.getRfc());
            System.out.println("  LCOM: " + result.getLcom());
            System.out.println("  DIT: " + result.getDit());
            System.out.println("  NOC: " + result.getNoc());
        }
        
        // Save to CSV
        saveToCSV();
        
        System.out.println("\nAnalysis complete! Check 'simple_metrics.csv' for results.");
    }
    
    private static void saveToCSV() {
        String filename = "class_metrics.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Header
            writer.println("ClassName,LOC,WMC,CBO,RFC,LCOM,DIT,NOC");
            
            // Data
            for (CKClassResult result : results) {
                if (result.getClassName().contains("Metrics")) continue;
                writer.printf("%s,%d,%d,%d,%d,%d,%d,%d%n",
                    result.getClassName(),
                    result.getLoc(),
                    result.getWmc(),
                    result.getCbo(),
                    result.getRfc(),
                    result.getLcom(),
                    result.getDit(),
                    result.getNoc()
                );
            }
            
            System.out.println("CSV file saved as `" + filename + "'");
            
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }
}