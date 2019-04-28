package fr.destiny.benedict.web.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.destiny.api.model.DestinyDefinitionsDestinyInventoryItemDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class DestinyInventoryItemRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final ObjectMapper mapper;

    public DestinyInventoryItemRepository(
            @Autowired NamedParameterJdbcTemplate jdbcTemplate,
            @Autowired ObjectMapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }


    private DestinyDefinitionsDestinyInventoryItemDefinition mapJsonToItem(String json) {
        try {
            return mapper.readValue(json, DestinyDefinitionsDestinyInventoryItemDefinition.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Set<DestinyDefinitionsDestinyInventoryItemDefinition> findAll() {
        List<String> jsonRows = jdbcTemplate.queryForList(
                "SELECT json FROM DestinyInventoryItemDefinition",
                Collections.emptyMap(),
                String.class);
        return jsonRows.stream()
                .map(this::mapJsonToItem)
                .collect(Collectors.toSet());
    }
}
