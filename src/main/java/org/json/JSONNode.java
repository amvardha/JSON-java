package org.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node in a JSON structure with its path information.
 * This class is used for streaming operations on JSON objects.
 */
public class JSONNode {
    private final Object value;
    private final List<String> path;
    
    /**
     * Creates a new JSONNode with the given value and path.
     * 
     * @param value The value of the node
     * @param path The path to this node from the root
     */
    public JSONNode(Object value, List<String> path) {
        this.value = value;
        this.path = new ArrayList<>(path);
    }
    
    /**
     * Gets the value of this node.
     * 
     * @return The node's value
     */
    public Object getValue() {
        return value;
    }
    
    /**
     * Gets the path to this node from the root.
     * 
     * @return The path as a list of strings
     */
    public List<String> getPath() {
        return new ArrayList<>(path);
    }
    
    /**
     * Gets the path to this node as a JSONPointer string.
     * 
     * @return The path in JSONPointer format (e.g. "/a/b/c")
     */
    public String getPointer() {
        StringBuilder sb = new StringBuilder();
        for (String segment : path) {
            sb.append('/').append(segment);
        }
        return sb.toString();
    }
    
    /**
     * Checks if this node's value is a JSONObject.
     * 
     * @return true if the value is a JSONObject
     */
    public boolean isObject() {
        return value instanceof JSONObject;
    }
    
    /**
     * Checks if this node's value is a JSONArray.
     * 
     * @return true if the value is a JSONArray
     */
    public boolean isArray() {
        return value instanceof JSONArray;
    }
    
    /**
     * Gets this node's value as a JSONObject.
     * 
     * @return The value as a JSONObject
     * @throws JSONException if the value is not a JSONObject
     */
    public JSONObject getObject() throws JSONException {
        if (!isObject()) {
            throw new JSONException("Value is not a JSONObject");
        }
        return (JSONObject) value;
    }
    
    /**
     * Gets this node's value as a JSONArray.
     * 
     * @return The value as a JSONArray
     * @throws JSONException if the value is not a JSONArray
     */
    public JSONArray getArray() throws JSONException {
        if (!isArray()) {
            throw new JSONException("Value is not a JSONArray");
        }
        return (JSONArray) value;
    }
    
    /**
     * Gets this node's value as a String.
     * 
     * @return The value as a String
     * @throws JSONException if the value is not a String
     */
    public String getString() throws JSONException {
        if (!(value instanceof String)) {
            throw new JSONException("Value is not a String");
        }
        return (String) value;
    }
    
    /**
     * Gets this node's value as a Number.
     * 
     * @return The value as a Number
     * @throws JSONException if the value is not a Number
     */
    public Number getNumber() throws JSONException {
        if (!(value instanceof Number)) {
            throw new JSONException("Value is not a Number");
        }
        return (Number) value;
    }
    
    /**
     * Gets this node's value as a Boolean.
     * 
     * @return The value as a Boolean
     * @throws JSONException if the value is not a Boolean
     */
    public Boolean getBoolean() throws JSONException {
        if (!(value instanceof Boolean)) {
            throw new JSONException("Value is not a Boolean");
        }
        return (Boolean) value;
    }
} 