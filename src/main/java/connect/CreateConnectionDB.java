package connect;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class CreateConnectionDB {

    public CreateConnectionDB(String pathInFile) {
        setPathInFile(pathInFile);
    }

    private String pathInFile;

    public String getPathInFile() {
        return pathInFile;
    }

    public void setPathInFile(String pathInFile) {
        this.pathInFile = pathInFile;
    }

    public SessionFactory getSessionFactory() {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(getPathInFile()).build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();

        return metadata.getSessionFactoryBuilder().build();

    }
}

