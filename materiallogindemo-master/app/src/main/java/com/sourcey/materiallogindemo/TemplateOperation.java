package com.sourcey.materiallogindemo;

import java.io.Serializable;
import java.util.UUID;

public class TemplateOperation implements Serializable{
    private String name;
    private MyArrayList<Operation> operations;
    private String uniqeId;
    public TemplateOperation(String _name,MyArrayList<Operation> _operation){
        name=_name;
        operations=_operation;
        uniqeId= UUID.randomUUID().toString();
    }

    public void setDetails(String _name,MyArrayList<Operation> _operation){
        name=_name;
        operations=_operation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyArrayList<Operation> getOperations() {
        return operations;
    }

    public String getName() {
        return name;
    }
}
