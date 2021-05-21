package domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tables", schema = "information_schema")
public class Tables implements Serializable {
    private String schema;
    private String name;
    private String engine;
    private Date createTime;
    private Date updateTime;
    private String collation;
    private String comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tables tables = (Tables) o;
        if (schema != null ? !schema.equals(tables.schema) : tables.schema != null) {
            return false;
        }
        return name != null ? name.equals(tables.name) : tables.name == null;
    }

    // TODO: 10/27/2020 根据ali规范 重写equals方法后应该重写hashcode()
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "tables{" +
                "schema='" + schema + '\'' +
                ", name='" + name + '\'' +
                ", engine='" + engine + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", collation='" + collation + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCollation() {
        return collation;
    }

    public void setCollation(String collation) {
        this.collation = collation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
