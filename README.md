# IB CS IA


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
- [ ] GUI Interface **(IN PROGRESS)**
- [ ] User Accounts
   - [x] Account Authentication
   - [x] Account Switching
   - [x] Password Hashing
   - [ ] Changing User Info/Password
- [ ] Library Functionality
   - [ ] Searching
      - [ ] Partial Searches
      - [ ] Autocorrect
   - [ ] Loaning/Returning
      - [ ] Loan Status Tracking
      - [ ] Overdue Reminders
   - [ ] Stock Operations
     - [x] Adding Books with:
       - [x] Multiple Languages
       - [x] Multiple Authors
       - [x] Identical Information
       - [x] No ISBN
       - [x] Outdated 10-digit ISBN


## Installation

### Prerequisites

- Java Development Kit (JDK) 21 or higher
- Apache Maven

### Steps

1. Clone the repository:

    ```sh
    git clone -b mavenwithlanterna --single-branch https://github.com/gungurbuz/IA
    ```

2. Navigate to the project directory:

    ```sh
    cd ia
    ```

3. Compile the project using Maven:

    ```sh
    mvn clean install
    ```

4. Run the application:

    ```sh
    mvn exec:java -Dexec.mainClass="App"
    ```

## Usage

1. Run the `App` class using the provided Maven command.
2. You will be presented with a login, signup, or exit option.
3. After logging in or signing up, you can search for books, add new books, or logout.


## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a Pull Request.

## License

This project is licensed under the MIT License. See the LICENSE file for details.