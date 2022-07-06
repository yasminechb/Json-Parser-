package com.example.demo;

public class Function {
	  String controller;
	    String opId;
	    Test param;
	    Test2 request;
	    Test2 response;

	    public String getController() {
	        return controller;
	    }

	    public void setController(String controller) {
	        this.controller = controller;
	    }

	    public String getOpId() {
	        return opId;
	    }

	    public void setOpId(String opId) {
	        this.opId = opId;
	    }

	    public Test getParam() {
	        return param;
	    }

	    public void setParam(Test param) {
	        this.param = param;
	    }

	    public Test2 getRequest() {
	        return request;
	    }

	    public void setRequest(Test2 request) {
	        this.request = request;
	    }

	    public Test2 getResponse() {
	        return response;
	    }

	    public void setResponse(Test2 response) {
	        this.response = response;
	    }

	    @Override
	    public String toString() {
	        return "Function{" +
	                "controller='" + controller + '\'' +
	                ", opId='" + opId + '\'' +
	                ", param=" + param +
	                ", request=" + request +
	                ", response=" + response +
	                '}';
	    }
}
