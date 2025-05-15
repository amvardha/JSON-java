import java.io.StringReader;

import org.json.JSONObject;
import org.json.XML;

public class TestM3 {
    public static void main(String[] args) {
        // Test case 1: Prefix transformation
        testPrefixTransformation();
        
        // Test case 2: Reverse transformation
        testReverseTransformation();
        
        // Test case 3: Complex XML with nested elements
        testComplexXMLTransformation();
    }
    
    private static void testPrefixTransformation() {
        String xml = "<root><foo>value</foo><bar attr=\"test\">content</bar></root>";
        XML.KeyTransformer prefixTransformer = key -> "swe262_" + key;
        
        try {
            // Print JSON without transformation
            JSONObject jsonNoTransform = XML.toJSONObject(new StringReader(xml));
            System.out.println("Prefix Test - No Transformation:");
            System.out.println(jsonNoTransform.toString(2));

            JSONObject json = XML.toJSONObject(new StringReader(xml), prefixTransformer);
            System.out.println("Prefix Test Result:");
            System.out.println("Expected: Keys should be prefixed with 'swe262_', but values should remain unchanged");
            System.out.println("Actual: " + json.toString(2));
            
            // Verify structure and values
            boolean keysTransformed = json.has("swe262_root") && 
                json.getJSONObject("swe262_root").has("swe262_foo") &&
                json.getJSONObject("swe262_root").has("swe262_bar") &&
                json.getJSONObject("swe262_root").getJSONObject("swe262_bar").has("attr");
                
            // Verify values remain unchanged
            boolean valuesUnchanged = 
                json.getJSONObject("swe262_root").getJSONObject("swe262_foo").getString("content").equals("value") &&
                json.getJSONObject("swe262_root").getJSONObject("swe262_bar").getString("attr").equals("test") &&
                json.getJSONObject("swe262_root").getJSONObject("swe262_bar").getString("content").equals("content");
            
            System.out.println("Keys transformed correctly: " + keysTransformed);
            System.out.println("Values unchanged: " + valuesUnchanged);
            System.out.println("Test passed: " + (keysTransformed && valuesUnchanged));
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testReverseTransformation() {
        String xml = "<test><data>123</data></test>";
        XML.KeyTransformer reverseTransformer = key -> new StringBuilder(key).reverse().toString();
        
        try {
            // Print JSON without transformation
            JSONObject jsonNoTransform = XML.toJSONObject(new StringReader(xml));
            System.out.println("Reverse Test - No Transformation:");
            System.out.println(jsonNoTransform.toString(2));

            JSONObject json = XML.toJSONObject(new StringReader(xml), reverseTransformer);
            System.out.println("Reverse Test Result:");
            System.out.println("Expected: Keys should be reversed, but values should remain unchanged");
            System.out.println("Actual: " + json.toString(2));
            
            // Verify structure and values
            boolean keysTransformed = json.has("tset") && 
                json.getJSONObject("tset").has("atad");
                
            // Verify values remain unchanged
            boolean valuesUnchanged = 
                json.getJSONObject("tset").getJSONObject("atad").getString("content").equals("123");
            
            System.out.println("Keys transformed correctly: " + keysTransformed);
            System.out.println("Values unchanged: " + valuesUnchanged);
            System.out.println("Test passed: " + (keysTransformed && valuesUnchanged));
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testComplexXMLTransformation() {
        String xml = 
            "<library>" +
            "  <book category=\"fiction\">" +
            "    <title>Sample Book</title>" +
            "    <author>John Doe</author>" +
            "    <year>2023</year>" +
            "    <reviews>" +
            "      <review rating=\"5\">Great book!</review>" +
            "      <review rating=\"4\">Good read</review>" +
            "    </reviews>" +
            "  </book>" +
            "</library>";
            
        XML.KeyTransformer upperCaseTransformer = String::toUpperCase;
        
        try {
            // Print JSON without transformation
            JSONObject jsonNoTransform = XML.toJSONObject(new StringReader(xml));
            System.out.println("Complex XML Test - No Transformation:");
            System.out.println(jsonNoTransform.toString(2));

            JSONObject json = XML.toJSONObject(new StringReader(xml), upperCaseTransformer);
            System.out.println("Complex XML Test Result:");
            System.out.println("Expected: All keys should be uppercase, but values should remain unchanged");
            System.out.println("Actual: " + json.toString(2));
            
            // Verify structure and values
            boolean keysTransformed = json.has("LIBRARY") && 
                json.getJSONObject("LIBRARY").has("BOOK") &&
                json.getJSONObject("LIBRARY").getJSONObject("BOOK").has("category") &&
                json.getJSONObject("LIBRARY").getJSONObject("BOOK").has("TITLE");
                
            // Verify values remain unchanged
            boolean valuesUnchanged = 
                json.getJSONObject("LIBRARY").getJSONObject("BOOK").getString("category").equals("fiction") &&
                json.getJSONObject("LIBRARY").getJSONObject("BOOK").getJSONObject("TITLE").getString("content").equals("Sample Book");
            
            System.out.println("Keys transformed correctly: " + keysTransformed);
            System.out.println("Values unchanged: " + valuesUnchanged);
            System.out.println("Test passed: " + (keysTransformed && valuesUnchanged));
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
        }
    }
} 