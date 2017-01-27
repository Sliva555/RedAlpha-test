package com.redalpha.test.call.unit.api.resource

import com.redalpha.test.call.api.domain.Call;
import com.redalpha.test.call.api.resourse.CallResource;
import com.redalpha.test.call.api.service.CallService
import org.springframework.http.HttpStatus;
import spock.lang.Specification;
import spock.lang.Subject;

/**
 * Created by alevkovsky on 1/26/2017.
 */
public class CallResourceSpec extends Specification {
    CallService callService = Mock();

    @Subject
    CallResource callResource = new CallResource(callService);


    def "Should return path of saved file" () {
        given:
        def call = new Call(firstName: "firstName", lastName: "lastName", telephoneNumber: "123456789");
        def filePath = "TEST_PATH.txt"
        when:
        def response = callResource.addCall(call);
        then:
        1 * callService.addCall(call) >> filePath
        response != null;
        response.body == filePath;
        response.statusCode == HttpStatus.OK;
    }
}
