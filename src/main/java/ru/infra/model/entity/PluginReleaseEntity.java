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
@Table(name = "plugin_release")
@Getter
@Setter
public class PluginReleaseEntity {

    @Id
    @Column(name = "plugin_nick", nullable = false)
    private String pluginNick;

    @Column(name = "plugin_type", nullable = false)
    private String pluginType;

    @Column(name = "version")
    private String version;

    @Column(name = "plugin_name")
    private String pluginName;

    @Column(name = "storage_id")
    private UUID storageId;

    @Convert(converter = StringListJsonConverter.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dependency_tags", columnDefinition = "jsonb")
    private List<String> dependencyTags;

}