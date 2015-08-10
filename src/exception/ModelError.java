package exception;

/**
 * Created by Tangent Chang on 6/22/15.
 */
public enum ModelError implements IErrorCode {

    MISSING_MODEL_PRICE(101, "no CarConfigApp_client.CarConfigApp_server.model price"),
    MISSING_OPTIONSET_DATA(103, "lack of some option set data"),
    MISSING_OPTION_DATA(105, "lack of some option data"),
    MISSING_FILENAME(107, "no such a file"),
    MISSING_MODEL_NAME(109, "no CarConfigApp_client.CarConfigApp_server.model name");

    private final int errorNo;
    private final String errorMsg;

    /*private ModelError(int number) {
        errorNo = number;
    }*/
    private ModelError(int number, String message){
        errorNo = number;
        errorMsg = message;
    }

    @Override
    public int getNumber() {
        return errorNo;
    }
    public String getMessage(){
        return errorMsg;
    }
}
