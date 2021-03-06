package nl.thehyve.ocdu.validators;

import nl.thehyve.ocdu.models.OCEntities.ClinicalData;
import nl.thehyve.ocdu.models.OcDefinitions.MetaData;
import nl.thehyve.ocdu.models.errors.ValidationErrorMessage;
import nl.thehyve.ocdu.validators.checks.DataFieldWidthCheck;
import nl.thehyve.ocdu.validators.crossChecks.*;
import nl.thehyve.ocdu.validators.checks.OcEntityCheck;
import org.openclinica.ws.beans.StudySubjectWithEventsType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotrzakrzewski on 04/05/16.
 */
public class ClinicalDataOcChecks {




    private final List<ClinicalData> clinicalData;
    private final MetaData metadata;

    /**
     * The list of the current event and crf status of all the subjects present in a study.
     */
    private final List<StudySubjectWithEventsType> subjectWithEventsTypeList;

    private  List<OcEntityCheck> recordChecks = new ArrayList<>();
    private  List<ClinicalDataCrossCheck> crossChecks = new ArrayList<>();

    public ClinicalDataOcChecks(MetaData metadata, List<ClinicalData> clinicalData, List<StudySubjectWithEventsType> subjectWithEventsTypes) {
        this.metadata = metadata;
        this.clinicalData = clinicalData;
        this.subjectWithEventsTypeList = subjectWithEventsTypes;
        recordChecks.add(new DataFieldWidthCheck());

        crossChecks.add(new EventExistsCrossCheck());
        crossChecks.add(new DataFieldWidthCrossCheck());
        crossChecks.add(new CrfExistsCrossCheck());
        crossChecks.add(new CRFVersionMatchCrossCheck());
        crossChecks.add(new CrfCouldNotBeVerifiedCrossCheck());
        crossChecks.add(new MultipleEventsCrossCheck());
        crossChecks.add(new MultipleStudiesCrossCheck());
        crossChecks.add(new ItemLengthCrossCheck());
        crossChecks.add(new ItemExistenceCrossCheck());
        crossChecks.add(new MandatoryInCrfCrossCheck());
        crossChecks.add(new DataTypeCrossCheck());
        crossChecks.add(new ValuesNumberCrossCheck());
        crossChecks.add(new RangeChecks());
        crossChecks.add(new SignificanceCrossCheck());
        crossChecks.add(new SsidUniqueCrossCheck());
        crossChecks.add(new EventRepeatCrossCheck());
        crossChecks.add(new CodeListCrossCheck());
    }

    public List<ValidationErrorMessage> getErrors() {
        List<ValidationErrorMessage> errors = new ArrayList<>();
        clinicalData.stream().forEach(cData ->
        {
            List<ValidationErrorMessage> recordErrors = getRecordErrors(cData);
            errors.addAll(recordErrors);
        });
        List<ValidationErrorMessage> interRecordInconsitencies = getCrossCheckErrors(clinicalData);
        errors.addAll(interRecordInconsitencies);
        return errors;
    }

    private List<ValidationErrorMessage> getRecordErrors(ClinicalData data) {
        List<ValidationErrorMessage> errors = new ArrayList<>();
        recordChecks.stream().forEach(
                check -> {
                    ValidationErrorMessage error = check.getCorrespondingError(data, metadata);
                    if (error != null) errors.add(error);
                }
        );
        return errors;
    }

    private List<ValidationErrorMessage> getCrossCheckErrors(List<ClinicalData> data) {
        List<ValidationErrorMessage> errors = new ArrayList<>();
        crossChecks.stream().forEach(
                check -> {
                    ValidationErrorMessage error = check.getCorrespondingError(data, metadata, subjectWithEventsTypeList);
                    if (error != null) errors.add(error);
                }
        );
        return errors;
    }

}
