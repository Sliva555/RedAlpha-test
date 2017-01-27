package com.redalpha.test.call.api.service.Impl;

import com.redalpha.test.call.api.dao.CallDao;
import com.redalpha.test.call.api.domain.Call;
import com.redalpha.test.call.api.exceptions.CallServiceException;
import com.redalpha.test.call.api.service.CallService;
import com.redalpha.test.call.api.util.CallValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by alevkovsky on 1/26/2017.
 */
@Service
public class CallServiceImpl implements CallService {
    private CallValidator callValidator;
    private CallDao callDao;

    @Autowired
    public CallServiceImpl(CallValidator callValidator, CallDao callDao) {
        this.callValidator = callValidator;
        this.callDao = callDao;
    }

    @Override
    public String addCall(Call call) {
        callValidator.validate(call);
        prepareTelephoneNumber(call);
        return callDao.save(call);
    }

    private void prepareTelephoneNumber(Call call) {
        String normalizeTelephoneNumber = normalizeTelephoneNumber(call.getTelephoneNumber());
        call.setTelephoneNumber(normalizeTelephoneNumber);
    }

    private String normalizeTelephoneNumber(String telephoneNumber) {
        switch (telephoneNumber.length()) {
            case 9:
                telephoneNumber = "00420" + telephoneNumber;
                break;
            case 11:
                telephoneNumber = "00420" + telephoneNumber.substring(2, 11);
                break;
            case 12:
                telephoneNumber = "00" + telephoneNumber;
                break;
            case 14:
                break;
            default:
                throw new CallServiceException(CallValidator.PHONE_NORMALIZE_MESSAGE);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(telephoneNumber.substring(0, 5));
        sb.append(" ");
        int i = 5;
        while (i < 14) {
            sb.append(telephoneNumber.substring(i, i += 3));
            sb.append(" ");
        }
        return sb.toString();
    }
}
