package com.example.data;

import com.example.data.common.DataUtils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class DataUtilsTest {

    @Test
    public void generateRandomId_Called5Times_ReturnsTrue() {

        //  Saves 5 random ids in String id and compares if they
        //  are distinct
        int howManyIds = 5;
        final String ids[] = new String[howManyIds];

        for (int i = 0; i < howManyIds; i++) {
            ids[i] = DataUtils.generateRandomId();
        }

        final Set<String> uniqueIds = new HashSet<>();
        uniqueIds.addAll(Arrays.asList(ids));

        //  If both sizes are same, we have distinct values.
        assertEquals(ids.length, uniqueIds.size());
    }

    @Test
    public void getCurrentDate_CompareWithCurrentDate_ReturnsTrue() {

        final Date currentDate = new Date();
        final Date dateFromUtils = DataUtils.getCurrentDate();
        assertEquals(currentDate, dateFromUtils);
    }

}
