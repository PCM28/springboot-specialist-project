package com.example.advanded_web_springboot.content.entities.employee.infrastructure.controller.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOutputDto {
    private String name;
    private String storedFilename;
}
