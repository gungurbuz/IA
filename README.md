# IB CS IA

## Overview

This repository contains the code for my Computer Science Internal Assessment, which is a library catalogue system with a variety of functions described below

## Features

- [x] Database Connection
- [ ] User Accounts
    - [x] Account Authentication
    - [x] Account Switching
    - [x] Password Hashing
    - [ ] Changing User Info/Password
- [x] Command Line Input Masking
- [ ] Library Functionality
    - [ ] Searching
        - [ ] Partial Searches
        - [ ] Autocorrect
    - [ ] Loaning/Returning
        - [ ] Loan Status Tracking
        - [ ] Overdue Reminders

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/gungurbuz/IA.git
    cd IA
    ```
2. Make sure you have Java and MySQL installed on your machine.
3. Add the MySQL JDBC driver to your project. You can download it from the [MySQL Connector/J page](https://dev.mysql.com/downloads/connector/j/).
4. Place the JDBC driver JAR file in your project's lib directory or add it to your classpath.

## Usage 

1. Compile the Java files:
```javac App.java Helper.java Password.java
```
2. Run the application:
```java App
```