package org.devnull.zuul.web.security

import org.devnull.zuul.data.dao.EnvironmentDao
import org.devnull.zuul.data.dao.SettingsDao
import org.devnull.zuul.data.dao.SettingsEntryDao
import org.devnull.zuul.data.dao.SettingsGroupDao
import org.devnull.zuul.data.model.Environment
import org.devnull.zuul.data.model.SettingsGroup
import org.devnull.zuul.web.test.ZuulWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

abstract class SecurityWebIntegrationTest extends ZuulWebIntegrationTest {

    @Autowired
    EnvironmentDao environmentDao

    @Autowired
    SettingsGroupDao settingsGroupDao

    @Autowired
    SettingsEntryDao settingsEntryDao

    @Autowired
    SettingsDao settingsDao


    protected SettingsGroup findRestrictedGroup() {
        def settings = settingsDao.findByName("app-data-config")
        def group = settingsGroupDao.findBySettingsAndEnvironment(settings, new Environment(name: "prod"))
        assert group.environment.restricted
        return group
    }

    protected SettingsGroup findUnRestrictedGroup() {
        def settings = settingsDao.findByName("app-data-config")
        def group = settingsGroupDao.findBySettingsAndEnvironment(settings, new Environment(name: "dev"))
        assert !group.environment.restricted
        return group
    }

    protected Environment findRestrictedEnvironment() {
        def env = environmentDao.findOne("prod")
        assert env.restricted
        return env
    }

    protected Environment findUnRestrictedEnvironment() {
        def env = environmentDao.findOne("dev")
        assert !env.restricted
        return env
    }

}
