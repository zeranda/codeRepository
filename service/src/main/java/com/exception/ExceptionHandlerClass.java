package com.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
@RestController
public class ExceptionHandlerClass {
    @Value("${spring.profiles}")
    private String env;

    @ExceptionHandler({Exception.class})
    public void exceptionHandle(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        if ("dev".equals(env)) {
            try {
                PrintWriter writer = response.getWriter();
                writer.print(exception.getMessage() + env);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
