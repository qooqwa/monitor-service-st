package ru.infra.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.infra.api.monitor.model.dto.PluginRelease;
import ru.infra.converter.PluginReleaseListJsonConverter;
import ru.infra.converter.StringListJsonConverter;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "agent")
@Getter
@Setter
public class Agent {

    @Id
    @Column(name = "agent_id", nullable = false)
    private UUID agentId;

    @Column(name = "agent_nick", nullable = false)
    private String agentNick;

    @Column(name = "group_nick")
    private String groupNick;

    @Column(name = "agent_note")
    private String agentNote;

    @Convert(converter = StringListJsonConverter.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dependency_tags", columnDefinition = "jsonb")
    private List<String> dependencyTags;

    @Convert(converter = PluginReleaseListJsonConverter.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "plugin_list", columnDefinition = "jsonb")
    private List<PluginRelease> pluginList;
}

