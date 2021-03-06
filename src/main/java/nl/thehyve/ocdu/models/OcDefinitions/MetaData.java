package nl.thehyve.ocdu.models.OcDefinitions;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotrzakrzewski on 01/05/16.
 */
@Entity
public class MetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String studyIdentifier; //TODO: shouldn't it be Study entity? Should we serialize Study as well?

    @OneToMany(targetEntity = EventDefinition.class, cascade = CascadeType.ALL)
    private List<EventDefinition> eventDefinitions;

    @OneToMany(targetEntity = ItemGroupDefinition.class, cascade = CascadeType.ALL )
    private List<ItemGroupDefinition> itemGroupDefinitions;

    @OneToMany(targetEntity = CodeListDefinition.class, cascade = CascadeType.ALL )
    private List<CodeListDefinition> codeListDefinitions;


    public void addEventDefinition(EventDefinition eventDef) {
        eventDefinitions.add(eventDef);
    }

    public void removeEventDefinition(EventDefinition eventDef) {
        eventDefinitions.remove(eventDef);
    }


    public void addItemGroupDefinition(ItemGroupDefinition itemGroupDef) {
        itemGroupDefinitions.add(itemGroupDef);
    }

    public void removeItemGroupDefinition(ItemGroupDefinition itemGroupDef) {
        itemGroupDefinitions.remove(itemGroupDef);
    }

    public void addCodeListDefinition(CodeListDefinition codeListDefinition) {
        codeListDefinitions.add(codeListDefinition);
    }

    public void removeCodeListDefinition(CodeListDefinition codeListDefinition) {
        codeListDefinitions.remove(codeListDefinition);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudyIdentifier() {
        return studyIdentifier;
    }

    public void setStudyIdentifier(String studyIdentifier) {
        this.studyIdentifier = studyIdentifier;
    }

    public List<EventDefinition> getEventDefinitions() {
        return eventDefinitions;
    }

    public void setEventDefinitions(List<EventDefinition> eventDefinitions) {
        this.eventDefinitions = eventDefinitions;
    }

    public List<ItemGroupDefinition> getItemGroupDefinitions() {
        return itemGroupDefinitions;
    }

    public void setItemGroupDefinitions(List<ItemGroupDefinition> itemGroupDefinitions) {
        this.itemGroupDefinitions = itemGroupDefinitions;
    }

    public List<CodeListDefinition> getCodeListDefinitions() {
        return codeListDefinitions;
    }

    public void setCodeListDefinitions(List<CodeListDefinition> codeListDefinitions) {
        this.codeListDefinitions = codeListDefinitions;
    }

    public MetaData() {
        this.codeListDefinitions = new ArrayList<>();
        this.itemGroupDefinitions = new ArrayList<>();
        this.eventDefinitions = new ArrayList<>();
    }
}
