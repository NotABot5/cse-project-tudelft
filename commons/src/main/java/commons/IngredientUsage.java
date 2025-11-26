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
    public String unit;

    public IngredientUsage(Recipe recipe, Ingredient ingredient, int amount, String unit)
    {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.amount = amount;
        this.unit = unit;
    }

    @SuppressWarnings("unused")
    private IngredientUsage() {}

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
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
}
