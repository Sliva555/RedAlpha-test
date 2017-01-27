package com.redalpha.test.call.unit.api.service

import com.redalpha.test.call.api.dao.CallDao
import com.redalpha.test.call.api.domain.Call
import com.redalpha.test.call.api.service.Impl.CallServiceImpl
import com.redalpha.test.call.api.util.CallValidator
import spock.lang.Specification
import spock.lang.Subject

/**
 * Created by alevkovsky on 1/26/2017.
 */
class CallServiceImplSpec extends Specification{
    CallValidator callValidator = Mock();
    CallDao callDao = Mock();

    @Subject
    CallServiceImpl callService = new CallServiceImpl(callValidator, callDao)

    def "normalizeTelephoneNumber() should return normalized telephone number"() {
        given:
        def telephoneNumbers = ["00420111222333",
                                "420111222333",
                                "111222333"]

        def resPhones = []
        def expected = "00420111222333"
        when:
        for (String phone : telephoneNumbers) {
            def resPhone = callService.normalizeTelephoneNumber(phone)
            resPhones.add(resPhone)
        }
        then:
        for (String phone  : resPhones) {
            phone == expected
        }
    }

}
