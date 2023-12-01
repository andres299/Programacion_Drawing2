package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VersionRepoImpl implements VersionRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public void saveVersion(Version version) {
        jdbcTemplate.update("INSERT INTO version (id_draw, figures, numFigures, modificationDate,id_user) VALUES (?, ?, ?, NOW(),?)",
                version.getId_draw(),version.getFigures(), version.getNumFigures() ,version.getId_user()
        );
    }
    

}
