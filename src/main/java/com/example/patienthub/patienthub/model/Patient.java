package com.example.patienthub.patienthub.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Entity(name = "${entity.table.name}")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String age;
    private String gender;
    private Long contactNumber;
    private String reasonForVisit;

    public Map<String, String> toMap() {
        Map<String, String> patientMap = new HashMap<>();
        patientMap.put("id", String.valueOf(this.id));
        patientMap.put("name", this.name);
        patientMap.put("age", this.age);
        patientMap.put("gender", this.gender);
        patientMap.put("reasonForVisit", this.reasonForVisit);
        return patientMap;
    }

}
