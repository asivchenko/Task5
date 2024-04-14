package org.example.dataRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
public class AdditionalPropertyVip {
    @JsonProperty("data")
    private List<AdditionalProperty> data;

    public List<AdditionalProperty> getData() {
        return data;
    }

    public void setData(List<AdditionalProperty> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AdditionalPropertyVip{" +
                "data=" + data +
                '}';
    }
}

