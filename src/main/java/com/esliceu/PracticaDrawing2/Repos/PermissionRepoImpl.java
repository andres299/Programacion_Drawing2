package com.esliceu.PracticaDrawing2.Repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRepoImpl implements PermissionRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;
}
