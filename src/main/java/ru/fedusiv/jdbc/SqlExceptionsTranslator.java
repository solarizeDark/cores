package ru.fedusiv.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import java.sql.SQLException;

public class SqlExceptionsTranslator extends SQLErrorCodeSQLExceptionTranslator {

    protected DataAccessException customTranslate(String task, String sql, SQLException exception) {
        if (exception.getSQLState().equals("23502")) {
            throw new IllegalArgumentException("Not null constraint violated");
        }
        return null;
    }

}
