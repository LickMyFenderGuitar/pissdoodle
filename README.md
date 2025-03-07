# LWJGL WebSocket Example

## Running the Application

1. Ensure you have Maven installed on your system.

2. Build and run the Java application using Maven:
    ```sh
    mvn compile
    mvn exec:java -Dexec.mainClass="com.example.Main"
    ```

3. Ensure the directory structure is correct: The `Main.java` file should be in the `src/main/java/com/example` directory and the `index.html` file should be in the `public` directory.

4. Rebuild and run the project: Use Maven to compile and run the project.

5. Open your web browser and navigate to `http://localhost:8080`.

You should see a canvas element that will display the LWJGL output.

## Diagnosing WebSocket Issues

- Check the browser console for WebSocket connection logs.
- Ensure the WebSocket server is running and listening on the correct port (8081).
- Verify that the WebSocket URL in the HTML file matches the WebSocket server URL.
