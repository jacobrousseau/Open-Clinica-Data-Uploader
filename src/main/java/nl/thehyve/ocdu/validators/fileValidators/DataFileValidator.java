package nl.thehyve.ocdu.validators.fileValidators;

import nl.thehyve.ocdu.factories.ClinicalDataFactory;
import nl.thehyve.ocdu.models.OCEntities.ClinicalData;
import nl.thehyve.ocdu.models.OCEntities.Study;
import nl.thehyve.ocdu.models.errors.FileFormatError;
import nl.thehyve.ocdu.models.errors.IncorrectNumberOfStudies;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by piotrzakrzewski on 11/04/16.
 */
public class DataFileValidator extends GenericFileValidator {

    private final List<Study> studies;

    public DataFileValidator(List<Study> studies) {
        super(ClinicalDataFactory.MANDATORY_HEADERS, new String[]{ClinicalDataFactory.EventRepeat});
        this.studies = studies;
    }

    @Override
    public void validateFile(Path file) {
        super.validateFile(file);
        try {
            String header = getHeader(file);
            String[] body = getBody(file);
            int columnIndex = getColumnIndex(header, ClinicalDataFactory.STUDY);
            if (columnIndex != -1) {
                Set<String> usedStudies = getUsedStudies(body, columnIndex);
                onlyOneStudy(usedStudies);
                studyExists(usedStudies);
            }
            if (getErrorMessages().size() > 0 ) {
                setValid(false);
            }
            //columnNamesWellFormed(header);
        } catch (IOException e) {
            setValid(false);
            addError(new FileFormatError("Internal Server Error prevented parsing the file. Contact administrator."));
            e.printStackTrace();
        }
    }

    private void studyExists(Set<String> usedStudies) {
        for (String studyName: usedStudies) {
            if (!studies.stream().anyMatch(study -> study.getName().equals(studyName))) {
                FileFormatError error = new FileFormatError("Study: "+ studyName+" does not exist");
                addError(error);
            }
        }
    }

    private void onlyOneStudy(Set<String> usedStudies) {
        if (usedStudies.size() != 1) {
            FileFormatError error = new FileFormatError("Data file must contain one and only one study");
            addError(error);
        }
    }

    private Set<String> getUsedStudies(String[] body, int columnIndex) {
        Set<String> usedStudies = new HashSet<>();
        for (int i = 0; i < body.length; i++) {
            String line = body[i];
            String[] split = line.split(ClinicalDataFactory.FILE_SEPARATOR);
            if (split.length > columnIndex) {
                String study = split[columnIndex];
                usedStudies.add(study);
            }
        }
        return usedStudies;
    }

    private void columnNamesWellFormed(String header) {
        // If item name contains "_" the part to the right of it must be a positive integer
        List<String> split = splitLine(header);
        split.stream().filter(columnName -> columnName.contains("_")).forEach(columnName -> {
            String[] itemSplit = columnName.split("_");
            int itemSplitLen = itemSplit.length;
            String lastToken = itemSplit[itemSplitLen - 1];
            if (!super.isInteger(lastToken)) {
                addError(new FileFormatError("Item name incorrect: " + columnName + "If column name contains _ part to the " +
                        "right of it must be an integer - to indicate group repeat."));
                setValid(false);
            } else {
                int intRep = Integer.parseInt(lastToken);
                if (intRep < 1) {
                    addError(new FileFormatError("Group repeat in item name: " + columnName + " incorrect. Group repeat must be greater than 0"));
                    setValid(false);
                }
            }
        });

    }

}
