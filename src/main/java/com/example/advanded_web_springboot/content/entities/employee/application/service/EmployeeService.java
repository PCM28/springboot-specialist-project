package com.example.advanded_web_springboot.content.entities.employee.application.service;

import com.example.advanded_web_springboot.content.entities.employee.infrastructure.controller.dto.output.EmployeeOutputDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface EmployeeService {
    void init() throws IOException;
    String saveFromForm(String employeeName, MultipartFile file) throws IOException;
    EmployeeOutputDto save(String employeeName, MultipartFile file) throws IOException;
    Stream<Path> list() throws IOException;
    Resource loadAsResource(String filename) throws IOException;
}
