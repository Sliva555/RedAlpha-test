package com.redalpha.test.call.api.dao.impl;

import com.redalpha.test.call.api.dao.CallDao;
import com.redalpha.test.call.api.domain.Call;
import com.redalpha.test.call.api.exceptions.DaoException;
import org.springframework.stereotype.Repository;

import java.io.*;

/**
 * Created by alevkovsky on 1/26/2017.
 */
@Repository
public class CallDaoImpl implements CallDao {

    @Override
    public String save(Call call) throws DaoException {
        String fileName = generateFileName(call);
        File file = new File(fileName);
        String result;
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            bw.write(call.getTelephoneNumber());
            bw.newLine();
            bw.write(call.getDate().toString());
            result = file.getAbsolutePath();
        } catch (IOException e) {
            throw new DaoException(e);
        }
        return result;
    }

    private String generateFileName(Call call) {
        StringBuilder sb = new StringBuilder(call.getLastName().toUpperCase());
        String firstName = call.getFirstName();
        if( firstName != null && !firstName.isEmpty() ) {
            sb.append("_");
            sb.append(firstName.toUpperCase());
        }
        sb.append(".txt");
        return sb.toString();
    }
}
