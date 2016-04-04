package com.zalando.paintshop.enums;

public enum Finish {
    GLOSSY(0), MATTE(1);

    private int code;

    Finish(int code) {
        this.code = code;
    }

    private static Finish getFinishByCode(int code){
        for(Finish value : Finish.values()){
            if(value.code == code) return value;
        }
        return null;
    }

    public static boolean isValidFinishCode(int code) {
        Finish finish = getFinishByCode(code);
        return finish != null;
    }

    public static boolean isGlossy(int code) {
        Finish finish = getFinishByCode(code);
        return finish == Finish.GLOSSY;
    }

    public static boolean isMatte(int code) {
        Finish finish = getFinishByCode(code);
        return finish == Finish.MATTE;
    }
}
