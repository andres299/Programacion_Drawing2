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

    @Override
    public boolean isTheOwnerOfDraw(int drawId, int id_user) {
        String checkPermissionsSql = "SELECT COUNT(*) FROM draw WHERE id = ? AND owner_id = ?";
        int count = jdbcTemplate.queryForObject(checkPermissionsSql, Integer.class, drawId, id_user);
        return count > 0;
    }

    @Override
    public Version getVersionById(int drawId) {
        String sql = "SELECT * FROM version WHERE id_draw = ? ORDER BY modificationDate DESC LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Version.class), drawId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}