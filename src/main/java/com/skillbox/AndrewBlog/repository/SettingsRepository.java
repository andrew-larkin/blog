package com.Skillbox.AndrewBlog.repository;

import com.Skillbox.AndrewBlog.model.GlobalSettings;
import org.springframework.data.repository.CrudRepository;

public interface SettingsRepository extends CrudRepository<GlobalSettings, Integer> {
}
