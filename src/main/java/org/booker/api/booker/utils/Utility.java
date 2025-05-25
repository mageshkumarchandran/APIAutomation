package org.booker.api.booker.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Properties;

public class Utility {
    private static Properties properties = new Properties();
    private LocalDate date;


    static {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file", e);
        }
    }
    /**  This function returns the property from config file  */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**  This function returns token from response  */
    public static String getAuthToken(Response response) {

        return response.getBody().jsonPath().get("token");
    }

    /**  This function convert request json file to map  */
    public Map<String, Object> returnJsonAsMap() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> requestMap = mapper.readValue(
                new File(getProperty("json.file.path")),
                new TypeReference<>() {
                }
        );

        return requestMap;
    }
    /**  This function update teh key value in request file  */
    public Map<String, Object> updateRecordInBookingReq(Map<String, Object> jsonMap, String key, String value)
    {
        ObjectMapper mapper = new ObjectMapper();
        Object parsedValue = null;
        try {
            parsedValue = mapper.readValue(value, Object.class);
            jsonMap.put(key, parsedValue);
        } catch (Exception e) {
            jsonMap.put(key, value);
        }
        return jsonMap;
    }

    /**  This function delete the  key in request file  */
    public static Map<String, Object> deleteRecordInBookingReq(Map<String, Object> jsonMap, String key)
    {
        jsonMap.remove(key);
        return jsonMap;
    }
    /**  This function update the  Dates in request file  */
    public Map<String, Object> updateDateInBookingReq(Map<String, Object> jsonMap, String type)
    {
        Map<String, Object> bookingDates = (Map<String, Object>) jsonMap.get("bookingdates");
        if (type.equalsIgnoreCase("checkin"))
            date = LocalDate.now();
        else
            date = LocalDate.now().plusDays(3);

        bookingDates.put(type, date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return bookingDates;

    }
    /**  This function remove wrapping key(only) and make response suitable for validation */
    public static Map<String, Object> removeBookingIDAndObject(Map<String, Object> jsonRecord)
    {

        jsonRecord.remove("bookingid");
        Object bookingObj = jsonRecord.remove("booking");

        if (bookingObj instanceof Map) {
            Map<String, Object> userMap = (Map<String, Object>) bookingObj;
            for (Map.Entry<String, Object> entry : userMap.entrySet()) {
                jsonRecord.put(entry.getKey(), entry.getValue());
            }
        }
        return jsonRecord;
    }
}

