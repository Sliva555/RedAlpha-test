package com.redalpha.test.call.api.util;

import com.redalpha.test.call.api.domain.Call;
import com.redalpha.test.call.api.exceptions.CallValidatorException;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by alevkovsky on 1/26/2017.
 */
@Component
public class CallValidator {
    public static final String PHONE_MESSAGE = "Unsupported phone format.";
    public static final String PHONE_BRACKETS_MESSAGE = "The  telephone number must have the same number of start and end brackets and the correct sequence of brackets";
    public static final String PHONE_EMPTY_MESSAGE = "Telephone number cannot be empty or null.";
    public static final String PHONE_NORMALIZE_MESSAGE = "Can't normalize telephone number. Unsupported phone format.";
    public static final String FIRST_NAME_LENGTH_MESSAGE = "First name max length is 30 characters.";
    public static final String LAST_NAME_LENGTH_MESSAGE = "Last name max length is 30 characters.";
    public static final String LAST_NAME_EMPTY_MESSAGE = "Last name cannot be empty or null.";
    public static final String ERROR_MESSAGE_TEMPLATE = "You enter: %s. %s";
    public static final int FIRST_NAME_MAX_LENGTH = 30;
    public static final int LAST_NAME_MAX_LENGTH = 30;

    private final Set<Character> allowedPhoneCharacters = new HashSet<>();

    public CallValidator() {
        allowedPhoneCharacters.addAll(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ', '-', ')', '('));
    }

    public void validate(Call call) {
        List<String> errorMessages = new LinkedList<>();
        validateFirstName(call.getFirstName(), errorMessages);
        validateLastName(call.getLastName(), errorMessages);
        String processedPhone = validatePhone(call.getTelephoneNumber(), errorMessages);
        if(!errorMessages.isEmpty()) {
            throw new CallValidatorException(errorMessages.toString());
        }
        call.setTelephoneNumber(processedPhone);
    }

    private void validateLastName(String lastName, List<String> errorMessages) {
        if(isBlankField(lastName)) {
            String message = String.format(ERROR_MESSAGE_TEMPLATE, lastName, LAST_NAME_EMPTY_MESSAGE);
            errorMessages.add(message);
            return;
        }

        if(checkMaxLastNameLength(lastName)) {
            String message = String.format(ERROR_MESSAGE_TEMPLATE, lastName, LAST_NAME_LENGTH_MESSAGE);
            errorMessages.add(message);
        }
    }

    private void validateFirstName(String firstName, List<String> errorMessages) {
        if(firstName != null && checkMaxFirstNameLength(firstName)) {
            String message = String.format(ERROR_MESSAGE_TEMPLATE, firstName, FIRST_NAME_LENGTH_MESSAGE);
            errorMessages.add(message);
        }
    }

    private String validatePhone(String telephoneNumber, List<String> errorMessages) {
        String processedPhone = telephoneNumber;
        if(isBlankField(processedPhone)) {
            String message = String.format(ERROR_MESSAGE_TEMPLATE, telephoneNumber, PHONE_EMPTY_MESSAGE);
            errorMessages.add(message);
            return null;
        }

        processedPhone = replaceFirstPlus(processedPhone);

        if(!isAllAllowedSymbols(processedPhone)) {
            String message = String.format(ERROR_MESSAGE_TEMPLATE, telephoneNumber, PHONE_MESSAGE);
            errorMessages.add(message);
            return null;
        }

        if(!isValidBracketCount(processedPhone)) {
            String message = String.format(ERROR_MESSAGE_TEMPLATE, telephoneNumber, PHONE_BRACKETS_MESSAGE);
            errorMessages.add(message);
            return null;
        }

        processedPhone = removeNonDigitSymbols(processedPhone);

        if(processedPhone.length() < 9 || processedPhone.length() > 14) {
            String message = String.format(ERROR_MESSAGE_TEMPLATE, telephoneNumber, PHONE_MESSAGE);
            errorMessages.add(message);
            return null;
        }

        return processedPhone;
    }

    private String removeNonDigitSymbols(String telephoneNumber) {
        telephoneNumber = telephoneNumber.replaceAll("[- ()]+", "");
        return telephoneNumber;
    }

    private boolean isAllAllowedSymbols(String telephoneNumber) {
        char[] chars = telephoneNumber.toCharArray();
        for (char aChar : chars) {
            if(!allowedPhoneCharacters.contains(aChar)) {
                return false;
            }
        }
        return true;
    }

    private String replaceFirstPlus(String telephoneNumber) {
        if(telephoneNumber.startsWith("+")) {
            telephoneNumber = telephoneNumber.replaceFirst("[+]", "00");
        }
        return telephoneNumber;
    }

    private boolean isValidBracketCount(String telephoneNumber) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < telephoneNumber.length(); i++) {
            char c = telephoneNumber.charAt(i);
            if ( '(' == c ) {
                stack.push(c);
            } else if ( ')' == c ) {
                if (!stack.isEmpty()) {
                    stack.pop();
                    continue;
                }
                return false;
            }
        }
        return stack.isEmpty();
    }

    private boolean isBlankField(String field) {
        return field == null || field.isEmpty();
    }

    private boolean checkMaxFirstNameLength(String firstName) {
        return firstName.length() >= FIRST_NAME_MAX_LENGTH;
    }

    private boolean checkMaxLastNameLength(String lastName) {
        return lastName.length() >= LAST_NAME_MAX_LENGTH;
    }
}
