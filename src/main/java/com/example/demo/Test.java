package com.example.demo;

public class Test {
	 String name;

	    Test2 schema;

	    public Test(String name, Test2 schema) {
	        this.name = name;
	        this.schema = schema;
	    }

	    @Override
	    public String toString() {
	        return "Test{" +
	                "name='" + name + '\'' +
	                ", schema=" + schema +
	                '}';
	    }
}
