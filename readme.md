# DPI-Based Network Packet Analyzer (Java)

A multithreaded Deep Packet Inspection (DPI) engine built in Java that processes PCAP files, classifies network traffic, detects application-level behavior (e.g., YouTube), and applies blocking rules.

---

## Project Overview

This project simulates a simplified **Deep Packet Inspection (DPI) system** used in real-world firewalls and network monitoring tools.

It:
- Reads raw network packets from a `.pcap` file
- Parses packet headers (IP, TCP/UDP)
- Identifies application traffic (HTTP, HTTPS, YouTube)
- Tracks flows using a 5-tuple
- Applies blocking rules (e.g., block YouTube traffic)
- Writes filtered packets to output

---

## Key Concepts Used

- Deep Packet Inspection (DPI)
- Multithreading (Producer–Consumer Pipeline)
- Networking (IP, TCP/UDP, Ports)
- Flow tracking (5-Tuple)
- HashMap-based flow management
- PCAP file parsing
- Rule-based filtering

---

## Architecture

The system uses a **3-stage pipeline architecture**:

Reader Thread → Processor Thread → Writer Thread

### 1️⃣ Reader Thread
- Reads packets from `.pcap` file
- Pushes packets into input queue

### 2️⃣ Processor Thread
- Parses packets
- Classifies traffic (HTTP/HTTPS/YouTube)
- Tracks flows
- Applies blocking rules

### 3️⃣ Writer Thread
- Writes allowed packets to output `.pcap`

---

## 📂 Project Structure
packet_analyzer_java/
│
├── src/
│ ├── app/
│ │ └── Main.java
│ │
│ ├── engine/
│ │ ├── DPIProcessor.java
│ │ └── RuleManager.java
│ │
│ ├── model/
│ │ ├── RawPacket.java
│ │ ├── ParsedPacket.java
│ │ ├── FiveTuple.java
│ │ ├── Flow.java
│ │ └── AppType.java
│ │
│ ├── parser/
│ │ ├── PcapReader.java
│ │ ├── PcapWriter.java
│ │ └── PacketParser.java
│ │
│ └── pipeline/
│ ├── ReaderThread.java
│ ├── ProcessorThread.java
│ └── WriterThread.java
│
├── input/
│ └── test_dpi.pcap
│
├── output/
│ └── output.pcap
│
└── README.md


---

## Features

-  Multithreaded packet processing
-  PCAP file parsing (no external libraries)
-  HTTP / HTTPS detection
-  YouTube traffic detection (Demo mode)
-  Flow tracking using 5-tuple
-  Rule-based blocking system
-  Output PCAP generation

---

## How Detection Works

### Current Logic (Demo Mode)


Port 443 → HTTPS → classified as YouTube

Note:

This is a simplified approach. Real DPI systems use:
- TLS SNI inspection
- Payload inspection
- ML-based classification

---

Go to project root:

Step 1: Compile Project
javac -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })

Step 2: Run Program
java -cp out app.Main input\test_dpi_old.pcap output\output.pcap
                                   or
java -cp out app.Main input\test_dpi_clean.pcap output\output.pcap                                
                                   or 
java -cp out app.Main input\test_dpi_clean2.pcap output\output.pcap   
