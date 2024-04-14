package org.example.dataRequest;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ErrorResponse {
    @SerializedName("error")
    private String error;
    public ErrorResponse(String error) {
        this.error = error;
    }
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
