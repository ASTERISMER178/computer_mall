package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.District;

import java.util.List;

public interface DistrictService {
    List<District> getByParent(String parent);
    String getNameByCode(String code);

}
