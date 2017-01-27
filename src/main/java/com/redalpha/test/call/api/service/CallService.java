package com.redalpha.test.call.api.service;

import com.redalpha.test.call.api.domain.Call;
import com.redalpha.test.call.api.exceptions.CallServiceException;

/**
 * Created by alevkovsky on 1/26/2017.
 */
public interface CallService {

    String addCall(Call call) throws CallServiceException;
}
