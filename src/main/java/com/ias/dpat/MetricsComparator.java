package com.ias.dpat;

import java.io.*;
import java.util.*;

public class MetricsComparator {
    
    static class ClassMetrics {
        String className;
        int loc, wmc, cbo, rfc, lcom, dit, noc;
        
        public ClassMetrics(String line) {
            String[] parts = line.split(",");
            className = parts[0];
            loc = Integer.parseInt(parts[1]);
            wmc = Integer.parseInt(parts[2]);
            cbo = Integer.parseInt(parts[3]);
            rfc = Integer.parseInt(parts[4]);
            lcom = Integer.parseInt(parts[5]);
            dit = Integer.parseInt(parts[6]);
            noc = Integer.parseInt(parts[7]);
        }
        
        public double getFlexibilityScore() {
            // Lower is better for most metrics
            // Score: 100 - penalties for poor metrics
            double score = 100.0;
            
            // Penalize high complexity
            if (wmc > 10) score -= (wmc - 10) * 5;
            else if (wmc > 5) score -= (wmc - 5) * 2;
            
            // Penalize high coupling
            if (cbo > 6) score -= (cbo - 6) * 8;
            else if (cbo > 3) score -= (cbo - 3) * 3;
            
            // Penalize poor cohesion
            if (lcom > 3) score -= lcom * 10;
            else if (lcom > 1) score -= lcom * 5;
            
            // Penalize high response set
            if (rfc > 15) score -= (rfc - 15) * 2;
            else if (rfc > 10) score -= (rfc - 10) * 1;
            
            return Math.max(0, score);
        }
        
        public String getFlexibilityGrade() {
            double score = getFlexibilityScore();
            if (score >= 90) return "A+ (Excellent)";
            if (score >= 80) return "A (Very Good)";
            if (score >= 70) return "B (Good)";
            if (score >= 60) return "C (Acceptable)";
            if (score >= 50) return "D (Needs Improvement)";
            return "F (Poor)";
        }
    }
    
    public static void main(String[] args) {
        String csvFile = "class_metrics.csv"; // Change to your CSV filename
        List<ClassMetrics> metrics = new ArrayList<>();
        
        // Read CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                metrics.add(new ClassMetrics(line));
            }
        } catch (IOException e) {
            System.out.println("Could not read " + csvFile + ". Make sure the file exists.");
            return;
        }
        
        System.out.println("=".repeat(80));
        System.out.println("                    CODE FLEXIBILITY ANALYSIS REPORT");
        System.out.println("=".repeat(80));
        
        // Overall statistics
        System.out.println("\n📊 OVERALL STATISTICS");
        System.out.println("-".repeat(50));
        System.out.printf("Total classes analyzed: %d%n", metrics.size());
        
        double avgComplexity = metrics.stream().mapToInt(m -> m.wmc).average().orElse(0);
        double avgCoupling = metrics.stream().mapToInt(m -> m.cbo).average().orElse(0);
        double avgCohesion = metrics.stream().mapToInt(m -> m.lcom).average().orElse(0);
        
        System.out.printf("Average complexity (WMC): %.1f%n", avgComplexity);
        System.out.printf("Average coupling (CBO): %.1f%n", avgCoupling);
        System.out.printf("Average cohesion (LCOM): %.1f%n", avgCohesion);
        
        // Top performers
        System.out.println("\n🏆 MOST FLEXIBLE CLASSES (Top 5)");
        System.out.println("-".repeat(50));
        metrics.sort((a, b) -> Double.compare(b.getFlexibilityScore(), a.getFlexibilityScore()));
        
        System.out.printf("%-40s | %-15s | Score%n", "Class", "Grade", "");
        System.out.println("-".repeat(70));
        
        for (int i = 0; i < Math.min(5, metrics.size()); i++) {
            ClassMetrics m = metrics.get(i);
            System.out.printf("%-40s | %-15s | %.1f%n", 
                shortenClassName(m.className), m.getFlexibilityGrade(), m.getFlexibilityScore());
        }
        
        // Bottom performers
        System.out.println("\n⚠️  LEAST FLEXIBLE CLASSES (Bottom 5)");
        System.out.println("-".repeat(50));
        
        System.out.printf("%-40s | %-15s | Score | Issues%n", "Class", "Grade", "");
        System.out.println("-".repeat(80));
        
        for (int i = metrics.size() - Math.min(5, metrics.size()); i < metrics.size(); i++) {
            ClassMetrics m = metrics.get(i);
            String issues = getIssues(m);
            System.out.printf("%-40s | %-15s | %.1f | %s%n", 
                shortenClassName(m.className), m.getFlexibilityGrade(), m.getFlexibilityScore(), issues);
        }
        
        // Pattern analysis
        System.out.println("\n🎯 DESIGN PATTERN ANALYSIS");
        System.out.println("-".repeat(50));
        
        analyzePatterns(metrics, "simple", "Simple/Naive Approach");
        analyzePatterns(metrics, "flexible", "Flexible/Pattern Approach");
        
        // Recommendations
        System.out.println("\n💡 RECOMMENDATIONS");
        System.out.println("-".repeat(50));
        
        for (ClassMetrics m : metrics) {
            if (m.getFlexibilityScore() < 60) {
                System.out.printf("🔧 %s: %s%n", shortenClassName(m.className), getRecommendations(m));
            }
        }
        
        System.out.println("\n" + "=".repeat(80));
    }
    
    private static void analyzePatterns(List<ClassMetrics> metrics, String pattern, String title) {
        List<ClassMetrics> patternClasses = metrics.stream()
            .filter(m -> m.className.contains(pattern))
            .toList();
            
        if (patternClasses.isEmpty()) return;
        
        double avgScore = patternClasses.stream()
            .mapToDouble(ClassMetrics::getFlexibilityScore)
            .average().orElse(0);
            
        System.out.printf("%s: %.1f avg score (%d classes)%n", 
            title, avgScore, patternClasses.size());
            
        for (ClassMetrics m : patternClasses) {
            System.out.printf("  • %s: %.1f%n", 
                shortenClassName(m.className), m.getFlexibilityScore());
        }
    }
    
    private static String shortenClassName(String className) {
        if (className.length() <= 40) return className;
        String[] parts = className.split("\\.");
        return "..." + parts[parts.length-1];
    }
    
    private static String getIssues(ClassMetrics m) {
        List<String> issues = new ArrayList<>();
        if (m.wmc > 10) issues.add("High complexity");
        if (m.cbo > 6) issues.add("High coupling");  
        if (m.lcom > 3) issues.add("Poor cohesion");
        if (m.rfc > 15) issues.add("High response");
        return String.join(", ", issues);
    }
    
    private static String getRecommendations(ClassMetrics m) {
        List<String> recommendations = new ArrayList<>();
        if (m.wmc > 10) recommendations.add("Split complex methods");
        if (m.cbo > 6) recommendations.add("Reduce dependencies");
        if (m.lcom > 3) recommendations.add("Improve cohesion - split class");
        if (m.rfc > 15) recommendations.add("Reduce method calls");
        if (recommendations.isEmpty()) recommendations.add("Consider refactoring");
        return String.join(", ", recommendations);
    }
}