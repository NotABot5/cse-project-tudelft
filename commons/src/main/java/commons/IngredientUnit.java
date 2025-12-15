package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class IngredientUnit {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    //Indicates type of unit i.e. what is measured with unit
    private UnitType type;

    //TRUE if and only if unit is formal, informal if FALSE
    private boolean isFormal;

    //Conversion factor to be multiplied with amount in IngredientUsage to go to "base" unit
    //MASS: grams, VOLUME: milliliters, COUNT: no base unit (so conversion factor always 1)
    private double conversionFactorToBase;

    public IngredientUnit(String name, UnitType type, boolean isFormal, double conversionFactorToBase) {
        this.name = name;
        this.type = type;
        this.isFormal = isFormal;
        if (isFormal) {
            this.conversionFactorToBase = conversionFactorToBase;
        }
        else{
            this.conversionFactorToBase = 0;
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnitType getType() {
        return type;
    }

    public void setType(UnitType type) {
        this.type = type;
    }

    public boolean getFormal() {
        return isFormal;
    }

    public void setFormal(boolean formal) {
        isFormal = formal;
    }

    public double getConversionFactorToBase() {
        return conversionFactorToBase;
    }

    /**
     * Setter for conversionFactorToBase
     * @param newConversionFactor new conversion factor to be set
     * @return true if unit was formal (and thus conversion factor was changed), false if informal
     */
    public boolean setConversionFactorToBase(double newConversionFactor) {
        if (getFormal()) {
            this.conversionFactorToBase = newConversionFactor;
            return(true);
        }
        return(false);
    }


    /**
     * Override of toString method
     * @return name of unit along with formality
     */
    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append("The ");
        if (getFormal()) {
            res.append("formal measurement unit ");
        }
        else {
            res.append("informal measurement unit ");
        }
        res.append(getName());
        return(res.toString());
    }
}