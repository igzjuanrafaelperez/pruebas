package rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import com.google.common.io.ByteStreams;

@Produces({"image/png", "image/jpg"})
public class ImageBodyWriter implements MessageBodyWriter<ByteArrayInputStream> {
    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return "image/png".equals( mediaType.getType() ) || "image/jpg".equals( mediaType.getType() );
    }

    @Override
    public long getSize(ByteArrayInputStream streamingOutput, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return 0;
    }

    @Override
    public void writeTo(ByteArrayInputStream byteArrayInputStream, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException {
        ByteStreams.copy(byteArrayInputStream, outputStream);
    }

}
