package com.abnamro.validation;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonValidator {
    private final static Logger LOGGER =  LogManager.getLogger(CommonValidator.class.getName());

    public static boolean commonValidate(String record,int lineNo){
        if(StringUtils.isEmpty(record)){
            //Error -- record no existing
            LOGGER.error("Line:"+lineNo+" Error -- record is empty");
            return  false;
        }

        if(record.length()>303){
            //Invalid format
            LOGGER.error("Line:"+lineNo+" Error -- record is too long");
            return false;
        }

        if(record.length()<73){
            //Invalid format
            LOGGER.error("Line:"+lineNo+" Error -- record is too short");
            return false;
        }

        //check illegal characters
        if(containsIllegals(record)){
            LOGGER.error("Line:"+lineNo+" Error -- record contains illegal character");
            return false;
        }

        return true;
    }

    private static  boolean containsIllegals(String toExamine) {
        Pattern pattern = Pattern.compile("[~#@]");
        Matcher matcher = pattern.matcher(toExamine);
        return matcher.find();
    }
}
