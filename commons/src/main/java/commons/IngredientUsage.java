package commons;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
    public int convertedAmount() {
        if (getUnit().getFormal()) {
            return (int) (getAmount() * getUnit().getConversionFactorToBase());
        }
        else{
            return(0);
        }
    }

    /**
     * Shows amount of ingredient needed, when converting to its basic unit in a readable format
     * @return
     */
    public String convertedAmountToString() {
        int convertedAmount = convertedAmount();
        StringBuilder res = new StringBuilder();
        if (getAmount() != 0 && getUnit().getType() != UnitType.COUNT) {
            res.append(convertedAmount);
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
            res.append(getAmount());
            res.append(" ");
        }
        res.append(getIngredient().getName());
        return(res.toString());
    }

}
