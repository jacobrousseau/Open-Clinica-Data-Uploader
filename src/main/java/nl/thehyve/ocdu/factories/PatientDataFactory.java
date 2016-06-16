package nl.thehyve.ocdu.factories;

import nl.thehyve.ocdu.models.OcUser;
import nl.thehyve.ocdu.models.UploadSession;
import nl.thehyve.ocdu.models.OCEntities.Subject;
import nl.thehyve.ocdu.repositories.SubjectRepository;
import org.thymeleaf.expression.Dates;

import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by bo on 6/6/16.
 */
public class PatientDataFactory extends UserSubmittedDataFactory {

    public final static String STUDY_SUBJECT_ID = "StudySubjectID";
    public final static String GENDER = "Gender";
    public final static String DATE_OF_BIRTH = "Date of Birth";
    public final static String DATE_OF_ENROLLMENT = "Date of Enrollment";
    public final static String PERSON_ID = "Person ID";
    public final static String SECONDARY_ID = "Secondary ID";
    public final static String STUDY = "Study";
    public final static String SITE = "Site";
    public final static String[] MANDATORY_HEADERS = {STUDY_SUBJECT_ID, STUDY, SITE};

    public PatientDataFactory(OcUser user, UploadSession submission) {
        super(user, submission);
    }

    public List<Subject> createPatientData(Path patientFile) {
        Optional<String[]> headerRow = getHeaderRow(patientFile);
        if (headerRow.isPresent()) {
            Map<String, Integer> columnsIndex = createColumnsIndexMap(headerRow.get());

            try (Stream<String> lines = Files.lines(patientFile)) {
                return lines.skip(1)
                        .map(UserSubmittedDataFactory::parseLine)
                        .map(row -> mapRow(row, columnsIndex))
                        .collect(Collectors.toList());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Patient file is empty.");
        }

    }

    private Subject mapRow(String[] row, Map<String, Integer> columnsIndex) {
        Subject subject = new Subject();
        subject.setOwner(getUser());
        subject.setSubmission(getSubmission());
        setValue(row, columnsIndex, STUDY_SUBJECT_ID, subject::setSsid);
        setValue(row, columnsIndex, GENDER, subject::setGender);
        setValue(row, columnsIndex, DATE_OF_BIRTH, subject::setDateOfBirth);
        setValue(row, columnsIndex, PERSON_ID, subject::setPersonId);
        setValue(row, columnsIndex, DATE_OF_ENROLLMENT, subject::setDateOfEnrollment);
        setValue(row, columnsIndex, SECONDARY_ID, subject::setSecondaryId);
        setValue(row, columnsIndex, STUDY, subject::setStudy);
        setValue(row, columnsIndex, SITE, subject::setSite);
        return subject;
    }

    private void setValue(String[] row, Map<String, Integer> columnsIndex, String columnName,
                            Consumer<String> consumer) {
        if (!columnsIndex.containsKey(columnName)) {
            return;
        }

        String cellValue = row[columnsIndex.get(columnName)];
        consumer.accept(cellValue);
    }


}
