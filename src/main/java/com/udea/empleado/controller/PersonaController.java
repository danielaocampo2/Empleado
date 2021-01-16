/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udea.empleado.controller;
import com.udea.empleado.exception.ModelNotFoundException;
import com.udea.empleado.model.Persona;
import com.udea.empleado.service.PersonaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping; 

@RestController
@RequestMapping("/empleado")
@CrossOrigin("*")

@Api(value = "Employee Management System", description = "Operations pertaining to employee in Person Management System")
public class PersonaController {

    @Autowired
    PersonaService personService;

    @ApiOperation(value = "Add Employee")
    @PostMapping("/save")
    public long save(@ApiParam(value = "Employee saved in the BD", required = true) @RequestBody Persona persona) {
        personService.save(persona);
        return persona.getIdPerson();
    }
    
@ApiOperation(value = "View a list of available persons", response = List.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list")
        ,
    @ApiResponse(code = 401, message = "You are not authorized to view the resource")
        ,
    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
        ,
    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })

    @GetMapping("/listAll")
    public Iterable<Persona> listAllPersons() {
        return personService.list();
    }

    @ApiOperation(value = "Get a person by Id")
    @GetMapping("/list/{id}")
    public Persona listPersonById(@ApiParam(value = "Employee id from which employee object will retrieve", required = true) @PathVariable("id") int id) {
        Optional<Persona> person = personService.listId(id);
        if (person.isPresent()) {
            return person.get();
        }

        throw new ModelNotFoundException("ID de persona invalido");
    }
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete an employee by Id")
    public void deleteEmployee(@PathVariable Long id) {
        Persona personaToBeDeleted = personService.listId(id).orElseThrow(() -> new ModelNotFoundException("Employee doesn't exists"));
        personService.delete(personaToBeDeleted);
    }

    

    @ApiOperation(value = "Update an employee")
    @PutMapping("/update/{id}")
    public Persona replaceEmployee(@PathVariable Long id, @RequestBody Persona employee) {
        return personService.listId(id)
                .map(newEmployee -> {
                    newEmployee.setFirstName(employee.getFirstName());
                    newEmployee.setLastName(employee.getLastName());
                    newEmployee.setEmail(employee.getEmail());
                    newEmployee.setHomeAddress(employee.getHomeAddress());
                    newEmployee.setBaseSalary(employee.getBaseSalary());
                    newEmployee.setRole(employee.getRole());
                    newEmployee.setOffice(employee.getOffice());
                    newEmployee.setDependency(employee.getDependency());
                    newEmployee.setAttachmentDate(employee.getAttachmentDate());
                    return personService.save(newEmployee);
                })
                .orElseGet(() -> {
                    employee.setIdPerson(id);
                    return personService.save(employee);
                });
    }
    
    @ApiOperation(value = "Increase salary if Attachment date > 2 years")
    @PutMapping("/increase/{id}")
    public String increaseSalary(@PathVariable Long id) {
        LocalDate twoYearsAgo = LocalDate.now().minusYears(2);
        Optional<Persona> newPersona = personService.listId(id);
        if (newPersona.isPresent()) {
            if (newPersona.get().getAttachmentDate().isBefore(twoYearsAgo)) {
                double salaryWithInc = (newPersona.get().getBaseSalary() * 0.1) + newPersona.get().getBaseSalary();
                newPersona.get().setBaseSalary(salaryWithInc);
                newPersona.get().setStatus("inactivo");
                personService.save(newPersona.get());
             
                return "Nuevo salario = " + newPersona.get().getBaseSalary().toString();
            } else {
                return "La persona no cumple con la antiguedad necesaria";
            }
        }
        throw new ModelNotFoundException("Persona no encontrada");
    }


}
