package com.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.apiori.AssociationRule;
import com.music.entity.Rule;

public interface RuleRepository extends JpaRepository<Rule, Long>{

}
