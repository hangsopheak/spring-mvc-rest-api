package com.rupp.spring.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.rupp.spring.domain.DCategory;

@Repository
public class CategoryDaoImpl {
    // Dummy database. Initialize with some dummy values.
    private static List<DCategory> categories = new ArrayList<>();

    static {
        categories.add(new DCategory(101L, "Restaurant"));
        categories.add(new DCategory(201L, "Food and Drink"));
        categories.add(new DCategory(301L, "Out Door"));
        categories.add(new DCategory(302L, null));
    }

    /**
     * Returns list of categories from dummy database.
     * 
     * @return list of categories
     */
    public List<DCategory> list() {
        return categories;
    }

    /**
     * Return dCategory object for given id from dummy database. If dCategory is not found for id, returns null.
     * 
     * @param id
     *            dCategory id
     * @return dCategory object for given id
     */
    public DCategory get(Long id) {

        for(DCategory c : categories) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Create new dCategory in dummy database. Updates the id and insert new dCategory in list.
     * 
     * @param dCategory
     *            DCategory object
     * @return dCategory object with updated id
     */
    public DCategory create(DCategory dCategory) {
        dCategory.setId(System.currentTimeMillis());
        categories.add(dCategory);
        return dCategory;
    }

    /**
     * Delete the dCategory object from dummy database. If dCategory not found for given id, returns null.
     * 
     * @param id
     *            the dCategory id
     * @return id of deleted dCategory object
     */
    public Long delete(Long id) {

        for(DCategory c : categories) {
            if (c.getId().equals(id)) {
                categories.remove(c);
                return id;
            }
        }

        return null;
    }

    /**
     * Update the dCategory object for given id in dummy database. If dCategory not exists, returns null
     * 
     * @param id
     * @param dCategory
     * @return dCategory object with id
     */
    public DCategory update(Long id, DCategory dCategory) {

        for(DCategory c : categories) {
            if (c.getId().equals(id)) {
                dCategory.setId(c.getId());
                categories.remove(c);
                categories.add(dCategory);
                return dCategory;
            }
        }

        return null;
    }

}
