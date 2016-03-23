package frenchcomputerguy.rest;

import java.util.Map;

import frenchcomputerguy.utils.JSONElement;

/**
 * This class represents an HTTP GET request.
 *
 * @author Quentin Brault
 * @since 2016/03/16
 */
public class GetRequest extends Request {

    public GetRequest(String url) {
        super(url);
    }

    public GetRequest(String url, Map<String, String> parameters) {
        super(url, parameters);
    }

    public JSONElement getResponse() {
        return fetch(GET);
    }

}
