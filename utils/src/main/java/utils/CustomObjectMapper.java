package utils;


import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import conf.JaxrsConf;

public class CustomObjectMapper {

    private ObjectMapper jsonObjectMapper;
    private XmlMapper xmlObjectMapper;

    public CustomObjectMapper() {
        JaxrsConf jaxrsConf = new JaxrsConf();
        this.jsonObjectMapper = jaxrsConf.jsonObjectMapper();
        this.xmlObjectMapper = jaxrsConf.xmlObjectMapper();
    }

    public String mapTotXml(Map<String, Object> map) {
        try {
            String xmlAsString = xmlObjectMapper.writeValueAsString(map);
            xmlAsString = xmlAsString.substring(xmlAsString.indexOf('>') + 1);
            xmlAsString = xmlAsString.substring(0, xmlAsString.lastIndexOf('<'));
            return xmlAsString;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String mapToJson(Map<String, Object> map) {
        try {
            return jsonObjectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String listToJson(List<Map<String, Object>> listMap) {
        try {
            return jsonObjectMapper.writeValueAsString(listMap);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
