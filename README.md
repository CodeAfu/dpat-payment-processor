# Payment System

## Setup

Install maven using your preferred package manager

```bash
# Mac
brew install maven
sdk install maven
sudo port install maven3

# Linux
sudo apt install maven
sudo dnf install maven
sudo yum install maven

# Windows
choco install maven
scoop install main/maven
```


Run:

```bash
mvn exec:java
# OR
mvn exec:java "-Dexec.mainClass=com.ias.dpat.App"
```

Flexibility Metrics:

```bash
mvn compile exec:java "-Dexec.mainClass=com.ias.dpat.MetricsAnalyzer"
```