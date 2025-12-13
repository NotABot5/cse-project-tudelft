package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class IngredientNameChange {

    @Id
    @GeneratedValue
    private Long id;
    private long ingredientId;
    private String oldName;
    private String newName;

@SuppressWarnings("unused")
    public IngredientNameChange() {}
    public IngredientNameChange(long ingredientId, String oldName, String newName) {
        this.ingredientId = ingredientId;
        this.oldName = oldName;
        this.newName = newName;
    }
    public Long getId() {return id;}
    public long getIngredientId() { return ingredientId; }
    public String getOldName() { return oldName; }
    public String getNewName() { return newName; }
}

