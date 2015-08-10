package exception;

import model.Automobile;

/**
 * Created by Tangent Chang on 6/22/15.
 */
class ModelExceptionHelper {
    protected void fix(int errorNo, Automobile model){
        switch(errorNo){
            case 101:
                model.setBasePrice(0);
                break;
            case 103: break;
            case 105: break;
            case 107: break;
            case 109:
                model.setModelName("Default name");
                break;
        }
    }

}
