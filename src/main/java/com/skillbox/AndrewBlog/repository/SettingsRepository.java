package com.skillbox.AndrewBlog.repository;

import com.skillbox.AndrewBlog.model.GlobalSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends CrudRepository<GlobalSettings, Integer> {

    @Query(name = "select value from global_settings where name = :name")
    String getGlobalSettingsByName(@Param("name") String name);

}
