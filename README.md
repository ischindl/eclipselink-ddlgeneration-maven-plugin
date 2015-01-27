The plugin uses eclipselink to generate DDL from JPA entities.

* [Maven Generated Site](http://boraxhacker.bitbucket.org/eclipselink-ddlgeneration-maven-plugin)

* command-line execution

    mvn process-classes

* maven settings.xml modifications

    <pluginGroups>
        ...
        <pluginGroup>us.hornerscorners.mojo</pluginGroup>
        ...
    </pluginGroups>

* maven pom.xml modifications

    <plugin>
        <groupId>us.hornerscorners.mojo</groupId>
        <artifactId>eclipselink-ddlgeneration-maven-plugin</artifactId>
        <version>1.4</version>
        <configuration>
            <jdbcUrl>jdbc:...your db connect url ...</jdbcUrl>
            <jdbcDriver>...your jdbc driver class ...</jdbcDriver>
            <jdbcUser>...your db user...</jdbcUser>
            <jdbcPassword>...your db pw...</jdbcPassword>
            <outputDir>src/main/resources/database</outputDir>
            <createFilename>schema.sql</createFilename>
            <deleteFilename>drop-schema.sql</deleteFilename>
            <unitName>...unit name in persistence.xml ...</unitName>
        </configuration>
        <executions>
            <execution>
                <id>ddl-generation</id>
                <goals>
                    <goal>execute</goal>
                </goals>
                <phase>process-classes</phase>
            </execution>
        </executions>
        <dependencies>
           <!-- jdbc driver, domain classes -->
        </dependencies>
    </plugin>

The plugin is available from the Maven Central Repository, thanks to
[Sonatype OSS Repository Hosting](https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide).