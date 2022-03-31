package ru.fedusiv.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeopleJdbcRepository {

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PeopleJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public PeopleJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // language=SQL
    private String SQL_INSERT = "insert into people(name, surname, patronymic, age) values (?, ?, ?, ?)";

    // language=SQL
    private String SQL_UPDATE = "update people set name = ?, surname = ?, patronymic = ?," +
            "age = ? where id = ?";

    //language=SQL
    private String SQL_DELETE = "delete from people where id = ?";

    //language=SQL
    private String SQL_SELECT_ALL = "select * from people";

    //language=SQL
    private String SQL_SELECT_ALL_UPPER_X = "select * from people where age > ?";

    //language=SQL
    private String SQL_GET_ALL_WITH_NAME = "select * from people where name = :name";

    public void save(Person person) {
        jdbcTemplate.update(SQL_INSERT, person.getName(), person.getSurname(),
                person.getPatronymic(), person.getAge());
    }

    public void update(Person person) {
        jdbcTemplate.update(SQL_UPDATE, person.getName(), person.getSurname(),
                person.getPatronymic(), person.getAge(), person.getId());
    }

    public void delete(Person person) {
        jdbcTemplate.update(SQL_DELETE, person.getId());
    }

    private RowMapper<Person> rowMapper = (row, rowNum) -> Person.builder()
            .id(row.getLong("id"))
            .age(row.getInt("age"))
            .name(row.getString("name"))
            .surname(row.getString("surname"))
            .patronymic(row.getString("patronymic"))
            .build();

    public List<Person> getAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    public List<Person> getAllAdults(int threshold) {
        return jdbcTemplate.query(SQL_SELECT_ALL_UPPER_X, rowMapper, threshold);
    }

    public List<Person> getAllWithName(String name) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        return namedParameterJdbcTemplate.query(SQL_GET_ALL_WITH_NAME, params, rowMapper);
    }

}
