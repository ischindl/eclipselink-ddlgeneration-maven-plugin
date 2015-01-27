/*
 * #%L
 * EclipseLink DDL Generation Maven Plugin
 * %%
 * Copyright (C) 2012 - 2015 Jim Horner
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package us.hornerscorners.mojo.eclipselink;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;

/**
 * Class description
 *
 *
 * @version        v1.0, 2012-07-27
 * @author         Jim Horner
 */
public class DDLGenerator extends Thread {

    /** Field description */
    private final Map<String, Object> properties;

    /** Field description */
    private final String unitName;

    /**
     * Constructs ...
     *
     *
     *
     * @param unitName
     * @param cl
     */
    public DDLGenerator(String unitName, ClassLoader cl) {

        super();
        this.unitName = unitName;
        this.properties = buildProperties();

        setContextClassLoader(cl);
    }

    /**
     * Method description
     *
     *
     * @return
     */
    private Map<String, Object> buildProperties() {

        Map<String, Object> props = new HashMap<String, Object>();

        // override with local transaction
        props.put(PersistenceUnitProperties.TRANSACTION_TYPE, "RESOURCE_LOCAL");
        props.put(PersistenceUnitProperties.JTA_DATASOURCE, null);
        props.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, null);
        props.put(PersistenceUnitProperties.VALIDATION_MODE, "NONE");

        // Enable DDL Generation
        props.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties
            .DROP_AND_CREATE);
        props.put(PersistenceUnitProperties.DDL_GENERATION_MODE, PersistenceUnitProperties
            .DDL_SQL_SCRIPT_GENERATION);

        return props;
    }

    /**
     * Method description
     *
     */
    @Override
    public void run() {

        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {

            emf = Persistence.createEntityManagerFactory(this.unitName, this.properties);

            try {

                em = emf.createEntityManager();

            } finally {

                if (em != null) {
                    em.close();
                }
            }

        } finally {

            if (emf != null) {
                emf.close();
            }
        }
    }

    /**
     * Method description
     *
     *
     * @param val
     */
    public void setCreateFile(String val) {

        this.properties.put(PersistenceUnitProperties.CREATE_JDBC_DDL_FILE, val);
    }

    /**
     * Method description
     *
     *
     * @param val
     */
    public void setDeleteFile(String val) {

        this.properties.put(PersistenceUnitProperties.DROP_JDBC_DDL_FILE, val);
    }

    /**
     * Method description
     *
     *
     * @param val
     */
    public void setJdbcDriver(String val) {
        this.properties.put(PersistenceUnitProperties.JDBC_DRIVER, val);
    }

    /**
     * Method description
     *
     *
     * @param val
     */
    public void setJdbcPassword(String val) {

        this.properties.put(PersistenceUnitProperties.JDBC_PASSWORD, val);
    }

    /**
     * Method description
     *
     *
     * @param val
     */
    public void setJdbcUrl(String val) {

        this.properties.put(PersistenceUnitProperties.JDBC_URL, val);
    }

    /**
     * Method description
     *
     *
     * @param val
     */
    public void setJdbcUser(String val) {

        this.properties.put(PersistenceUnitProperties.JDBC_USER, val);
    }

    /**
     * Method description
     *
     *
     * @param dir
     */
    public void setOutputDir(File dir) {

        if (dir.exists() == false) {

            dir.mkdirs();
        }

        this.properties
            .put(PersistenceUnitProperties.APP_LOCATION, dir.getAbsolutePath());
    }
}
