package com.university.manager.constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum University {

    CAIRO_UNIVERSITY("Cairo University"),
    AIN_SHAMS_UNIVERSITY("Ain Shams University");

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static University getUniversity(String name) {
        for (University university : University.values()) {
            if (university.getName().equals(name)) {
                return university;
            }
        }
        return null;
    }




}
