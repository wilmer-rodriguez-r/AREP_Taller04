package backend;

import org.example.files.exception.ExceptionFile;
import org.example.miniSpark.MiniSpark;
import org.example.handlers.Request;
import org.example.handlers.Response;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SparkTest {

    @Test
    public void whenTheEndpointExistGET() throws ExceptionFile {
        //Arrange
        String responseExpect = "Test";
        MiniSpark.get("/test1", (request, response) -> responseExpect);
        //Act
        Request request = new Request("GET /test1", "");
        Response response = new Response(request.getEndpoint());
        String contentResponse = (String) MiniSpark.search(request.getEndpoint(), request.getVerb()).handle(request, response);
        //Assert
        assertEquals(contentResponse, responseExpect);
    }

    @Test(expected = NullPointerException.class)
    public void whenTheEndpointNotExistGET() throws ExceptionFile {
        //Arrange
        //Act
        Request request = new Request("GET /test", "");
        Response response = new Response(request.getEndpoint());
        System.out.println(request.getEndpoint());
        String contentResponse = (String) MiniSpark.search(request.getEndpoint(), request.getVerb()).handle(request, response);
        //Assert
    }

    @Test
    public void whenTheEndpointExistPOST() throws ExceptionFile {
        //Arrange
        String responseExpect = "Test2";
        String payload = "{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}";
        MiniSpark.post("/test2", (request, response) -> responseExpect);
        //Act
        Request request = new Request("POST /test2", payload);
        Response response = new Response(request.getEndpoint());
        String contentResponse = (String) MiniSpark.search(request.getEndpoint(), request.getVerb()).handle(request, response);
        //Assert
        assertEquals(request.getBody().toString(), payload);
    }

    @Test(expected = NullPointerException.class)
    public void whenTheEndpointNotExistPOST() throws ExceptionFile {
        //Arrange
        //Act
        Request request = new Request("POST /test", "");
        Response response = new Response(request.getEndpoint());
        String contentResponse = (String) MiniSpark.search(request.getEndpoint(), request.getVerb()).handle(request, response);
        //Assert
    }
}
