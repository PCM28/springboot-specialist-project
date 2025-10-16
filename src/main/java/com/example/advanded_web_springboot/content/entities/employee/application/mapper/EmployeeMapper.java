package com.example.advanded_web_springboot.content.entities.employee.application.mapper;

import com.example.advanded_web_springboot.content.entities.employee.domain.entity.Employee;
import com.example.advanded_web_springboot.content.entities.employee.infrastructure.controller.dto.input.EmployeeInputDto;
import com.example.advanded_web_springboot.content.entities.employee.infrastructure.controller.dto.output.EmployeeOutputDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeOutputDto employeeToEmployeeOutputDto(Employee employee);

    Employee employeeOutputDtoToEmployee(EmployeeInputDto employeeInputDto);
}
