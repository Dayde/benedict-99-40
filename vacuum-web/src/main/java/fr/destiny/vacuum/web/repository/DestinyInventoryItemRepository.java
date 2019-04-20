package fr.destiny.vacuum.web.repository;

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

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper mapper;

    public DestinyDefinitionsDestinyInventoryItemDefinition findById(long hash) {
        int id = (int) hash;

        String json = jdbcTemplate.queryForObject(
                "SELECT json FROM DestinyInventoryItemDefinition WHERE id = :id",
                Collections.singletonMap("id", id),
                String.class);
        return mapJsonToItem(json);
    }

    public Set<DestinyDefinitionsDestinyInventoryItemDefinition> findAllById(Set<Long> hashes) {
        Set<Integer> ids = hashes.stream()
                .map(Long::intValue)
                .collect(Collectors.toSet());

        List<String> jsonRows = jdbcTemplate.queryForList(
                "SELECT json FROM DestinyInventoryItemDefinition WHERE id in (:ids)",
                Collections.singletonMap("ids", ids),
                String.class);
        return jsonRows.stream()
                .map(this::mapJsonToItem)
                .collect(Collectors.toSet());
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
