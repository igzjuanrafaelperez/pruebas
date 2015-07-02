package conf;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import exceptions.GenericSmartException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

public class JsonDateSerializer extends JsonSerializer<String> {

    private static final Logger LOGGER = LogManager.getLogger(JsonDateSerializer.class.getName());
    private final SimpleDateFormat format;

    public JsonDateSerializer() {
        format = new SimpleDateFormat("yyyyMMddHHmmss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public void serialize(String date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        try {
            jsonGenerator.writeNumber(parse(date).getTime());
        } catch (GenericSmartException e) {
            throw new IOException();
        }
    }

    public Date parse(String date) throws GenericSmartException {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            LOGGER.error( e.getMessage(), e );
            throw new GenericSmartException( SC_INTERNAL_SERVER_ERROR );
        }
    }
}
