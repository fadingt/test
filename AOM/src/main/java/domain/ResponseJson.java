package domain;

import java.util.List;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 5/8/2021 3:23 PM
 */
public class ResponseJson {
    private int status;
    private String appcode;
    private String uuid;
    private String tradeName;
    private String message;
    private Data data;
    private ErrorObject errorObject;

}
class Data{
    private int totalPage;
    private List<String> record;
    private int pageSize;
    private int currentPage;
    private int totalRecord;

}
class ErrorObject{
    private String errorType;
    private String errorCode;
    private String message;
    private String errorParams;
    private String logfile;
    private String uuid;
    private String tradeName;
    private boolean success;
}
