package com.macro.mall.common.openfeign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.Decoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;

@Log4j2
public class CustomFeignDecoder implements Decoder {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpMessageConverter<Object> messageConverter = new MappingJackson2HttpMessageConverter(objectMapper);

    @Override
    public Object decode(Response response, Type type) throws IOException {
        if (response.status() == HttpStatus.NO_CONTENT.value() || response.body() == null) {
            return null;
        }
        Collection<String> contentTypeHeader = response.headers().getOrDefault("Content-Type", Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM_VALUE));
        log.info("Response headers:{}", response.headers());
        String contentType;
        if (contentTypeHeader != null && !contentTypeHeader.isEmpty()) {
            contentType = contentTypeHeader.iterator().next();
        } else {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        MediaType mediaType = MediaType.parseMediaType(contentType);
        String body = StreamUtils.copyToString(response.body().asInputStream(), StandardCharsets.UTF_8);
        return messageConverter.read((Class<?>) type, new FakeHttpInputMessage(body, mediaType));
    }

    public static class FakeHttpInputMessage implements org.springframework.http.HttpInputMessage {
        private final String body;
        private final MediaType mediaType;

        public FakeHttpInputMessage(String body, MediaType mediaType) {
            this.body = body;
            this.mediaType = mediaType;
        }

        @Override
        public InputStream getBody() {
            return new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
        }

        @Override
        public org.springframework.http.HttpHeaders getHeaders() {
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.setContentType(mediaType);
            return headers;
        }
    }
}

