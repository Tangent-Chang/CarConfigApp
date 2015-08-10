package scale;

/**
 * Created by Tangent Chang on 7/3/15.
 */
public enum FunctionCode {
    EDIT_OPTION_NAME(01),
    EDIT_OPTION_PRICE(03),
    ADD_OPTION(05),
    DELETE_OPTION(07);

    private final int editNo;

    private FunctionCode(int number){
        editNo = number;
    }

    public int getNo(){
        return editNo;
    }
}
