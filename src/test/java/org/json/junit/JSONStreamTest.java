package org.json.junit;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONNode;
import org.json.JSONObject;
import org.json.XML;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests for the streaming functionality of JSONObject and JSONArray.
 */
public class JSONStreamTest {

    @Test
    public void testBasicStreaming() {
        String jsonStr = "{\"name\":\"John\",\"age\":30,\"address\":{\"street\":\"123 Main St\",\"city\":\"Boston\"},\"phones\":[\"123-456-7890\",\"098-765-4321\"]}";
        JSONObject obj = new JSONObject(jsonStr);

        // Test basic streaming
        List<JSONNode> nodes = obj.toStream().collect(Collectors.toList());
        assertEquals(9, nodes.size());

        // Test leaf streaming
        List<JSONNode> leafNodes = obj.toLeafStream().collect(Collectors.toList());
        assertEquals(6, leafNodes.size());

        // Test object streaming
        List<JSONNode> objectNodes = obj.toObjectStream().collect(Collectors.toList());
        assertEquals(2, objectNodes.size());

        // Test array streaming
        List<JSONNode> arrayNodes = obj.toArrayStream().collect(Collectors.toList());
        assertEquals(1, arrayNodes.size());
    }

    @Test
    public void testNestedStreaming() {
        String jsonStr = "{\"books\":[{\"title\":\"Book1\",\"author\":\"Author1\"},{\"title\":\"Book2\",\"author\":\"Author2\"}]}";
        JSONObject obj = new JSONObject(jsonStr);

        // Test streaming with nested objects and arrays
        List<JSONNode> nodes = obj.toStream().collect(Collectors.toList());
        assertEquals(8, nodes.size());

        // Test leaf streaming
        List<JSONNode> leafNodes = obj.toLeafStream().collect(Collectors.toList());
        assertEquals(4, leafNodes.size());

        // Test object streaming
        List<JSONNode> objectNodes = obj.toObjectStream().collect(Collectors.toList());
        assertEquals(3, objectNodes.size());

        // Test array streaming
        List<JSONNode> arrayNodes = obj.toArrayStream().collect(Collectors.toList());
        assertEquals(1, arrayNodes.size());
    }

    @Test
    public void testPathTracking() {
        String jsonStr = "{\"a\":{\"b\":{\"c\":\"value\"}}}";
        JSONObject obj = new JSONObject(jsonStr);

        // Test path tracking
        List<JSONNode> nodes = obj.toStream().collect(Collectors.toList());

        // Find the node with value "value"
        JSONNode valueNode = nodes.stream()
                .filter(node -> "value".equals(node.getValue()))
                .findFirst()
                .orElse(null);

        assertNotNull(valueNode);
        assertEquals("/a/b/c", valueNode.getPointer());

        List<String> path = valueNode.getPath();
        assertEquals(3, path.size());
        assertEquals("a", path.get(0));
        assertEquals("b", path.get(1));
        assertEquals("c", path.get(2));
    }

    @Test
    public void testArrayPathTracking() {
        String jsonStr = "{\"items\":[{\"id\":1},{\"id\":2}]}";
        JSONObject obj = new JSONObject(jsonStr);

        // Test path tracking in arrays
        List<JSONNode> nodes = obj.toStream().collect(Collectors.toList());

        // Find the node with id 2
        JSONNode idNode = nodes.stream()
                .filter(node -> node.getValue() instanceof Number && ((Number) node.getValue()).intValue() == 2)
                .findFirst()
                .orElse(null);

        assertNotNull(idNode);
        assertEquals("/items/1/id", idNode.getPointer());

        List<String> path = idNode.getPath();
        assertEquals(3, path.size());
        assertEquals("items", path.get(0));
        assertEquals("1", path.get(1));
        assertEquals("id", path.get(2));
    }

    @Test
    public void testStreamOperations() {
        String jsonStr = "{\"books\":[{\"title\":\"Book1\",\"author\":\"Author1\",\"price\":10.99},{\"title\":\"Book2\",\"author\":\"Author2\",\"price\":20.99}]}";
        JSONObject obj = new JSONObject(jsonStr);

        // Test filtering
        List<String> titles = obj.toStream()
                .filter(node -> node.getValue() instanceof String && node.getPath().get(node.getPath().size() - 1).equals("title"))
                .map(node -> (String) node.getValue())
                .collect(Collectors.toList());

        assertEquals(2, titles.size());
        assertEquals("Book1", titles.get(0));
        assertEquals("Book2", titles.get(1));

        // Test mapping
        List<Double> prices = obj.toStream()
                .filter(node -> node.getValue() instanceof Number && node.getPath().get(node.getPath().size() - 1).equals("price"))
                .map(node -> ((Number) node.getValue()).doubleValue())
                .collect(Collectors.toList());

        assertEquals(2, prices.size());
        assertEquals(10.99, prices.get(0), 0.001);
        assertEquals(20.99, prices.get(1), 0.001);
    }

