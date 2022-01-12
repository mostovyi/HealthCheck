package com.example.demo.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
class Controller {

    @GetMapping(value = "/healthcheck", produces ="application/json")
    public String healthcheck(@RequestParam String format) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            switch (format) {
                case "long":  return objectMapper.writeValueAsString(new HttpStateAndTime(ZonedDateTime.now(), HttpStatus.OK));
                case "short":  return objectMapper.writeValueAsString(new HttpState(HttpStatus.OK));
                default:  return objectMapper.writeValueAsString(new HttpState(HttpStatus.BAD_REQUEST));
            }
    }

    protected static class HttpState {

        private HttpStatus status;

        protected HttpState(HttpStatus httpStatus) { this.status = httpStatus; }
        public HttpStatus getStatus() { return status; }
        public void setStatus(HttpStatus status) { this.status = status; }
    }

    protected static class HttpStateAndTime {

        private ZonedDateTime currentTime;
        private HttpStatus status;
        protected HttpStateAndTime(ZonedDateTime zonedDateTime, HttpStatus httpStatus) {
            this.currentTime = zonedDateTime;
            this.status = httpStatus;
        }

        public ZonedDateTime getCurrentTime() { return currentTime; }
        public void setCurrentTime(ZonedDateTime currentTime) { this.currentTime = currentTime; }
        public HttpStatus getStatus() { return status; }
        public void setStatus(HttpStatus status) { this.status = status; }

    }
}
