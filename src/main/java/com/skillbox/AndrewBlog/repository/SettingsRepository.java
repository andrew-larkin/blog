package com.skillbox.AndrewBlog.repository;

import com.skillbox.AndrewBlog.model.GlobalSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingsRepository extends CrudRepository<GlobalSettings, Integer> {
}
