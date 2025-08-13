package com.rookies4.MySpringBootLab.config;


public class MyEnviroment {
    String mode;

    public MyEnviroment(String mode){
        this.mode = mode;

    }

    @Override
    public String toString(){
        return "현재모드 : " + mode;
    }}
