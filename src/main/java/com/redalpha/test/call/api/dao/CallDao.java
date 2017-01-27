package com.redalpha.test.call.api.dao;

import com.redalpha.test.call.api.domain.Call;
import com.redalpha.test.call.api.exceptions.DaoException;

/**
 * Created by alevkovsky on 1/26/2017.
 */
public interface CallDao {
    String save(Call call) throws DaoException;
}
