package nl.thehyve.ocdu.validators.patientDataChecks;

import nl.thehyve.ocdu.models.OCEntities.Subject;
import nl.thehyve.ocdu.models.OcDefinitions.MetaData;
import nl.thehyve.ocdu.models.errors.ValidationErrorMessage;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

/**
 * Created by bo on 6/15/16.
 */
public class DateOfBirthPatientDataCheck implements PatientDataCheck{
    @Override
    public ValidationErrorMessage getCorrespondingError(int index, Subject subject, MetaData metaData) {

        String ssid = subject.getSsid();
        String commonMessage = getCommonErrorMessage(index, ssid);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        dateFormat.setLenient(false);

        ValidationErrorMessage error = null;
        String dob = subject.getDateOfBirth();
        if(!StringUtils.isBlank(dob)) {
            if(dob.length() == 4) {//BIRTH YEAR
                try{
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    int birthYear = Integer.valueOf(dob);
                    if(birthYear > currentYear) {
                        error  = new ValidationErrorMessage(commonMessage+"Birth year format is invalid.");
                    }
                }
                catch (NumberFormatException e) {
                    error  = new ValidationErrorMessage(commonMessage+"Birth year format is invalid.");
                }
            }
            else{//FULL DATE
                try{
                    Date date = dateFormat.parse(dob);
                    Date currentDate = new Date();
                    if(currentDate.before(date)) {
                        error  = new ValidationErrorMessage(commonMessage+"Birth date should be in the past.");
                    }
                }catch (ParseException e) {
                    error  = new ValidationErrorMessage(commonMessage+"Birth date format is invalid.");
                }
            }
        }

        return error;
    }
}
