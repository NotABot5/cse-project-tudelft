package commons;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class IngredientUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @ManyToOne
    public Recipe recipe;
    @ManyToOne
    public Ingredient ingredient;
    public int amount;

    //This may or may not be changed in the future to a different structure
    public IngredientUnit unit;

    //Convention: For informal units where the amount IS appropriate (e.g. 2 big handfuls), specify amount as usual
    //  For informal units where the amount IS NOT appropriate (e.g. a pinch), specify amount to be 0

    public IngredientUsage(Recipe recipe, Ingredient ingredient, int amount, IngredientUnit unit)
    {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.amount = amount;
        this.unit = unit;
    }

    @SuppressWarnings("unused")
    public IngredientUsage() {}

    public long getId() {
        return id;
    }

    public Recipe getRecipe() {
        return recipe;
    }


    public Ingredient getIngredient() {
        return ingredient;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public IngredientUnit getUnit() {
        return unit;
    }

    public void setUnit(IngredientUnit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setRecipe(Recipe selectedRecipe) {
        this.recipe = selectedRecipe;
    }

    /**
     * Determines the amount of ingredient needed, when converting to its basic unit
     * @return the amount of ingredient needed after conversion
     */
    public double convertedAmount() {
        if (getUnit().getFormal()) {
            return(getAmount() * getUnit().getConversionFactorToBase());
        }
        else{
            return(0);
        }
    }

    /**
     * Shows amount of ingredient needed, when converting to its basic unit in a readable format
     * @return String containing amount of ingredient in standard unit in readable format
     */
    public String convertedToString() {
        double convertedAmount = convertedAmount();
        StringBuilder res = new StringBuilder();
        if (getAmount() != 0 && getUnit().getType() != UnitType.COUNT) {
            res.append(BigDecimal.valueOf(convertedAmount).stripTrailingZeros().toPlainString());
            res.append(" ");

            if (getUnit().getType() == UnitType.MASS) {
                res.append("grams");
            } else if (getUnit().getType() == UnitType.VOLUME) {
                res.append("milliliters");
            }
        }
        if (!getUnit().getFormal()) {
            res.append(getUnit().getName());
        }
        if (getUnit().getType() != UnitType.COUNT) {res.append(" of ");}
        else {
            res.append(BigDecimal.valueOf(getAmount()).stripTrailingZeros().toPlainString());
            res.append(" ");
        }
        res.append(getIngredient().getName());
        return(res.toString());
    }

    /**
     * Produces string of amount ingredient when converted to a specified unit, given that it is compatible
     * The compatibility conditions are as follows:
     * Both ingredient units are of the same type (MASS, VOLUME, COUNT)
     * Both ingredient units are formal
     * NOTE: targetUnit may not have conversion factor of 0
     * NOTE: Currently, trailing zeros are removed, but converted amounts are not rounded
     * NOTE: For simplicity, Results may miss plural forms if not specified
     * @param targetUnit Unit in which amount of ingredient needed should be expressed
     * @return String converted to specified unit in readable format
     */
    public String convertedToString(IngredientUnit targetUnit) {
        StringBuilder res = new StringBuilder();
        //Check whether unit types are compatible, otherwise ingredient amount cannot be converted
        //If so, mention this incompatibility through to end result
        //Additionally, incompatibility occurs if one or both of the units are informal
        IngredientUnit thisUnit = getUnit();
        if (thisUnit.getType() != targetUnit.getType()
                || !thisUnit.getFormal()
                || !targetUnit.getFormal()) {
            res.append("Unknown quantity of ");
        }
        //Otherwise, convert ingredient amount
        else {
            double baseUnitAmount = convertedAmount();
            double targetAmount = baseUnitAmount / targetUnit.getConversionFactorToBase();
            //Check whether amount of "whole" ingredients is measured and targetUnit has no name
            //Example: 5 apples instead of "5 of apple"
            if (targetUnit.getName().isEmpty() && targetUnit.getType() == UnitType.COUNT) {
                return(convertedToStringShortFormat(targetUnit, res, targetAmount));
            }
            //After conversion, remove trailing zeros if present
            else {
                res.append(BigDecimal.valueOf(targetAmount).stripTrailingZeros().toPlainString());
                res.append(" ");
                res.append(targetUnit.getName());
                res.append(" of ");
            }
        }
        res.append(getIngredient().getName());
        return (res.toString());
    }

    /** Helper method for special case of target unit being "empty count" unit
     *  Example: "5 broccoli" instead of "5 of broccoli"
     * @param targetUnit Unit in which amount of ingredient needed should be expressed
     * @param res Empty StringBuilder
     * @param amount Converted ingredient amount (may contain trailing zeros)
     * @return String converted to specified unit in readable format
     */

    public String convertedToStringShortFormat(IngredientUnit targetUnit, StringBuilder res, double amount) {
        //Strip trailing zeros if present from conversion
        res.append(BigDecimal.valueOf(amount).stripTrailingZeros().toPlainString());
        res.append(" ");
        res.append(getIngredient().getName());
        return(res.toString());
    }

    public String convertedToStringStandard() {
        if (!getUnit().getFormal()) {
            return(convertedToString());
        }
        else {
            switch (getUnit().getType()) {
                case MASS:
                    return(convertedToString(new IngredientUnit("grams", UnitType.MASS, true, 1)));
                case VOLUME:
                    return(convertedToString(new IngredientUnit("milliliters", UnitType.VOLUME, true, 1)));
                case COUNT:
                    return(convertedToString(new IngredientUnit("", UnitType.COUNT, true, 1)));
                default:
                    return(convertedToString());
            }
        }
    }
}
