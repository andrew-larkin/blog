package com.skillbox.AndrewBlog.repository;

import com.skillbox.AndrewBlog.model.CaptchaCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CaptchaRepository extends JpaRepository<CaptchaCode, Integer> {

    List<CaptchaCode> findByTimeBefore(Date time);

    Optional<CaptchaCode> findSecretCodeByCode(String code);
}
