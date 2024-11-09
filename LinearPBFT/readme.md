# Server Configurations

## Overview
This document outlines the configurations for the servers and the View Server responsible for handling transactions.

## Server Details
The following servers have been considered:

| Server Name          | Port Number |
|----------------------|-------------|
| S1                   | 8001        |
| S2                   | 8002        |
| S3                   | 8003        |
| S4                   | 8004        |
| S5                   | 8005        |
| S6                   | 8006        |
| S7                   | 8007        |
| Client / View Server | 8000        |


## View Server
This is actually not a View Server by definition. This is just a bridge between Input File and servers.

Like an Input Controller Client. This represents a Client that sends transactions to all the servers.

## Commands

| Command Names | Command Details                                            |
|---------------|------------------------------------------------------------|
| PrintDB       | Prints Database state & balances                           |
| PrintLog      | Prints all Transaction Logs (all request related metadata) |
| PrintStatus   | Prints All Transaction SeqNum statuses across servers      |
| PrintView     | Prints New View Messages                                   |



## Execution Steps

1. Start the servers S1, S2, S3, S4, S5, S6, S7 and the View Server.
2. The View Server will read the input file and setup the input test sets.
3. The View Server terminal will prompt us to press 'Enter' to start the transactions.
4. Press 'Enter' to start the transactions of that set.
5. Each time we get to the next Set, we also run Commands: PrintDB and PrintStatus
6. Once all the test sets are completed, it will automatically trigger all commands.
7. Logs/ Log-ServerPortNumber-Commands.txt will be generated for each server with all the outputs of commands executed.
8. Logs/ Log-ServerPortNumber-PrintLogs.txt has all common logs - all logs.
9. Logs/ Log-ServerPortNumber.txt has all the logs of the server. Consensus Phases, Transaction executions and other important logs.
