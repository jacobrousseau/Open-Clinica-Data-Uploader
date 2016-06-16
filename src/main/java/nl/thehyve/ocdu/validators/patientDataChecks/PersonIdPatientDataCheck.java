package nl.thehyve.ocdu.validators.patientDataChecks;

import nl.thehyve.ocdu.models.OCEntities.Subject;
import nl.thehyve.ocdu.models.OcDefinitions.MetaData;
import nl.thehyve.ocdu.models.errors.ValidationErrorMessage;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by bo on 6/15/16.
 */
public class PersonIdPatientDataCheck implements PatientDataCheck {

    @Override
    public ValidationErrorMessage getCorrespondingError(int index, Subject subject, MetaData metaData) {

        String ssid = subject.getSsid();
        String commonMessage = getCommonErrorMessage(index, ssid);

        ValidationErrorMessage error = null;
        String personId = subject.getPersonId();

        if (!StringUtils.isBlank(personId)) {
            error = new ValidationErrorMessage(commonMessage + "Person ID is not needed. ");
        }

        return error;
    }

}
