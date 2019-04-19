package fr.destiny.vacuum.web.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class DestinyInventoryItemRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper mapper;

    public DestinyDefinitionsDestinyInventoryItemDefinition findById(long hash) {
        int id = (int) hash;

        String json = jdbcTemplate.queryForObject(
                "SELECT json FROM DestinyInventoryItemDefinition WHERE id = ?",
                new Object[]{id},
                String.class);
        try {
            return mapper.readValue(json, DestinyDefinitionsDestinyInventoryItemDefinition.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
