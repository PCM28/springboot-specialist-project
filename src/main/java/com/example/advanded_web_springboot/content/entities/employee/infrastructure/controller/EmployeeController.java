package com.example.advanded_web_springboot.content.entities.employee.infrastructure.controller;

import com.example.advanded_web_springboot.content.entities.employee.application.service.EmployeeService;
import com.example.advanded_web_springboot.content.entities.employee.domain.entity.Employee;

import com.example.advanded_web_springboot.content.entities.employee.infrastructure.controller.dto.input.EmployeeInputDto;
import com.example.advanded_web_springboot.content.entities.employee.infrastructure.controller.dto.output.EmployeeOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Controller
@RequestMapping
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // ---------- 1) FORM HTML + @ModelAttribute ----------
    // Recoge name + file en un POJO, con enctype multipart/form-data (como indica el PDF). :contentReference[oaicite:9]{index=9}
    @PostMapping(path = "/employee", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveEmployeeFromForm(@ModelAttribute Employee employee) throws Exception {
        String stored = employeeService.saveFromForm(employee.getName(), employee.getDocument());
        return ResponseEntity.ok("Subido como: " + stored);
    }
    /*
    En caso no quieras devolver una vista de respuesta usar @RestController + ResponseEntity:

    @PostMapping(path="/employee", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeOutputDto> saveFromFormApi(@ModelAttribute EmployeeInputDto employee, @RequestParam("document") MultipartFile document) throws Exception {
        EmployeeOutputDto out = employeeService.save(employee.getName(), document);
        return ResponseEntity.ok(out); // JSON
    }

    Resumen:
        - Si quieres pintar una página → devuelve String (nombre de vista).
        - Si quieres responder JSON (p.ej., llamado desde Postman/Ajax/Angular) → devuelve ResponseEntity<...> o usa @RestController.
    */


    // ---------- 2) JSON + FILE con @RequestPart ----------
    // El PDF destaca marcar el JSON como application/json y mandar además el archivo. :contentReference[oaicite:10]{index=10}
    @PostMapping(path = "/requestpart/employee", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EmployeeOutputDto> saveEmployeeWithRequestPart(
            @RequestPart("employee") EmployeeInputDto employeeInputDto, //{"name":"Ana"} tal cual se coloca en el vaor de su campo en postman, esto por usar @RequestPArt. Si se usaría @RequestParam se envíaría el valor entero como tal: Ana, ya que esta anotación espera un valor no un json como el rpimero
            @RequestPart("document") MultipartFile document) throws Exception {
        EmployeeOutputDto out = employeeService.save(employeeInputDto.getName(), document);
        return ResponseEntity.ok(out); // para hacerlo como el proyecto fundations hace falta de Spring JPA, pero para este proyecto no se está usando, para el resto sí
    }

    // ---------- 3) KEY/VALUE + FILE con @RequestParam ----------
    // Enfoque para datos simples (par clave/valor + archivo), como en el PDF. :contentReference[oaicite:11]{index=11}
    @PostMapping(path = "/requestparam/employee", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EmployeeOutputDto> saveEmployeeWithRequestParam(
            @RequestParam("employee") String name,
            @RequestPart("document") MultipartFile document) throws Exception {
        EmployeeOutputDto out = employeeService.save(name, document);
        return ResponseEntity.ok(out);
    }

    // ---------- Vista del formulario ----------, ask to GPT if I delete this I can still see the form
    @GetMapping("/employee/form")
    public String form(org.springframework.ui.Model model) {
        model.addAttribute("employee", new com.example.advanded_web_springboot.content.entities.employee.domain.entity.Employee());
        return "form";  // con tu ruta actual templates/form.html
    }

    // ---------- Descarga de archivos ----------
    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws Exception {
        Resource file = employeeService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }
}