    @Test
    public void testXMLToJSONBasicStreaming() {
        String xml = "<root><name>John</name><age>30</age><address><street>123 Main St</street><city>Boston</city></address><phones><phone>123-456-7890</phone><phone>098-765-4321</phone></phones></root>";
        JSONObject obj = XML.toJSONObject(xml);

        // Test streaming
        List<JSONNode> nodes = obj.toStream().collect(Collectors.toList());
        assertEquals(11, nodes.size());

        // Test leaf streaming
        List<JSONNode> leafNodes = obj.toLeafStream().collect(Collectors.toList());
        assertEquals(6, leafNodes.size());

        // Test object streaming
        List<JSONNode> objectNodes = obj.toObjectStream().collect(Collectors.toList());
        System.out.println("JSONStreamTest.testXMLToJSONBasicStreaming(): " + objectNodes);
        assertEquals(4, objectNodes.size());

        // Test array streaming
        List<JSONNode> arrayNodes = obj.toArrayStream().collect(Collectors.toList());
        assertEquals(1, arrayNodes.size());
    }

    @Test
    public void testXMLToJSONWithAttributesStreaming() {
        String xml = "<books><book id=\"1\" category=\"fiction\"><title>Book1</title></book><book id=\"2\" category=\"non-fiction\"><title>Book2</title></book></books>";
        JSONObject obj = XML.toJSONObject(xml);
        System.out.println("PRINTING XML TO JSON STRING:");
        System.out.println(obj.toString());

        // Test streaming
        List<JSONNode> nodes = obj.toStream().collect(Collectors.toList());
        assertEquals(11 , nodes.size());

        // Test leaf streaming
        List<JSONNode> leafNodes = obj.toLeafStream().collect(Collectors.toList());
        assertEquals(6, leafNodes.size());

        // Test object streaming
        List<JSONNode> objectNodes = obj.toObjectStream().collect(Collectors.toList());
        assertEquals(4, objectNodes.size());

        // Test array streaming
        List<JSONNode> arrayNodes = obj.toArrayStream().collect(Collectors.toList());
        assertEquals(1, arrayNodes.size());
    }

    @Test
    public void testXMLToJSONWithNestedArraysStreaming() {
        String xml = "<library><books><book><title>Book1</title><authors><author>Author1</author><author>Author2</author></authors></book><book><title>Book2</title><authors><author>Author3</author></authors></book></books></library>";
        JSONObject obj = XML.toJSONObject(xml);

        // Test array streaming (should find authors arrays)
        List<JSONNode> arrayNodes = obj.toArrayStream().collect(Collectors.toList());
        assertTrue(arrayNodes.size() >= 1);

        // Test getting all authors
        List<String> authors = obj.toStream()
                .filter(node -> node.getPath().contains("author") && node.getValue() instanceof String)
                .map(node -> (String) node.getValue())
                .collect(Collectors.toList());
        assertTrue(authors.contains("Author1"));
        assertTrue(authors.contains("Author2"));
        assertTrue(authors.contains("Author3"));
    }

    @Test
    public void testXMLToJSONWithCDATAStreaming() {
        String xml = "<book><title><![CDATA[Special & Character < > \" ']]></title><description>Normal text</description></book>";
        JSONObject obj = XML.toJSONObject(xml);

        // Find the CDATA content
        JSONNode cdataNode = obj.toStream()
                .filter(node -> node.getPath().contains("title") && node.getValue() instanceof String)
                .findFirst()
                .orElse(null);
        assertNotNull(cdataNode);
        assertEquals("Special & Character < > \" '", cdataNode.getValue());

        // Test regular text content
        JSONNode normalNode = obj.toStream()
                .filter(node -> node.getPath().contains("description") && node.getValue() instanceof String)
                .findFirst()
                .orElse(null);
        assertNotNull(normalNode);
        assertEquals("Normal text", normalNode.getValue());
    }
}
