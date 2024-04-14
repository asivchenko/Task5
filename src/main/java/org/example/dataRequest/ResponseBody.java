package org.example.dataRequest;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ResponseBody {
    @SerializedName("data")
    private ResponseData data;

    public ResponseBody(ResponseData data) {
        this.data = data;
    }

    public ResponseData getData() {
        return data;
    }
}
