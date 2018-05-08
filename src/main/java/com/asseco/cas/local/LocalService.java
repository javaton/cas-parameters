package com.asseco.cas.local;

import com.asseco.cas.parameters.domain.ParameterItem;
import com.asseco.cas.parameters.domain.ParameterList;
import com.asseco.cas.parameters.domain.SystemParameterList;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class LocalService {

    static ParameterList parameterList = new SystemParameterList();
    static ParameterList parameterList2 = new SystemParameterList();
    static List<ParameterList> parameterValuesList = new ArrayList<>();
    private static LocalService localService = null;


    public ParameterList getParameterList() {
        return parameterList;
    }

    public void setParameterList(ParameterList parameterList) {
        this.parameterList = parameterList;
    }

    public ParameterList getParameterList2() {
        return parameterList2;
    }

    public void setParameterList2(ParameterList parameterList2) {
        this.parameterList2 = parameterList2;
    }

    public List<ParameterList> getParameterValuesList() {
        return parameterValuesList;
    }

    public void setParameterValuesList(List<ParameterList> parameterValuesList) {
        this.parameterValuesList = parameterValuesList;
    }


    public static LocalService getLocalService(){

        if(localService == null) {
            localService = new LocalService();

            parameterList.setId((long) 1);
            parameterList.setName("ListName");
            parameterList.setStateCode(ParameterList.ParameterListEnum.INITIAL);
            parameterList.setVersion((long) 123456);

            parameterList2.setId((long) 2);
            parameterList2.setName("ListTwoName");
            parameterList2.setStateCode(ParameterList.ParameterListEnum.ACTIVE);
            parameterList2.setVersion((long) 654321);




            Set<ParameterItem> set = new HashSet<>();
            Set<ParameterItem> set2 = new HashSet<>();

            ParameterItem items[];

            InputStream is = LocalService.class.getClassLoader().getResourceAsStream("MOCK_DATA.json");
            if (is != null) {
                ObjectMapper objectMapper = new ObjectMapper();

                try {
                    items = objectMapper.readValue(is, ParameterItem[].class);
                    Collections.addAll(set, items);
                    Collections.addAll(set2, items);
                    parameterList.setParameterItems(set);
                    parameterList2.setParameterItems(set2);
                } catch (IOException e) {
                    System.out.print(e.getMessage());
                }
            }

            parameterValuesList.add(parameterList);
            parameterValuesList.add(parameterList2);
        }

        return localService;

    }

    private LocalService() {    }
}
