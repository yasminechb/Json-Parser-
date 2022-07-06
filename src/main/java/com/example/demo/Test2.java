package com.example.demo;
import java.util.Arrays;
public class Test2 {
    String type;
    String $ref;

    @Override
    public String toString() {
        return "Test2{" +
                "type='" + type + '\'' +
                ", $ref='" + $ref + '\'' +
                '}';
    }

    public Test2(String type, String $ref) {
        this.type = type;
        this.$ref = $ref;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String get$ref() {
		return $ref;
	}

	public void set$ref(String $ref) {
		this.$ref = $ref;
	}
    
}
