package domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "server", schema = "ITD_MGT")
public class Server {
    private String ipadress;
    private String cloud;

    @Override
    public String toString() {
        return "Server{" +
                "ipadress='" + ipadress + '\'' +
                ", cloud='" + cloud + '\'' +
                '}';
    }

    public String getIpadress() {
        return ipadress;
    }

    public void setIpadress(String ipadress) {
        this.ipadress = ipadress;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }
}
