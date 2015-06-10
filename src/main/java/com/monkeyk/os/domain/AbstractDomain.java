package com.monkeyk.os.domain;

import com.monkeyk.os.domain.shared.GuidGenerator;
import com.monkeyk.os.infrastructure.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Shengzhao Li
 */
//@MappedSuperclass
public abstract class AbstractDomain implements Serializable {

    /**
     * Database id
     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")
    protected int id;

    //    @Column(name = "archived", columnDefinition = "tinyint(1)")
    protected boolean archived;

    //    @Version
//    @Column(name = "version")
    protected int version;
    /**
     * Domain business guid.
     */
//    @Column(name = "guid", unique = true)
    protected String guid = GuidGenerator.generate();

    /**
     * The domain create time.
     */
//    @Column(name = "create_time")
//    @Type(type = "org.hibernate.type.TimestampType")
    protected Date createTime = DateUtils.now();

    public AbstractDomain() {
    }

    public int id() {
        return id;
    }

    public void id(int id) {
        this.id = id;
    }

    public boolean archived() {
        return archived;
    }

    public AbstractDomain archived(boolean archived) {
        this.archived = archived;
        return this;
    }

    public String guid() {
        return guid;
    }

    public void guid(String guid) {
        this.guid = guid;
    }

    public Date createTime() {
        return createTime;
    }

    public boolean isNewly() {
        return id == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractDomain)) {
            return false;
        }
        AbstractDomain that = (AbstractDomain) o;
        return guid.equals(that.guid);
    }

    @Override
    public int hashCode() {
        return guid.hashCode();
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{id=").append(id);
        sb.append(", archived=").append(archived);
        sb.append(", version=").append(version);
        sb.append(", guid='").append(guid).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}