package ru.infra.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.infra.converter.StringListJsonConverter;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "agent_release")
@Getter
@Setter
public class AgentReleaseEntity {

    @Id
    @Column(name = "agent_type", nullable = false)
    private String agentType;

    @Column(name = "version")
    private String version;

    @Column(name = "release_note")
    private String releaseNote;

    @Column(name = "storage_id")
    private UUID storageId;

    @Convert(converter = StringListJsonConverter.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "def_dependency_tags", columnDefinition = "jsonb")
    private List<String> defDependencyTags;

}