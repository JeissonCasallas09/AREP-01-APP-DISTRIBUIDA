# Distributed Applications

For this project we made a simple HTTP server implementation using technologies like Java, JavaScript, CSS, HTML and REST request without using frameworks like Spring Boot, just a simple server. All of this divided by directories.

![](/images/1.png)
![](/images/2.png)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You need to install the following tools to run the project:
1. Java

   Try using the following command
    ```
    java -version
    ```
    You should see something like this:

    ![](/images/3.png)

2. Maven

   We need maven too, so use the following command.

    ```
    mvn -version
    ```
    You should see something like this:
    
    ![](/images/4.png)

3. Git

   To finish, we must have git. You can check it with this command.
    ```
    git --version
    ```
    It should appear something like this:
    
    ![](/images/5.png)

### Installing

1. First, we need to open a terminal and put the following command to clone the project:

    ```
    git clone https://github.com/JeissonCasallas09/AREP-01-APP-DISTRIBUIDA
    ```
2. Open the folder with the project in a new terminal and build it with the following command:
    ```
    mvn package
    ```
    This message will tell you that it was successfull:

     ![](/images/6.png)

3. Now just run the project using:
    ```
    mvn exec:java
    ```
    The project is now running:

    ![](/images/7.png)
    
4. Enter from your browser to the local server with port 35000

 ![](/images/8.png)


## Architecture


#### Overview
This project implements a basic HTTP server capable of serving static files, handling REST API requests, and managing content types. Below is a breakdown of the main components, explaining the role of each file and its key methods.

#### 1️.  HttpServer.java
✅ Core class that implements the HTTP server.

🔹 Key responsibilities:

   - Listens for client connections on port 35000.
   - Parses HTTP requests to determine whether to serve a static file or process a REST API request.
   - Uses the serveFile() method to return static content (HTML, CSS, JS, images).
   - Implements the helloRestService() method to handle API requests at /app/hello?name=yourname.
   - Determines MIME types with the getContentType() method.

🔹 Key methods:

   - main(String[] args) → Starts the server, listens for connections, and processes requests.
   - serveFile(PrintWriter out, String filePath, String contentType) → Reads and returns the requested static file's content.
   - getContentType(String file) → Determines the MIME type of a file (HTML, CSS, JS, images, etc.).
   - helloRestService(String path, String query) → Handles API requests like /app/hello?name=John, returning JSON responses.

#### 2️. HttpServerTest.java
✅ Unit test class that ensures the server behaves correctly.

🔹 Key tests:

   - testServeStaticFile() → Verifies the server correctly serves an existing HTML file.
   - testHelloRestService() → Ensures that the /app/hello REST endpoint returns a valid JSON response.
   - testFileNotFound() → Checks that the server returns a 500 Internal Server Error for non-existent files.
   - testGetContentTypeHtml(), testGetContentTypeCss(), t testGetContentTypeJs(), testGetContentTypePng() → Validate the MIME type detection.

#### 3️. index.html (or other static files)

✅ HTML page served by the server when requested.

🔹 Key role:

   - Rendered in the browser when navigating to /index.html.
   - Can include CSS and JavaScript files that are also served by the server.

#### 4️. styles.css (or any other CSS file)

✅ Defines the visual style of the HTML files served by the server.

#### 5️. script.js (or other JavaScript files)

✅ Contains JavaScript logic that may interact with the server via AJAX or fetch requests.

## Running the tests

The project includes unit tests to validate the functionality of the HTTP server, ensuring correct behavior when handling static files, REST endpoints, and content types. Below is an explanation of each test:

#### 1. testServeStaticFile()

✅ Purpose: This test verifies that the server correctly serves an existing static file (index.html).

🔹 Steps:
  - Sends an HTTP GET request for /index.html.
  - Reads the server's response.
 - Checks if the response contains HTTP/1.1 200 OK (successful request).
Ensures the Content-Type is text/html.

#### 2. testHelloRestService()
✅ Purpose: This test ensures that the REST endpoint /app/hello returns a valid JSON response when queried.

🔹 Steps:

 - Sends an HTTP GET request for /app/hello?name=TestUser.
 - Reads the response.
 - Checks if the response contains HTTP/1.1 200 OK (successful request).
 - Ensures the Content-Type is application/json.

#### 3. testFileNotFound()

✅ Purpose: This test checks the error handling when requesting a nonexistent file.

🔹 Steps:
   - Sends an HTTP GET request for /nonexistent.html.
   - Reads the response.
   - Verifies that the server responds with HTTP/1.1 500 Internal Server Error.

#### 4. testGetContentTypeHtml()

✅ Purpose: Ensures that the MIME type detection for HTML files works correctly.

🔹 Steps:

   - Calls getContentType("index.html").
   - Asserts that the return value is "text/html".

#### 5. testGetContentTypeCss()

✅ Purpose: Validates that the MIME type detection for CSS files returns the expected value.

🔹 Steps:

   - Calls getContentType("styles.css").
   - Checks that it returns "text/css".

#### 6️. testGetContentTypeJs()
✅ Purpose: Ensures that JavaScript files are served with the correct MIME type.

🔹 Steps:

   - Calls getContentType("script.js").
   - Verifies that it returns "application/javascript".

#### 7️. testGetContentTypePng()
✅ Purpose: Tests that image files (.png) are assigned the correct MIME type.

🔹 Steps:

   - Calls getContentType("image.png").
   - Ensures that the return value is "image/png".


![](/images/9.png)

These tests validate the core functionality of the HTTP server, ensuring it properly serves static files, REST responses, and handles content type detection effectively. 🚀

## Conclusions

This project implements a basic HTTP server in Java capable of handling client requests, serving static files, and responding with JSON data. Throughout the development, the following objectives were achieved:

✅ Implementation of a basic HTTP server that listens on a specific port and processes client requests.

✅ Handling of static files (.html, .css, .js, images, etc.), allowing content to be loaded from a predefined directory.

✅ Detection of content types (MIME types) to serve files with the correct format based on their extensions.

✅ Implementation of a basic REST service (/app/hello?name=XYZ) that responds with dynamic JSON data.

✅ Unit testing with JUnit to validate the correct detection of MIME types for served files.

✅ Modular code structure, separating file handling logic, HTTP response generation, and server configuration.

This project served as a foundation for understanding the fundamentals of HTTP servers in Java, socket management, static file handling, and content validation through unit testing. 🚀


## Built With

* [Maven](https://maven.apache.org/) - Dependency Control
* [GIT](https://git-scm.com) - Versioning


## Versioning

versioning made it by [GitHub](http://git-scm.com).

## Authors

* **Jeisson Steban Casallas Rozo** - [JeissonCasallas09](https://github.com/JeissonCasallas09)

Date: 28/01/2025
## License

This project is licensed by ECI.

