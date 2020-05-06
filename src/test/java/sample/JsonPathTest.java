package sample;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonPathTest {

    private final Calculator calculator = new Calculator();

    @Test
    void addition() {
        assertEquals(2, calculator.add(1, 1));
    }

    @Test
    void jsonQuery(){
        String json = "{\n" +
                "    \"store\": {\n" +
                "        \"book\": [\n" +
                "            {\n" +
                "                \"category\": \"reference\",\n" +
                "                \"author\": \"Nigel Rees\",\n" +
                "                \"title\": \"Sayings of the Century\",\n" +
                "                \"price\": 8.95\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Evelyn Waugh\",\n" +
                "                \"title\": \"Sword of Honour\",\n" +
                "                \"price\": 12.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"Herman Melville\",\n" +
                "                \"title\": \"Moby Dick\",\n" +
                "                \"isbn\": \"0-553-21311-3\",\n" +
                "                \"price\": 8.99\n" +
                "            },\n" +
                "            {\n" +
                "                \"category\": \"fiction\",\n" +
                "                \"author\": \"J. R. R. Tolkien\",\n" +
                "                \"title\": \"The Lord of the Rings\",\n" +
                "                \"isbn\": \"0-395-19395-8\",\n" +
                "                \"price\": 22.99\n" +
                "            }\n" +
                "        ],\n" +
                "        \"bicycle\": {\n" +
                "            \"color\": \"red\",\n" +
                "            \"price\": 19.95\n" +
                "        }\n" +
                "    },\n" +
                "    \"expensive\": 10\n" +
                "}";

        List<String> authors = JsonPath.read(json, "$.store.book[*].author");

        assertNotNull(authors);
    }

    @Test
    void customSample(){
        String json = "{\n" +
                "\"example\": {\n" +
                "\t\"my-key\": 1,\n" +
                "\t\"my-second-key\" : 2\n" +
                "\t}\n" +
                "}";

        Integer myKeyValue = JsonPath.read(json, "$.example.my-key");

        Integer expectedValue = 1;
        assertEquals(myKeyValue, expectedValue);
    }

    @Test
    void deleteElement() throws IOException {
        String json = "{\n" +
                "\"example\": {\n" +
                "\t\"my-key\": 1,\n" +
                "\t\"my-second-key\" : 2\n" +
                "\t}\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(json);

        ObjectNode exampleNode = (ObjectNode)actualObj.path("example");
        exampleNode.remove("my-key");

        assertTrue(actualObj.path("example").path("my-key").isMissingNode());
    }

    private class Calculator {

        public int add(int a, int b){
            return a+b;
        }
    }
}
