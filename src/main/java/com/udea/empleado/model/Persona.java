/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udea.empleado.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;

/**
 *
 * @author Daniela
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@ApiModel(description="Devuelve todos los detalles de la persona")

public class Persona implements Serializable {
    
    @ApiModelProperty(notes="La BD genera el ID de la persona")
    
  
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="idperson")
    private Long idPerson;
    
    @ApiModelProperty(notes="El nombre de la empleado")
    @Column(name="firstname", nullable=false,length=80)
    private @NonNull String firstName;
    
    @ApiModelProperty(notes="El apellido de la empleado")
    @Column(name="lastname", nullable=false,length=80)
    private @NonNull String lastName;
    
    @ApiModelProperty(notes="El email de la empleado")
    @Column(name="email", nullable=false,length=80)
    private @NonNull String email;
    
    @ApiModelProperty(notes="La direccion del empleado")
    @Column(name="homeaddress", nullable=false, length=80)
    private @NonNull String homeAddress;
    
    @ApiModelProperty(notes="El salario del empleado")
    @Column(name="basesalary", nullable=false, length=9)
    private @NonNull Double baseSalary;
    
    @ApiModelProperty(notes="El rol que tiene en la empresa")
    @Column(name="role", nullable=false, length=30)
    private @NonNull String role;
    
    @ApiModelProperty(notes="Número de oficina en la que se encuentra el empleado")
    @Column(name="office", nullable=false, length=30)
    private @NonNull String office;
    
    @ApiModelProperty(notes="Dependencia para la cual trabaja el empleado")
    @Column(name="dependency", nullable=false, length=50)
    private @NonNull String dependency;
    
    @ApiModelProperty(notes="Fecha en la que ingresa los datos")
    @Column(name="attachment", nullable=false, length=30)
    private LocalDate attachmentDate;
    
    
    @ApiModelProperty(notes="Estado del empleado")
    @Column(name="status", length=30)
    private String status ="activo";
    
}
