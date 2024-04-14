package org.example.dataRequest;

import lombok.Data;

@Data
public class AdditionalProperty {
    private String key;
    private String value;
    private String name;
    @Override
    public String toString() {
        return "AdditionalProperty{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}