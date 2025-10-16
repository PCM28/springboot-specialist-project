package com.example.advanded_web_springboot.content.entities.employee.application.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface EmployeeService {
    void init() throws IOException;
    String save(String employeeName, MultipartFile file) throws IOException;
    Stream<Path> list() throws IOException;
    Resource loadAsResource(String filename) throws IOException;
}
