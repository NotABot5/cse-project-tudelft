package commons;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String name;
    public String lang;
    @ElementCollection
    @OrderColumn(name = "step")
    public List<String> preparation = new ArrayList<>();

    public Recipe(String name, String lang, List<String> preparation)
    {
        this.name = name;
        this.lang = lang;
        this.preparation = new ArrayList<>(preparation);
    }

    @SuppressWarnings("unused")
    private Recipe() {}

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

    public Long getId()   {  return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getLang() { return lang; }

    public void setLang(String lang) { this.lang = lang; }

    public List<String> getPreparation() { return preparation; }

    public void setPreparation(List<String> preparation) { this.preparation =
            new ArrayList <>(preparation); }
    }

