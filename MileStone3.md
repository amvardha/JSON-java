# Milestone 3: Key Transformation in XML Parsing

## Overview
This milestone introduces a flexible and efficient way to transform XML keys during parsing in the JSON-java library. By overloading the parser method to accept a key transformation function, users can now apply custom transformations to XML element and attribute names as the XML is parsed into a `JSONObject`.

## How to Run the Code

### 1. Compile the Source Code
Make sure you are in the project root directory. Compile the Java source files and package them into a JAR:

```sh
cd src/main/java
javac org/json/*.java
cd ../../../
jar cf json-java.jar -C src/main/java org/json
```

### 2. Compile and Run the JUnit Test Suite
The key transformation tests are now included in the main test suite at `src/test/java/org/json/junit/XMLTest.java`.

To compile the test suite:

```sh
javac -cp json-java.jar:src/test/java src/test/java/org/json/junit/XMLTest.java
```

To run the tests using JUnit (for example, with the JUnit Console Launcher):

```sh
# If you have junit-platform-console-standalone.jar downloaded:
java -jar junit-platform-console-standalone.jar --class-path .:json-java.jar:src/test/java --scan-class-path
```

Or, if you are using an IDE like IntelliJ or Eclipse, simply right-click on `XMLTest.java` and select "Run" to execute all tests, including the new key transformation tests.

## Testing
The test file demonstrates:
- How to use the new overloaded `toJSONObject` method with a `KeyTransformer` functional interface.
- How to compare the output of the parser with and without key transformation.
- That values in the XML remain unchanged, while keys are transformed as specified.
- That the transformation logic is now part of the main test suite for maintainability and regression testing.

## Implementation Details & Performance Discussion

### Overloaded Parser Method
The core improvement in this milestone is the introduction of an overloaded `toJSONObject` method that accepts a `KeyTransformer` functional interface. This allows users to pass any custom transformation logic for XML keys, such as prefixing, uppercasing, or reversing key names.

### Why Overloading Improves Performance and Flexibility
- **Single-Pass Transformation:** The transformation is applied during parsing, not as a post-processing step. This means each key is transformed exactly once, reducing unnecessary iterations and memory usage.
- **No Redundant Traversal:** Previous approaches might have required traversing the entire parsed `JSONObject` to rename keys, which is inefficient for large XML documents. The overloaded method eliminates this overhead.
- **Customizability:** By accepting a functional interface, the method supports any transformation logic, making it highly flexible for different use cases.
- **Backward Compatibility:** Existing code using the original parser methods remains unaffected, ensuring a smooth upgrade path.

### Example Usage
```java
// Prefix all keys with "custom_"
XML.KeyTransformer prefixer = key -> "custom_" + key;
JSONObject result = XML.toJSONObject(new StringReader(xmlString), prefixer);
```

## Conclusion
This milestone delivers a robust, efficient, and extensible solution for XML key transformation in the JSON-java library. The overloaded parser method ensures high performance and flexibility, making it easy to adapt XML-to-JSON conversion to a wide range of requirements. 