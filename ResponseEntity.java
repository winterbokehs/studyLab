package com.example.myblog.pojo;

/**
 * 服务器响应数据对象
 *
 */
public class ResponseEntity {
    int code;//响应状态码
    String message;//响应的消息
    Object data;//响应数据体
    public  ResponseEntity(){}

    public ResponseEntity(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseEntity OK(){
        ResponseEntity responseEntity=new ResponseEntity();
        responseEntity.code=200;
        responseEntity.message="success";
        return responseEntity;
    }
    public static ResponseEntity OK(Object data){
        ResponseEntity responseEntity=new ResponseEntity();
        responseEntity.code=200;
        responseEntity.message="success";
        responseEntity.data = data;
        return responseEntity;
    }

    public static ResponseEntity ERROR(String s){
        ResponseEntity responseEntity=new ResponseEntity();
        responseEntity.code=500;
        responseEntity.message=s;
        return  responseEntity;
    }

    public static ResponseEntity ERROR(){
        ResponseEntity responseEntity=new ResponseEntity();
        responseEntity.code=500;
        responseEntity.message="error";
        return  responseEntity;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
