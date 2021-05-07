package com.sanmukk.resume.handle;

import com.sanmukk.resume.utils.SHA256Util;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AdminTypeHandler<T> extends BaseTypeHandler<T> {

    final SHA256Util sha256Util;

    @Autowired
    public AdminTypeHandler(SHA256Util sha256Util) {
        this.sha256Util = sha256Util;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, this.sha256Util.getSHA256((String) parameter));
    }

    @Override
    public T getNullableResult(ResultSet resultSet, String s) {
        return null;
    }

    @Override
    public T getNullableResult(ResultSet resultSet, int i) {
        return null;
    }

    @Override
    public T getNullableResult(CallableStatement callableStatement, int i) {
        return null;
    }
}
