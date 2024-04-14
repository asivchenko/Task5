package org.example.dataRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ResponceBuilder {
    // Метод для формирования сообщения об ошибке в формате JSON
    //не использую вроде не надо
    //   public static String buildErrorMessage(String errorMessage) {
    //    ErrorResponse errorResponse = new ErrorResponse(errorMessage);
    //    return errorResponse.toJson();
   // }

    // Методы для формирования ResponseBody в формате JSON
    public static String buildResponseBody(Integer productId,
                                           List<Integer> registerId,
                                           List<Integer> agreementId) {
        ResponseData responseData = new ResponseData(productId, registerId, agreementId);
        ResponseBody responseBody = new ResponseBody(responseData);
        Gson gson = new Gson();
        return gson.toJson(responseBody);
    }
    public static String buildResponse(ResponseData responseData) {
        ResponseBody responseBody = new ResponseBody(responseData);
        Gson gson = new Gson();
        return gson.toJson(responseBody);
    }
    public static String buildResponseRegisterId (Integer registerId)
    {
        ObjectMapper objectMapper =new ObjectMapper();
        ObjectNode responseData =objectMapper.createObjectNode();
        responseData.put("registerId",String.valueOf(registerId) );
        //обернем еще раз
        ObjectNode responseJson =objectMapper.createObjectNode();
        responseJson.set("data",responseData);
        return responseJson.toString();
    }



    public static Timestamp convertDateToTimeStamp (Date date)  //Зачем оно здесь перенеси
    {
        Timestamp timestamp =new Timestamp(date.getTime());
        return timestamp;
    }
}