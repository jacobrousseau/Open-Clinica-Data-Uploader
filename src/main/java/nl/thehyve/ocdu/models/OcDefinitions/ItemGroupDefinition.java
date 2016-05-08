package nl.thehyve.ocdu.models.OcDefinitions;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotrzakrzewski on 01/05/16.
 */
@Entity
public class ItemGroupDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private boolean repeating;

    private String oid;

    private String name;

    private boolean mandatoryInCrf;

    public boolean isMandatoryInCrf() {
        return mandatoryInCrf;
    }

    public void setMandatoryInCrf(boolean mandatoryInCrf) {
        this.mandatoryInCrf = mandatoryInCrf;
    }

    @OneToMany(targetEntity = ItemDefinition.class, cascade = CascadeType.ALL)
    private List<ItemDefinition> items;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ItemDefinition> getItems() {
        return items;
    }

    public void setItems(List<ItemDefinition> items) {
        this.items = items;
    }

    public boolean isRepeating() {
        return repeating;
    }

    public void setRepeating(boolean repeating) {
        this.repeating = repeating;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemGroupDefinition() {
    }

    public ItemGroupDefinition(ItemGroupDefinition prototype) {
        this.repeating = prototype.isRepeating();
        this.name = prototype.getName();
        this.oid = prototype.getOid();
        this.items = new ArrayList<>(prototype.getItems());
    }

}
