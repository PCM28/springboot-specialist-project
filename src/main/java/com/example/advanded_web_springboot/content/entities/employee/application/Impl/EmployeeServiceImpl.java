package com.example.advanded_web_springboot.content.entities.employee.application.Impl;

import com.example.advanded_web_springboot.content.entities.employee.application.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Path root;

    public EmployeeServiceImpl(@Value("${app.upload-dir}") String uploadDir) {
        this.root = Paths.get(uploadDir);
    }

    @PostConstruct
    @Override
    public void init() throws IOException {
        if (!Files.exists(root)) Files.createDirectories(root);
    }

    @Override
    public String save(String employeeName, MultipartFile file) throws IOException {

        if(file == null || file.isEmpty()) throw new IOException("Archivo vacío");

        String original = StringUtils.cleanPath(file.getOriginalFilename());
        String stored = System.currentTimeMillis() + "-" + original;
        Files.copy(file.getInputStream(), root.resolve(stored), StandardCopyOption.REPLACE_EXISTING);

        //Podría persistir (employee, stored) en BD
        return stored;
    }

    @Override
    public Stream<Path> list() throws IOException {
        if (!Files.exists(root)) return Stream.empty();
        return Files.list(root).filter(Files::isRegularFile);
    }

    @Override
    public Resource loadAsResource(String filename) throws IOException {
        Path p = root.resolve(filename).normalize();
        if (!Files.exists(p)) throw new IOException("No encontrado: " + filename);
        return new FileSystemResource(p);
    }
}
