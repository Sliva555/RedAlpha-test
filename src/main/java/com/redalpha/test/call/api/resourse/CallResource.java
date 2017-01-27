package com.redalpha.test.call.api.resourse;

import com.redalpha.test.call.api.domain.Call;
import com.redalpha.test.call.api.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alevkovsky on 1/25/2017.
 */
@RestController
public class CallResource {

    private CallService callService;

    @Autowired
    public CallResource(CallService callService) {
        this.callService = callService;
    }

    @RequestMapping(path = "api/call", method = RequestMethod.POST)
    public ResponseEntity addCall(@RequestBody Call call) {
        String addedCall = callService.addCall(call);
        return ResponseEntity.ok(addedCall);
    }
}
