package org.booker.api.booker.interfaces;

import io.restassured.response.Response;
public interface ApiFlows {
    Response get(String id);
    Response post(Object payload);
    Response put(String id, Object payload,String token);
    Response delete(String id,String token);
    Response generateToken();
}
