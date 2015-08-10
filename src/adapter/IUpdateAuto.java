package adapter;

/**
 * Created by Tangent Chang on 6/20/15.
 */
public interface IUpdateAuto {
    public void updateOptionSetName(String modelName, String optionsetName, String newName);
    public void updateOptionPrice(String modelName, String OptionName, String option, float newPice);
}
