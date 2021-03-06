package nl.thehyve.ocdu.validators.checks;

import nl.thehyve.ocdu.models.OCEntities.ClinicalData;
import nl.thehyve.ocdu.models.OcDefinitions.MetaData;
import nl.thehyve.ocdu.models.OCEntities.OcEntity;
import nl.thehyve.ocdu.models.errors.ValidationErrorMessage;

import static nl.thehyve.ocdu.models.errors.ValidationErrorMessage.generateOffendingValueString;

/**
 * Created by piotrzakrzewski on 04/05/16.
 */
public class DataFieldWidthCheck implements OcEntityCheck {
    //TODO: refactor as a CrossCheck - there is no need for OcEntityCheck interface at all
    public static final int ITEM_MAX_LENGTH = 4000; //TODO: make configurable

    @Override
    public ValidationErrorMessage getCorrespondingError(OcEntity dataRecord, MetaData metaData) {
        ClinicalData data = (ClinicalData) dataRecord;
        ValidationErrorMessage error = new ValidationErrorMessage("One or more values in your " +
                "data violate Open Clinica field width constraints");
        String item = data.getItem();
        boolean itemLengthViolated = item.length() > ITEM_MAX_LENGTH;
        if (itemLengthViolated) {
            error.addOffendingValue(generateOffendingValueString(data, item));
        }
        if (itemLengthViolated) {
            return error;
        } else return null;
    }

}
