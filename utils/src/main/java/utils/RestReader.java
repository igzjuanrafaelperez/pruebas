package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class RestReader {

    private GenericType<Map<String, Object>> mapGenericType = new MapGenericType();

    private GenericType<List<Map<String, Object>>> listGenericType = new ListGenericType();

    public GenericType<Map<String, Object>> getMapGenericType() {
        return mapGenericType;
    }

    public void setMapGenericType(GenericType<Map<String, Object>> mapGenericType) {
        this.mapGenericType = mapGenericType;
    }

    public GenericType<List<Map<String, Object>>> getListGenericType() {
        return listGenericType;
    }

    public void setListGenericType(GenericType<List<Map<String, Object>>> listGenericType) {
        this.listGenericType = listGenericType;
    }

    public Map<String, Object> readMap(Response response) {

        if (!response.hasEntity()) {
            return new HashMap<>();
        }

        return response.readEntity(mapGenericType);
    }

    public List<Map<String, Object>> readList(Response response) {

        if (!response.hasEntity()) {
            return new ArrayList<>();
        }

        return response.readEntity(listGenericType);
    }

    public<T> T read(Response response, Class<T> clazz) {
        if (!response.hasEntity()) {
            return null;
        }
        return response.readEntity(clazz);
    }

    private static class MapGenericType extends GenericType<Map<String, Object>> {
    }

    private static class ListGenericType extends GenericType<List<Map<String, Object>>> {
    }
}
