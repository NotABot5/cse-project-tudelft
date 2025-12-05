package commons;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Set;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

/**
 * Entity Ingredient implements Comparable in order to make sorting of ingredients possible
 * Sorting ingredients is required for one of the backlog requirements, so hence why it is implemented here
 */
@Entity
public class Ingredient implements Comparable<Ingredient> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String name;
    public double protein;
    public double fat;
    public double carbs;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<IngredientUsage> usedInRecipes;

    public Ingredient(String name, double protein, double fat, double carbs)
    {
        this.name = name;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    @SuppressWarnings("unused")
    public Ingredient() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
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

    /**
     * Implements compareTo to make Ingredients comparable
     * Compares Ingredients by name
     * @param other the Ingredient with which is compared.
     * @return 0 if Ingredients are "equal" in terms of name, -1 if other has a greater value or 1 if other has a smaller value
     */
    @Override
    public int compareTo(Ingredient other) {
        if(getName() == null) {
            throw new NullPointerException("Name of ingredient (used to call comparing method) is null");
        }
        if(other.getName() == null) {
            throw new NullPointerException("Name of ingredient (given as argument in comparing method) is null");
        }
        int indicatorValue = getName().compareTo(other.getName());
        return Integer.compare(indicatorValue, 0);
    }
}
