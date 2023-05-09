package org.github.xx;

import org.springframework.stereotype.Component;

import java.io.Serializable;


public class Test implements Serializable {
    private static final long serialVersionUID = -5021582605621235663L;
    private String a;

    public Test() {
    }

    public Test(String a) {
        this.a = a;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }
}
