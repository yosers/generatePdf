package com.test.maybank.demo.util.file;

public class Util {

    private Util() {
    }

    public static String getTargetTypeByFileExtension(String fileName) {
        String targetType = "application/octet-stream";

         if(fileName.endsWith(".pdf"))
            targetType = "application/pdf";

        return targetType;
    }

}
