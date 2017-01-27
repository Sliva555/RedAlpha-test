package com.redalpha.test.call.unit.api.util

import com.redalpha.test.call.api.util.CallValidator
import spock.lang.Specification
import spock.lang.Subject

/**
 * Created by alevkovsky on 1/26/2017.
 */
class CallValidatorSpec extends Specification{

    @Subject
    def callValidator = new CallValidator()

    def "allowedPhoneCharacters field should be correctly initialized and contains all necessary symbols"() {
        given:
        def template = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ', '-', ')', '(']
        expect:
        callValidator.allowedPhoneCharacters.size() == template.size()
        def result = true;
        for (Character symbol  : template) {
            if(!callValidator.allowedPhoneCharacters.contains(symbol)) {
                result = false
            }
        }
        result == true
    }

    def "validateFirstName() should do nothing if firstName is null"(){
        given:
        def errorMessages = []
        when:
        callValidator.validateFirstName(null, errorMessages)
        then:
        errorMessages.isEmpty()
    }

    def "validateFirstName() should do nothing if firstName is empty string"(){
        given:
        def errorMessages = []
        when:
        callValidator.validateFirstName("", errorMessages)
        then:
        errorMessages.isEmpty()
    }

    def "validateFirstName() should do nothing if firstName is a string with length less than 30"(){
        given:
        def errorMessages = []
        when:
        callValidator.validateFirstName("firstName", errorMessages)
        then:
        errorMessages.isEmpty()
    }

    def "validateFirstName() should add errorMessage if firstName length is grate than 30 characters"(){
        given:
        def firstName =  "Test123456789012345678901234567"
        def errorMessages = []
        def message = "You enter: " + firstName + ". First name max length is 30 characters."
        when:
        callValidator.validateFirstName(firstName, errorMessages)
        then:
        errorMessages.size() == 1
        errorMessages.get(0) == message
    }

    def "validateLastName() should add errorMessage if lastName is null"(){
        given:
        def lastName = null
        def errorMessages = []
        def message = "You enter: " + lastName + ". Last name cannot be empty or null."
        when:
        callValidator.validateLastName(lastName, errorMessages)
        then:
        errorMessages.size() == 1
        errorMessages.get(0) == message
    }

    def "validateLastName() should add errorMessage if lastName is empty string"(){
        given:
        def lastName = ""
        def errorMessages = []
        def message = "You enter: " + lastName + ". Last name cannot be empty or null."
        when:
        callValidator.validateLastName(lastName, errorMessages)
        then:
        errorMessages.size() == 1
        errorMessages.get(0) == message
    }

    def "validateLastName() should do nothing if lastName is a string with length less than 30 characters"(){
        given:
        def lastName = "lastName"
        def errorMessages = []
        when:
        callValidator.validateLastName(lastName, errorMessages)
        then:
        errorMessages.size() == 0
    }

    def "isBlankField() should return true if input string is null"(){
        given:
        def input = null
        when:
        def result = callValidator.isBlankField(input)
        then:
        result == true
    }

    def "isBlankField() should return true if input string is empty string"(){
        given:
        def input = ""
        when:
        def result = callValidator.isBlankField(input)
        then:
        result == true
    }

    def "isBlankField() should return false if input string contains 1 or more characters"(){
        given:
        def input = "test"
        when:
        def result = callValidator.isBlankField(input)
        then:
        result == false
    }

    def "isValidBracketCount() should return false if number of start and end brackets not the same"() {
        given:
        def input = "(111) 222 (333"
        when:
        def result = callValidator.isValidBracketCount(input)
        then:
        result == false
    }

    def "isValidBracketCount() should return false if order of start and end brackets not valid"() {
        given:
        def input = ")111( 222 (333)"
        when:
        def result = callValidator.isValidBracketCount(input)
        then:
        result == false
    }

    def "isValidBracketCount() should return true if order and count of start and end brackets are valid"() {
        given:
        def input = "(111) 222 (333)"
        when:
        def result = callValidator.isValidBracketCount(input)
        then:
        result == true
    }

    def "replaceFirstPlus() should return the same string if first symbol not a '+' "(){
        given:
        def input = "00420111+222333"
        when:
        def result = callValidator.replaceFirstPlus(input)
        then:
        result == input
    }

    def "replaceFirstPlus() should replace '+' by '00' if the first symbol of string is '+' "(){
        given:
        def input = "+004201+11222333"
        def expected = "00004201+11222333"
        when:
        def result = callValidator.replaceFirstPlus(input)
        then:
        result == expected
    }

    def "removeNonDigitSymbols() should return string without spaces, '-', '(', ')' "() {
        given:
        def input = "(420)-111 2 2 -2 (33)-3"
        def expected = "420111222333"
        when:
        def result = callValidator.removeNonDigitSymbols(input)
        then:
        result == expected
    }

    def "isAllAllowedSymbols() should return false if not all of the symbols in input string are allowed"() {
        given:
        def input = "ssa00s42011122ss"
        when:
        def result = callValidator.isAllAllowedSymbols(input)
        then:
        result == false
    }

    def "isAllAllowedSymbols() should return true if  all of the symbols in input string are allowed"() {
        given:
        def input = "(420)-111222333"
        when:
        def result = callValidator.isAllAllowedSymbols(input)
        then:
        result == true
    }

    def "validatePhone() should add errorMessage if telephoneNumber is incorrect"() {
        given:
        def telephoneNumbers = [null,
                                "",
                                "dfsadfaads",
                                "+4201112+2333",
                                "1234567",
                                "12345678901234567"]
        def errorMessages = []
        when:
        for (String phone  : telephoneNumbers) {
            callValidator.validatePhone(phone, errorMessages)
        }
        then:
        errorMessages.size() == telephoneNumbers.size()
    }

    def "validatePhone() should do nothing if telephoneNumber is correct"() {
        given:
        def telephoneNumbers = ["+(420) 111 222 333",
                                "+(420)-111222333",
                                "+420111222333",
                                "00420111222333",
                                "(111) 222 (333)",
                                "123456789"]
        def errorMessages = []
        when:
        for (String phone  : telephoneNumbers) {
            callValidator.validatePhone(phone, errorMessages)
        }
        then:
        errorMessages.isEmpty()
    }

}


