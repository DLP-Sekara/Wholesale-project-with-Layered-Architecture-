package Validation;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class Validation {
    public static Object validate(LinkedHashMap<TextField, Pattern> map, Button btn) {
        btn.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.setStyle("-fx-border-color: red ;-fx-text-fill: red ; -fx-border-width: 1px ;");
                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-border-color: #32ff7e ; -fx-border-width: 1px ;");
        }
        btn.setDisable(false);
        return true;
    }

    public static Object validateCustomer(LinkedHashMap<TextField, Pattern> map, Button saveButton) {
        saveButton.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.setStyle("-fx-border-color: red ;-fx-text-fill: red ; -fx-border-width: 1px ;");
                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-border-color: #32ff7e ; -fx-border-width: 1px ;");
        }
        saveButton.setDisable(false);
        return true;
    }
}
