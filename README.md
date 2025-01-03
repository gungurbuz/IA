# IB CS IA

![GitHub License](https://img.shields.io/github/license/gungurbuz/IA?style=for-the-badge)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/gungurbuz/IA?style=for-the-badge)
![GitHub Downloads (all assets, all releases)](https://img.shields.io/github/downloads/gungurbuz/IA/total?style=for-the-badge)
![GitHub last commit](https://img.shields.io/github/last-commit/gungurbuz/IA?display_timestamp=author&style=for-the-badge)
![GitHub Release](https://img.shields.io/github/v/release/gungurbuz/IA?include_prereleases&sort=date&display_name=release&style=for-the-badge)

## Overview

This repository contains the code for my Computer Science Internal Assessment, which is a library catalogue system with a variety of functions described below
## Table of Contents

1. [Features](#features)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Contributing](#contributing)
5. [License](#license)

## Features

- [x] Database Connection
- [x] GUI Interface
- [x] User Accounts
   - [x] Account Authentication
   - [x] Account Switching
   - [x] Password Hashing
   - [ ] Changing User Info/Password
- [x] Library Functionality
   - [x] Searching
      - [x] Partial Searches
      - [ ] Autocorrect
   - [x] Loaning/Returning
      - [x] Loan Status Tracking
      - [ ] Overdue Reminders
   - [x] Stock Operations
     - [x] Adding Books with:
       - [x] Multiple Languages
       - [x] Multiple Authors
       - [x] Identical Information
       - [x] No ISBN
       - [x] Outdated 10-digit ISBN

## Known Problems

- The native Windows console is not supported

## Installation

### Prerequisites

- Java Development Kit (JDK) 21 or higher
- Apache Maven
- Oracle MySQL
- Unix or Unix-like system

### Steps

1. Clone the repository:

    ```sh
    git clone https://github.com/gungurbuz/IA
    ```

2. Navigate to the project directory:

    ```sh
    cd IA
    ```

3. Compile the project using Maven:

    ```sh
    mvn compile -Dexec.mainClass=applicationlayer.App
    ```

4. Run the application:

    ```sh
    mvn exec:java -Dexec.mainClass="applicationlayer.App"
    ```

## Usage

1. Run the `App` class using the provided Maven command.
2. You will be presented with a login, signup, or exit option.
3. After logging in or signing up, you can search for books, add new books, take out loans, return loaned books, or check loan status.


## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a Pull Request.

## License

This project is licensed under the GPL-3.0 License. See the LICENSE file for details.
